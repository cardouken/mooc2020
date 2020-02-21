public class Cube {
    private int edgeLength;

    public Cube(int edgeLength) {
        this.edgeLength = edgeLength;
    }

    @Override
    public String toString() {
        return "The length of the edge is " + this.edgeLength + " and the volume " + volume();
    }

    public int volume() {
        return this.edgeLength * this.edgeLength * this.edgeLength;
    }
}
