
import java.util.Scanner;

public class AverageOfPositiveNumbers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();

        int count = 0;
        int sum = 0;

        while (num != 0) {
            if (num > 0) {
                count++;
                sum += num;
            }
            num = scanner.nextInt();
        }
        double avg = (double) sum / count;

        if (count == 0) {
            System.out.println("Cannot calculate the average");
        } else {
            System.out.println(avg);
        }
    }
}
