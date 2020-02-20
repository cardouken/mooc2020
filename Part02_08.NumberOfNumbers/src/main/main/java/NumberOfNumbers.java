
import java.util.Scanner;

public class NumberOfNumbers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = 0;
        System.out.println("Give a number:");
        int input = scanner.nextInt();

        while (input != 0) {
            System.out.println("Give a number:");
            input = scanner.nextInt();
            count++;
        }
        System.out.println("Number of numbers: " + count);
    }
}
