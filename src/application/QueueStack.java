package application;

public class QueueStack<T extends Comparable<T>> {

	linkedQueue<T> temp = new linkedQueue<>();
	linkedQueue<T> soso = new linkedQueue<>();
	linkedQueue<T> coco = new linkedQueue<>();

	public boolean isEmpty() {
		return soso.isEmpty();
	}

	public void push(T data) {

		coco.enqueue(data);
		while (!soso.isEmpty()) {
			coco.enqueue(soso.dequeue());
		}
		temp = soso;
		soso = coco;
		coco = temp;
	}

	public T pop() {
		if (soso.isEmpty())
			System.out.println("Empty stack");
		return soso.dequeue();
	}

	public T peek() {
		return soso.getFront();
	}

	public void clear() {
		soso.clear();
	}

	public void print() {
		System.out.print("Stack: ");
		if (!soso.isEmpty()) {
			for (int i = 0; i < soso.length_ittrariv(); i++) {
				T data = soso.dequeue();
				System.out.print(data + " ");
				soso.enqueue(data);
			}
			System.out.println();
		} else
			System.out.println("Empty stack:(");
	}

	public void pushAt(T data, int index) {
		if (index == 0)
			push(data);
		else if (isEmpty() && index > 0)
			System.out.println("Error");
		else {
			T t = pop();
			pushAt(data, index - 1);
			push(t);
		}
	}

}
