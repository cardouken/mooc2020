import java.util.ArrayList;
import java.util.Collections;

public class ChangeHistory {

    private ArrayList<Double> inventoryHistory;

    public ChangeHistory() {
        this.inventoryHistory = new ArrayList<>();
    }

    public void add(double status) {
        this.inventoryHistory.add(status);
    }

    public void clear() {
        this.inventoryHistory.clear();
    }

    public double maxValue() {
        return Collections.max(inventoryHistory);
    }

    public double minValue() {
        return Collections.min(inventoryHistory);

    }

    public double average() {
        double sum = 0;
        for (Double inv : inventoryHistory) {
            sum += inv;
        }

        return sum / inventoryHistory.size();
    }

    @Override
    public String toString() {
        return inventoryHistory.toString();
    }
}
