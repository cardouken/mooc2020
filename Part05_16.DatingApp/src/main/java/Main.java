
public class Main {

    public static void main(String[] args) {
        SimpleDate now = new SimpleDate(13, 2, 2015);
        now.advance(7);

        System.out.println("Now: " + now);
        System.out.println("After one week: " + now);
    }
}
