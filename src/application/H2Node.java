package application;

import application.AVLTree;

public class H2Node<T extends Comparable<T>> {
	private T data;
	private char flag;
	private AVLTree<T> avlTree;


	public AVLTree<T> getAvlTree() {
		return avlTree;
	}

	public void setAvlTree(AVLTree<T> avlTree) {
		this.avlTree = avlTree;
	}

	public H2Node(T data) {
		super();
		setData(data);
		setFlag('E');

	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public char getFlag() {
		return flag;
	}

	public void setFlag(char flag) {
		this.flag = flag;
	}

//	public String toString() {
//		String dataString = (data != null) ? data.toString() : "";
//		return (getFlag() == 'E') ? dataString : String.format("[%s  |  %s]", dataString, getFlag());
//	}
	@Override
	public String toString() {
		return String.format("[%s | %s]", data.toString(), flag);
	}

}
