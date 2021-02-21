import java.text.MessageFormat;

public class Student extends Person {

    private int credits;

    public Student(String name, String address) {
        super(name, address);
    }

    public void study() {
        this.credits++;
    }

    public int credits() {
        return credits;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}\n  Study credits {1}", super.toString(), credits);
    }
}
