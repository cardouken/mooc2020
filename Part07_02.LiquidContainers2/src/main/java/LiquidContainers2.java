
import java.util.Scanner;

public class LiquidContainers2 {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        final Container containerOne = new Container();
        final Container containerTwo = new Container();

        while (true) {
            System.out.println("First: " + containerOne);
            System.out.println("Second: " + containerTwo);

            String input = scan.nextLine();
            if (input.equals("quit")) {
                break;
            }

            final String[] parts = input.split(" ");
            final String command = parts[0];
            final int amount = Integer.parseInt(parts[1]);

            if (command.equalsIgnoreCase("add")) {
                containerOne.add(amount);
            }

            if (command.equalsIgnoreCase("move")) {
                containerTwo.add(Math.min(containerOne.contains(), amount));
                containerOne.remove(amount);
            }

            if (command.equalsIgnoreCase("remove")) {
                containerTwo.remove(amount);
            }
        }
    }

}
