
public class Door extends Thread {

    public static final int DOOR_INTERVAL = 100; //Longest time between customers

    private int counter = 0;
    private ServingArea servingArea;

    public Door(ServingArea servingArea) {
        this.servingArea = servingArea;
    }

    public int getCounter() {
        return counter;
    }

    public void run() {
        while (SushiBar.isOpen) {
            Customer customer = new Customer(counter, servingArea);
            new Thread(customer).run();
            System.out.println("Customer #" + customer.getID() + " is now created.");
            if (servingArea.getCustomerCount() < servingArea.getAreaSize())
                notifyCustomer();
            counter++;
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep((int) Math.floor(Math.random() * DOOR_INTERVAL));
        } catch (Exception e) {

        }
    }

    private void notifyCustomer() {
        try {
            synchronized (servingArea) {
                notify();
            }
        } catch(Exception e) {

        }
    }

}
