import java.util.Arrays;
import java.util.ListIterator;
import java.util.NoSuchElementException;

class Solution {
	public static void main(String[] args) {
		SimpleArrayList<Integer> e = new SimpleArrayList<>();
		e.add(1);
		e.add(2);
		e.add(3);
		e.add(4);

		SimpleArrayList<Integer> m = new SimpleArrayList<>();
		m.add(1);
		m.add(2);
		m.add(3);
		m.add(4);

		System.out.println(e.contains(1));
		System.out.println(e.contains(null));
		System.out.println(e.indexOf(1));
		System.out.println(e.lastIndexOf(1));
		System.out.println(e.equals(m));
		
		m.remove(2);
	}
}

/**
 * An implementation of SimpleArray List using Object []array
 *  - The default capacity of array is 10
 *  - implementation can store element at max 2 Billion i.e. Integer.MAX_VALUE
 *  - Size of array is auto-extensible by 75% of the last size
 *  - It should be externally synchronized
 *  - Capacity can be defined at declaration
 *  - Supports addition and removing elements from data structure
 *  - Supports lookup in worst case O(N) 
 *  - Adding and look up of null is possible
 *  
 *  @author Jatish Khanna
 */
class SimpleArrayList<E> {
	
	// Initial capacity if unavailable during declaration
	private static final int DEFAULT_ELEMENT_CAPACITY = 10;
	// Initial Empty array if unavailable during declaration
	private static final Object[] DEFAULT_EMPTY_ELEMENT_STORE = {};
	// To verify huge capacity requirements
	private static final int HUGE_ARRAY_CAPACITY = Integer.MAX_VALUE - DEFAULT_ELEMENT_CAPACITY;

	// The data store to keep element references
	private Object[] element;
	// Number of elements present in list 
	// (different from total capacity &quot;element&quot; data store can hold)
	private int listSize;

	// New data store initialized with EMPTY store i.e. DEFAULT_EMPTY_ELEMENT_STORE
	public SimpleArrayList() {
		super();
		element = DEFAULT_EMPTY_ELEMENT_STORE;
	}

	/**
	 * Initialize data store with the &quot;capacity&quot;
	 *  - Capacity should be greater than zero
	 *  - Initialized with DEFAULT ELEMENT empty store when capacity is zero
	 *  
	 * @param capacity - initial capacity for element data store
	 * @throws IllegalArgumentException specifies capacity should be positive or zero
	 */
	public SimpleArrayList(int capacity) {
		super();
		if (capacity < 0)
			throw new IllegalArgumentException("Capacity should be postive or 0");

		element = capacity > 0 ? new Object[capacity] : DEFAULT_EMPTY_ELEMENT_STORE;
	}

	/**
	 * Append new element at the end of list 
	 *  - ensure the capacity for &quot;element&quot; data store
	 * 
	 * @param data object reference to be appended in &quot;element&quot; data store 
	 * @return listSize the current listSize 
	 */
	public int add(E data) {
		ensureCapacity(listSize + 1);
		element[listSize++] = data;
		return listSize;
	}

	/**
	 * removes the reference from &quot;element&quot; data store at provided index
	 *  - check if position is valid
	 *  - stores data to be returned
	 *  - Utilizes System.arraycopy implementation, reducing the list to list - 1
	 * 
	 * @param index position at which value will be removed
	 * @return data provides the value have been removed
	 */
	@SuppressWarnings("unchecked")
	public E remove(int index) {
		checkRange(index);

		E data = (E) element[index];
		int copyLegth = listSize - index - 1;
		if (copyLegth > 0)
			System.arraycopy(element, index + 1, element, index, copyLegth);
		
		element[--listSize] = null;
		return data;
	}

	/**
	 * Returns the &quot;element&quot; from data store at index provided
	 *  - validate index is within bounds
	 *  
	 * @param index position at which value is present
	 * @return element reference at index
	 */
	@SuppressWarnings("unchecked")
	public E get(int index) {
		checkRange(index);
		return (E) element[index];
	}
	
	/**
	 * check index if in bounds
	 * 
	 * @param index value should be smaller than listSize
	 */
	private void checkRange(int index) {
		if (index - listSize >= 0)
			throw new IndexOutOfBoundsException();
	}

	/**
	 * Ensure the capacity of &quot;element&quot; data store is sufficient
	 *   - Check if expected capacity is lower than available capacity
	 *   - Ask for extra capacity if required capacity is lower than available
	 *   - request DEFAULT_ELEMENT_CAPACITY in case expected capacity is lower than defined limit
	 *   
	 * @param expectedSize required space to ensure new elements can be appended
	 */
	private void ensureCapacity(int expectedSize) {
		if (expectedSize - element.length > 0)
			increaseCapacity(Math.max(DEFAULT_ELEMENT_CAPACITY, expectedSize));
	}

