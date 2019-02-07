/**
 * 
 */
package scheduling_application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Zhepu Zhao
 *
 */
public class SchedulingApplication {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Job[] jobs = readData();
		QuickSortByPriority(jobs, 0, 9999);
		for (int i = 0; i < 9999; i++) {
			System.out.println(jobs[i].getWeight() + " " + jobs[i].getLength() + " " + jobs[i].getPriority());
		}
		long completionTime = 0;
		long cost = 0;
		for (int i = 9999; i >= 0; i--) {
			completionTime += (long)jobs[i].getLength();
			cost += (long)jobs[i].getWeight() * completionTime;
		}
		System.out.println(cost);
		// subtract version optimal: 69119377652
		// divide versiono optimal: 67311454237
		
	}
	
	public static Job[] readData() throws IOException {
		FileReader fr = new FileReader(System.getProperty("user.dir") + "/src/greedy_algorithms/jobs.txt");
		BufferedReader br = new BufferedReader(fr);
		Job[] jobs = new Job[10000];		
		int position = 0;
		String line = "";
		while((line = br.readLine()) != null) {						
			jobs[position] = new Job(Integer.parseInt(line.split(" ")[0]), Integer.parseInt(line.split(" ")[1]));
			position++;
		}
		return jobs;
	}

	public static void QuickSortByPriority(Job[] inputArray, int start, int end) {
		if ((end-start) == 0) return;
		// take the first element as pivot
//		int splitBoundary = partitionAndCountComparisonFromFirstOne(inputArray, start, end);
		
		// take the last element as pivot
//		int splitBoundary = partitionAndCountComparisonFromLastOne(inputArray, start, end);

		// take the median number of first, last, and the median element as pivot
		int splitBoundary = partition(inputArray, start, end);
		if(start < splitBoundary) {
			QuickSortByPriority(inputArray, start, splitBoundary-1);
		}
		if(splitBoundary+1 < end) {
			QuickSortByPriority(inputArray, splitBoundary+1, end);
		}
	}

	
	public static int partition(Job[] inputArray, int start, int end) {
		int i = start+1;
		int pivot = start; // take the first element of each array as the pivot element
		Job temp = new Job(0,0);
		for (int j = start+1; j <= end; j++) {
//			if (inputArray[j].getPriority() < inputArray[pivot].getPriority()) {
//				temp = inputArray[j];
//				inputArray[j] = inputArray[i];
//				inputArray[i] = temp;
//				i++;
//			}	
			if (inputArray[j].getOptimalPriority() < inputArray[pivot].getOptimalPriority()) {
				temp = inputArray[j];
				inputArray[j] = inputArray[i];
				inputArray[i] = temp;
				i++;
			}	
//			if (inputArray[j].getPriority() == inputArray[pivot].getPriority()) {
//				if (inputArray[j].getWeight() < inputArray[pivot].getWeight()) {
//					temp = inputArray[j];
//					inputArray[j] = inputArray[i];
//					inputArray[i] = temp;
//					i++;
//				}
//			}
		}
		temp = inputArray[i-1];
		inputArray[i-1] = inputArray[pivot];
		inputArray[pivot] = temp;
		return i-1;
	}

}
