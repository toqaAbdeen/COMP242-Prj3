package application;


public class SNode<T extends Comparable<T>> {
	private T data;
	private SNode next;

	public SNode(T data) {
		super();
		this.data = data;
	}

	public SNode(T data, SNode next) {
		super();
		this.data = data;
		this.next = next;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public SNode getNext() {
		return next;
	}

	public void setNext(SNode next) {
		this.next = next;
	}

	@Override
	public String toString() {
		return "" + data;
	}

}
