
import java.util.Scanner;

public class OnlyPositives {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give a number:");
        int input = scanner.nextInt();

        while (input != 0) {
            if (input > 0) {
                System.out.println(input * input);
            }
            if (input < 0) {
                System.out.println("Unsuitable number");
            }
            System.out.println("Give a number");
            input = scanner.nextInt();
        }
    }
}
