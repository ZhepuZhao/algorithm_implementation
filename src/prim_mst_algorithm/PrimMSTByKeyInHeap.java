package prim_mst_algorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class PrimMSTByKeyInHeap {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		int total_cost = 0;
		int heap_root = 1;

		int[][] edges = readData();
		
		List<Integer> x = new ArrayList<Integer>();		
		x.add(1);
		
		PriorityQueue<Integer> remaining_vertices = new PriorityQueue<Integer>();
		Map<Integer, Integer> key_vertex_pair = new HashMap<Integer, Integer>();
		key_vertex_pair.put(1, 0);
		
		while (x.size() != 500) {
			// initialize heap
			for (int i = 0; i < edges.length; i++) {
				if ((x.contains(edges[i][0]) && !x.contains(edges[i][1])) ||
						(x.contains(edges[i][1]) && !x.contains(edges[i][0]))) {
					if (!key_vertex_pair.containsKey(edges[i][0])) {
						remaining_vertices.add(edges[i][2]);
					} else if (!key_vertex_pair.containsKey(edges[i][1])) {
						remaining_vertices.add(edges[i][2]);
					}
					if (x.contains(edges[i][0]) && key_vertex_pair.containsKey(edges[i][1]) 
							&& edges[i][2] < key_vertex_pair.get(edges[i][1])) {
						remaining_vertices.remove(key_vertex_pair.get(edges[i][1]));
						key_vertex_pair.replace(edges[i][1], edges[i][2]);
						remaining_vertices.add(edges[i][2]);
						if (remaining_vertices.peek() == edges[i][2]) {
							heap_root = edges[i][1];
						}
					}
					if (x.contains(edges[i][1]) && key_vertex_pair.containsKey(edges[i][0]) 
							&& edges[i][2] < key_vertex_pair.get(edges[i][0])) {
						remaining_vertices.remove(key_vertex_pair.get(edges[i][0]));
						key_vertex_pair.replace(edges[i][0], edges[i][2]);
						remaining_vertices.add(edges[i][2]);
						if (remaining_vertices.peek() == edges[i][2]) {
							heap_root = edges[i][0];
						}
					}
										
					if (remaining_vertices.peek() == edges[i][2]) {
						if (x.contains(edges[i][0])) {
							heap_root = edges[i][1];
							key_vertex_pair.put(edges[i][1], edges[i][2]);
						} else {
							heap_root = edges[i][0];
							key_vertex_pair.put(edges[i][0], edges[i][2]);
						}
					}
					
				} else if(!key_vertex_pair.containsKey(edges[i][0])) {
					remaining_vertices.add((int)Double.POSITIVE_INFINITY);
					key_vertex_pair.put(edges[i][0], (int)Double.POSITIVE_INFINITY);					
				} else if(!key_vertex_pair.containsKey(edges[i][1])) {
					remaining_vertices.add((int)Double.POSITIVE_INFINITY);
					key_vertex_pair.put(edges[i][1], (int)Double.POSITIVE_INFINITY);
				}
			}
			total_cost += remaining_vertices.poll();
			x.add(heap_root);
			for (int i = 0; i < edges.length; i++) {
				if ((edges[i][0]==x.get(x.size()-1) && !x.contains(edges[i][1])) ||
						(edges[i][1]==x.get(x.size()-1) && !x.contains(edges[i][0]))) {
					
					if (edges[i][0] == x.get(x.size()-1)) {
						remaining_vertices.remove(key_vertex_pair.get(edges[i][1]));
						key_vertex_pair.replace(edges[i][1], min(edges[i][2], key_vertex_pair.get(edges[i][1])));
						remaining_vertices.add(key_vertex_pair.get(edges[i][1]));
						if (remaining_vertices.peek() == key_vertex_pair.get(edges[i][1])) {
							heap_root = edges[i][1];
						}
						
					} else {
						remaining_vertices.remove(key_vertex_pair.get(edges[i][0]));
						key_vertex_pair.replace(edges[i][0], min(edges[i][2], key_vertex_pair.get(edges[i][0])));
						remaining_vertices.add(key_vertex_pair.get(edges[i][0]));
						if (remaining_vertices.peek() == key_vertex_pair.get(edges[i][0])) {
							heap_root = edges[i][0];
						}
					}					
				}
			}			
		}		
		System.out.println(total_cost);
	}
	
	public static int min(int a, int b) {
		if (a >= b) {
			return b; 
		} else {
			return a;
		}
	}
	public static int[][] readData() throws IOException {
		FileReader fr = new FileReader(System.getProperty("user.dir") + "/src/greedy_algorithms/edges.txt");
		BufferedReader br = new BufferedReader(fr);
		int[][] edges = new int[2184][3];	
//		int[][] edges = new int[10][3];	
		int position = 0;
		String line = "";
		while((line = br.readLine()) != null) {						
			edges[position][0] = Integer.parseInt(line.split(" ")[0]);
			edges[position][1] = Integer.parseInt(line.split(" ")[1]);
			edges[position][2] = Integer.parseInt(line.split(" ")[2]);
			position++;
		}
		return edges;
	}

}
