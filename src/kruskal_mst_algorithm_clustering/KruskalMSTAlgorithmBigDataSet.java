package kruskal_mst_algorithm_clustering;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KruskalMSTAlgorithmBigDataSet {

	public static void main(String[] args) throws IOException {

		// time starts
		long start = System.currentTimeMillis();
		
		// all nodes
		int cluster_num = 0;
		int bit_differ = 0;
		// the 1st element is useless
		String[] nodes = readData(); 		
		System.out.println("-----number of unique nodes-----");
		System.out.println(nodes.length-1);
		Map<int[], Integer> pairs = new HashMap<int[], Integer>();
		Map<Integer,ArrayList<Integer>> map = new HashMap<Integer,ArrayList<Integer>>();
		for (int i = 1; i < nodes.length; i++) {			
			map.put(i, new ArrayList<Integer>());	
			map.get(i).add(i);
		}
		System.out.println("-----variable initialization completed-----");
		
		// iterate the nodes
		for (int i = 1; i < nodes.length; i++) {
			for (int j = i+1; j < nodes.length; j++) {
				if (nodes[i].equals(nodes[j])) {
					continue;
				} else {                         
					for (int k = 0; k < 47; k+=2) {
						if (bit_differ < 3) {
							if (nodes[i].charAt(k) != nodes[j].charAt(k)) {
								bit_differ += 1;
							}
						} else {
							break;
						}
					}
					if (bit_differ < 3) {
						int[] temp = {i, j};
						pairs.put(temp, bit_differ);
					}
					bit_differ = 0;
				}
			}
		}
		System.out.println("-----iterate nodes done-----");
		
		for (Map.Entry<int[], Integer>  entry: pairs.entrySet()) {
			if (entry.getValue() == 1) {
				union(map, entry.getKey()[0], entry.getKey()[1]);
			}
		}

		System.out.println("-----cost = 1 finished-----");
		for (Map.Entry<int[], Integer>  entry: pairs.entrySet()) {
			if (entry.getValue() == 2) {
				union(map, entry.getKey()[0], entry.getKey()[1]);
			}
		}
		cluster_num = map.size();
		long end = System.currentTimeMillis();
		System.out.println("Your algorithm took " + (double)(end - start)/1000 + " seconds");
		System.out.println("You should have at least " + cluster_num + " clusters in total.");
		
		
 	}
	
	public static int min(int a, int b) {
		if (a <= b) {
			return a;
		} else {
			return b;
		}
	}
	
	public static int find(Map<Integer,ArrayList<Integer>> map, int x) {
		int parent = 0;
		for (Map.Entry<Integer, ArrayList<Integer>> entry: map.entrySet()){
			if (entry.getValue().contains(x)) {
				parent = entry.getKey();
				break;
			}
		}
		return parent;
	}

	public static void union(Map<Integer,ArrayList<Integer>> map, int x, int y) {
		int x_parent = find(map, x);
		int y_parent = find(map, y);
		if (x_parent != y_parent) {		
			map.get(x_parent).addAll(map.get(y_parent));
			map.get(y_parent).removeAll(map.get(y_parent));
			
			if (map.get(y_parent).isEmpty()){
				map.remove(y_parent);				
			}		
		}
	} 
	
	public static String[] readData() throws IOException {
		FileReader fr = new FileReader(System.getProperty("user.dir") + "/src/kruskal_mst_algorithm_clustering/clustering_big.txt");
		BufferedReader br = new BufferedReader(fr);
		Map<String, Integer> nodes = new HashMap<String, Integer>();
		int position = 1;
		String line = "";
		while((line = br.readLine()) != null) {						
			if (!nodes.containsKey(line)) {
				nodes.put(line, position);
				position++;
			} 
		}
		br.close();
		String[] nodes_info = new String[nodes.size()+1];
		for (Map.Entry<String, Integer> entry: nodes.entrySet()) {
			nodes_info[entry.getValue()] = entry.getKey();
		}
		return nodes_info;
	}
}
