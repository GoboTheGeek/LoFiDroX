package ch.gobothegeek.lofidrox.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@RequestScoped
public class RequestService {
	private final Logger logger = LoggerFactory.getLogger(RequestService.class);

	@Inject	private HttpServletRequest request;

	public String getRemoteAddress() {
		return request.getRemoteAddr();
	}
}

