

import java.text.MessageFormat;
import java.util.ArrayList;

public class SimpleCollection {

    private String name;
    private ArrayList<String> elements;

    public SimpleCollection(String name) {
        this.name = name;
        this.elements = new ArrayList<>();
    }

    public void add(String element) {
        this.elements.add(element);
    }

    public ArrayList<String> getElements() {
        return this.elements;
    }

    @Override
    public String toString() {
        if (elements.isEmpty()) {
            return MessageFormat.format("The collection {0} is empty.", name);
        }

        final StringBuilder elementsStrings = new StringBuilder();
        final String elementSingularPlural = elements.size() > 1 ? "elements" : "element";
        final String amountOfElements = MessageFormat.format("The collection {0} has {1} {2}:\n", name, elements.size(), elementSingularPlural);
        for (String element : elements) {
            elementsStrings.append(element);
            if (elements.size() > 1) {
                elementsStrings.append("\n");
            }
        }

        return amountOfElements + elementsStrings;
    }
}
