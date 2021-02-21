import java.text.MessageFormat;
import java.util.ArrayList;

public class Suitcase {

    private final ArrayList<Item> items = new ArrayList<>();
    private final int maxWeight;

    public Suitcase(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void addItem(Item item) {
        final int currentTotalWeight = this.items.stream()
                .mapToInt(Item::getWeight)
                .sum();

        if (item.getWeight() + currentTotalWeight <= maxWeight) {
            this.items.add(item);
        }
    }

    @Override
    public String toString() {
        if (items.isEmpty()) {
            return "no items (0kg)";

        }
        int totalWeight = 0;
        String itemPluralMultipleNaming = "item";
        if (items.size() > 1) {
            itemPluralMultipleNaming = "items";
        }
        for (Item item : items) {
            totalWeight += item.getWeight();
        }
        return MessageFormat.format("{0} {1} ({2} kg)", items.size(), itemPluralMultipleNaming, totalWeight);
    }

    public void printItems() {
        StringBuilder output = new StringBuilder();
        for (Item item : items) {
            output.append(item.toString());
            if (items.size() > 1) {
                output.append(System.lineSeparator());
            }
        }
        System.out.println(output);
    }

    public int totalWeight() {
        return this.items.stream()
                .mapToInt(Item::getWeight)
                .sum();
    }

    public Item heaviestItem() {
        if (items.isEmpty()) {
            return null;
        }
        Item heaviest = new Item("", 0);
        for (Item item : items) {
            if (item.getWeight() > heaviest.getWeight()) {
                heaviest = item;
            }
        }
        return heaviest;
    }
}
