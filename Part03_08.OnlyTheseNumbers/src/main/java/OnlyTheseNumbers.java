
import java.util.ArrayList;
import java.util.Scanner;

public class OnlyTheseNumbers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Integer> numbers = new ArrayList<>();
        while (true) {
            int number = Integer.parseInt(scanner.nextLine());
            if (number == -1) {
                break;
            }

            numbers.add(number);
        }
        System.out.println("From where?");
        int from = scanner.nextInt();
        System.out.println("To where?");
        int to = scanner.nextInt();

        for (int i = from; i <= to; i++) {
            System.out.println(numbers.get(i));
        }

    }
}
