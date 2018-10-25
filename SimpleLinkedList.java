import java.util.NoSuchElementException;

class SimpleLinkedList<E> {

	static class Node<E> {
		Node<E> next;
		E element;
		Node<E> previous;

		Node(Node<E> previous, E element, Node<E> next) {
			super();
			this.previous = previous;
			this.element = element;
			this.next = next;
		}

		public E getElement() {
			return element;
		}
	}

	private enum Operations {
		INSERT, REMOVE, READ
	};

	private final int INDEX_NODE_ZERO = 0;

	int listSize;
	Node<E> first;
	Node<E> last;

	public SimpleLinkedList() {
		super();
		listSize = 0;
		first = null;
		last = null;
	}

	public int size() {
		return listSize;
	}

	// private void joinFirst(E node) {
	// Node<E> head = first;
	// final Node<E> newNode = new Node<>(null, node, head);
	// first = newNode;
	//
	// if (head != null) {
	// head.previous = first;
	// } else {
	// last = newNode;
	// }
	// ++listSize;
	// }

	private void checkBounds(int index, Operations operation) {
		if (!verifyPoistion(index, operation))
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
	}

	private boolean verifyPoistion(int index, Operations operation) {
		if (operation == Operations.INSERT && index >= INDEX_NODE_ZERO && index <= listSize)
			return true;
		else if ((operation == Operations.REMOVE || operation == Operations.READ) && index >= INDEX_NODE_ZERO
				&& index < listSize)
			return true;
		return false;
	}

	private Node<E> contains(int index) {
		Node<E> nodeAtIndex = null;

		if (index < (listSize >> 1)) {
			nodeAtIndex = first;
			for (int position = 0; position < index; position++) {
				nodeAtIndex = nodeAtIndex.next;
			}
		} else {
			nodeAtIndex = last;
			for (int position = listSize - 1; position > index; position--) {
				nodeAtIndex = nodeAtIndex.previous;
			}
		}
		return nodeAtIndex;
	}

	public int indexOf(Object object) {
		Node<E> current = first;
		int index = 0;
		if (object == null) {
			while (current != null) {
				if (current.element == null) {
					return index;

				}
				current = current.next;
				index++;
			}
		} else {
			while (current != null) {
				if (current.element.equals(object)) {
					return index;
				}
				current = current.next;
				index++;
			}
		}
		return -1;
	}

	public int lastIndexOf(Object object) {
		Node<E> current = last;
		int index = size() - 1;
		if (object == null) {
			while (current != null) {
				if (current.element == null) {
					return index;

				}
				current = current.previous;
				index--;
			}
		} else {
			while (current != null) {
				if (current.element.equals(object)) {
					return index;
				}
				current = current.previous;
				index--;
			}
		}
		return -1;
	}

	private Node<E> findNode(Object object) {
		Node<E> current = first;
		if (object == null) {
			while (current != null) {
				if (current.element == null) {
					return current;

				}
				current = current.next;
			}
		} else {
			while (current != null) {
				if (current.element.equals(object)) {
					return current;
				}
				current = current.next;
			}
		}
		return null;
	}

	public void clear() {
		Node<E> current = first;
		Node<E> next;
		while (current != null) {
			next = current.next;

			current.previous = null;
			current.element = null;
			current.next = null;

			current = next;
		}
		listSize = 0;
		first = last = null;
	}

	public boolean add(E node) {
		addLast(node);
		return true;
	}

	public void add(int index, E node) {
		checkBounds(index, Operations.INSERT);

		if (index == listSize) {
			addLast(node);
		} else {
			joinNode(node, contains(index));
		}
	}

	public void addFirst(E node) {
		add(INDEX_NODE_ZERO, node);
	}

	public void addLast(E node) {
		joinLast(node);
	}

	private void joinLast(E node) {
		Node<E> tail = last;
		final Node<E> newNode = new Node<>(tail, node, null);

		last = newNode;
		if (tail != null) {
			tail.next = last;
		} else {
			first = newNode;
		}
		++listSize;
	}

	private void joinNode(E node, Node<E> successor) {
		Node<E> predecessor = successor.previous;
		final Node<E> newNode = new Node<>(predecessor, node, successor);

		successor.previous = newNode;
		if (predecessor != null) {
			predecessor.next = newNode;
		} else {
			first = newNode;
		}
		listSize++;
	}

	public E remove() {
		return removeFirst();
	}

	public E removeFirst() {
		Node<E> node = removeNodeAtIndex(INDEX_NODE_ZERO);
		if (node == null)
			throw new NoSuchElementException();
		return getElementOrNull(node);
	}

	public E removeLast() {
		Node<E> node = removeNodeAtIndex(size() - 1);
		if (node == null)
			throw new NoSuchElementException();
		return getElementOrNull(node);
	}

	public E remove(int index) {
		Node<E> node = removeNodeAtIndex(index);
		if (node == null)
			throw new NoSuchElementException();
		return getElementOrNull(node);
	}

	public E remove(Object object) {
		Node<E> node = findNode(object);

		if (node == null)
			throw new NoSuchElementException();

		node = removeNode(node);
		return getElementOrNull(node);

	}

