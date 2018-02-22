
public class Customer extends Thread {

    private final int id;
    private int state = 0;
    private ServingArea servingArea;

    public Customer(int id, ServingArea servingArea) {
        this.id = id;
        this.servingArea = servingArea;
    }

    public int getID() {
        return id;
    }

    public void run() {
        System.out.println("Customer #" + id + " is now waiting for a free seat");

        while (servingArea.getAreaSize() <= servingArea.getCustomerCount())
            awaitServingArea();
        servingArea.enter(this);
        int servingAmount = getServingAmount();
        SushiBar.servedOrders.add(servingAmount);
        SushiBar.takeawayOrders.add(SushiBar.maxOrder - servingAmount);
        System.out.println("Customer #" + id + " is now eating sushi.");
        try {
            Thread.sleep((int) Math.floor(Math.random() * 500) + 5000);
        } catch(Exception e) {

        }
        servingArea.leave(this);
    }

    private void awaitServingArea() {
        try {
            wait();
        } catch (Exception e) {

        }
    }

    private int getServingAmount() {
        return (int) Math.floor(Math.random() * (SushiBar.maxOrder - 1)) + 1;
    }
}
