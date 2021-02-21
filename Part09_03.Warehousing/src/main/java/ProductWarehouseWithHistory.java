import java.text.MessageFormat;

public class ProductWarehouseWithHistory extends ProductWarehouse {

    private final ChangeHistory changeHistory;

    public ProductWarehouseWithHistory(String productName, double capacity, double initialBalance) {
        super(productName, capacity);
        this.changeHistory = new ChangeHistory();
        setInitialBalance(initialBalance);
    }

    private void setInitialBalance(double initialBalance) {
        changeHistory.add(initialBalance);
        super.addToWarehouse(initialBalance);
    }

    public String history() {
        return changeHistory.toString();
    }

    public void printAnalysis() {
        System.out.println(
                MessageFormat.format("Product: {0}\nHistory: {1}\nLargest amount of product: {2}\nSmallest amount of product:{3}\nAverage: {4}",
                        super.getName(),
                        changeHistory,
                        changeHistory.maxValue(),
                        changeHistory.minValue(),
                        changeHistory.average()
                )
        );
    }

    @Override
    public void addToWarehouse(double amount) {
        super.addToWarehouse(amount);
        changeHistory.add(super.getBalance());
    }

    @Override
    public double takeFromWarehouse(double amount) {
        double amountTaken = super.takeFromWarehouse(amount);
        changeHistory.add(super.getBalance());
        return amountTaken;
    }

    @Override
    public String toString() {
        return changeHistory.toString();
    }
}
