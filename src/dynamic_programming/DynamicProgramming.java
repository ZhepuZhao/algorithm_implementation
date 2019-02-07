package dynamic_programming;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DynamicProgramming {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int[] input = readData();
		int[] result = new int[input.length+1];
		List<Integer> s = new ArrayList<Integer>();
		result[0] = 0;
		result[1] = input[0];
		for (int i = 2; i < result.length; i++) {
			result[i] = returnBigger(result[i-1], result[i-2] + input[i-1]);
		}
		int i = result.length-1;
		while(i >= 1) {
			if (i == 1) {
				s.add(result[i]);
				i--;
			} else {
				if (result[i-1] > (result[i-2]+input[i-1])) {
					i--;
				} else {
					s.add(input[i-1]);
					i -= 2;
				}
			}
		}
		
//		System.out.println(input[0]);
//		System.out.println(input[1]);
//		System.out.println(input[2]);
//		System.out.println(input[3]);
//		System.out.println(input[16]);
//		System.out.println(input[116]);
//		System.out.println(input[516]);
//		System.out.println(input[996]);
		for (int j = 0; j < s.size(); j++) {
			System.out.println(s.get(j));
		}
//		for (int j = 0; j < s.size(); j++) {
//			switch (s.get(j)) {
//			case 4962786:
//				System.out.println("1 exists");
//				break;
//			case 6395702:
//				System.out.println("2 exists");
//				break;
//			case 5601590:
//				System.out.println("3 exists");
//				break;
//			case 3803402:
//				System.out.println("4 exists");
//				break;
//			case 3889157:
//				System.out.println("17 exists");
//				break;
//			case 7463231:
//				System.out.println("117 exists");
//				break;
//			case 5906419:
//				System.out.println("517 exists");
//				break;
//			case 7546051:
//				System.out.println("997 exists");
//				break;
//			}
//		}
	}
	
	public static int returnBigger(int a, int b) {
		if (a > b) {
			return a;
		} else {
			return b;
		}
	}
	
	public static int[] readData() throws IOException {
		FileReader fr = new FileReader(System.getProperty("user.dir") + "/src/dynamic_programming/mwistest.txt");
		BufferedReader br = new BufferedReader(fr);
		int[] input = new int[10]; 
		int position = 0;
		String line = "";
		while((line = br.readLine()) != null) {						
			input[position] = Integer.parseInt(line);
			position++;
		}
		br.close();
		return input;
		
	}

}
