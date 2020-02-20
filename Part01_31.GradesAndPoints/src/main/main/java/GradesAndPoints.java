
import java.util.Scanner;

public class GradesAndPoints {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Give points [0-100]:");
        int score = Integer.parseInt(scan.nextLine());

        if (score > 100) {
            System.out.println("Incredible");
        }
        if (score >= 90 && score <= 100) {
            System.out.println("Grade: 5");
        }
        if (score >= 80 && score <= 89) {
            System.out.println("Grade: 4");
        }
        if (score >= 70 && score <= 79) {
            System.out.println("Grade: 3");
        }
        if (score >= 60 && score <= 69) {
            System.out.println("Grade: 2");
        }
        if (score >= 50 && score <= 59) {
            System.out.println("Grade: 1");
        }
        if (score >= 0 && score <= 49) {
            System.out.println("failed");
        }
        if (score < 0) {
            System.out.println("impossible!");
        }


    }
}
