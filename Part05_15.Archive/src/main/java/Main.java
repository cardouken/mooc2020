
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Item> itemList = new ArrayList<>();
        Item item;
        while (true) {
            System.out.println("Identifier? (empty will stop)");
            String identifier = scanner.nextLine();
            if (identifier.isEmpty()) {
                break;
            }

            System.out.println("Name? (empty will stop)");
            String name = scanner.nextLine();
            if (name.isEmpty()) {
                break;
            }

            item = new Item(identifier, name);
            if (!itemList.contains(item)) {
                itemList.add(item);
            }

        }
        System.out.println("==Items==");
        for (Item items : itemList) {
            System.out.println(items);
        }
    }
}
