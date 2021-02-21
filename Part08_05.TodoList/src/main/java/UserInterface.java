import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private final TodoList todoList;
    private final Scanner scanner;

    public UserInterface(TodoList todoList, Scanner scanner) {
        this.todoList = todoList;
        this.scanner = scanner;
    }

    public void start() {
        final List<String> validCommands = new ArrayList<>();
        validCommands.add("stop");
        validCommands.add("add");
        validCommands.add("list");
        validCommands.add("remove");

        while (true) {
            System.out.print("Command: ");
            String input = scanner.nextLine();
            if (!validCommands.contains(input)) {
                System.out.println("Unknown command");
            }

            if ("stop".equalsIgnoreCase(input)) {
                break;
            }

            if ("add".equalsIgnoreCase(input)) {
                System.out.print("To add: ");
                final String taskToAdd = scanner.nextLine();
                todoList.add(taskToAdd);
            }

            if ("list".equalsIgnoreCase(input)) {
                todoList.print();
            }

            if ("remove".equalsIgnoreCase(input)) {
                System.out.print("Which one is removed? ");
                final int numberToRemove = Integer.parseInt(scanner.nextLine());
                todoList.remove(numberToRemove);
            }

        }

    }
}
