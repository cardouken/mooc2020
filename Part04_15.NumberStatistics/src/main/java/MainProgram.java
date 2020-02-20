import java.util.Scanner;

public class MainProgram {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Statistics statistics = new Statistics();
        Statistics evens = new Statistics();
        Statistics odds = new Statistics();
        System.out.println("Enter numbers:");

        while (true) {
            int num = sc.nextInt();

            if (num == -1) {
                break;
            }
            statistics.addNumber(num);

            if (num % 2 == 0) {
                evens.addNumber(num);
            } else {
                odds.addNumber(num);
            }

        }

        System.out.println(statistics.average());
        System.out.println("Sum: " + statistics.sum());
        System.out.println("Sum of even numbers: " + evens.sum());
        System.out.println("Sum of odd numbers: " + odds.sum());

    }
}