	private Node<E> removeNodeAtIndex(int index) {
		checkBounds(index, Operations.REMOVE);
		final Node<E> node = contains(index);

		return removeNode(node);
	}

	private Node<E> removeNode(Node<E> object) {
		final Node<E> node = object;
		if (node == null)
			return null;

		final Node<E> predecessor = node.previous;
		final Node<E> successor = node.next;

		if (predecessor != null) {
			predecessor.next = successor;
		} else {
			first = successor;
		}

		if (successor != null) {
			successor.previous = predecessor;
		} else {
			last = predecessor;
		}

		listSize--;
		return node;
	}

	public E poll() {
		return pollFirst();
	}

	public E pollFirst() {
		Node<E> node = contains(INDEX_NODE_ZERO);
		removeNode(node);
		return (node == null) ? null : node.getElement();
	}

	public E pollLast() {
		Node<E> node = contains(size() - 1);
		removeNode(node);
		return (node == null) ? null : node.getElement();
	}

	public E get(int index) {
		checkBounds(index, Operations.READ);
		Node<E> node = contains(index);
		return node.element;
	}

	public E getFirst() {
		Node<E> node = contains(INDEX_NODE_ZERO);
		return node.element;
	}

	public E getLast() {
		Node<E> node = contains(size() - 1);
		return node.element;
	}

	private E getElementOrNull(Node<E> node) {
		return (node == null) ? null : node.element;
	}

	public E set(int index, E element) {
		checkBounds(index, Operations.READ);

		Node<E> node = contains(index);
		E data = getElementOrNull(node);
		node.element = element;
		return data;
	}

}

public class Solution {

	public static void main(String[] args) {

//		LinkedList<Integer> l = new LinkedList<>();
		SimpleLinkedList<Integer> in = new SimpleLinkedList<>();

		// System.out.println(in.get(0));
		// System.out.println(l.get(0));

		System.out.println(in.add(111));
		System.out.println(in.get(0));

		in.addFirst(11);
		System.out.println(in.get(0));

		in.addLast(123);
		System.out.println(in.get(in.size() - 1));

		in.add(1, 1);
		System.out.println(in.get(1));

		in.clear();

		System.out.println(in.add(null));
		System.out.println(in.get(0));

		in.addFirst(null);
		System.out.println(in.get(0));

		in.addLast(null);
		System.out.println(in.get(in.size() - 1));

		in.add(1, null);
		System.out.println(in.get(1));

		// in.clear();

		System.out.println(in.size());

		// in.add(1, 1);

		// System.out.println(in.remove());
		// l.add(null);
		in.remove();
		System.out.println(in.size());
		in.remove();
		System.out.println(in.size());
		in.remove();
		System.out.println(in.size());
		in.remove();
		System.out.println(in.size());
		// in.remove();

		in.clear();
		in.add(1);
		// in.remove(1);
		in.remove(0);

		in.addLast(1111);
		System.out.println(in.size());
		System.out.println(in.get(0));

		Integer primary = new Integer(1111);
		in.remove(primary);

		// System.out.println(in.get(0));

		in.add(null);
		System.out.println(in.size());
		in.remove(null);
		System.out.println(in.size());

		in.add(1234);
		System.out.println(in.size());
		in.removeFirst();
		System.out.println(in.size());

		in.addLast(1234);
		System.out.println(in.size());
		in.removeFirst();
		System.out.println(in.size());

		in.addFirst(1234);
		System.out.println(in.size());
		in.removeFirst();
		System.out.println(in.size());

		in.addLast(1234);
		System.out.println(in.size());
		in.removeLast();
		System.out.println(in.size());

		in.add(300);
		in.add(301);
		in.add(302);
		in.add(303);
		in.add(304);
		System.out.println(in.getFirst());
		System.out.println(in.get(1));
		in.removeFirst();
		System.out.println(in.getFirst());
		System.out.println(in.get(0));

		System.out.println(in.getLast());
		System.out.println(in.get(in.size() - 1));
		in.removeLast();
		System.out.println(in.getLast());
		System.out.println(in.get(in.size() - 1));

		System.out.println(in.poll());
		System.out.println(in.poll());
		System.out.println(in.poll());
		System.out.println(in.poll());

		System.out.println(in.pollFirst());
		System.out.println(in.pollLast());

		in.add(300);
		in.add(301);
		in.add(302);
		System.out.println(in.getFirst());
		System.out.println(in.pollFirst());
		System.out.println(in.getFirst());

		System.out.println(in.getLast());
		System.out.println(in.pollLast());
		System.out.println(in.getLast());

		System.out.println(in.indexOf(302));
		System.out.println(in.indexOf(301));
		in.add(301);
		in.add(302);
		in.add(301);
		System.out.println(in.indexOf(301));
		System.out.println(in.lastIndexOf(301));
		System.out.println(in.size());

		System.out.println(in.set(0, 302));
		System.out.println(in.indexOf(301));
		System.out.println(in.indexOf(302));
		System.out.println(in.lastIndexOf(302));

	}
}
