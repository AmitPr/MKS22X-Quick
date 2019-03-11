import java.util.Random;

public class Quick {
    public static void main(String[] args) {
        System.out.println(partition(new int[] { 8, 6, 7, 5, 3, 0, 9 }, 0, 6));
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
        int pIndex = new Random().nextInt(data.length);
        int pivot = data[pIndex];
        data[pIndex] = data[start];
        data[start] = pivot;
        for (int i = start + 1; i <= end; i++) {
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