	/**
	 * Increase the capacity to ensure new elements can be stored in &quot;element&quot; data store 
	 *  - Get the current capacity
	 *  - Calculate the capacity to increase - 75% of the current capacity
	 *  - check if array requires HUGE capacity i.e. Integer.MAX_VALUE
	 *  - Copy the contents of current &quot;element&quot; data store  into new &quot;element&quot; data store 
	 *  
	 * @param initializeToCapcity required capacity for &quot;element&quot; data store 
	 */
	private void increaseCapacity(int initializeToCapcity) {
		int currentCapacity = element.length;
		int newLength = currentCapacity + (currentCapacity >> 1) + (currentCapacity >> 2);

		if (initializeToCapcity - newLength > 0) {
			newLength = initializeToCapcity;
		}
		if (newLength - HUGE_ARRAY_CAPACITY > 0) {
			newLength = fetchHugeValue(initializeToCapcity);
		}
		element = Arrays.copyOf(element, newLength);
	}
	
	/**
	 * Calculate value to be initialized for HUGE_ARRAY_CAPACITY
	 *  - Capacity to be initialized is over Integer.MAX_VALUE results negative value
	 * 
	 * @param initializeToCapcity huge capacity to be initialized 
	 * @return Integer.MAX_VALUE  max capacity to store references
	 * @throws OutOfMemoryError when capacity is over Integer.MAX_VALUE
	 */

	private int fetchHugeValue(int initializeToCapcity) {
		if (initializeToCapcity < 0)
			throw new OutOfMemoryError();
		return Integer.MAX_VALUE;
	}

	/**
	 * Size of &quot;element&quot; data store 
	 * @return listSize number of references stored by  &quot;element&quot; data store 
	 */
	public int size() {
		return listSize;
	}

	/**
	 * Validated the  &quot;element&quot; data store  if isEmpty?
	 * @return true if listSize == 0; else it return false
	 */
	public boolean isEmpty() {
		return listSize == 0;
	}

	/**
	 * Check if reference is available in  &quot;element&quot; data store 
	 *  - calculates the index of first find for Object reference
	 *  
	 * @param check object reference to be verified in data store
	 * @return true if object reference is found in  &quot;element&quot; data store; else returns false 
	 */
	public boolean contains(Object check) {

		return indexOf(check) >= 0;
	}

	/**
	 * Returns the index of first occurrence in  &quot;element&quot; data store 
	 *  - if object to lookup is null - return first index of null reference 
	 *  - if object to lookup is non-null - return first index of references
	 *  
	 * @param check object reference to lookup in list; &quot;null&quot; is also possible
	 * @return index of the object reference in list; else return -1
	 */
	public int indexOf(Object check) {
		if (check == null) {
			for (int i = 0; i < listSize; i++)
				if (element[i] == null)
					return i;
		} else {
			for (int i = 0; i < listSize; i++)
				if (check.equals(element[i]))
					return i;
		}
		return -1;
	}

	/**
	 * Returns the index of last occurrence in  &quot;element&quot; data store 
	 *  - if object to lookup is null - return last index of null reference 
	 *  - if object to lookup is non-null - return last index of references
	 *  
	 * @param check object reference to lookup in list; &quot;null&quot; is also possible
	 * @return index of the object reference in list; else return -1
	 */
	public int lastIndexOf(Object check) {

		if (check == null) {
			for (int i = listSize - 1; i >= 0; i--)
				if (element[i] == null)
					return i;
		} else {
			for (int i = listSize - 1; i >= 0; i--)
				if (check.equals(element[i]))
					return i;
		}
		return -1;
	}

