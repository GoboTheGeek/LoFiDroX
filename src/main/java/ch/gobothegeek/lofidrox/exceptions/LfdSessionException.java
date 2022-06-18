package ch.gobothegeek.lofidrox.exceptions;

// exception specialized for session errors
public class LfdSessionException extends LfdException {
	public LfdSessionException(String message) {
		super(message);
	}
}
