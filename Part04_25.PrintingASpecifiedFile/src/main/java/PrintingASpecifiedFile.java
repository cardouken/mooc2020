
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class PrintingASpecifiedFile {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which file should have its contents printed?");
        String filename = scanner.nextLine();

        BufferedReader br = new BufferedReader(new FileReader(filename));

        String str;
        while ((str = br.readLine()) != null) {
            System.out.println(str);
        }
    }
}
