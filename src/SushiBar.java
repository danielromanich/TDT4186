import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class SushiBar {
    //SushiBar settings
    private static int capacity = 10;
    private static int duration = 10;
    public static int maxOrder = 10;
    public static int customerWait = 3000;
    public static int doorWait = 100;
    public static boolean isOpen = true;

    //Creating log file
    private static File log;
    private static String path = "./";

    //Variables related to statistics
    public static SynchronizedInteger customerCounter = new SynchronizedInteger(0);
    public static SynchronizedInteger servedOrders = new SynchronizedInteger(0);
    public static SynchronizedInteger takeawayOrders = new SynchronizedInteger(0);
    public static SynchronizedInteger totalOrders = new SynchronizedInteger(0);
    public static int lastCustomer;

    /**
     * @param args
     */
    public static void main(String[] args) {
        log = new File(path + "log.txt");
        if (log.exists())
            log.delete();
        try {
            log.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Clock(duration);
        ServingArea servingArea = new ServingArea(capacity);
        Door door = new Door(servingArea);
        new Thread(door).start(); //Start the door thread
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
            e.printStackTrace();
        }
    }

    public static void sleep(TimeUnit unit, int duration) {
        try {
            unit.sleep(duration);
        } catch (Exception e) {

        }
    }

}
