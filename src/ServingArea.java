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
        System.out.println("Customer #" + customer.getID() + " was now seated.");
        notify();
        customers.add(customer);
    }

    public synchronized void leave(Customer customer) {
        customers.remove(customer);
        notify();
    }
}
