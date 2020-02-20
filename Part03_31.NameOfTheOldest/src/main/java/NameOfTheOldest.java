
import java.util.Scanner;

public class NameOfTheOldest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String oldest = null;
        int greatest = 0;
        String[] arr = new String[0];
        while (!input.isEmpty()) {
            arr = input.split(",");

            for (int i = 1; i < arr.length; i = i + 2) {
                if (Integer.parseInt(arr[i]) > greatest) {
                    greatest = Integer.parseInt(arr[i]);
                    oldest = arr[i - 1];
                }
            }

            input = scanner.nextLine();
        }

        System.out.println(oldest);
    }
}
