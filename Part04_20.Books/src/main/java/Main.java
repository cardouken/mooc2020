import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        List<Book> books = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Title:");
            String name = sc.nextLine();

            if (name.isEmpty()) {
                break;
            }

            System.out.println("Pages:");
            int pages = Integer.parseInt(sc.nextLine());

            System.out.println("Year:");
            int year = Integer.parseInt(sc.nextLine());

            books.add(new Book(name, pages, year));

        }

        System.out.println("What information will be printed?");
        String input = sc.nextLine();

        for (Book book : books) {
            if (input.equals("everything")) {
                System.out.println(book.toString());
            }
            if (input.equals("name")) {
                System.out.println(book.getTitle());
            }

        }

    }
}
