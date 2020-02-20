
import java.util.Scanner;

public class CarryOn {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Carry on?");
        String input = scanner.nextLine();

        while (!input.equalsIgnoreCase("no")) {
            System.out.println("Carry on?");
            input = scanner.nextLine();
        }
    }
}
