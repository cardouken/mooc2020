import java.util.Scanner;

public class MainProgram {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        PaymentCard paul = new PaymentCard(20);
        PaymentCard matt = new PaymentCard(30);

        paul.eatHeartily();
        matt.eatAffordably();

        System.out.println("Paul: " + paul.toString());
        System.out.println("Matt: " + matt.toString());

        paul.addMoney(20);
        matt.eatHeartily();

        System.out.println("Paul: " + paul.toString());
        System.out.println("Matt: " + matt.toString());

        paul.eatAffordably();
        paul.eatAffordably();

        matt.addMoney(50);

        System.out.println("Paul: " + paul.toString());
        System.out.println("Matt: " + matt.toString());
    }
}
