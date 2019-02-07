package dynamic_programming;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class KnapsackProblem {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Item[] items = readData();
		int total_weight = 10000;
		int[][] result = new int[items.length+1][total_weight+1];
		
		for (int i = 0; i < total_weight+1; i++) {
			result[0][i] = 0;
		}
		for (int i = 1; i < items.length+1; i++) {
			for (int j = 0; j < total_weight+1; j++) {
				if ((j-items[i-1].weight) < 0) {
					result[i][j] = result[i-1][j];
				} else {
					result[i][j] = returnBigger(result[i-1][j],result[i-1][j-items[i-1].weight]+items[i-1].value);
				}
			}
		}
		System.out.println(result[items.length][total_weight]);
		
	}

	public static int returnBigger(int a, int b) {
		if (a > b) {
			return a;
		} else {
			return b;
		}
	}
	
	public static Item[] readData() throws IOException {
		FileReader fr = new FileReader(System.getProperty("user.dir") + "/src/dynamic_programming/knapsack1.txt");
		BufferedReader br = new BufferedReader(fr);
		Item[] input = new Item[100]; 
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

class Item {
	int value;
	int weight;
	public Item(int value, int weight) {
		this.value = value;
		this.weight = weight;
	}
}
