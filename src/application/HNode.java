package application;

import application.AVLTree;
import application.Martyr;

public class HNode<T extends Comparable<T>> {
	private T data;
	private char flag;
	AVLTree<Martyr> tree;

	public HNode(T data) {
		super();
		setData(data);
		setFlag('E');
		tree = new AVLTree<Martyr>();

	}

	public AVLTree<Martyr> getTree() {
		return tree;
	}

	public void setTree(AVLTree<Martyr> tree) {
		this.tree = tree;
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

	public String toString() {
		String dataString = (data != null) ? data.toString() : "";
		return (getFlag() == 'E') ? dataString : String.format("[%s  %s |  %s]", dataString, getTree(), getFlag());
	}

}
