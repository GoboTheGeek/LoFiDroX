package ch.gobothegeek.lofidrox.controllers;

import ch.gobothegeek.lofidrox.exceptions.LfdException;
import ch.gobothegeek.lofidrox.model.entities.User;
import ch.gobothegeek.lofidrox.model.json.user.*;
import ch.gobothegeek.lofidrox.security.LfdSecured;
import ch.gobothegeek.lofidrox.security.urls.LfdSecuredUrl;
import ch.gobothegeek.lofidrox.security.urls.LfdUnsecuredUrl;
import ch.gobothegeek.lofidrox.services.SessionService;
import ch.gobothegeek.lofidrox.services.UserService;
import org.apache.deltaspike.security.api.authorization.AccessDeniedException;
import org.apache.deltaspike.security.api.authorization.Secured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// Manage access point in user package
@RequestScoped
@Path("/user")
@Named
public class UserController {
	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Inject private UserService userService;
	@Inject private SessionService sessionService;

	// url to log in user
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Secured(LfdUnsecuredUrl.class)
	public Response loginUser(JsonUserLogin nameLog) {
		Boolean isOk;

		// requires user login
		isOk = this.userService.loginUser(nameLog.getUsername(), nameLog.getPwd());
		// clear password
		nameLog.setPwd(null);
		// add status
		nameLog.setLogged(isOk);
		// return response
		return Response.status((isOk? Response.Status.OK: Response.Status.UNAUTHORIZED)).entity(nameLog).build();
	}

	// url to log out user
	@DELETE
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Secured(LfdSecuredUrl.class)
	@LfdSecured
	public Response logoutUser(JsonUserLogout nameLog) {
		// ask for log out
		this.userService.logoffUser(nameLog.getUsername());
		// add status
		nameLog.setExited(true);
		// return response
		return Response.status(Response.Status.OK).entity(nameLog).build();
	}

	// url to register user account
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerUser(JsonUserRegister nameReg) {
		User reg;

		try {
			// require user creation
			reg = this.userService.createUser(nameReg.getUsername(), nameReg.getPwd());
			if (null != reg) {
				// user is registered
				nameReg.setRegistered(true);
				// clear password
				nameReg.setPwd(null);
				// return OK response
				return Response.status(Response.Status.OK).entity(nameReg).build();
			}
			// user is not registerd because it already exists
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(null).build();
		} catch (LfdException e) {
			// user is not registerd due to technical error
			return Response.status(Response.Status.BAD_REQUEST).entity(null).build();
		}
	}

	// url used to check if user has a valid session
	@POST
	@Path("/check")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkUserSession(JsonUserCheck userCheck) {
		userCheck.setSession(false);
		try {
			// add status
			userCheck.setSession(this.securedCheckUserSession(userCheck.getUsername()));
			// return response
		} catch (AccessDeniedException e) {
			logger.error("User [" + userCheck.getUsername() + "] is not logged in.", e);
			this.sessionService.delete(userCheck.getUsername()); // remove session
		}
		return Response.status((userCheck.getSession()? Response.Status.OK : Response.Status.UNAUTHORIZED)).entity(userCheck).build();
	}

	// we have to handle a call through secured method to check if user is really logged or not
	@Secured(LfdSecuredUrl.class)
	@LfdSecured
	private boolean securedCheckUserSession(String username) {
		return this.sessionService.hasSession(username);
	}

	// url used to return user's list
	@POST
	@Path("/list")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Secured(LfdSecuredUrl.class)
	@LfdSecured
	public Response getUsersList() {
		JsonUsersList json = new JsonUsersList();
		json.setUsers(this.userService.listUsernames());
		// return response
		return Response.status(Response.Status.OK).entity(json).build();
	}
}
