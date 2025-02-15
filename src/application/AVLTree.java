package application;

public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> {

	private TNode<T> rotateRight(TNode<T> node) {
		TNode<T> leftChildOfN = node.getLeft();
		node.setLeft(leftChildOfN.getRight());
		leftChildOfN.setRight(node);
		return leftChildOfN;
	}

	private TNode<T> rotateLeft(TNode<T> node) {
		TNode<T> rightChildOfN = node.getRight();
		node.setRight(rightChildOfN.getLeft());
		rightChildOfN.setLeft(node);
		return rightChildOfN;
	}

	private TNode<T> rotateRightLeft(TNode<T> node) {
		TNode<T> rightChildOfN = node.getRight();
		node.setRight(rotateRight(rightChildOfN));
		return rotateLeft(node);
	}

	private TNode<T> rotateLeftRight(TNode<T> node) {
		TNode<T> leftChildOfN = node.getLeft();
		node.setLeft(rotateLeft(leftChildOfN));
		return rotateRight(node);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		inOrderTraversal(getRoot(), sb);
		return sb.toString().trim();
	}

	private void inOrderTraversal(TNode node, StringBuilder sb) {
		if (node != null) {
			inOrderTraversal(node.getLeft(), sb);
			sb.append(node.getData()).append(" \n");
			inOrderTraversal(node.getRight(), sb);
		}
	}

	private TNode<T> rebalance(TNode<T> node) {
		int diff = getHeightDiff(node); // left - right

		if (diff > 1) { // left subtree is taller
			if (getHeightDiff(node.getLeft()) > 0) // left-left addition
				node = rotateRight(node);
			else
				node = rotateLeftRight(node); // left-right addition
		} else if (diff < -1) {// right subtree is taller
			if (getHeightDiff(node.getRight()) < 0) // right-right addition
				node = rotateLeft(node);
			else
				node = rotateRightLeft(node); // right-left addition
		}
		return node;
	}

	private int getHeightDiff(TNode<T> node) {
		return height(node.getLeft()) - height(node.getRight());
	}

	@Override
	public void insert(T data) {
		if (getRoot() == null)
			setRoot(new TNode<>(data));
		else {
			TNode<T> rootNode = getRoot();
			addEntry(data, rootNode);
			setRoot(rebalance(rootNode));
		}
	}

	private void addEntry(T data, TNode<T> rootNode) {
		assert rootNode != null;
		if (data.compareTo(rootNode.getData()) < 0) { // insert into left
			if (rootNode.hasLeft()) {
				TNode<T> leftChild = rootNode.getLeft();
				addEntry(data, leftChild);
				rootNode.setLeft(rebalance(leftChild));
			} else
				rootNode.setLeft(new TNode<>(data));
		} else {
			if (rootNode.hasRight()) {
				TNode<T> rightChild = rootNode.getRight();
				addEntry(data, rightChild);
				rootNode.setRight(rebalance(rightChild));
			} else
				rootNode.setRight(new TNode<>(data));
		}
	}

	@Override
	public TNode<T> delete(T data) {
		TNode<T> temp = super.delete(data);
		if (temp != null) {
			TNode<T> rootNode = getRoot();
			setRoot(rebalance(rootNode));
			// setRoot(rebalance(getRoot()));
		}
		return temp;
	}

	public void update(T oldData, T newData) {
		insert(newData);
		delete(oldData);

	}
}
