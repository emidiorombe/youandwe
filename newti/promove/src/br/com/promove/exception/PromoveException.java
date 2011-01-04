package br.com.promove.exception;

public class PromoveException extends Exception {

	public PromoveException() {
		super();
	}

	public PromoveException(String message, Throwable cause) {
		super(message, cause);
	}

	public PromoveException(String message) {
		super(message);
	}

	public PromoveException(Throwable cause) {
		super(cause);
	}
	
}
