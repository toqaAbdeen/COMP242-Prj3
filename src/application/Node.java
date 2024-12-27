package application;

public class Node<T extends Comparable<T>> {
	private T data;
	private Node next;

	public Node(T data) {
		super();
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Node getNext() {
		return next;
	}

	
	public void setNext(Node next) {
		this.next = next;
	}

	@Override
	public String toString() {
		return data.toString();
	}

}