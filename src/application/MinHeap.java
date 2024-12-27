package application;

public class MinHeap<T extends Comparable<T>> implements MinHeapInterface<T> {

	private T[] arr;
	private int N;

	@SuppressWarnings("unchecked")
	public MinHeap(int size) {
		arr = (T[]) new Comparable[size + 1];
	}

	public void add(T data) {
		if (N + 1 == arr.length) { // Check if capacity is full before adding
			resize(arr.length * 2); // Double the capacity if full
		}
		arr[++N] = data;
		swim(N);
	}

	public String heapSortDescString(Comparable[] a) {
		int N = a.length - 1;
		Comparable temp;
		minHeapify(a);
		StringBuilder stBuilder = new StringBuilder();
		while (N > 1) {
			temp = a[1];
			a[1] = a[N];
			a[N] = temp;

			stBuilder.append(a[N]).append(" ");
			N--;
			int k = 1;
			while (2 * k <= N) {
				int j = 2 * k;
				if (j < N && a[j].compareTo(a[j + 1]) >= 0)
					j++;
				if (a[k].compareTo(a[j]) < 0)
					break;
				temp = a[k];
				a[k] = a[j];
				a[j] = temp;
				k = j;
			}
		}

		stBuilder.append(a[1]);
		return stBuilder.toString();
	}

	@Override
	public T removeMin() {
		T min = arr[1];
		swap(1, N--);
		sink(1);
		arr[N + 1] = null; // prevent loitering ??
		return min;
	}

	@Override
	public T getMin() {
		return arr[1];
	}

	@Override
	public boolean isEmpty() {
		return N == 0;
	}

	@Override
	public int getSize() {
		return N;
	}

	@Override
	public void clear() {
		N = 0;
	}

	private void swim(int k) {
		while (k > 1 && !less(k / 2, k)) {
			swap(k / 2, k);
			k /= 2;
		}
	}

	private boolean less(int p, int c) {
		return arr[p].compareTo(arr[c]) < 0;
	}

	private void swap(int p, int c) {
		T temp = arr[p];
		arr[p] = arr[c];
		arr[c] = temp;
	}

	private void sink(int k) {
		while (2 * k <= N) {
			int j = 2 * k;
			if (j < N && !less(j, j + 1))
				j++;
			if (less(k, j))
				break;
			swap(k, j);
			k = j;
		}
	}

	public static boolean isMinHeap(Comparable[] a) {
		int N = a.length;
		for (int i = 1; i <= N / 2; i++) {
			int lc = i * 2, rc = lc + 1;
			if (lc < N && a[i].compareTo(a[lc]) > 0)
				return false;
			if (rc < N && a[i].compareTo(a[rc]) > 0)
				return false;
		}
		return true;
	}

	public static void heapSortDesc(Comparable[] a) {
		int N = a.length - 1;
		Comparable temp;

		minHeapify(a); // to a max-heap

		while (N > 1) {
			// 1. Swap the first element with the last element
			temp = a[1];
			a[1] = a[N];
			a[N] = temp;

			// 2. decrement N
			N--;

			// 3. sink[1]
			int k = 1;
			while (2 * k <= N) {
				int j = 2 * k;
				if (j < N && a[j].compareTo(a[j + 1]) >= 0)
					j++;
				if (a[k].compareTo(a[j]) < 0)
					break;
				temp = a[k];
				a[k] = a[j];
				a[j] = temp;
				k = j;
			}
		}
	}

	public static void minHeapify(Comparable[] a) {
		int N = a.length - 1, i = N / 2;
		Comparable temp;
		while (i-- > 0) {
			// sink [i+1]
			int k = i + 1;
			while (2 * k <= N) {
				int j = 2 * k;
				if (j < N && a[j].compareTo(a[j + 1]) >= 0)
					j++;
				if (a[k].compareTo(a[j]) < 0)
					break;
				temp = a[k];
				a[k] = a[j];
				a[j] = temp;
				k = j;
			}
		}
	}

	public  void sort(Comparable[] arr) {
		int n = arr.length - 1;
		for (int i = arr.length / 2; i >= 0; i--)
			sink(arr, i, n);

		while (n > 0) {
			exchange(arr, 0, n);
			sink(arr, 0, --n);
		}
	}

	private static void sink(Comparable[] arr, int i, int n) {
		while (i * 2 + 1 <= n) {
			int k = i * 2 + 1;
			if (k + 1 < n && less(arr, k, k + 1))
				k++;

			if (less(arr, i, k))
				exchange(arr, i, k);
			i = k;
		}
	}

	private static void exchange(Comparable[] arr, int i, int k) {
		Comparable d = arr[i];
		arr[i] = arr[k];
		arr[k] = d;
	}

	private static boolean less(Comparable[] arr, int k, int i) {
		return arr[k].compareTo(arr[i]) < 0;
	}

	private void resize(int newCapacity) {
		T[] newArr = (T[]) new Comparable[newCapacity + 1];
		System.arraycopy(arr, 1, newArr, 1, N); 
		arr = newArr;
		N = Math.min(N, newCapacity);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
//	        sb.append("[");
		for (int i = 1; i <= N; i++) {
			sb.append(arr[i]);
			if (i < N) {
				sb.append("\n");
			}
		}
//	        sb.append("]");
		return sb.toString();
	}
}
