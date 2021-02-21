
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final Scanner sc = new Scanner(System.in);
        final GradeRegister register = new GradeRegister();

        System.out.println("Enter point totals, -1 stops:");
        while (true) {
            final int input = Integer.parseInt(sc.nextLine());
            if (input == -1) {
                break;
            }

            if (input < 0 || input > 100) {
                continue;
            }
            register.addGradeBasedOnPoints(input);
        }

        register.getPointsAverage();
        register.printPassingPointsAverage();
        register.printGradeDistribution();
    }

}
