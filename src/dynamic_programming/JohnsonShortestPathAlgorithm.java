package dynamic_programming;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class JohnsonShortestPathAlgorithm {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BellmanFordGraph graph = readData();
		int shortest_path = 100000;
		int shortest_shortest_path = 100000;
		boolean has_negative_cycle = false;
		int n = graph.vertex_num - 1;
		
		// Step 1: add vertex 0 to the graph with length 0 to every other vertex
		for (int i = 1; i < graph.vertex_num; i++) {
			graph.addEdge(0, i, 0);
		}
		
		// Step 2: Run Bellman-Ford Algorithm with vertex 0
		int[][] shortest_path_distance = new int[graph.vertex_num][graph.vertex_num];
		
		// initialize the distance array
		for (int i = 1; i < graph.vertex_num; i++) {
			if (0 != i) {
				shortest_path_distance[0][i] = 100000;
			}
		}
		
		// main for loop
		for (int i = 1; i < graph.vertex_num; i++) {
			for (int v = 1; v < graph.vertex_num; v++) {  
				int min_w_v = 100000;
				for (int w = 0; w < graph.back_edge_list[v].size(); w++) {
					if (min_w_v > shortest_path_distance[i-1][graph.back_edge_list[v].get(w)] 
							+ graph.length_list[v].get(w)) {
						min_w_v = shortest_path_distance[i-1][graph.back_edge_list[v].get(w)] 
								+ graph.length_list[v].get(w);
					}
				}
				shortest_path_distance[i][v] = returnSmaller(shortest_path_distance[i-1][v],
						min_w_v);
			}
		}
		
		// check if there exists a negative cycle
		for (int i = 1; i < graph.vertex_num; i++) {
			if (shortest_path_distance[n-1][i] != shortest_path_distance[n][i]) {
				has_negative_cycle = true;
				break;
			}
		}
		if (has_negative_cycle) {
			System.out.println("Has negative cycle.");
		} else {
			for (int i = 0; i < n; i++) {
				if (shortest_path > shortest_path_distance[n-1][i]) {
					shortest_path = shortest_path_distance[n-1][i];
				}
			}
//			if (shortest_shortest_path > shortest_path) {
//				shortest_shortest_path = shortest_path;
//			}
			
			// Step 3: re-weighting the graph edge
			Map<Integer, Integer> vertex_path_pair = new HashMap<Integer, Integer>();
			int min_to_v = 100000;
			for (int v = 1; v < graph.vertex_num; v++) {
				for (int i = 0; i < graph.vertex_num; i++) {
					if (min_to_v > shortest_path_distance[i][v]) {
						min_to_v = shortest_path_distance[i][v];
					}
				}
				vertex_path_pair.put(v, min_to_v);
			}
			for (int i = 1; i < graph.vertex_num; i++) {
				for (int j = 0; j < graph.back_edge_list[i].size(); j++) {
					int previous = graph.length_list[i].get(j);
					int p_i = vertex_path_pair.get(i);
					int p_u = vertex_path_pair.get(graph.back_edge_list[i].get(j));
					int source = graph.back_edge_list[i].get(j);
					int destination = graph.back_edge_list[source].get(graph.back_edge_list[source].indexOf(i));
					graph.forward_length_list[source].
						set(destination, previous + p_u - p_i);
				}
			}
			
			// Step 4: Dijkstra's Algorithm
			for (int vertex = 1; vertex < graph.vertex_num; vertex++) {
				List<Integer> x = new ArrayList<Integer>(); // processed vertices from vertex 1 to others
				x.add(vertex);
				List<Integer> s = new ArrayList<Integer>(); // computed shortest path distances
				s.add(0);
				PriorityQueue<Pair> heap = new PriorityQueue<Pair>(new LengthComparator());
				
				while(x.size() < graph.vertex_num - 1) {
					for (int i = 1; i < graph.vertex_num; i++) {
						for (int j = 0; j < graph.back_edge_list[i].size(); j++) {
							if (x.contains(i) && !x.contains(graph.back_edge_list[i].get(j))) {
								
								Pair pair = new Pair(i, graph.back_edge_list[i].get(j), s.get(x.indexOf(i)) + 
										graph.forward_length_list[i].get(graph.back_edge_list[i].get(j)));
								heap.add(pair);
							}
						}
					}				
					x.add(heap.peek().destination);
					
					// Step 5: re-calculate the distances
					s.add(s.get(x.indexOf(heap.peek().source)) + heap.peek().weight - 
							vertex_path_pair.get(heap.peek().source) + vertex_path_pair.get(heap.peek().destination));
					heap.clear();
				}
				for (int i = 0; i < s.size(); i++) {
					if (shortest_shortest_path > s.get(i)) {
						shortest_shortest_path = s.get(i);
					}
				}
			}
			System.out.println(shortest_shortest_path);
		}
		
	}

	public static int returnSmaller(int a, int b) {
		if (a < b) {
			return a;
		} else {
			return b;
		}
	}
	
	public static BellmanFordGraph readData() throws IOException {
		FileReader fr = new FileReader(System.getProperty("user.dir") + "/src/dynamic_programming/g3.txt");
		BufferedReader br = new BufferedReader(fr);
		BellmanFordGraph graph = new BellmanFordGraph(1001);		
		String line = "";
		while((line = br.readLine()) != null) {						
			graph.addEdge(Integer.parseInt(line.split(" ")[0]), Integer.parseInt(line.split(" ")[1]), 
					Integer.parseInt(line.split(" ")[2]));
		}
		br.close();
		return graph;
	}
}

class LengthComparator implements Comparator<Pair>{

	@Override
	public int compare(Pair p1, Pair p2) {
		// TODO Auto-generated method stub
		int value1 = p1.weight;
		int value2 = p2.weight;
		return value1-value2;
	}
	
}
class Pair{
	int source;
	int destination;
	int weight;
	
	public Pair(int source, int destination, int weight) {
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}
}
