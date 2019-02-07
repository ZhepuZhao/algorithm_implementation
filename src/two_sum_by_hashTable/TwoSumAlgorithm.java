/**
 * 
 */
package two_sum_by_hashTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zhepu Zhao
 *
 */
public class TwoSumAlgorithm {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		System.out.println("f");
		long[] input = readData();
		int sum = 0;
		
		/*
		 * the following method is implemented by hash table, but it took hours to give me the answer.
		 */
//		Map<Integer, Long> map = new HashMap<Integer, Long>();
//		for (int i = 0; i < input.length; i++) {
//			map.put(i, input[i]);
//		}
//				
//		for (long i = -10000; i <= 10000; i++) {			
//			for (int j = 0; j < input.length; j++) {
//				if (map.containsValue(i - input[j]) && ((i - input[j]) != input[j])) {
//					sum++;
//					break;
//				}
//			}
//		}
//		System.out.println(sum);
		
		/*
		 * the alternative method is to implement by organize the data in an intelligent way
		 */
		long start = System.currentTimeMillis();
		// Step1: sort the data
		QuickSortComparisonNumber(input, 0, input.length-1);

		// Step2: narrow the left and right index number down
		// use the temp array to help check if the sum of left and right has already
		// existed in it to avoid counting multiple times.
		int left = 0;
		int right = input.length - 1;
		int[] result = new int[20001];
		while(left < right) {
			if ((input[left] + input[right]) > (long) 10000) {
				right--;
			} else if ((input[left]+input[right]) < (long) -10000) {
				left++;
			} else {
				if (input[left] != input[right] && result[(int) (input[left]+input[right]+10000)] == 0) {
					result[(int) (10000+input[left]+input[right])] = 1;
					sum++;
					left++;
				} else {
					left++;
				}
			}			
		}
		System.out.println(sum);
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}
	
	public static void QuickSortComparisonNumber(long[] inputArray, int start, int end) {
		if ((end-start) == 0) return;
		// take the first element as pivot
//		int splitBoundary = partitionAndCountComparisonFromFirstOne(inputArray, start, end);
		
		// take the last element as pivot
//		int splitBoundary = partitionAndCountComparisonFromLastOne(inputArray, start, end);

		// take the median number of first, last, and the median element as pivot
		int splitBoundary = partitionAndCountComparisonFromFirstOne(inputArray, start, end);
		if(start < splitBoundary) {
			QuickSortComparisonNumber(inputArray, start, splitBoundary-1);
		}
		if(splitBoundary+1 < end) {
			QuickSortComparisonNumber(inputArray, splitBoundary+1, end);
		}
	}
	
	public static int partitionAndCountComparisonFromFirstOne(long[] inputArray, int start, int end) {
		int i = start+1;
		int pivot = start; // take the first element of each array as the pivot element
		long temp = 0;
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
	
	public static long[] readData() throws IOException {
		FileReader fr = new FileReader(System.getProperty("user.dir") + "/src/twoSumByHashTable/2sum.txt");
		BufferedReader br = new BufferedReader(fr);
		
		long[] input = new long[1000000];
		int position = 0;
		String line = "";
		while((line = br.readLine()) != null) {
			input[position] = Long.parseLong(line);			
			position++;
		}
		return input;
	}

}
