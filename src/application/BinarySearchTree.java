package application;

import java.util.Collections;

import application.SingleLinkedList;
import application.LinkedStack;
import application.QueueStack;
import application.linkedQueue;

public class BinarySearchTree<T extends Comparable<T>> {
	private QueueStack<TNode<T>> stack = new QueueStack<>();
	private linkedQueue<T> queue = new linkedQueue<>();
	private QueueStack<T> pervStack = new QueueStack<>();

	private TNode<T> root;

	public TNode<T> getRoot() {
		return root;
	}

	public void setRoot(TNode<T> root) {
		this.root = root;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public void traverseInOrder() {
		traverseInOrder(root);
	}

	public TNode<T> getFirstNode() {
		TNode<T> curr = root;
		while (curr.getLeft() != null) {
			curr = curr.getLeft();
		}
		return curr;
	}

	private void traverseInOrder(TNode node) {
		if (node != null) {
			if (node.getLeft() != null)
				traverseInOrder(node.getLeft());
			System.out.print(node + " ");
			if (node.getRight() != null)
				traverseInOrder(node.getRight());
		}
	}

	public void traversePreOrder() {
		traversePreOrder(root);
	}

	private void traversePreOrder(TNode node) {
		if (node != null) {
			System.out.print(node + " ");
			if (node.getLeft() != null)
				traversePreOrder(node.getLeft());
			if (node.getRight() != null)
				traversePreOrder(node.getRight());
		}
	}

	public void traversePostOrder() {
		traversePostOrder(root);
	}

	private void traversePostOrder(TNode node) {
		if (node != null) {
			if (node.getLeft() != null)
				traversePostOrder(node.getLeft());
			if (node.getRight() != null)
				traversePostOrder(node.getRight());
			System.out.print(node + " ");

		}
	}

	public void traverseLevelOrder() {
		if (root == null)
			return;
		linkedQueue<TNode<T>> queue = new linkedQueue<>();
		queue.enqueue(root);
		while (!queue.isEmpty()) {
			TNode<T> curr = queue.dequeue();
			System.out.print(curr + " ");
			if (curr.hasLeft())
				queue.enqueue(curr.getLeft());
			if (curr.hasRight())
				queue.enqueue(curr.getRight());
		}
	}

	public boolean isBST(TNode node) {
		if (root == null)
			return true;
		linkedQueue<TNode<T>> queue = new linkedQueue<>();
		queue.enqueue(root);
		while (!queue.isEmpty()) {
			int levelSize = queue.length_ittrariv();
			if (levelSize > 2)
				return false;
			for (int i = 0; i < levelSize; i++) {
				TNode<T> curr = queue.dequeue();
				if (curr.getLeft() != null)
					queue.enqueue(curr.getLeft());
				if (curr.getRight() != null)
					queue.enqueue(curr.getRight());
			}
		}
		return true;
	}

	private void enqueueInOrderData(TNode<T> node) {
		if (node != null) {
			TNode<T> curr = node;

			while (curr != null || !stack.isEmpty()) {
				while (curr != null) {
					stack.push(curr);
					curr = curr.getLeft();
				}
				if (!stack.isEmpty()) {
					curr = stack.pop();
					queue.enqueue(curr.getData());
					curr = curr.getRight();
				}
			}
		}
	}

	public String iterativeTraverseLevelOrderString() {
		if (root == null) {
			return "Empty Tree";
		}

		StringBuilder sb = new StringBuilder();
		linkedQueue<TNode<T>> queue = new linkedQueue<>();
		queue.enqueue(root);

		while (!queue.isEmpty()) {
			TNode<T> curr = queue.dequeue();
			sb.append(curr.toString() + " ");

			if (curr.hasLeft()) {
				queue.enqueue(curr.getLeft());
			}
			if (curr.hasRight()) {
				queue.enqueue(curr.getRight());
			}
		}

		return sb.toString();
	}

	public T iterativeTraverseInOrderStringQueue() {
		if (queue.isEmpty()) {
			enqueueInOrderData(root);
		}
		T data = queue.dequeue();
		pervStack.push(data);
		return data;
	}

	public T getPreviousData() {
		if (!pervStack.isEmpty()) {
			return pervStack.pop();
		}
		return null;
	}

	public void iterativeTraverseInOrder() {
		if (root == null) {
			return;
		}
		LinkedStack<TNode<T>> stack = new LinkedStack();
		TNode<T> current = root;

		while (current != null || !stack.isEmpty()) {
			while (current != null) {
				stack.push(current);
				current = current.getLeft();
			}

			if (!stack.isEmpty()) {
				TNode<T> node = stack.pop().getData();
				System.out.print(node.getData() + " ");
				current = node.getRight();
			}
		}
	}

	public SingleLinkedList<T> iterativeTraverseInOrderT(TNode<T> root) {
		if (root == null) {
			return new SingleLinkedList<>(); // Return a new empty SingleLinkedList<T>
		}
		SingleLinkedList<T> result = new SingleLinkedList<>();
		LinkedStack<TNode<T>> stack = new LinkedStack<>();
		TNode<T> current = root;

		while (current != null || !stack.isEmpty()) {
			while (current != null) {
				stack.push(current);
				current = current.getLeft();
			}

			if (!stack.isEmpty()) {
				TNode<T> node = stack.pop().getData();
				result.insert(node.getData()); // Add node value (T) to the result list
				current = node.getRight();
			}
		}

		return result;
	}

	public TNode find(T data) {
		return find(data, root);
	}

	private TNode find(T data, TNode node) {
		if (node != null) {
			int comp = node.getData().compareTo(data);
			if (comp == 0)
				return node;
			else if (comp > 0 && node.hasLeft())
				return find(data, node.getLeft());
			else if (comp < 0 && node.hasRight())
				return find(data, node.getRight());
		}
		return null;
	}

	public TNode smallest() {
		return smallest(root);
	}

	private TNode<T> smallest(TNode node) {
		if (node != null) {
			if (!node.hasLeft())
				return (node);
			return smallest(node.getLeft());
		}
		return null;
	}

	public TNode iterativeSmallest() {
		return iterativeSmallest(root);
	}

	private TNode<T> iterativeSmallest(TNode<T> node) {
		if (node == null)
			return null;
		TNode<T> curr = node;
		while (curr.hasLeft()) {
			curr = curr.getLeft();
		}
		return curr;
	}

	public TNode largest() {
		return largest(root);
	}

	private TNode<T> largest(TNode node) {
		if (node != null) {
			if (!node.hasRight())
				return (node);
			return largest(node.getRight());
		}
		return null;
	}

	public int height() {
		return height(root);
	}

	protected int height(TNode node) {
		if (node == null)
			return 0;
		if (node.isLeaf())
			return 1;
		int left = 0;
		int right = 0;
		if (node.hasLeft())
			left = height(node.getLeft());
		if (node.hasRight())
			right = height(node.getRight());
		return (left > right) ? (left + 1) : (right + 1);
	}

	public int size() {
		return size(root);
	}

	private int size(TNode root) {
		if (root == null) {
			return 0;
		}

		int leftSize = size(root.getLeft());
		int rightSize = size(root.getRight());

		return 1 + leftSize + rightSize;
	}

	public void insert(T data) {
		if (root == null)
			root = new TNode<>(data);
		else
			insert(data, root);
	}

	private void insert(T data, TNode<T> curr) {
		if (data.compareTo(curr.getData()) >= 0) { // insert into right subtree
			if (!curr.hasRight())
				curr.setRight(new TNode<>(data));
			else
				insert(data, curr.getRight());
		} else { // insert into left subtree
			if (!curr.hasLeft())
				curr.setLeft(new TNode<>(data));
			else
				insert(data, curr.getLeft());
		}
	}

	public TNode<T> delete(T data) {
		TNode<T> curr = root, parent = root;
		boolean isLeftChild = false;

		if (root == null)
			return null; // empty tree

		while (curr != null && curr.getData().compareTo(data) != 0) {
			parent = curr;
			if (data.compareTo(curr.getData()) < 0) { // left subtree
				curr = curr.getLeft();
				isLeftChild = true;
			} else { // right subtree
				curr = curr.getRight();
				isLeftChild = false;
			}
		}

		if (curr == null)
			return null; // not found

		// case 1: deleted node is a leaf
		if (curr.isLeaf()) {
			if (curr == root)
				root = null; // tree has only one node
			else if (isLeftChild)
				parent.setLeft(null);
			else
				parent.setRight(null);
		}

		// case 2: deleted node has one child
		// broken into 2 sub-cases
		else if (curr.hasLeft() && !curr.hasRight()) { // has only left child
			if (curr == root)
				root = curr.getLeft();
			else if (isLeftChild)
				parent.setLeft(curr.getLeft());
			else
				parent.setRight(curr.getLeft());

		} else if (curr.hasRight() && !curr.hasLeft()) { // has only right child
			if (curr == root)
				root = curr.getRight();
			else if (isLeftChild)
				parent.setLeft(curr.getRight());
			else
				parent.setRight(curr.getRight());
		}

		// case 3: deleted node has 2 children
		else {
			TNode<T> successor = getSuccessor(curr);
			if (curr == root)
				root = successor;
			else if (isLeftChild)
				parent.setLeft(successor);
			else
				parent.setRight(successor);
			successor.setLeft(curr.getLeft());
		}
		return curr;
	}

	private TNode<T> getSuccessor(TNode<T> node) {
		TNode<T> successorParent = node, successor = node, curr = node.getRight();

		while (curr != null) {
			successorParent = successor;
			successor = curr;
			curr = curr.getLeft();
		}
		if (successor != node.getRight()) { // fix successor connections
			successorParent.setLeft(successor.getRight());
			successor.setRight(node.getRight());
		}
		return successor;
	}

	public int countLeaves() {
		return countLeaves(root);
	}

	private int countLeaves(TNode<T> curr) {
		if (curr == null)
			return 0;
		if (curr.isLeaf())
			return 1;
		return countLeaves(curr.getLeft()) + countLeaves(curr.getRight());
	}

	public int countParents() {
		return countParents(root);
	}

	private int countParents(TNode<T> curr) {
		if (curr == null)
			return 0;
		if (!curr.isLeaf())
			return 1;
		return countParents(curr.getLeft()) + countParents(curr.getRight());
	}

}