	/**
	 * Compare object references parameter with &quot;this&quot; reference
	 *  - verify if argument reference is non-null
	 *  - the runtime class of both reference should be same
	 *  
	 *  - if both object reference are same (return true)
	 *  
	 *  - if both the references are list (might be empty list) 
	 *  - fetch custom ListIteratorImpl for each object reference (considering objects are list)
	 *  - compare size of both list (return false in case of different)
	 *  - Iterate over each element from both the list until hastNext() return true 
	 *  - Two elements are equals if
	 *  	Element of &quot;this&quot; reference is null and parameter &quot;check&quot; is null
	 *      Element of &quot;this&quot; reference equals (parameter &quot;check&quot; ) evaluates true
	 *      
	 * - returns true if both the list iterator (this, check) references has no elements to process 
	 *      
	 *  @param check object reference to be evaluated for comparison
	 *  @return true if two objects (this, check) are equal by contract of equals; else return false
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object check) {

		if (check == null || check.getClass() != this.getClass())
			return false;
		if (this == check)
			return true;

		ListIterator<E> thisListItr = listIterator();
		ListIterator<?> checkListItr = ((SimpleArrayList<?>) check).listIterator();

		E currentElement;
		Object compareElement;

		if (this.size() != ((SimpleArrayList<E>) check).size())
			return false;

		while (thisListItr.hasNext() && checkListItr.hasNext()) {
			currentElement = thisListItr.next();
			compareElement = checkListItr.next();
			if (!(currentElement == null ? compareElement == null : currentElement.equals(compareElement))) {
				return false;
			}
		}

		return !(thisListItr.hasNext() || checkListItr.hasNext());

	}

	/**
	 * Computes the hasCode value for object reference
	 *  - Stream over all the element 
	 *  - filter non-null element in the list
	 *  - map to the hashCode of current Object reference
	 *  - reduce to single hash code for the complete list
	 *  
	 * @return hashCode - computed hashCode for list
	 */
	@Override
	public int hashCode() {
		
//		int hashCode = 1;
//		for (Object e : this.element) {
//			hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
//		}
//		return hashCode;
		return Arrays.stream(this.element)
				.filter(element -> element != null)
				.map(element -> element.hashCode())
//				.forEach(System.out::println);
				.reduce(1, (hashCode1, element) -> 31 * hashCode1+ element);
		

	}

	/**
	 * Provides a new instance of custom listIterator over the list
	 * 
	 * @return new instance of ListIteraorImpl starting at index 0
	 */
	private ListIterator<E> listIterator() {
		return new ListIteratorImpl(0);
	}

	/**
	 * Custom implementation of LisIterator as ListIteratorImpl
	 *  - tape operations possible - next and previous
	 *  - validity tape operations - hasNext and hasPrevious
	 *  - nextIndex and previousIndex operations for iteration
	 * 
	 * @author Jatish Khanna
	 *
	 */
	
	private class ListIteratorImpl implements ListIterator<E> {
		// stores current position for iterator cursor
		int position;
		// previous cursor value for iterator
		int prevPosition;

		/**
		 * A new instance of ListIterator 
		 * @param position the index for which ListIterator is initialized
		 */
		public ListIteratorImpl(int position) {
			super();
			this.position = position;
		}

		/**
		 *  Check for next element availability in iterator
		 *  
		 * @return true if next element is available for sequential iteration
		 */
		@Override
		public boolean hasNext() {
			return listSize > position;
		}

		/**
		 * Provide the next element in iterator
		 *  - check if next element is available
		 *  - update pointer to store previous and next position
		 *  - return element as E from  &quot;element&quot; data store 
		 *  
		 *  @return E object reference from the next sequential iteration
		 *  @throws NoSuchElementException when iterator accessing out of bounds
		 * 
		 * @see java.util.ListIterator#next()
		 */
		@SuppressWarnings("unchecked")
		@Override
		public E next() {
			if (hasNext()) {
				prevPosition = position;
				position++;
				return (E) SimpleArrayList.this.element[prevPosition];
			} else {
				throw new NoSuchElementException();
			}
		}

		/**
		 *  Check previous element availability in iterator
		 *  
		 * @return true if previous element is available for sequential iteration
		 */
		@Override
		public boolean hasPrevious() {

			return position > 0;
		}

		/**
		 * Provide the previous element in iterator
		 *  - check if previous element is available
		 *  - update pointer to store previous and next position
		 *  - return element as E from  &quot;element&quot; data store 
		 *  
		 *  @return E object reference from the previous sequential iteration
		 *  @throws NoSuchElementException when iterator accessing out of bounds
		 * 
		 * @see java.util.ListIterator#previous()
		 */
		@SuppressWarnings("unchecked")
		@Override
		public E previous() {
			if (hasPrevious()) {
				position = prevPosition;
				prevPosition--;
				return (E) SimpleArrayList.this.element[prevPosition];
			} else {
				throw new NoSuchElementException();
			}
		}

		/**
		 *  Provide next element index availability in iterator
		 *  
		 * @return position if next element is available for sequential iteration
		 */
		@Override
		public int nextIndex() {
			return position;
		}
		
		/**
		 *  Provide previous element index availability in iterator
		 *  
		 * @return prevPosition if previous element is available for sequential iteration and -1 for beginning of cursor
		 */
		@Override
		public int previousIndex() {
			return prevPosition;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("The operation is unavailable");
		}

		@Override
		public void set(E e) {
			throw new UnsupportedOperationException("The operation is unavailable");
		}

		@Override
		public void add(E e) {
			throw new UnsupportedOperationException("The operation is unavailable");
		}

	}

}
