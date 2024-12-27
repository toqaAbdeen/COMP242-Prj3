package application;

public class LinkedStack<T extends Comparable<T>> {
	private SNode<T> topNode;

	public void push(T data) {
		SNode<T> newNode = new SNode<T>(data);
		newNode.setNext(topNode);
		topNode = newNode;
	}

	public SNode<T> pop() {
		SNode<T> toDel = topNode;
		if (topNode != null)
			topNode = topNode.getNext();
		return toDel;
	}

	public SNode<T> peek() {
		return topNode;
	}

	public int length() {
		int length = 0;
		SNode<T> curr = topNode;
		while (curr != null) {
			length++;
			curr = curr.getNext();
		}
		return length;
	}

	public boolean isEmpty() {
		return (topNode == null);
	}

	public void clear() {
		topNode = null;
	}
}
