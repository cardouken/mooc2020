
public class MainProgram {

    public static void main(String[] args) {
        Counter counter = new Counter(1);
        counter.increase();
        counter.increase(3);
        counter.decrease();
        counter.decrease(2);
        System.out.println(counter.value());
    }
}
