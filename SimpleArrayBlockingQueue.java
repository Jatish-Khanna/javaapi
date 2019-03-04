import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

class ConsumerExchange {

	// Size of the queue - that define upper bound to store the count of element of
	// type T
	// BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

	// Custom ArrayBlockingQueu implementation using low level API
	Queue<Integer> queue = new LinkedList<>();
	private static final int QUEUE_SIZE = 10;

	// External lock instead of intrinsic lock
	Object lock = new Object();

	Random rndProducer = new Random();
	Random rndConsumer = new Random();

	private void producer() throws InterruptedException {
		while (true) {
			synchronized (lock) {
//				System.out.println("Producer Lock acquired");
				// Indefinite wait until lock has been acquired
				if (queue.size() >= QUEUE_SIZE) {
					// if the lock has not been acquired then
					// java.lang.IllegalMonitorStateException
					//wait();
					lock.wait();
				}
				queue.offer(rndProducer.nextInt(100));
//				System.out.println("Producer Lock acquired after waiting");
			}
		}
	}

	/**
	 * This way we can also implement BackPressure technique
	 *  - Here Consumer has high advantage of acquiring lock over consumer
	 *  - Code is biased towards consumer
	 *  - Or to mimic a high processing consumer whereas slow producer
	 * @throws InterruptedException
	 */
	
	private void consumer() throws InterruptedException {
		while (true) {
			synchronized (lock) {
				// System.out.println("Consumer acquired the lock");
				if (rndConsumer.nextInt(10) % 6 == 0 && !queue.isEmpty()) {
					// It will wait unit the lock has been relinquished
					queue.peek();
					System.out.println("Element: " + queue.poll() + " ;Queue size: " + queue.size());
					// If the lock has not been acquired then -
					// java.lang.IllegalMonitorStateException
					lock.notify();
				}
				// The synchronized block has not been completed
				Thread.sleep(200);
				// Now the waiting threads will be active
//				System.out.println("Consumer relinquished the lock");
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ConsumerExchange cExchange = new ConsumerExchange();
		Thread t1 = new Thread(() -> {
			try {
				cExchange.producer();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		Thread t2 = new Thread(() -> {
			try {
				cExchange.consumer();
			} catch (InterruptedException e) {
			}
		});

		t1.start();
		t2.start();

		t1.join();
		t2.join();
	}
}
