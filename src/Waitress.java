/**
 * This class implements the consumer part of the producer/consumer problem.
 * One waitress instance corresponds to one consumer.
 */
public class Waitress implements Runnable {

    private WaitingArea waitingArea;

    /**
     * Creates a new waitress. Make sure to save the parameter in the class
     *
     * @param waitingArea The waiting area for customers
     */
    Waitress(WaitingArea waitingArea) {
        this.waitingArea = waitingArea;
    }

    /**
     * This is the code that will run when a new thread is
     * created for this instance
     */
    @Override
    public void run() {
        Customer customer = null;
        while (SushiBar.isOpen) {
            customer = waitingArea.next();
            while (customer == null) {
                waitForArea();
                customer = waitingArea.next();
            }
            synchronized (this.waitingArea) {
                customer.order();
            }
            SushiBar.sleep(SushiBar.waitressWait + SushiBar.random(0, 50));
            SushiBar.write("Customer #" + customer.getCustomerID() + " is now eating.");
            SushiBar.sleep(SushiBar.customerWait + SushiBar.random(0, 150));
            SushiBar.write("Customer #" + customer.getCustomerID() + " is now leaving.");
        }
        if (customer != null) {
            if (SushiBar.lastCustomer.get() == customer.getCustomerID()) {
                SushiBar.write("Total orders: " + (SushiBar.takeawayOrders.get() + SushiBar.servedOrders.get()));
                SushiBar.write("Served orders: " + (SushiBar.servedOrders.get()));
                SushiBar.write("Takeaway orders: " + (SushiBar.takeawayOrders.get()));
            }
        }
    }

    public void waitForArea() {
        synchronized (this.waitingArea) {
            try {
                this.waitingArea.wait();
            } catch (Exception e) {

            }
        }
    }


}
