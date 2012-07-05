package be.lacerta.cq2.utils;

public interface ThreadedService extends Runnable {  
    /** 
    * Starts the thread. This resets the counter for execution count and the uptime date stamp. 
    */  
    public void startThread();  
   
  
    /** 
    * Stops the thread. This resets the down time date stamp. 
    */  
    public void stopThread();  
      
    /** 
    * The thing that is to be executed within this thread. This is the body of the the threaded service, and is required to be implemented by the child class. 
    */  
    public void service();  
      
    /** 
     * Queries if this thread is currently running. 
     * 
     * @return The boolean state of the thread. 
     */  
    boolean getRunning ();  
}  