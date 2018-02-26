/**
 * This class implements the Door component of the sushi bar assignment
 * The Door corresponds to the Producer in the producer/consumer problem
 */
public class Door implements Runnable {

    private WaitingArea waitingArea;
    private int counter;

    /**
     * Creates a new Door. Make sure to save the
     * @param waitingArea   The customer queue waiting for a seat
     */
    public Door(WaitingArea waitingArea) {
        this.waitingArea = waitingArea;
    }

    /**
     * This method will run when the door thread is created (and started)
     * The method should create customers at random intervals and try to put them in the customerQueue
     */
    @Override
    public void run() {
        while(SushiBar.isOpen) {
            Customer customer = new Customer(counter);
            counter++;
            if (waitingArea.getCustomerCount() >= waitingArea.getSize())
                waitForArea();
            waitingArea.enter(customer);
            SushiBar.sleep(SushiBar.doorWait + SushiBar.random(0, 250));
        }
        SushiBar.write("***** NO MORE CUSTOMERS - THE SHOP IS CLOSED NOW. *****");
    }

    public void waitForArea() {
        synchronized (this.waitingArea) {
            try {
                this.waitingArea.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Add more methods as you see fit
}
