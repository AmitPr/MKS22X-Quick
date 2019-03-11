import java.util.Arrays;
import java.util.Random;

public class Quick {
    public static void main(String[] args) {
        int size = 1000;
        int[] test = new int[size];
        for (int i = 0; i < size; i++) {
            test[i] = new Random().nextInt(1000000);
        }
        int[] sorted = Arrays.copyOf(test, size);
        Arrays.sort(sorted);
        for (int i = 0; i < size; i++) {
            int x = new Random().nextInt(size);
            if (quickselect(test, x) != sorted[x]) {
                System.out.println("FAIL");
            } else {
                System.out.println("SUCCESS");
            }
        }
    }

    /*
     * return the value that is the kth smallest value of the array.
     */
    public static int quickselect(int[] data, int k) {
        int end = data.length - 1;
        int start = 0;
        int x = partition(data, start, end);
        while (x != k) {
            x = partition(data, start, end);
            if (x < k) {
                start = x;
            } else if (x > k) {
                end = x - 1;
            }
        }
        return data[x];
    }

    /*
     * Choose a random pivot element between the start and end index inclusive, Then
     * modify the array such that:
     * 
     * 1. Only the indices from start to end inclusive are considered in range
     * 
     * 2. A random index from start to end inclusive is chosen, the corresponding
     * element is designated the pivot element.
     * 
     * 3. all elements in range that are smaller than the pivot element are placed
     * before the pivot element.
     * 
     * 4. all elements in range that are larger than the pivot element are placed
     * after the pivot element.
     * 
     * @return the index of the final position of the pivot element.
     */
    public static int partition(int[] data, int start, int end) {
        int pIndex = 0;
        if (!(end - start == 0)) {
            pIndex = new Random().nextInt(end - start) + start + 1;
        }
        int pivot = data[pIndex];
        data[pIndex] = data[start];
        data[start] = pivot;
        for (int i = start; i <= end; i++) {
            int x = data[i];
            if (x > pivot) {
                while (data[end] > pivot) {
                    if (i != end) {
                        end--;
                    } else {
                        data[start] = data[i - 1];
                        data[i - 1] = pivot;
                        return i - 1;
                    }
                }
                data[i] = data[end];
                data[end] = x;
            }
            if (i == end) {
                if (x > pivot) {
                    data[start] = data[i - 1];
                    data[i - 1] = pivot;
                    return i - 1;
                }
                data[start] = data[i];
                data[i] = pivot;
                return i;
            }
        }
        return end;
    }
}