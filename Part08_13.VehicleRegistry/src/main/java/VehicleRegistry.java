import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class VehicleRegistry {

    private final HashMap<LicensePlate, String> licensePlateMap = new HashMap<>();

    public boolean add(LicensePlate licensePlate, String owner) {
        if (licensePlateMap.get(licensePlate) == null) {
            licensePlateMap.put(licensePlate, owner);
            return true;
        }

        return false;
    }

    public String get(LicensePlate licensePlate) {
        return licensePlateMap.get(licensePlate);
    }

    public boolean remove(LicensePlate licensePlate) {
        if (licensePlateMap.containsKey(licensePlate)) {
            licensePlateMap.remove(licensePlate);
            return true;
        } else {
            return false;
        }
    }

    public void printLicensePlates() {
        for (LicensePlate licensePlate : licensePlateMap.keySet()) {
            System.out.println(licensePlate);
        }
    }

    public void printOwners() {
        final Set<String> owners = new HashSet<>(licensePlateMap.values());
        for (String owner : owners) {
            System.out.println(owner);
        }
    }
}
