
public class DecreasingCounter {

    private int value;  // an object variable for storing the value of the counter

    public DecreasingCounter(int initialValue) {
        this.value = initialValue;
    }

    public void printValue() {
        // Do not change this code!
        System.out.println("value: " + this.value);
    }

    public void decrement() {
        if (this.value != 0) {
            this.value -= 1;
        }
    }

    public void reset() {
        this.value = 0;
    }

    // the other methods go here
}
