public class Item {
    @Override
    public String toString() {
        return identifier + ": " + name;
    }

    public Item(String identifier, String name) {
        this.identifier = identifier;
        this.name = name;
    }

    private String identifier;
    private String name;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Item() {
    }

    public boolean equals(Object compared) {
        Item item = (Item) compared;
        return this.identifier.equals(item.identifier);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
