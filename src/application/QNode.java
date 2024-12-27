package application;

public class QNode<T extends Comparable<T>> {
	private T data;
	private QNode next;

	public QNode(T data) {
		super();
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public QNode getNext() {
		return next;
	}

	public void setNext(QNode next) {
		this.next = next;
	}

	@Override
	public String toString() {
		return data + "";
	}

}