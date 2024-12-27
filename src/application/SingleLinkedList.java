package application;

public class SingleLinkedList<T extends Comparable<T>> {
	private Node<T> head;
	private Node<T> dummy = head;

	public Node<T> getHead() {
		return head;
	}

	public void setHead(Node<T> head) {
		this.head = head;
	}

	public Node<T> getDummy() {
		return dummy;
	}

	public void setDummy(Node<T> dummy) {
		this.dummy = dummy;
	}

	public void insert(T data) {
		Node newNode = new Node(data);
		Node<T> prev = null;
		Node<T> curr = head;
		for (; curr != null && curr.getData().compareTo(data) < 0; prev = curr, curr = curr.getNext())
			;
		if (prev == null) { // case 1:insert first.
			newNode.setNext(curr);
			head = newNode;
		} else if (curr == null) { // case 3:insert last.
			prev.setNext(newNode);
		} else { // case 2:insert between.
			newNode.setNext(curr);
			prev.setNext(newNode);

		}
	}

	public Node delete(T data) {

		Node newNode = new Node(data);
		Node<T> prev = null;
		Node<T> curr = head;
		for (; curr != null && curr.getData().compareTo(data) < 0; prev = curr, curr = curr.getNext())
			;
		if (curr != null && curr.getData().compareTo(data) == 0) {
			if (head == curr) // case 1: delete first node
				head = curr.getNext();
			else if (curr.getNext() == null) // case 2: delete between two nodes
				prev.setNext(null);
			else
				prev.setNext(curr.getNext()); // case 3: delete last node
			return curr;
		}

		return null;
	}

	public void update(T oldData, T newData) {
		insert(newData);
		delete(oldData);

	}

	public Node<T> find(T data) {
		Node<T> curr = head;
		while (curr != null && curr.getData().compareTo(data) <= 0) {
			if (curr.getData().compareTo(data) == 0)
				return curr;
			curr = curr.getNext();
		}
		return null;

	}

	public Node<T> goToNext(T data) {
		if (data == null)
			return null;
		Node<T> curr = head;
		for (; curr != null && curr.getData().compareTo(data) != 0; curr = curr.getNext())
			;
		if (curr == null)
			return null;
		return curr.getNext();
	}

	public SingleLinkedList<T> navigate(T data) {
		SingleLinkedList<T> resultNewList = new SingleLinkedList<>();
		Node<T> curr = head;
		while (curr != null && curr.getData().compareTo(data) !=0) {
			curr = curr.getNext();
		}
		if (curr != null) {
			curr = curr.getNext();
			while (curr != null) {
				resultNewList.insert(curr.getData());
				curr = curr.getNext();
			}
		}

		return resultNewList;
	}

	public void traverse() {
		Node<T> curr = head;
		System.out.print("Head -->");
		while (curr != null) {
			System.out.print(curr + " -->");
			curr = curr.getNext();
		}
		System.out.println("Null");
	}

	@Override
	public String toString() {
		String res = "Head --> ";
		Node<T> curr = this.head;
		while (curr != null) {
			res += curr + "-->";
			curr = curr.getNext();
		}
		return res + "null";
	}

}
