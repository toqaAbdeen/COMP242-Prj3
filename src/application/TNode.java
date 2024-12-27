package application;

public class TNode<T extends Comparable<T>> implements Comparable<TNode<T>>{
	private T data;
	private TNode left;
	private TNode right;

	public TNode(T data) {
		super();
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public TNode getLeft() {
		return left;
	}

	public void setLeft(TNode left) {
		this.left = left;
	}

	public TNode getRight() {
		return right;
	}

	public void setRight(TNode right) {
		this.right = right;
	}

	public boolean isLeaf() {
		return left == null && right == null;
	}

	public boolean hasLeft() {
		return left != null;
	}

	public boolean hasRight() {
		return right != null;
	}

	@Override
	public String toString() {
		return "" + data;
	}

	@Override
	public int compareTo(TNode<T> o) {
		// TODO Auto-generated method stub
		return 0;
	}
}