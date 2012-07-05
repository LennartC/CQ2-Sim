package be.lacerta.cq2.sim;

public class SimException extends RuntimeException {
	private static final long serialVersionUID = -2657784590358253158L;

	public SimException(String message) {
		super(message);
	}
	
	public SimException(String message, Throwable cause) {
		super(message, cause);
	}
}
