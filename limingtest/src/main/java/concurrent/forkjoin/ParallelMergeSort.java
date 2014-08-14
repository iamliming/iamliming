package concurrent.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * TODO 类的功能描述。
 *
 * @author liming
 * @version 2.2.0
 * @date 2014-08-14 11:02
 * @id $Id$
 */
public class ParallelMergeSort {

	private static ForkJoinPool threadPool;
	private static final int THRESHOLD = 16;

	private static void sort(Comparable[] objArr) {

		Comparable[] destArr = new Comparable[objArr.length];
		threadPool.invoke(new SortTask(objArr, destArr, 0, objArr.length - 1));
	}

	static class SortTask extends RecursiveAction {

		private Comparable[] sourceArr;
		private Comparable[] destArr;
		private int lowIndex, upperIndex;

		SortTask(Comparable[] sourceArr, Comparable[] destArr, int lowIndex, int upperIndex) {

			this.upperIndex = upperIndex;
			this.sourceArr = sourceArr;
			this.destArr = destArr;
			this.lowIndex = lowIndex;
		}

		@Override
		protected void compute() {

			if (upperIndex - lowIndex < THRESHOLD) {
				insertionSort(sourceArr, lowIndex, upperIndex);
				return;
			}
			int midIdx = (lowIndex + upperIndex) >>> 1;
			invokeAll(new SortTask(sourceArr, destArr, lowIndex, midIdx), new SortTask(sourceArr, destArr, midIdx + 1, upperIndex));
			merge(sourceArr, destArr, lowIndex, midIdx, upperIndex);
		}
	}

	private static void merge(Comparable[] sourceArray, Comparable[] destArray, int lowerIndex,
			int midIndex, int upperIndex) {

		if (sourceArray[midIndex].compareTo(sourceArray[midIndex + 1]) <= 0) {
			return;
		}
		System.arraycopy(sourceArray, lowerIndex, destArray, lowerIndex, midIndex - lowerIndex + 1);
		int i = lowerIndex;
		int j = midIndex + 1;
		int k = lowerIndex;

		while (k < j && j <= upperIndex) {
			if (destArray[i].compareTo(sourceArray[j]) <= 0) {
				sourceArray[k++] = destArray[i++];
			} else {
				sourceArray[k++] = sourceArray[j++];
			}
		}
		System.arraycopy(destArray, i, sourceArray, k, j - k);
	}

	private static void insertionSort(Comparable[] objectArray, int lowerIndex, int upperIndex) {

		for (int i = lowerIndex + 1; i <= upperIndex; i++) {
			int j = i;
			Comparable tempObj = objectArray[j];
			while (j > lowerIndex && tempObj.compareTo(objectArray[j - 1]) < 0) {
				objectArray[j] = objectArray[j - 1];
				--j;
			}
			objectArray[j] = tempObj;
		}
	}

	public static Double[] createRandomData(int length) {

		Double[] data = new Double[length];
		for (int i = 0; i < length; i++) {
			data[i] = length * Math.random();
		}
		return data;
	}

	public static void main(String[] args) {

		int processors = Runtime.getRuntime().availableProcessors();
		System.out.println("NO. of process:" + processors);
		threadPool = new ForkJoinPool(processors);
		Double[] data = createRandomData(10000);
		System.out.println("original data:");
		for (Double d : data) {
			System.out.printf("%3.2f", (double) d);
			System.out.print(" ");
		}
		sort(data);
		System.out.println("sort array:");
		for (Double d : data) {
			System.out.printf("%3.2f", (double) d);
			System.out.print(" ");
		}
	}
}

