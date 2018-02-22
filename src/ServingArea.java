import java.util.ArrayList;


public class ServingArea {

    private ArrayList<Customer> customers = new ArrayList<>();
    private final int areaSize;

    public ServingArea(int areaSize) {
        this.areaSize = areaSize;
    }

    public int getAreaSize() {
        return this.areaSize;
    }

    public int getCustomerCount() {
        return this.customers.size();
    }

    public synchronized void enter(Customer customer) {
        SushiBar.customerCounter.increment();
        SushiBar.write("Customer #" + customer.getID() + " was now seated.");
        customers.add(customer);
    }

    public synchronized void leave(Customer customer) {
        customers.remove(customer);
        SushiBar.write("Customer #" + customer.getID() + " left the bar.");
        if (getCustomerCount() == areaSize - 1)
            SushiBar.write("There is now a free seat in the shop.");
        if (SushiBar.isOpen) {
            synchronized (this) {
                notify();
            }
        }
        if (!SushiBar.isOpen && getCustomerCount() == 0) {
            SushiBar.write("The total number of orders was: " + (SushiBar.servedOrders.get() + SushiBar.takeawayOrders.get()));
            SushiBar.write("The total number of takeaway orders was: " + SushiBar.takeawayOrders.get());
            SushiBar.write("The total number of served orders was: " + SushiBar.servedOrders.get());
            SushiBar.write("The total number of customer was: " + SushiBar.customerCounter.get());
        }
    }
}
