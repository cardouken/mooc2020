import java.text.MessageFormat;

public class Item {

    public Item(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    private String name;
    private int weight;

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0} ({1} kg)", name, weight);
    }
}
