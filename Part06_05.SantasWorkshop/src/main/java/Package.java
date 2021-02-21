import java.util.ArrayList;

public class Package {

    private final ArrayList<Gift> gifts = new ArrayList<>();

    public void addGift(Gift gift) {
        this.gifts.add(gift);
    }

    public int totalWeight() {
        return gifts.stream()
                .mapToInt(Gift::getWeight)
                .sum();
    }
}
