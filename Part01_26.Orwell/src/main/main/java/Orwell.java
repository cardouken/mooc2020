
import java.util.Scanner;

public class Orwell {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Give a number:");
        int orwell = Integer.parseInt(scan.nextLine());
        if (orwell == 1984) {
            System.out.println("Orwell");
        }
    }
}
