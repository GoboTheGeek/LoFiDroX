package ch.gobothegeek.lofidrox.controllers;

import ch.gobothegeek.lofidrox.model.entities.FileRecipient;
import ch.gobothegeek.lofidrox.model.json.file.*;
import ch.gobothegeek.lofidrox.security.LfdUserBean;
import ch.gobothegeek.lofidrox.security.urls.LfdSecuredUrl;
import ch.gobothegeek.lofidrox.services.FileDescriptorService;
import ch.gobothegeek.lofidrox.services.FileRecipientService;
import ch.gobothegeek.lofidrox.utils.GtgDateUtils;
import org.apache.deltaspike.security.api.authorization.Secured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

// Manage access point in file package
@RequestScoped
@Path("/file")
@Named
public class FileController {
	private final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Inject	private LfdUserBean lfdUserBean;
	@Inject private FileDescriptorService fileDescriptorService;
	@Inject private FileRecipientService fileRecipientService;

	// url to upload file for users
	@POST
	@Path("/send")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Secured(LfdSecuredUrl.class)
	public Response uploadFile(JsonFileUpload jsonFile) {
		JsonFileUploaded reply = new JsonFileUploaded();

		if ((null != jsonFile.getFilename()) && (null != jsonFile.getUsers()) && (0 != jsonFile.getUsers().length) && (null != jsonFile.getData())) {
			reply.setFilename(jsonFile.getFilename());
			reply.setWritten(this.fileDescriptorService.uploadFileToUsers(jsonFile.getUsers(), jsonFile.getFilename(), jsonFile.getData(),
					this.lfdUserBean.getLfdUser().getUser()));
		}
		// return response
		return Response.status((reply.getWritten()? Response.Status.OK: Response.Status.BAD_REQUEST)).entity(reply).build();
	}

	// list files for user
	@POST
	@Path("/list")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Secured(LfdSecuredUrl.class)
	public Response listFiles() {
		JsonFilesList reply;
		List<FileRecipient> files;

		reply = new JsonFilesList();
		files = this.fileRecipientService.listFilesForUser(this.lfdUserBean.getLfdUser().getUser());
		if ((null != files) && (0 != files.size())) {
			for (FileRecipient file : files) {
				if (null != file.getFile()) {
					reply.getFiles().add(new JsonFileInfo(file.getFileId(), file.getFile().getName(), !file.getDownloaded(), file.getFile().getSource(),
							GtgDateUtils.FORMAT_DATE_DDMMYYYYHHMMSS.format(file.getFile().getSendOn())));
				}
			}
		}
		return Response.status(Response.Status.OK).entity(reply).build();
	}

	// return the required file as Base64 stream
	@POST
	@Path("/download")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Secured(LfdSecuredUrl.class)
	public Response downloadFile(JsonFileInfo jsonFileInfo) {
		JsonFileDownload jsonDL;

		jsonDL = this.fileDescriptorService.downloadFile(jsonFileInfo.getId(), this.lfdUserBean.getLfdUser().getUser());
		if (null != jsonDL) {
				return Response.status(Response.Status.OK).entity(jsonDL).build();
		} else {
			logger.error("Unable to fetch file with id [" + jsonFileInfo.getId() + "] for user [" + this.lfdUserBean.getLfdUser().getUser() + "]");
		}
		return Response.status(Response.Status.BAD_REQUEST).entity(null).build();
	}

	// delete the specified file. The file must be owned by user calling this url
	@DELETE
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Secured(LfdSecuredUrl.class)
	public Response deleteFiles(JsonFilesDelete jsonDel) {
		JsonFilesDeleted jsonRepDel;
		int deleted;

		jsonRepDel = new JsonFilesDeleted();
		if ((null != jsonDel.getFiles()) && (0 != jsonDel.getFiles().size())) {
			deleted = this.fileDescriptorService.deleteFiles(jsonDel.getFiles(), this.lfdUserBean.getLfdUser().getUser());
			jsonRepDel.setDeletedCount(deleted);
		}
		return Response.status((-1 < jsonRepDel.getDeletedCount()? Response.Status.OK : Response.Status.BAD_REQUEST)).entity(jsonRepDel).build();
	}
}
