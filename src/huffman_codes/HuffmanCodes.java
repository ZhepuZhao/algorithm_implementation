package huffman_codes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;

public class HuffmanCodes {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int[] input = readData();
		PriorityQueue<HuffmanNode> sigma = new PriorityQueue<HuffmanNode>(input.length, new MyComparator());
		for (int i = 0; i < input.length; i++) {
			HuffmanNode hn = new HuffmanNode();
			hn.data = input[i];
			hn.left = null;
			hn.right = null;
			sigma.add(hn);
		}		
		HuffmanNode root = null;
		while (sigma.size() > 1) {
			HuffmanNode a = sigma.poll();
			HuffmanNode b = sigma.poll();
			HuffmanNode a_b = new HuffmanNode();
			a_b.data = a.data + b.data;
			a_b.left = a;
			a_b.right = b;
			root = a_b;
			sigma.add(a_b);					
		}
		System.out.println(maxLength(root));
		System.out.println(minLength(root));
	}
	
	public static int maxLength(HuffmanNode root) {
		int left_length = 0;
		int right_length = 0;
		if (root.left != null) {
			left_length++;
			left_length += maxLength(root.left);
		}
		
		if (root.right != null) {
			right_length++;
			right_length += maxLength(root.right);
		}
		
		if (root.left == null && root.right == null) {
			return left_length;
		}
		
		if (left_length > right_length) {
			return left_length;
		} else {
			return right_length;
		}				
	}
	
	public static int minLength(HuffmanNode root) {
		int left_length = 0;
		int right_length = 0;
		if (root.left != null) {
			left_length++;
			left_length += minLength(root.left);
		}
		
		if (root.right != null) {
			right_length++;
			right_length += minLength(root.right);
		}
		
		if (root.left == null && root.right == null) {
			return left_length;
		}
		
		if (left_length > right_length) {
			return right_length;
		} else {
			return left_length;
		}				
	}
	public static int[] readData() throws IOException {
		FileReader fr = new FileReader(System.getProperty("user.dir") + "/src/huffman_codes/huffman.txt");
		BufferedReader br = new BufferedReader(fr);
		int[] input = new int[1000]; 
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

class HuffmanNode {	 
    int data;
 
    HuffmanNode left;
    HuffmanNode right;
}

class MyComparator implements Comparator<HuffmanNode> {
	
    public int compare(HuffmanNode x, HuffmanNode y) { 
        return x.data - y.data;
    }
}
