import java.util.ArrayList;

public class Stack {

    private final ArrayList<String> strings = new ArrayList<>();

    public boolean isEmpty() {
        return this.strings.isEmpty();
    }

    public void add(String value) {
        strings.add(value);
    }

    public ArrayList<String> values() {
        return strings;
    }

    public String take() {
        final String topMost = strings.get(strings.size() - 1);
        strings.remove(topMost);
        return topMost;
    }
}