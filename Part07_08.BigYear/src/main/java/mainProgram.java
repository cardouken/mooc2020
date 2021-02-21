
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class mainProgram {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        final List<Bird> birds = new ArrayList<>();

        while (true) {
            System.out.print("? ");
            final String command = sc.nextLine();

            if ("quit".equalsIgnoreCase(command)) {
                break;
            }

            if ("one".equalsIgnoreCase(command)) {
                System.out.print("Bird? ");
                final String name = sc.nextLine();

                birds.stream()
                        .filter(b -> Objects.equals(b.getName(), name))
                        .findFirst()
                        .ifPresent(System.out::println);
            }

            if ("add".equalsIgnoreCase(command)) {
                System.out.print("Name: ");
                final String name = sc.nextLine();
                System.out.print("Name in Latin: ");
                final String latinName = sc.nextLine();

                birds.add(new Bird(name, latinName));
            }

            if ("observation".equalsIgnoreCase(command)) {
                System.out.print("Bird? ");
                final String name = sc.nextLine();

                final Bird queriedBird = birds.stream()
                        .filter(b -> Objects.equals(b.getName(), name))
                        .findFirst()
                        .orElse(null);

                if (queriedBird == null) {
                    System.out.println("Not a bird!");
                    continue;
                }
                queriedBird.increaseObservationCount();

            }

            if ("all".equalsIgnoreCase(command)) {
                for (Bird bird : birds) {
                    System.out.println(bird);
                }
            }
        }
    }

}
