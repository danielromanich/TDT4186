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
            if (servingArea.getCustomerCount() < servingArea.getAreaSize() && SushiBar.isOpen)
                notifyCustomer();
            counter++;
            sleep();
        }
        SushiBar.write("***** NO MORE CUSTOMERS - THE SHOP IS CLOSED NOW. *****");
    }

    private void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep((int) Math.floor(Math.random() * DOOR_INTERVAL));
        } catch (Exception e) {

        }
    }

    private void notifyCustomer() {
        try {
            synchronized (servingArea) {
                servingArea.notify();
            }
        } catch(Exception e) {

        }
    }

}
