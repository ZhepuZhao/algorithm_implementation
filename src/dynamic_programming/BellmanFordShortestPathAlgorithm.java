package dynamic_programming;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/*
 * The Bellman-ford Algorithm took about 20 mins to compute the final results for g3.txt
 * g1.txt and g2.txt have non-negative cycles
 */
public class BellmanFordShortestPathAlgorithm {

	public static void main(String[] args) throws IOException {

		BellmanFordGraph graph = readData();
		// the first index: number of edges allowed to compute the path distance
		// the second index: vertex number
		// take 100000 as a infinite big number
		int shortest_path = 100000;
		int shortest_shortest_path = 100000;
		boolean has_negative_cycle = false;
		int n = graph.vertex_num - 1;
		
		// for each vertex in the BellmanFordGraph
		for (int j = 1; j < graph.vertex_num; j++) {
			int[][] shortest_path_distance = new int[graph.vertex_num][graph.vertex_num];
			
			// initialize the distance array
			for (int i = 1; i < graph.vertex_num; i++) {
				if (j != i) {
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
			if (!has_negative_cycle) {
				for (int i = 0; i < n; i++) {
					if (shortest_path > shortest_path_distance[n-1][i]) {
						shortest_path = shortest_path_distance[n-1][i];
					}
				}
				if (shortest_shortest_path > shortest_path) {
					shortest_shortest_path = shortest_path;
				}
			} else {
				break;
			}

		}
		if (has_negative_cycle) {
			System.out.println("has negative cycle");
		} else {
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
		BellmanFordGraph BellmanFordGraph = new BellmanFordGraph(1001);		
		String line = "";
		while((line = br.readLine()) != null) {						
			BellmanFordGraph.addEdge(Integer.parseInt(line.split(" ")[0]), Integer.parseInt(line.split(" ")[1]), 
					Integer.parseInt(line.split(" ")[2]));
		}
		br.close();
		return BellmanFordGraph;
	}

}

class BellmanFordGraph{

	int vertex_num;
	LinkedList<Integer>[] edge_list;
	LinkedList<Integer>[] length_list;
	LinkedList<Integer>[] forward_length_list;
	LinkedList<Integer>[] back_edge_list;
	
	public BellmanFordGraph(int vertex_num) {
		this.vertex_num = vertex_num;
		edge_list = new LinkedList[vertex_num];
		for (int i = 0; i < vertex_num; i++) {
			edge_list[i] = new LinkedList<Integer>();
		}
		length_list = new LinkedList[vertex_num];
		for (int i = 0; i < vertex_num; i++) {
			length_list[i] = new LinkedList<Integer>();
		}
		back_edge_list = new LinkedList[vertex_num];
		for (int i = 0; i < vertex_num; i++) {
			back_edge_list[i] = new LinkedList<Integer>();
		}
		forward_length_list = new LinkedList[vertex_num];
		for (int i = 0; i < vertex_num; i++) {
			forward_length_list[i] = new LinkedList<Integer>();
		}
		
	}
	
	public void addEdge(int source, int destination, int length) {
		edge_list[source].addFirst(destination);
		back_edge_list[destination].addFirst(source);
		length_list[destination].addFirst(length);
		forward_length_list[source].addFirst(length);
//		// add the following line if the BellmanFordGraph is a directed one 
//		edge_list[destination].addFirst(source);
	}
	
	public void printBellmanFordGraph() {
		for (int i = 0; i < vertex_num; i++) {
			if (edge_list[i].size() > 0) {
				System.out.print("Vertex " + i + " is connected to: ");
				for (int j = 0; j < edge_list[i].size(); j++) {
					System.out.print(edge_list[i].get(j) + " with length " + 
							length_list[i].get(j) + ", ");
				}
				System.out.println();
				
			}
		}
	}
}

