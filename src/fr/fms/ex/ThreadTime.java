package fr.fms.ex;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadTime {

  public static void main(String[] args) {
    Thread thread = new Thread(new MonRunnable(1000));
    thread.start();
  }

  private static class MonRunnable implements Runnable {

    private final long delai;
    private volatile boolean running = true;

    public MonRunnable(long delai)  {
        this.delai = delai;
    }

      public void stop() {
          running = false;
      }

    @Override
    public void run() {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");

    	while(running) {
	      try {
              System.out.print("\r" + df.format(new Date()));

	        Thread.sleep(delai);

	      } catch (InterruptedException e) {
              System.err.println("\nThread interrompu: " + e.getMessage());
              Thread.currentThread().interrupt();
              stop();
	      }
    	}
        System.out.println("\nArrêt de l'horloge");
    }
  }
}