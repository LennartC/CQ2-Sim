package be.lacerta.cq2.sim.messaging;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public enum Executor {
	INSTANCE(Executors.newSingleThreadExecutor());
	
	private ExecutorService service;
	
	
	private Executor(ExecutorService service) {
		this.service = service;
	}
	
	public ExecutorService getService() {
		return service;
	}
}
