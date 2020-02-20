
import java.util.Scanner;

public class AverageOfThreeNumbers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Give the first number:");
        int n1 = Integer.parseInt(scanner.nextLine());
        System.out.println("Give the second number:");
        int n2 = Integer.parseInt(scanner.nextLine());
        System.out.println("Give the third number:");
        int n3 = Integer.parseInt(scanner.nextLine());

        double result = (double)(n1 + n2 + n3) / 3;
        System.out.println("The average is " + result);

    }
}
