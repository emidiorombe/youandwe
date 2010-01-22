package br.com.yaw.exception;

/**
 * Exception of repository operations
 * @author Rafael Nunes
 *
 */
public class RepositoryException extends Exception {

	public RepositoryException() {
		super();
	}

	public RepositoryException(String message, Throwable cause) {
		super(message, cause);
	}

	public RepositoryException(String message) {
		super(message);
	}

	public RepositoryException(Throwable cause) {
		super(cause);
	}
}
