
import java.util.Scanner;

public class Reprint {

    public static void main(String[] args) {
        System.out.println("How many times?");
        Scanner scanner = new Scanner(System.in);
        int amount = scanner.nextInt();

        for (int i = 0; i < amount; i++) {
            printText();
        }
    }

    public static void printText() {
        System.out.println("In a hole in the ground there lived a method");
    }
}
