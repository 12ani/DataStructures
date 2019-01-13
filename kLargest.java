package sxm180018;

/** K LARGEST ELEMENT.
*
* @author
* Sheetal Kadam (sak170006)
* Shivani Mankotia(sxm180018)
*/

import java.util.Random;
import java.util.PriorityQueue;

public class kLargest {

	final static int T = 15;
	public static Random random = new Random();

	public static void main(String args[]) {

		int n = 16000000;
		int choice = 1;
		if (args.length > 0) {
			n = Integer.parseInt(args[0]);
		}
		if (args.length > 1) {
			choice = Integer.parseInt(args[1]);
		}

		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = i;
		}
		Shuffle.shuffle(arr);

		int k = n / 2;

		Timer timer = new Timer();
		switch (choice) {
		case 1:
			select(arr, k);
			break;
		case 2:
			kLargestPQ(arr, k);
			break;
		}

		// printArraySelect(arr,k);
		timer.end();

		System.out.println("Choice: " + choice + "\n" + timer);

	}

	public static void printArraySelect(int[] arr, int k) {
		for (int i = arr.length - k; i < arr.length; i++) {
			System.out.println(arr[i]);
		}

	}

	public static void select(int[] arr, int k) {
		select(arr, 0, arr.length, k); // result in arr[len-k]..arr[len-1]

	}

	private static int select(int arr[], int p, int n, int k) {
		int q = -1, left = -1, right = -1;
		if (n < T) {
			insertionSort(arr, p, p + n - 1);
			return arr.length - k;
		} else {
			q = randomisedPartition(arr, p, p + n - 1); // returns index of partition

			left = q - p;
			right = n - left - 1;
			// k largest elements will be in the right subarray

			// if size of right subarray is greater than k then select on right subarray
			if (right >= k) {
				return select(arr, q + 1, right, k);

			} else if ((right + 1) == k) {
				return q;
			} else {
				// if size of right subarray is less than k, select on left subarray
				return select(arr, p, left, k - right - 1);
			}
		}
	}

	private static int randomisedPartition(int[] arr, int p, int r) {
		int i = (int) (Math.random() * ((r - p) + 1)) + p;
		Shuffle.swap(arr, i, r);
		return partition(arr, p, r);

	}

	private static int partition(int[] arr, int p, int r) {
		int x = arr[r]; // pivot element
		int i = p - 1;

		for (int j = p; j < r; j++) {
			if (arr[j] <= x) {
				i++;
				Shuffle.swap(arr, i, j);
			}

		}
		// Bring pivot back to the middle

		Shuffle.swap(arr, i + 1, r);
		return i + 1;

	}

	public static void insertionSort(int[] arr, int p, int r) {
		for (int i = p + 1; i <= r; i++) {
			int tmp = arr[i];
			int j = i - 1;
			while (j >= p && tmp < arr[j]) {
				arr[j + 1] = arr[j];
				j = j - 1;
			}
			arr[j + 1] = tmp;
		}

	}

	/**
	 * @author rbk : based on algorithm described in a book
	 */

	/* Shuffle the elements of an array arr[from..to] randomly */
	public static class Shuffle {

		public static void shuffle(int[] arr) {
			shuffle(arr, 0, arr.length - 1);
		}

		public static <T> void shuffle(T[] arr) {
			shuffle(arr, 0, arr.length - 1);
		}

		public static void shuffle(int[] arr, int from, int to) {
			int n = to - from + 1;
			for (int i = 1; i < n; i++) {
				int j = random.nextInt(i);
				swap(arr, i + from, j + from);
			}
		}

		public static <T> void shuffle(T[] arr, int from, int to) {
			int n = to - from + 1;
			Random random = new Random();
			for (int i = 1; i < n; i++) {
				int j = random.nextInt(i);
				swap(arr, i + from, j + from);
			}
		}

		static void swap(int[] arr, int x, int y) {
			int tmp = arr[x];
			arr[x] = arr[y];
			arr[y] = tmp;
		}

		static <T> void swap(T[] arr, int x, int y) {
			T tmp = arr[x];
			arr[x] = arr[y];
			arr[y] = tmp;
		}

		public static <T> void printArray(T[] arr, String message) {
			printArray(arr, 0, arr.length - 1, message);
		}

		public static <T> void printArray(T[] arr, int from, int to, String message) {
			System.out.print(message);
			for (int i = from; i <= to; i++) {
				System.out.print(" " + arr[i]);
			}
			System.out.println();
		}
	}

	/*
	 * Timer class for roughly calculating running time of programs
	 *
	 * @author rbk Usage: Timer timer = new Timer(); timer.start(); timer.end();
	 * System.out.println(timer); // output statistics
	 */
	public static void kLargestPQ(int[] arr, int k) {
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		for (int x : arr) {
			if (pq.size() < k) {
				pq.add(x);
			} else {
				if (x > pq.peek()) {
					pq.add(x);
					pq.remove();
				}
			}
		}
	}

	public static class Timer {

		long startTime, endTime, elapsedTime, memAvailable, memUsed;
		boolean ready;

		public Timer() {
			startTime = System.currentTimeMillis();
			ready = false;
		}

		public void start() {
			startTime = System.currentTimeMillis();
			ready = false;
		}

		public Timer end() {
			endTime = System.currentTimeMillis();
			elapsedTime = endTime - startTime;
			memAvailable = Runtime.getRuntime().totalMemory();
			memUsed = memAvailable - Runtime.getRuntime().freeMemory();
			ready = true;
			return this;
		}

		public long duration() {
			if (!ready) {
				end();
			}
			return elapsedTime;
		}

		public long memory() {
			if (!ready) {
				end();
			}
			return memUsed;
		}

		public void scale(int num) {
			elapsedTime /= num;
		}

		public String toString() {
			if (!ready) {
				end();
			}
			return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed / 1048576) + " MB / "
					+ (memAvailable / 1048576) + " MB.";
		}
	}

}
