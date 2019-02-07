package specialization_1;

import java.util.Scanner;

public class QuickSort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// test cases
//		int[] a = {3,8,2,5,1,4,7,6};
//		int[] b = {1,2,3,4,5};
//		int[] c = {5,4,3,2,1};
//		int[] d = {3,4,53,2,1,6,7,8,9};
//		int comparison = QuickSortComparisonNumber(a,0,7);
//		for (int i = 0; i < a.length; i++) {
//			System.out.println(a[i]);
//		}
//		System.out.println(comparison);
		
		Scanner sc = new Scanner(System.in);
		int[] a = new int[10000];
		for (int i = 0; i < 10000; i++) {
			a[i] = sc.nextInt();
		}
		int comparisonNum = QuickSortComparisonNumber(a,0,9999);
		System.out.println(comparisonNum);
		
//		int result = findMedian(d,0,8,4);
//		System.out.println(result);
		// 162085, 164123, 138382
	}

	/*
	 * input: the array to be sorted, and the length of array
	 * return: total number of comparisons throughout the whole process
	 */
	public static int QuickSortComparisonNumber(int[] inputArray, int start, int end) {
		int comparisonNumber = 0;
		if ((end-start) == 0) return 0;
		// take the first element as pivot
//		int splitBoundary = partitionAndCountComparisonFromFirstOne(inputArray, start, end);
		
		// take the last element as pivot
//		int splitBoundary = partitionAndCountComparisonFromLastOne(inputArray, start, end);

		// take the median number of first, last, and the median element as pivot
		int splitBoundary = partitionAndCountComparisonByMedian(inputArray, start, end);
		comparisonNumber += end - start;
		if(start < splitBoundary) {
			comparisonNumber += QuickSortComparisonNumber(inputArray, start, splitBoundary-1);
		}
		if(splitBoundary+1 < end) {
			comparisonNumber += QuickSortComparisonNumber(inputArray, splitBoundary+1, end);
		}
		return comparisonNumber;
	}
	
	/*
	 * input: the array to be sorted and the length
	 * return: the number of comparisons in this step
	 */
	public static int partitionAndCountComparisonFromFirstOne(int[] inputArray, int start, int end) {
		int i = start+1;
		int pivot = start; // take the first element of each array as the pivot element
//		int pivot = end; // take the last element of each array as the pivot element
		int temp = 0;
		for (int j = start+1; j <= end; j++) {
			if (inputArray[j] < inputArray[pivot]) {
				temp = inputArray[j];
				inputArray[j] = inputArray[i];
				inputArray[i] = temp;
				i++;
			}				
		}
		temp = inputArray[i-1];
		inputArray[i-1] = inputArray[pivot];
		inputArray[pivot] = temp;
		return i-1;
	}
	
	public static int partitionAndCountComparisonFromLastOne(int[] inputArray, int start, int end) {
		int i = start+1;
		int pivot = start; // take the last element of each array as the pivot element
		int temp = 0;
		/*
		 * swap the last element with the first element
		 */
		temp = inputArray[end];
		inputArray[end] = inputArray[start];
		inputArray[start] = temp;
		
		for (int j = start+1; j <= end; j++) {
			if (inputArray[j] < inputArray[pivot]) {
				temp = inputArray[j];
				inputArray[j] = inputArray[i];
				inputArray[i] = temp;
				i++;
			}				
		}
		temp = inputArray[i-1];
		inputArray[i-1] = inputArray[pivot];
		inputArray[pivot] = temp;
		return i-1;
	}
	
	public static int partitionAndCountComparisonByMedian(int[] inputArray, int start, int end) {
		int i = start+1;
		int pivot = start; // take the last element of each array as the pivot element
		int temp = 0;
		
		int median = 0;
		if ((end - start + 1) % 2 == 0) {
			median = start + (end - start + 1) / 2 - 1; // median is the position in the original array
			median = findMedian(inputArray, start, end, median);
		} else {
			median = start + (end - start + 1) / 2;
			median = findMedian(inputArray, start, end, median);
		}
		/*
		 * swap the pivot element with the first element
		 */
		temp = inputArray[median];
		inputArray[median] = inputArray[start];
		inputArray[start] = temp;
		
		for (int j = start+1; j <= end; j++) {
			if (inputArray[j] < inputArray[pivot]) {
				temp = inputArray[j];
				inputArray[j] = inputArray[i];
				inputArray[i] = temp;
				i++;
			}				
		}
		temp = inputArray[i-1];
		inputArray[i-1] = inputArray[pivot];
		inputArray[pivot] = temp;
		return i-1;
	}
	
	public static int findMedian(int[] array, int start, int end, int median) {
		if (array[start] > array[end] && array[start] > array[median]) {
			if (array[end] > array[median]) {
				return end;
			} else {
				return median;
			}
		} else if (array[end] > array[start] && array[end] > array[median]) {
			if (array[start] > array[median]) {
				return start;
			} else {
				return median;
			}
		} else {
			if (array[end] > array[start]) {
				return end;
			} else {
				return start;
			}
		}
	}
}
