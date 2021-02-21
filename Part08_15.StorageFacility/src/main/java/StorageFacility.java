import java.util.ArrayList;
import java.util.HashMap;

public class StorageFacility {

    private final HashMap<String, ArrayList<String>> storageMap;

    public StorageFacility() {
        this.storageMap = new HashMap<>();
    }

    public void add(String unit, String item) {
        storageMap.computeIfAbsent(unit, u -> new ArrayList<>()).add(item);
    }

    public ArrayList<String> contents(String storageUnit) {
        return storageMap.getOrDefault(storageUnit, new ArrayList<>());
    }

    public void remove(String storageUnit, String item) {
        final ArrayList<String> items = storageMap.get(storageUnit);
        items.remove(item);
        if (items.isEmpty()) {
            storageMap.remove(storageUnit);
        }
    }

    public ArrayList<String> storageUnits() {
        final ArrayList<String> unitNames = new ArrayList<>();
        storageMap.forEach((key, value) -> {
            if (!value.isEmpty()) {
                unitNames.add(key);
            }
        });
        return unitNames;
    }
}
