/**
 * This class implements a customer, which is used for holding data and update the statistics
 *
 */
public class Customer {

    private int id;

    /**
     *  Creates a new Customer.
     *  Each customer should be given a unique ID
     */
    public Customer(int id) {
        this.id = id;
    }


    /**
     * Here you should implement the functionality for ordering food as described in the assignment.
     */
    public synchronized void order() {
        int orderCount = SushiBar.random(0, SushiBar.maxOrder);
        SushiBar.servedOrders.add(orderCount);
        SushiBar.takeawayOrders.add(SushiBar.random(0, SushiBar.maxOrder - orderCount));
    }

    /**
     *
     * @return Should return the customerID
     */
    public int getCustomerID() {
        return id;
    }

}
