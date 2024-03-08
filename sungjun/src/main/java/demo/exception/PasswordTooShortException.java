package demo.exception;

public class PasswordTooShortException extends RuntimeException {

	public PasswordTooShortException() {
		super();
	}

	public PasswordTooShortException(String message) {
		super(message);
	}

	public PasswordTooShortException(Throwable cause) {
		super(cause);
	}

	public PasswordTooShortException(String message, Throwable cause) {
		super(message, cause);
	}

}
