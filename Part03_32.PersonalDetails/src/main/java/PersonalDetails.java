
import java.util.ArrayList;
import java.util.Scanner;

public class PersonalDetails {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String longestNameStr = null;
        int longestName = 0;
        int sum = 0;
        int count = 0;
        double avg = 0;

        while (true) {
            String input = scanner.nextLine();
            String[] arr = input.split(",");

            if (input.equals("")) {
                break;
            }

            if (arr[0].toCharArray().length > longestName) {
                longestName = arr[0].toCharArray().length;
                longestNameStr = arr[0];
            }
            count++;
            sum += Integer.parseInt(arr[1]);

        }

        avg = (double) sum / count;
        System.out.println("Longest name: " + longestNameStr);
        System.out.println("Average of the birth years: " + avg);
    }
}
