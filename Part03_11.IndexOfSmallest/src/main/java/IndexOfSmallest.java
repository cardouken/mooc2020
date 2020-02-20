
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IndexOfSmallest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> userList = new ArrayList<>();

        while (true) {
            int input = scanner.nextInt();
            if (input == 9999) {
                break;
            }
            userList.add(input);
        }

        int smallest = userList.get(0);

        for (Integer integer : userList) {
            if (integer < smallest) {
                smallest = integer;
            }
        }

        System.out.println("Smallest number: " + smallest);

        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i) == smallest) {
                System.out.println("Found at index: " + i);
            }
        }

        
    }
}
