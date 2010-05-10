package br.com.yaw.exception;

public class UsuarioExistenteException extends RuntimeException {

	public UsuarioExistenteException() {
		super();
	}

	public UsuarioExistenteException(String message, Throwable cause) {
		super(message, cause);
	}

	public UsuarioExistenteException(String message) {
		super(message);
	}

	public UsuarioExistenteException(Throwable cause) {
		super(cause);
	}
	
}
