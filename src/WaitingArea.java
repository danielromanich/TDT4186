import java.util.ArrayList;

/**
 * This class implements a waiting area used as the bounded buffer, in the producer/consumer problem.
 */
public class WaitingArea {

    private ArrayList<Customer> customerList = new ArrayList<>();
    private int size;

    /**
     * Creates a new waiting area.
     * areaSize decides how many people can be waiting at the same time (how large the shared buffer is)
     *
     * @param size The maximum number of Customers that can be waiting.
     */
    public WaitingArea(int size) {
        this.size = size;
    }

    /**
     * This method should put the customer into the waitingArea
     *
     * @param customer A customer created by Door, trying to enter the waiting area
     */
    public synchronized void enter(Customer customer) {
        SushiBar.write("Customer #" + customer.getCustomerID() + " is now waiting.");
        this.customerList.add(customer);
        if (getCustomerCount() < getSize())
            this.notify();
    }

    /**
     * @return The customer that is first in line.
     */
    public synchronized Customer next() {
        if (getCustomerCount() > 0) {
            Customer customer = this.customerList.remove(0);
            SushiBar.write("Customer #" + customer.getCustomerID() + " is now fetched.");
            SushiBar.lastCustomer.set(customer.getCustomerID());
            this.notifyAll();
            return customer;
        }
        return null;
    }

    public int getSize() {
        return this.size;
    }

    public int getCustomerCount() {
        return this.customerList.size();
    }

}
