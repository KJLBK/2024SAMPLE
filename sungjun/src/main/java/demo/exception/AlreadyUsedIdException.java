package demo.exception;

public class AlreadyUsedIdException extends RuntimeException {

	public AlreadyUsedIdException() {
		super();
	}

	public AlreadyUsedIdException(String message) {
		super(message);
	}

	public AlreadyUsedIdException(Throwable cause) {
		super(cause);
	}

	public AlreadyUsedIdException(String message, Throwable cause) {
		super(message, cause);
	}

}
