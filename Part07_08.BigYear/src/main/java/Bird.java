import java.text.MessageFormat;

public class Bird {

    private final String name;
    private final String latinName;
    private int observations;

    public Bird(String name, String latinName) {
        this.name = name;
        this.latinName = latinName;
        this.observations = 0;
    }

    public String getName() {
        return name;
    }

    public String getLatinName() {
        return latinName;
    }

    public void increaseObservationCount() {
        this.observations++;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0} ({1}): {2} observations", name, latinName, observations);
    }
}
