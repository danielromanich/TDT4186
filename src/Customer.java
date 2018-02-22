import java.util.concurrent.TimeUnit;

public class Customer extends Thread {

    private final int id;
    private ServingArea servingArea;

    public Customer(int id, ServingArea servingArea) {
        this.id = id;
        this.servingArea = servingArea;
    }

    public int getID() {
        return id;
    }

    public void run() {
        SushiBar.write("Customer #" + id + " is now waiting for a free seat");
        awaitServingArea();
        servingArea.enter(this);
        int servingAmount = getServingAmount(SushiBar.maxOrder);
        SushiBar.servedOrders.add(servingAmount);
        SushiBar.takeawayOrders.add(getServingAmount(SushiBar.maxOrder - servingAmount));
        SushiBar.write("Customer #" + id + " is now eating sushi.");

        try {
            TimeUnit.SECONDS.sleep(2 + (int) Math.floor(Math.random() * 3));
        } catch(Exception e) {
            e.printStackTrace();
        }
        servingArea.leave(this);
    }

    private void awaitServingArea() {
        synchronized (servingArea) {
            try {
                servingArea.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int getServingAmount(int max) {
        return (int) Math.floor(Math.random() * (max - 1)) + 1;
    }
}
