import java.text.MessageFormat;
import java.util.ArrayList;

public class Hold {

    private final ArrayList<Suitcase> suitcases = new ArrayList<>();
    private final int maxWeight;

    public Hold(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void addSuitcase(Suitcase suitcase) {
        final int currentTotalWeight = suitcases.stream()
                .mapToInt(Suitcase::totalWeight)
                .sum();

        if (suitcase.totalWeight() + currentTotalWeight <= maxWeight) {
            this.suitcases.add(suitcase);
        }
    }

    @Override
    public String toString() {
        final int currentTotalWeight = suitcases.stream()
                .mapToInt(Suitcase::totalWeight)
                .sum();
        return MessageFormat.format("{0} suitcases ({1} kg)", suitcases.size(), currentTotalWeight);
    }

    public void printItems() {
        for (Suitcase suitcase : suitcases) {
            suitcase.printItems();
        }
    }
}
