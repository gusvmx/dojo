package github.gusvmx.util;

/**
 * @author gus
 *
 */
public class QuicksortWorker implements Runnable {

	/***/
	private PartitionDispatcher dispatcher;
	/***/
	private int maxPartitioningLevels;
	/**
	 * @param dispatcher The partition dispatcher.
	 * @param partitioningLevels 
	 */
	public QuicksortWorker(final PartitionDispatcher dispatcher, final int partitioningLevels) {
		this.dispatcher = dispatcher;
		this.maxPartitioningLevels = partitioningLevels;
	}

	@Override
	public final void run() {
		Partition partition = dispatcher.getPartitionToSort();

		while (partition != null) {
			int currentPartitionLevel = partition.getPartitionLevel();
			if (canPartitionAgain(currentPartitionLevel)) {
				partition(partition, currentPartitionLevel);
			} else {
				Sort.quicksort(partition.getArray(), partition.getStartIndex(), partition.getEndIndex());
			}
			
			partition = dispatcher.getPartitionToSort();
		}
	}

	/**
	 * @param currentPartitionLevel The current partition.
	 * @return <code>true</code> if current partition level is less than the maximum partition levels.
	 */
	private boolean canPartitionAgain(final int currentPartitionLevel) {
		return currentPartitionLevel < this.maxPartitioningLevels;
	}

	/**
	 * @param partition The current partition to divide.
	 * @param currentPartitionLevel The current partition level.
	 */
	private void partition(final Partition partition, final int currentPartitionLevel) {
		int startIndex = partition.getStartIndex();
		int endIndex = partition.getEndIndex();
		int nextPartitionLevel = currentPartitionLevel + 1;
		if (startIndex < endIndex) {
			int[] array = partition.getArray();
			int pivotIndex = Sort.partition(array, startIndex, endIndex);
			Partition leftPartition = new Partition(array, startIndex, pivotIndex - 1, nextPartitionLevel);
			Partition rightPartition = new Partition(array, pivotIndex + 1, endIndex, nextPartitionLevel);
			
			dispatcher.sortWhenAWorkerIsAvailable(leftPartition);
			dispatcher.sortWhenAWorkerIsAvailable(rightPartition);
		}
	}

}
