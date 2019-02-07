package heap_application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.PriorityQueue;

public class MedianMaintenance {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		int[] input = readData();
		// find the median by using two heaps
		// heap_low helps to support Extract Max
		PriorityQueue<Integer> heap_low = new PriorityQueue<Integer>(Collections.reverseOrder());
		// heap_high helps to support Extract Min
		PriorityQueue<Integer> heap_high = new PriorityQueue<Integer>();
		
		int median_sum = 0;
		int median = 0;
		int result = 0;
		
		for (int i = 0; i < input.length; i++) {
			if (i == 0) {
				heap_low.add(input[i]);
			} else if(i % 2 == 0) {
				if (input[i] < heap_high.peek()) {
					heap_low.add(input[i]);
				} else {
					heap_low.add(heap_high.poll());
					heap_high.add(input[i]);
				}
			} else {
				if (heap_high.isEmpty() || input[i] < heap_high.peek()) {
					heap_low.add(input[i]);
					heap_high.add(heap_low.poll());
				} else {
					heap_high.add(input[i]);
				}
			}
			median = heap_low.peek();
			median_sum += median;
		}
		
		result = median_sum % 10000;
		System.out.println(result);
		
	}
	
	public static int[] readData() throws IOException {
		FileReader fr = new FileReader(System.getProperty("user.dir") + "/src/heap_application/Median.txt");
		BufferedReader br = new BufferedReader(fr);
		int[] input = new int[10000];
		int position = 0;
		String line = "";
		while((line = br.readLine()) != null) {
			input[position] = Integer.parseInt(line);
			position++;
		}
		return input;
	}

}
