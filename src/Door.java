import java.util.concurrent.TimeUnit;

public class Door extends Thread {

    public static final int DOOR_INTERVAL = 500; //Longest time between customers

    private int counter = 0;
    private ServingArea servingArea;

    public Door(ServingArea servingArea) {
        this.servingArea = servingArea;
    }

    public void run() {
        while (SushiBar.isOpen) {
            Customer customer = new Customer(counter, servingArea);
            SushiBar.write("Customer #" + customer.getID() + " is now created.");
            new Thread(customer).start();
            counter++;
            SushiBar.sleep(TimeUnit.MILLISECONDS, (int) Math.floor(Math.random() * DOOR_INTERVAL));
        }
        SushiBar.write("***** NO MORE CUSTOMERS - THE SHOP IS CLOSED NOW. *****");
        synchronized (servingArea) {
            servingArea.notifyAll();
        }
    }

}
