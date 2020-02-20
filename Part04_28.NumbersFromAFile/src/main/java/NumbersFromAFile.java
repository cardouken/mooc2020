
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NumbersFromAFile {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("File? ");
        String file = scanner.nextLine();
        System.out.print("Lower bound? ");
        int lowerBound = Integer.parseInt(scanner.nextLine());
        System.out.print("Upper bound? ");
        int upperBound = Integer.parseInt(scanner.nextLine());

        BufferedReader br = new BufferedReader(new FileReader(file));

        List<Integer> list = new ArrayList<>();

        String str;
        while ((str = br.readLine()) != null) {
            list.add(Integer.parseInt(str));
        }

        int count = 0;
        for (Integer integer : list) {
            if (integer >= lowerBound && integer <= upperBound) {
                count++;
            }
        }

        System.out.println("Numbers: " + count);

    }

}
