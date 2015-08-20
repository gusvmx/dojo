package github.gusvmx.util;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author gus
 *
 */
public class PartitionDispatcher {

	/** The amount of free workers. */
	private int totalfreeWorkers;
	/** The amount of workers. */
	private int amountOfWorkers;
	/** The partitions to sort. */
	private LinkedBlockingQueue<Partition> partitionsToSort;
	
	/**
	 * @param amountOfWorkers The amount of workers.
	 */
	public PartitionDispatcher(final int amountOfWorkers) {
		this.amountOfWorkers = amountOfWorkers;
		this.totalfreeWorkers = 0;
		this.partitionsToSort = new LinkedBlockingQueue<Partition>();
	}

	/**
	 * @param partition The partition to sort in the future.
	 */
	public final void sortWhenAWorkerIsAvailable(final Partition partition) {
		synchronized (partitionsToSort) {
			partitionsToSort.add(partition);
			partitionsToSort.notifyAll();
		}
	}
	
	/**
	 * @return The partition to sort.
	 */
	public final Partition getPartitionToSort() {
		synchronized (partitionsToSort) {
			while (partitionsToSort.isEmpty()) {
				try {
					if (++totalfreeWorkers == amountOfWorkers) {
						synchronized (this) {
							this.notifyAll();
						}
					}
					partitionsToSort.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					totalfreeWorkers--;
				}
			}
			Partition partition = null;
			try {
				partition = partitionsToSort.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return partition;
		}
	}

	/**
	 * Waits until all the workers have finished sorting the array.
	 */
	public final synchronized void waitUntilFullySorted() {
		while (totalfreeWorkers != amountOfWorkers) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println("Someone interrupted me!");
			}
		}
	}

}
