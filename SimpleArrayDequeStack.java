import java.util.ArrayDeque;
import java.util.NoSuchElementException;

class SimpleArrayDequeStack<E> {

	Object[] elements;
	int head;
	int tail;

	private static final int MIN_INITIAL_CAPACITY = 16;

	private static final int MAX_STORE_CAPACITY = 1 << 30;

	public SimpleArrayDequeStack() {
		super();
		elements = new Object[MIN_INITIAL_CAPACITY];
	}

	public SimpleArrayDequeStack(int capacity) {
		if (capacity < 0)
			throw new IllegalArgumentException("Invalid initial size specified");

		int newCapacity = Math.max(MIN_INITIAL_CAPACITY, convertCapacityPowerTwo(capacity));
		elements = new Object[newCapacity];

	}

	private int convertCapacityPowerTwo(int capacity) {
		int calculate = (int) (Math.log(capacity) / Math.log(2));
		return (int) (Math.pow(2, calculate));
	}

	private void ensureCapacity() {
		int currentLength = elements.length;
		int currentHead = head;
		int elementsAfterHead = currentHead - currentHead;

		int newCapacity = currentHead << 1;
		if (newCapacity < 0) {
			throw new IllegalStateException("Queue too big");
		}

		Object[] newElements = new Object[newCapacity];
		System.arraycopy(elements, currentHead, newElements, 0, elementsAfterHead);
		System.arraycopy(elements, 0, newElements, elementsAfterHead, currentHead);

		elements = newElements;
		head = 0;
		tail = currentLength;
	}

	public boolean add(E data) {
		addLast(data);
		return true;
	}

	public void addFirst(E data) {
		if (data == null) {
			throw new NullPointerException("Specified element should be non-null");
		}
		head = head - 1;
		elements[(head) & (elements.length - 1)] = data;
		if (isEmpty())
			ensureCapacity();
	}

	public void addLast(E data) {
		if (data == null) {
			throw new NullPointerException("Specified element should be non-null");
		}
		elements[tail] = data;
		tail = (tail + 1) & (elements.length - 1);
		if (isEmpty())
			ensureCapacity();
	}

	public boolean offer(E data) {
		return offerLast(data);
	}

	public boolean offerFirst(E data) {

		addFirst(data);
		return true;
	}

	public boolean offerLast(E data) {

		addLast(data);
		return true;
	}

	public E remove() {
		return removeFirst();
	}

	public E removeFirst() {
		E data = pollFirst();
		if (data == null)
			throw new NoSuchElementException();
		return data;
	}

	public E removeLast() {
		E data = pollLast();
		if (data == null)
			throw new NoSuchElementException();
		return data;
	}

	public E poll() {
		return pollFirst();
	}

	@SuppressWarnings("unchecked")
	public E pollFirst() {
		E data = (E) elements[head];
		if (data == null)
			return null;

		elements[head] = null;
		head = ((head + 1) & (elements.length - 1));

		return data;
	}

	@SuppressWarnings("unchecked")
	public E pollLast() {
		int newTail = ((tail - 1) & (elements.length - 1));
		E data = (E) elements[newTail];

		if (data == null)
			return null;
		elements[newTail] = null;
		tail = newTail;

		return data;
	}

	public E peek() {
		return peekFirst();
	}

	@SuppressWarnings("unchecked")
	public E peekFirst() {
		E data = (E) elements[head];
		return data;
	}

	@SuppressWarnings("unchecked")
	public E peekLast() {
		int elementLast = ((tail - 1) & (elements.length - 1));
		E data = (E) elements[elementLast];
		return data;
	}

	public E element() {
		return getFirst();
	}

	public E getFirst() {
		E data = peekFirst();
		if (data == null)
			throw new NoSuchElementException();
		return data;
	}

	public E getLast() {
		E data = peekLast();
		if (data == null)
			throw new NoSuchElementException();
		return data;
	}

	public void push(E data) {
		addFirst(data);
	}

	public E pop() {
		return removeFirst();
	}

	public int size() {
		return (tail - head) & (elements.length - 1);
	}

	public boolean isEmpty() {
		return head == tail;
	}

	public boolean contains(Object object) {
		if (object == null)
			return false;
		int lastIndex = elements.length - 1;
		int index = head;
		Object element = elements[index];
		while (element != null) {
			if (object.equals(element))
				return true;
			index = (index + 1) & lastIndex;
			element = elements[index];
		}
		return false;
	}

	public void clear() {
		int lastIndex = elements.length - 1;
		int currentHead = head;
		int currentTail = tail;

		while (currentHead != currentTail) {
			elements[currentHead] = null;
			currentHead = ((currentHead + 1) & (lastIndex));
		}
		head = 0;
		tail = 0;
	}

	public E[] toArray() {
		@SuppressWarnings("unchecked")
		E[] readReplicaElements = (E[]) new Object[size()];
		return copyElements(readReplicaElements);
	}

	private E[] copyElements(E[] readReplicaElements) {

		if (head < tail) {
			System.arraycopy(elements, head, readReplicaElements, 0, size());
		} else if (head > tail) {
			int elementsAfterHead = elements.length - head;
			System.arraycopy(elements, head, readReplicaElements, 0, elementsAfterHead);
			System.arraycopy(elements, 0, readReplicaElements, elementsAfterHead, tail);
		}

		return readReplicaElements;
	}
}

public class Test {

	public static void main(String[] args) {
		
	
	}
}
