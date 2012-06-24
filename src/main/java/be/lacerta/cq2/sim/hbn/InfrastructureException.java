package be.lacerta.cq2.sim.hbn;

public class InfrastructureException
extends RuntimeException {

public InfrastructureException() {
}

public InfrastructureException(String message) {
	super(message);
}

public InfrastructureException(String message, Throwable cause) {
	super(message, cause);
}

public InfrastructureException(Throwable cause) {
	super(cause);
}
}
