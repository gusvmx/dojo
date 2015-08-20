/**
 * 
 */
package github.gusvmx.util.test;

import github.gusvmx.util.Sort;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author gus
 *
 */
public class SortTest {

	@Test
	public final void sortNumbers() {
		int[] unsorted = new int[] {5, 10, 1, 3, 7, 4, 6, 11, 2};
		Sort.quicksort(unsorted);
		Assert.assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 10, 11}, unsorted);
	}
	
	@Test
	public final void sortHugeAmountOfNumbers() {
		int size = 100000000;
		int[] unsorted = buildUnsortedArray(size);
		long startTime = System.currentTimeMillis();
		Sort.quicksort(unsorted);
		System.out.println("Elapsed: " + (System.currentTimeMillis() - startTime));
		Assert.assertTrue(isSorted(unsorted));
	}
	
	private boolean isSorted(int[] array) {
		for (int i = 0; i < array.length; i++) {
			if (i + 1 < array.length) {
				if (array[i] > array[i + 1]) {
					return false;
				}
			}
		}
		return true;
	}
	
	@Test
	public final void sortAnAlreadyOrderedArray() {
		int[] sorted = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		Assert.assertTrue(isSorted(sorted));
		Sort.quicksort(sorted);
		Assert.assertTrue(isSorted(sorted));
	}
	
	@Test
	public final void sortHugeAmountOfNumbersInParallel() {
		int size = 100000000;
		int amountOfWorkers = 8;
		int maxPartitioningLevels = 15;
		int[] unsorted = buildUnsortedArray(size);
		long startTime = System.currentTimeMillis();
		Sort.parallelQuicksort(unsorted, amountOfWorkers, maxPartitioningLevels);
		System.out.println("Elapsed: " + (System.currentTimeMillis() - startTime));
		Assert.assertTrue(isSorted(unsorted));
	}
	
	private int[] buildUnsortedArray(int size) {
		int[] unsorted = new int[size];
		Random random = new Random();
		for (int i = 0; i < size; i++) {
			unsorted[i] = random.nextInt(size);
		}
		return unsorted;
	}
}
