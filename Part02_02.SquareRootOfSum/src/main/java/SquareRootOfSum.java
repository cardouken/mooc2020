
import java.util.Scanner;

public class SquareRootOfSum {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n1 = scanner.nextInt();
        int n2 = scanner.nextInt();
        int sum = n1 + n2;

        double sqrt = Math.sqrt(sum);
        System.out.println(sqrt);
    }
}
