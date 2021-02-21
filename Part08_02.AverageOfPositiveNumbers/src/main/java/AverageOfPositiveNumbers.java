
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AverageOfPositiveNumbers {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        final List<Integer> numbers = new ArrayList<>();
        while (true) {
            final int input = Integer.parseInt(sc.nextLine());
            if (input == 0) {
                printAverage(numbers);
                break;
            }
            if (input >= 0) {
                numbers.add(input);
            }
        }

    }

    private static void printAverage(List<Integer> numbers) {
        if (numbers.isEmpty()) {
            System.out.println("Cannot calculate the average");
            return;
        }
        double sum = numbers.stream()
                .mapToDouble(num -> num)
                .sum();

        System.out.println(sum / numbers.size());
    }
}
