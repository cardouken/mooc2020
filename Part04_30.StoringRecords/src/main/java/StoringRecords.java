
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class StoringRecords {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Filename:");
        String file = scan.nextLine();

        ArrayList<Person> records = readRecordsFromFile(file);
        System.out.println("Persons: " + records.size());
        System.out.println("Persons:");
        for (Person person : records) {
            System.out.println(person);

        }
    }

    public static ArrayList<Person> readRecordsFromFile(String file) {
        ArrayList<Person> persons = new ArrayList<>();
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(file));
            String str;

            while ((str = br.readLine()) != null) {
                String[] strArray = str.split(",");
                String name = strArray[0];
                int age = Integer.parseInt(strArray[1]);

                persons.add(new Person(name, age));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return persons;
    }
}
