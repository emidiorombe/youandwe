package br.com.yaw.exception;

public class SenhaInvalidaException extends Exception {

	public SenhaInvalidaException() {
	}

	public SenhaInvalidaException(String message) {
		super(message);
	}

	public SenhaInvalidaException(Throwable cause) {
		super(cause);
	}

	public SenhaInvalidaException(String message, Throwable cause) {
		super(message, cause);
	}

}
