import java.util.concurrent.RecursiveAction;

public class MergeSortTask extends RecursiveAction {
    int[] arr;
    int lower, upper;
    public MergeSortTask(int[] arr, int lower, int upper) {
        this.arr = arr;
        this.lower = lower;
        this.upper = upper;
    }

    @Override
    protected void compute() {
        if (lower == upper){
            return;
        }
        else{
            MergeSortTask left = new MergeSortTask(arr, lower, (lower+upper)/2);
            MergeSortTask right = new MergeSortTask(arr, (lower+upper)/2 + 1, upper);
            invokeAll(left, right);
            merge(this.arr, lower, upper);
        }
    }

    private void merge(int[] arr, int lower, int upper) {
        int[] left = new int[(upper+lower)/2 - lower + 1];
        for (int i = lower; i <= (upper+lower)/2; i++){
            left[i-lower] = arr[i];
        }

        int[] right = new int[upper - (upper+lower)/2 - 1];
        for (int i = (upper+lower)/2 + 1; i <= upper; i++){
            left[i-(upper+lower)/2 - 1] = arr[i];
        }

        int resIndex = lower;
        int leftIndex = lower, rightIndex = (upper+lower)/2 + 1;
        while (leftIndex <= (upper+lower)/2 && rightIndex <= upper){
            if (arr[leftIndex] <= arr[rightIndex]) {
                arr[resIndex++] = left[leftIndex++ - lower]; // needed?
                //leftIndex++;
            }
            else{
                arr[resIndex++] = left[rightIndex++ - (upper+lower)/2 - 1]; // needed?
                //leftIndex++;
            }
        }

        if (leftIndex <= (upper+lower)/2) {
            while (resIndex <= upper){
                arr[resIndex++] = left[leftIndex - lower];
            }
        }

        if (rightIndex <= upper) {
            while (resIndex <= upper){
                arr[resIndex++] = left[rightIndex - (upper+lower)/2 - 1];
            }
        }
    }
}
