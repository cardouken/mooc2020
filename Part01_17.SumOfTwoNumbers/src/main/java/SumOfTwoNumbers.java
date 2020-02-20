
import java.util.Scanner;

public class SumOfTwoNumbers {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Give the first number:");
        int first = Integer.parseInt(sc.nextLine());
        System.out.println("Give the second number:");
        int second = Integer.parseInt(sc.nextLine());
        int result = first + second;

        System.out.println("The sum of the numbers is " + result);

    }
}
