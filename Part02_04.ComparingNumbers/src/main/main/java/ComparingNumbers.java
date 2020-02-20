
import java.util.Scanner;

public class ComparingNumbers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n1 = scanner.nextInt();
        int n2 = scanner.nextInt();

        if (n1 > n2) {
            System.out.println(n1 + " is greater than " + n2);
        } else if (n1 < n2) {
            System.out.println(n1 + " is smaller than" + n2);
        } else {
            System.out.println(n1 + " is equal to " + n2);
        }
    }
}
