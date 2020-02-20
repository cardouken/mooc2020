public class Film {
    private String name;
    private int ageRating;

    public Film(String name, int ageRating) {
        this.name = name;
        this.ageRating = ageRating;
    }

    public String name() {
        return this.name;
    }

    public int ageRating() {
        return this.ageRating;
    }

}
