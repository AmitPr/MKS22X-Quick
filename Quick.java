import java.util.Arrays;
import java.util.Random;

public class Quick {
    public static void main(String[] args) {
        int size = 10;
        for (int i = 0; i < 1; i++) {
            int[] test = new int[size];
            for (int j = 0; j < size; j++) {
                test[j] = new Random().nextInt(5);// - (size / 2);
            }
            int[] sorted = Arrays.copyOf(test, size);
            partitionDutch(sorted, 0, sorted.length-1);
            System.out.println(Arrays.toString(sorted));
            /*
            long time = System.nanoTime();
            Arrays.sort(sorted);
            System.out.print("Built-in Took: " + ((System.nanoTime() - time) / 1000000) + "ms");
            time = System.nanoTime();
            quicksort(test);
            System.out.println(", Mine Took: " + ((System.nanoTime() - time) / 1000000) + "ms");
            boolean fail = false;
            for (int j = 0; j < test.length; j++) {
                if (test[j] != sorted[j]) {
                    fail = true;
                }
            }
            if (fail) {
                System.out.println("FAIL");
            } else {
                System.out.println("SUCCESS");
            }*/
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
     * Modify the array to be in increasing order.
     */
    public static void quicksort(int[] data) {
        quickHelper(data, 0, data.length - 1);
    }

    public static void quickHelper(int[] data, int lo, int hi) {
        if (hi - lo > 0) {
            int index = partition(data, lo, hi);
            quickHelper(data, lo, index - 1);
            quickHelper(data, index + 1, hi);
        }
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
        int pIndex = start;
        int middle = (int) (new Random().nextBoolean() ? Math.ceil((start + end) / 2f)
                : Math.floor((start + end) / 2f));
        int median = median(data[start], data[middle], data[end], start, middle, end);
        if (!(end - start == 0)) {
            pIndex = median;
        }
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

            if (x == pivot) {
                if (new Random().nextBoolean()) {
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

    /*
     * DUTCH FLAG OPTIMIZATION
     * 
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
    public static int[] partitionDutch(int[] data, int lo, int hi) {
        int pIndex = lo;
        int middle = (int) (new Random().nextBoolean() ? Math.ceil((lo + hi) / 2f) : Math.floor((lo + hi) / 2f));
        int median = median(data[lo], data[middle], data[hi], lo, middle, hi);
        if (!(hi - lo == 0)) {
            pIndex = median;
        }
        int pivot = data[pIndex];
        System.out.println(pivot);
        data[pIndex] = data[lo];
        data[lo] = pivot;
        lo++;
        int[] ltgt = new int[] { lo, hi };
        // |-----r1------|-----r2-----|-----r3-----|----r4----|
        int count = 0;
        while (lo <= hi) {
            count++;
            if (data[lo] > pivot) {
                while (data[hi] > pivot) {
                    hi--;
                    ltgt[1] = ltgt[1] - 1;
                    if(lo >= hi){
                        data[0]=data[ltgt[0]-1];
                        data[ltgt[0]-1]=pivot;
                        return ltgt;
                    }
                }
                if (data[hi] <= pivot) {
                    int temp = data[hi];
                    data[hi] = data[lo];
                    data[lo]=temp;
                    hi--;
                    ltgt[1] = ltgt[1] - 1;
                    if(data[lo]==pivot){
                        lo++;
                    }
                }
            }else if (data[lo] < pivot){
                int temp = data[lo];
                data[lo]=data[ltgt[0]];
                data[ltgt[0]]=temp;
                ltgt[0]++;
                lo++;
            }else{
                lo++;
            }
        }
        data[0]=data[ltgt[0]-1];
        data[ltgt[0]-1]=pivot;
        System.out.println(ltgt[0]);
        return ltgt;
    }

    public static int median(int a, int b, int c, int aInd, int bInd, int cInd) {
        if (a > b) {
            if (a > c) {
                if (b > c) {
                    return cInd;
                } else {
                    return bInd;
                }
            } else {
                return aInd;
            }
        } else {
            if (a > c) {
                return aInd;
            } else {
                if (b > c) {
                    return cInd;
                } else {
                    return bInd;
                }
            }
        }
    }
}