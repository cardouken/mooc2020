
import java.util.Scanner;

public class GiftTax {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Value of the gift?");
        int sum = scan.nextInt();
        double taxAmount = 0;

        if (sum < 5000) {
            System.out.println("No tax!");
        }
        if (sum >= 5000 && sum < 25000) {
            taxAmount = 100 + (sum - 5000) * 0.08;
            System.out.println("Tax: " + taxAmount);

        }
        if (sum >= 25000 && sum < 55000) {
            taxAmount = 1700 + (sum - 25000) * 0.1;
            System.out.println("Tax: " + taxAmount);

        }
        if (sum >= 55000 && sum < 200000) {
            taxAmount = 4700 + (sum - 55000) * 0.12;
            System.out.println("Tax: " + taxAmount);

        }
        if (sum >= 200000 && sum < 1000000) {
            taxAmount = 22100 + (sum - 200000) * 0.15;
            System.out.println("Tax: " + taxAmount);

        }
        if (sum >= 1000000) {
            taxAmount = 142100 + (sum - 1000000) * 0.17;
            System.out.println("Tax: " + taxAmount);
        }
    }
}
