package application;

public interface MinHeapInterface <T extends Comparable /* < ? super T>> */ <T>> {

	void add (T data);
	T removeMin();
	T getMin();
	boolean isEmpty();
	int getSize();
	void clear();
}
