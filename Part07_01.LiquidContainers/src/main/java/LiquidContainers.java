
import java.util.Scanner;

public class LiquidContainers {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int containerOne = 0;
        int containerTwo = 0;

        while (true) {
            System.out.println("First: " + containerOne + "/" + "100");
            System.out.println("Second: " + containerTwo + "/" + "100");

            String input = scan.nextLine();
            if (input.equals("quit")) {
                break;
            }

            final String[] parts = input.split(" ");
            final String command = parts[0];
            final int amount = Integer.parseInt(parts[1]);

            if (amount < 1) {
                continue;
            }

            if (command.equalsIgnoreCase("add")) {
                if (containerOne + amount <= 100) {
                    containerOne += amount;
                } else {
                    containerOne = 100;
                }
            }

            if (command.equalsIgnoreCase("move")) {
                if (amount > containerOne) {
                    containerTwo += containerOne;
                } else {
                    containerTwo += amount;
                    containerOne -= amount;
                }
            }

            if (command.equalsIgnoreCase("remove")) {
                if (containerTwo - amount >= 0) {
                    containerTwo -= amount;
                }
            }

            if (containerTwo > 100) {
                containerTwo = 100;
            }
        }

    }

}
