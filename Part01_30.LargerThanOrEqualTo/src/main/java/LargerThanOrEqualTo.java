
import java.util.Scanner;

public class LargerThanOrEqualTo {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Give the first number:");
        int n1 = Integer.parseInt(scan.nextLine());
        System.out.println("Give the second number:");
        int n2 = Integer.parseInt(scan.nextLine());

        if (n1 == n2) {
            System.out.println("equal");
        } else {
            System.out.println("Larger number is: " + Math.max(n1, n2));
        }

    }
}
