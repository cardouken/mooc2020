
import java.util.Scanner;

public class Password {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Password?");
        String pw = scan.nextLine();

        System.out.println(!pw.equals("Caput Draconis") ? "Off with you!" : "Welcome!");
    }
}
