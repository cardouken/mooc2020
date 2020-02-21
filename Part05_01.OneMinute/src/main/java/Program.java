
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Timer timer = new Timer();
        long time = System.currentTimeMillis();
        long end = time + 15000;

        while (System.currentTimeMillis() < end) { // run for 15 seconds
            System.out.println(timer);
            timer.advance();

            try {
                Thread.sleep(10);
            } catch (Exception ignored) {

            }
        }

    }
}
