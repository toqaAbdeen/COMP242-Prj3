package application;

public class linkedQueue<T extends Comparable<T>> {

	private QNode<T> first;
	private QNode<T> last;

	public QNode<T> getFirst() {
		return first;
	}

	public void setFirst(QNode<T> first) {
		this.first = first;
	}

	public QNode<T> getLast() {
		return last;
	}

	public void setLast(QNode<T> last) {
		this.last = last;
	}

	public void enqueue(T data) {
		QNode<T> newNode = new QNode<>(data);
		if (isEmpty())
			first = newNode;
		else
			last.setNext(newNode);
		last = newNode;
	}

	public T dequeue() {
		T front = getFront();
		if (!isEmpty())
			first = first.getNext();
		if (first == null)
			last = null;
		return front;

	}

	public boolean isEmpty() {
		return (first == null && last == null);
	}

	public void clear() {
		first = null;
		last = null;
	}

	public T getFront() {
		if (!isEmpty())
			return first.getData();
		return null;

	}

	public T getBack() {
		if (!isEmpty())
			return last.getData();
		return null;

	}

	public String toString() {
		if (isEmpty()) {
			return "Queue: empty";
		}
		StringBuilder sb = new StringBuilder("Queue: front <--");
		QNode<T> curr = first;
		while (curr != null) {
			sb.append(curr.getData()).append("<--");
			curr = curr.getNext();
		}
		sb.setLength(sb.length() - 3); // Remove the last " -> "
		sb.append("<-- back");
		return sb.toString();
	}

	public int length_ittrariv() {
		int counter = 0;
		QNode<T> curr = first;
		while (curr != null) {
			counter++;

			curr = curr.getNext();
		}
		return counter;
	}

}
