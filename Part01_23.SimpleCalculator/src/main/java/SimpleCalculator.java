
import java.util.Scanner;

public class SimpleCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Give the first number:");
        int n1 = Integer.parseInt(scanner.nextLine());
        System.out.println("Give the second number:");
        int n2 = Integer.parseInt(scanner.nextLine());

        int add = n1 + n2;
        System.out.println(n1 + " + " + n2 + " = " + add);
        int subtract = n1 - n2;
        System.out.println(n1 + " - " + n2 + " = " + subtract);
        int multiply = n1 * n2;
        System.out.println(n1 + " * " + n2 + " = " + multiply);
        double divide = (double) n1 / n2;
        System.out.println(n1 + " / " + n2 + " = " + divide);

    }
}
