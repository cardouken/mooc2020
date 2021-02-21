import java.text.MessageFormat;

public class Teacher extends Person {

    private final int salary;

    public Teacher(String name, String address, int salary) {
        super(name, address);
        this.salary = salary;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}\n  salary {1} euro/month", super.toString(), salary);
    }
}

