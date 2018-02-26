import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class SushiBar {

    //SushiBar settings
    private static int waitingAreaCapacity = 20;
    private static int waitressCount = 10;
    private static int duration = 10;
    public static int maxOrder = 10;
    public static int waitressWait = 50; // Used to calculat the time the waitress spends before taking the order
    public static int customerWait = 3000; // Used to calculate the time the customer uses eating
    public static int doorWait = 100; // Used to calculate the interval at which the door tries to create a customer
    public static boolean isOpen = true;

    //Creating log file
    private static File log;
    private static String path = "./";

    //Variables related to statistics
    public static SynchronizedInteger customerCounter;
    public static SynchronizedInteger servedOrders;
    public static SynchronizedInteger takeawayOrders;
    public static SynchronizedInteger totalOrders;
    public static SynchronizedInteger lastCustomer;

    public static void main(String[] args) {
        log = new File(path + "log.txt");
        if (log.exists()) {
            log.delete();
        }
        try {
            log.createNewFile();
        } catch (IOException e) {

        }
        //Initializing shared variables for counting number of orders
        customerCounter = new SynchronizedInteger(0);
        totalOrders = new SynchronizedInteger(0);
        servedOrders = new SynchronizedInteger(0);
        takeawayOrders = new SynchronizedInteger(0);
        lastCustomer = new SynchronizedInteger(0);
        WaitingArea waitingArea = new WaitingArea(waitingAreaCapacity);

        new Thread(new Door(waitingArea)).start();
        for (int i = 0; i < waitressCount; i++) {
            new Thread(new Waitress(waitingArea)).start();
        }
        new Clock(duration);
        // TODO initialize the bar and start the different threads
    }

    //Writes actions in the log file and console
    public static void write(String str) {
        try {
            FileWriter fw = new FileWriter(log.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Clock.getTime() + ", " + str + "\n");
            bw.close();
            System.out.println(Clock.getTime() + ", " + str);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int random(int start, int end) {
        return start + (int) Math.floor(Math.random() * (end - start));
    }
}
