
import java.util.Scanner;

public class Cubes {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            final String input = sc.nextLine();
            if (input.equalsIgnoreCase("end")) {
                break;
            }

            System.out.println(calculateCube(Integer.parseInt(input)));
        }

    }

    public static int calculateCube(int number) {
        return number * number * number;
    }
}
