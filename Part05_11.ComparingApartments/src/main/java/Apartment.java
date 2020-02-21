
public class Apartment {

    private int rooms;
    private int squares;
    private int princePerSquare;

    public Apartment(int rooms, int squares, int pricePerSquare) {
        this.rooms = rooms;
        this.squares = squares;
        this.princePerSquare = pricePerSquare;
    }

    public boolean largerThan(Apartment compared) {
        return this.squares > compared.squares;
    }

    public int priceDifference(Apartment compared) {
        int price = this.squares * this.princePerSquare;
        return Math.abs(compared.princePerSquare * compared.squares - price);
    }

    public boolean moreExpensiveThan(Apartment compared) {
        return this.princePerSquare * this.squares > compared.squares * compared.princePerSquare;
    }
}