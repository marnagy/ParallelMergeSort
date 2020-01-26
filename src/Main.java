import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) {
        Random r = new Random();
	    int[] arr = new int[10_000_000];
	    for (int i = 0; i < arr.length; i++){
	        arr[i] = r.nextInt();
        }

	    long start = System.nanoTime();
	    int[] sortedArr = mergeSort(arr, 0, arr.length - 1);
        long end = System.nanoTime();
        long singleRes = end - start;
        System.out.println("Single-threaded: " + singleRes + "ns");

        start = System.nanoTime();
	    parallelSort(arr);
        end = System.nanoTime();
        long multiRes = end - start;
        System.out.println("Multi-threaded: " + multiRes + "ns");

        System.out.println("Multi - Single --> " + (multiRes - singleRes));
        System.out.println("Is multi faster? " + (multiRes - singleRes < 0 ));

        //Arrays.sort(arr);
	    for (int i = 0; i < arr.length; i++){
	        assert arr[i] == sortedArr[i];
        }
    }
    public static int[] mergeSort(int[] arr, int lower, int upper){
        if (upper - lower == 0){
            int[] ret = new int[1];
            ret[0] = arr[lower];
            return ret;
        }
        else{
            int[] lowerArr = mergeSort(arr, lower, (upper + lower)/2);
            int[] upperArr = mergeSort(arr, (upper + lower)/2 + 1, upper);

            int[] res = new int[upper - lower + 1];

            int l = 0, u = 0, resIndex = 0;
            while ( l < lowerArr.length && u < upperArr.length){
                if (lowerArr[l] < upperArr[u]){
                    res[resIndex++] = lowerArr[l++];
                }
                else{
                    res[resIndex++] = upperArr[u++];
                }
            }

            if (l < lowerArr.length){
                while (resIndex < res.length){
                    res[resIndex++] = lowerArr[l++];
                }
            }

            if (u < upperArr.length){
                while (resIndex < res.length){
                    res[resIndex++] = upperArr[u++];
                }
            }
            return res;
        }
    }
    public static void parallelSort(int[] arr) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(new MergeSortTask(arr, 0, arr.length-1));
    }
}
