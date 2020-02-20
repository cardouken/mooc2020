
import java.util.Scanner;

public class SumOfNumbers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give a number:");
        int input = scanner.nextInt();

        int sum = input;
        while (input != 0) {
            System.out.println("Give a number:");
            input = scanner.nextInt();
            sum += input;
        }
        System.out.println("Sum of the numbers: " + sum);
    }
}
