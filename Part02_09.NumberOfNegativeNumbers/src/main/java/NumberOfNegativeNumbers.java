
import java.util.Scanner;

public class NumberOfNegativeNumbers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give a number:");
        int num = scanner.nextInt();

        int count = 0;
        while (num != 0) {
            if (num < 0) {
                count++;
            }
            System.out.println("Give a number:");
            num = scanner.nextInt();

        }
        System.out.println("Number of negative numbers: " + count);
    }
}
