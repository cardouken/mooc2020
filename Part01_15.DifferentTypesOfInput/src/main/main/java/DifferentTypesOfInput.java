
import java.util.Scanner;

public class DifferentTypesOfInput {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Give a string:");
        String str = scan.nextLine();
        System.out.println("Give an integer:");
        int integer = Integer.parseInt(scan.nextLine());
        System.out.println("Give a double:");
        double dbl = Double.parseDouble(scan.nextLine());
        System.out.println("Give a boolean:");
        boolean bool = Boolean.parseBoolean(scan.nextLine());

        System.out.println("You gave the string " + str);
        System.out.println("You gave the integer " + integer);
        System.out.println("You gave the double " + dbl);
        System.out.println("You gave the boolean " + bool);


    }
}
