/**
 * 
 */
package github.gusvmx.util;

/**
 * @author gus
 *
 */
public final class Partition {

	/** The array to sort. */
	private final int[] array;
	/** The start index of the region of elements to sort. */
	private final int startIndex;
	/** The end index of the region of elements to sort. */
	private final int endIndex;
	/** The current partition level. */
	private final int partitionLevel;
	/**
	 * @param array The array to sort.
	 * @param startIndex The start index of the region of elements to sort.
	 * @param endIndex The end index of the region of elements to sort.
	 * @param partitionLevel The current partition level.
	 */
	public Partition(final int[] array, final int startIndex, final int endIndex, final int partitionLevel) {
		this.array = array;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.partitionLevel = partitionLevel;
	}
	/**
	 * @return the array
	 */
	public int[] getArray() {
		return array;
	}
	/**
	 * @return the startIndex
	 */
	public int getStartIndex() {
		return startIndex;
	}
	/**
	 * @return the endIndex
	 */
	public int getEndIndex() {
		return endIndex;
	}
	/**
	 * @return the partitionLevel
	 */
	public int getPartitionLevel() {
		return partitionLevel;
	}
	
}
