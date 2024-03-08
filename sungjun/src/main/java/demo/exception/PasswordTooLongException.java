package demo.exception;

public class PasswordTooLongException extends RuntimeException {

	public PasswordTooLongException() {
		super();
	}

	public PasswordTooLongException(String message) {
		super(message);
	}

	public PasswordTooLongException(Throwable cause) {
		super(cause);
	}

	public PasswordTooLongException(String message, Throwable cause) {
		super(message, cause);
	}

}
