package dynamic_programming;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KnapsackProblemBig {

	public static void main(String[] args) throws IOException {

		// load the data and create item array
		Item[] items = readData();
		int total_weight = 2000000;
		int[] current = new int[total_weight+1];
		int[] previous = new int[total_weight+1];
		
		for (int i = 1; i < items.length+1; i++) {
			for (int j = 0; j < total_weight+1; j++) {
				if ((j-items[i-1].weight) < 0) {
					continue;
				} else {
					current[j] = returnBigger(previous[j],previous[j-items[i-1].weight]+items[i-1].value);
				}
			}
			previous = current.clone();
		}
		System.out.println(current[total_weight]);
		
	}

	public static int returnBigger(int a, int b) {
		if (a > b) {
			return a;
		} else {
			return b;
		}
	}
	
	public static Item[] readData() throws IOException {
		FileReader fr = new FileReader(System.getProperty("user.dir") + "/src/dynamic_programming/knapsack_big.txt");
		BufferedReader br = new BufferedReader(fr);
		Item[] input = new Item[2000]; 
		int position = 0;
		String line = "";
		while((line = br.readLine()) != null) {						
			input[position] = new Item(Integer.parseInt(line.split(" ")[0]),
					Integer.parseInt(line.split(" ")[1]));
			position++;
		}
		br.close();
		return input;
	}

}

