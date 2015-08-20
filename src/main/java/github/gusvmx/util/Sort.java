package github.gusvmx.util;

/**
 * @author gus
 *
 */
public class Sort {

	/**
	 * @param unsorted The array to sort.
	 */
	public static void quicksort(final int[] unsorted) {
		quicksort(unsorted, 0, unsorted.length - 1);
	}
	
	/**
	 * Sorts elements between the start and end index of the array using the quicksort algorithm.
	 * 
	 * Use 0 as start index and array.length - 1 as end index to sort the entire array.
	 * 
	 * @param array The array to sort.
	 * @param startIndex The start index to sort
	 * @param endIndex The end index to sort
	 */
	public static void quicksort(final int[] array, final int startIndex, final int endIndex) {
		if (startIndex < endIndex) {
			int pivotIndex = partition(array, startIndex, endIndex);
			quicksort(array, startIndex, pivotIndex - 1);
			quicksort(array, pivotIndex + 1, endIndex);
		}
	}

	/**
	 * Puts all the elements less or equal than the pivot, the last element of
	 * the array, on its left side. The rest of the elements are relocated to
	 * the right side of the pivot.
	 * 
	 * @param array The array to partition.
	 * @param startIndex The start index.
	 * @param endIndex The end index.
	 * @return The final pivot index that divides the less or equal elements and the greater elements than the pivot. 
	 */
	public static int partition(final int[] array, final int startIndex, final int endIndex) {
		int pivotChosenIndex = endIndex;
		int pivot = array[pivotChosenIndex];
		int pivotFinalIndex = startIndex;
		
		for (int i = startIndex; i < endIndex; i++) {
			if (array[i] <= pivot) {
				swap(array, pivotFinalIndex, i);
				pivotFinalIndex++;
			}
		}
		
		swap(array, pivotFinalIndex, pivotChosenIndex);
		return pivotFinalIndex;
	}

	/**
	 * Swaps positions within the array.
	 * @param array The source.
	 * @param i The first position to switch
	 * @param j The second position to switch.
	 */
	private static void swap(final int[] array, final int i, final int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	/**
	 * @param unsorted
	 * @param amountOfWorkers
	 */
	public static void parallelQuicksort(final int[] unsorted, final int amountOfWorkers, final int partitioningLevels) {
		PartitionDispatcher dispatcher = new PartitionDispatcher(amountOfWorkers);
		
		Partition partition = new Partition(unsorted, 0, unsorted.length - 1, 1);
		dispatcher.sortWhenAWorkerIsAvailable(partition);
		
		for (int i = 0; i < amountOfWorkers; i++) {
			QuicksortWorker worker = new QuicksortWorker(dispatcher, partitioningLevels);
			Thread thread = new Thread(worker);
			thread.start();
		}
		
		dispatcher.waitUntilFullySorted();
	}

}
