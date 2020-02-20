

public class FromOneToParameter {

    public static void main(String[] args) {
        printUntilNumber(3);
    }

    private static void printUntilNumber(int number) {
        for (int i = 1; i <= number; i++) {
            System.out.println(i);
        }
    }

}
