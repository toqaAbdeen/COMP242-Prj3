package application;

import application.QueueStack;
import application.AVLTree;
import application.linkedQueue;

public class HopeDone<T extends Comparable<T>> {

	protected H2Node<T>[] table;
	protected int m, size;
	protected linkedQueue<T> queue = new linkedQueue<>();
	protected QueueStack<T> stack = new QueueStack<>();

	@SuppressWarnings("unchecked")
	public HopeDone(int dataSize) {
		m = dataSize * 2;
		for (; !isPrime(++m);)
			;
		table = new H2Node[m];
		for (int i = 0; i < m; i++) {
			table[i] = new H2Node<>(null);
			table[i].setAvlTree(new AVLTree<>());
		}
		size = 0;
	}

	public void add(T data) {
		if (size == m / 2) {
			rehash();
		}
		int index = Math.abs(data.hashCode()) % m;
		if (find(data) != null) {
			table[index].getAvlTree().insert(data);
		} else {
			int i = 0;
			for (; table[(index + i * i) % m].getFlag() != 'E' && i <= m; i++)
				;
			if (i > m) // a loop occurred
				System.out.println("This element cannot be added");
			else {
				index = (index + i * i) % m;
				table[index].setData(data);
				table[index].setFlag('F');
				++size;
			}
		}
	}

	public H2Node<T> find(T data) {
		int index = Math.abs(data.hashCode()) % m; // Use m instead of size
		int i = 0;
		char flag = table[(index + i * i) % m].getFlag();

		for (; table[(index + i * i) % m].getFlag() == 'D'
				|| flag == 'F' && table[(index + i * i) % m].getData().compareTo(data) != 0
						&& i <= m; flag = table[(index + (++i) * i) % m].getFlag())
			;

		if (flag == 'F' && table[(index + i * i) % m].getData().compareTo(data) == 0)
			return table[(index + i * i) % m];

		return null;
	}

	private boolean isPrime(int n) {
		for (int i = 2; i * i <= n; i++)
			if (n % i == 0)
				return false;
		return true;
	}

	public void traverse() {
		for (int i = 0; i < m; i++)
			if (table[i].getData() != null)
				System.out.print(i + "" + table[i] + " - ");
	}

	@SuppressWarnings("unchecked")
	protected void rehash() {
		int tempM = m;
		H2Node<T>[] tempTable = table;

		m *= 2;
		for (; !isPrime(++m);)
			;
		table = new H2Node[m];
		for (int i = 0; i < m; i++) {
			table[i] = new H2Node<>(null);
			table[i].setAvlTree(new AVLTree<>());
		}
		size = 0;

		for (int i = 0; i < tempM; i++)
			if (tempTable[i].getFlag() == 'F')
				add(tempTable[i].getData());
	}

	public String printTable(boolean includeEmpty) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < m; i++) {
			if (table[i] != null) {
				char flag = table[i].getFlag();
				if (includeEmpty || flag == 'F' || flag == 'D') {
					sb.append(i).append(": [").append(table[i].getData()).append(" | ").append(flag).append("]\n");
				}
			} else if (includeEmpty) {
				sb.append(i).append(": [empty | N/A]\n");
			}
		}
		return sb.toString();
	}

	public H2Node<T> delete(T data) {
		H2Node<T> deleted = find(data);
		if (deleted != null)
			deleted.setFlag('D');
		--size;
		return deleted;
	}

	public T test() {
		for (int i = 0; i < m; i++)
			if (table[i].getData() != null && table[i].getFlag() == 'F')
				queue.enqueue(table[i].getData());
		T data = queue.dequeue();
		stack.push(data);
		return data;
	}

	public T getPreviousData() {
		if (!stack.isEmpty()) {
			return stack.pop();
		}
		return null;
	}

}