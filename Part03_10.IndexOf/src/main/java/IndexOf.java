
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IndexOf {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Integer> list = new ArrayList<>();
        while (true) {
            int input = Integer.valueOf(scanner.nextLine());
            if (input == -1) {
                break;
            }

            list.add(input);
        }

        System.out.println("");

        System.out.println("Search for?");
        int input = scanner.nextInt();


        for (int i = 0; i < list.size(); i++) {
            Integer number = list.get(i);
            if (number == input) {
                System.out.println(number + " is at index " + i);
            }
        }
    }
}
