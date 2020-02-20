
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AverageOfAList {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Integer> userList = new ArrayList<>();

        while(true) {
            int input = scanner.nextInt();
            if (input == -1) {
                break;
            }

            userList.add(input);
        }

        int sum = 0;
        int count = 0;
        for (Integer integer : userList) {
            sum += integer;
            count++;
        }

        double avg = sum / (double) count;
        System.out.println("Average: " + avg);
    }
}
