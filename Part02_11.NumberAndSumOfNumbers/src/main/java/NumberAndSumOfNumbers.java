
import java.util.Scanner;

public class NumberAndSumOfNumbers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give a number:");
        int num = scanner.nextInt();

        int count = 0;
        int sum = num;

        while (num != 0) {
            System.out.println("Give a number:");
            num = scanner.nextInt();
            count++;
            sum += num;
        }
        System.out.println("Number of numbers: " + count);
        System.out.println("Sum of the numbers: " + sum);
    }
}
