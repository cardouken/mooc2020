import java.util.Arrays;
import java.util.stream.IntStream;

public class MainProgram {

    public static void main(String[] args) {

    }

    public static int smallest(int[] array) {
        return Arrays.stream(array).min().orElse(-1);
    }

    public static int indexOfSmallest(int[] array) {
        final int smallest = Arrays.stream(array).min().orElse(-1);
        return IntStream.range(0, array.length)
                .filter(i -> smallest == array[i])
                .findFirst()
                .orElse(-1);
    }

    public static int indexOfSmallestFrom(int[] array, int startIndex) {
        int smallest = Integer.MAX_VALUE;
        int index = Integer.MAX_VALUE;
        for (int i = startIndex; i < array.length; i++) {
            if (array[i] < smallest) {
                smallest = array[i];
                index = i;
            }
        }
        return index;
    }

    public static void swap(int[] array, int index1, int index2) {
        int temp = array[index1];
        int temp2 = array[index2];
        array[index1] = temp2;
        array[index2] = temp;
    }

    public static void sort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println(Arrays.toString(array));
            swap(array, indexOfSmallestFrom(array, i), i);
        }
    }

}
