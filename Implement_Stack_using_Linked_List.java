import java.util.Scanner;

class StackImplementationWithLinkedList {

	static class Node<T> {
		T key;
		Node<T> next;

		public Node(T key) {
			this.key = key;
		}
	}

	static class LinkedListStack<T> {
		private static final Node<Object> NOT_FOUND_NODE = new Node<>(-1);

		Node<T> top;
		int size;

		public void push(T key) {
			addFirst(key);
		}

		private void addFirst(T key) {
			Node<T> newNode = new Node<>(key);
			newNode.next = top;
			top = newNode;
			size++;
		}

		public Node<T> pop() {
			return removeFirst();
		}

		@SuppressWarnings("unchecked")
		private Node<T> removeFirst() {
			if (isEmpty())
				return (Node<T>) NOT_FOUND_NODE;
			Node<T> foundNode = top;
			top = top.next;
			size--;
			return foundNode;
		}

		public boolean isEmpty() {
			return size <= 0 || top == null;
		}
	}

	public static void main(String[] args) {
		LinkedListStack<Integer> lStack = new LinkedListStack<>();
//		lStack.push(10);
//		lStack.push(20);
//		lStack.pop();
//		lStack.pop();
//		lStack.pop();
//		lStack.pop();
//		System.out.println("Poped item is " + lStack.pop().key);
//		lStack.push(30);
//		lStack.push(40);
//		lStack.push(50);

		Scanner in = new Scanner(System.in);
		int queries = in.nextInt();
		int type;
		int data;
		for (int query = 0; query < queries; query++) {
			type = in.nextInt();
			switch(type) {
			case 1:
				data = in.nextInt();
				lStack.push(data);
				break;
			case 2:
				System.out.println(lStack.pop().key);
				break;
			}
		}

		in.close();
	}


}
