package dynamic_programming;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class FloydWarshallShortestPathAlgorithm {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		// Take 100000 as a infinitely big number of edge length
		Graph graph = readData();
		int shortest_shortest_path = 100000;
		boolean has_negative_cycle = false;
		int n = graph.vertex_num - 1;
		
		// Initialize the shortest_path_distance
		int[][] shortest_path_distance = new int[n][n];
		int[][] shortest_path_distance_clone = shortest_path_distance.clone();
		
		// k = 0
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) {
					shortest_path_distance[i][j] = 0;
				} else if (graph.edge_list[i+1].contains(j+1)) {
					shortest_path_distance[i][j] = graph.length_list[i+1].
							get(graph.edge_list[i+1].indexOf(j+1));
				} else {
					shortest_path_distance[i][j] = 100000;
					shortest_path_distance[i][j] = 100000;
				}
			}
		}
		
		for (int k = 1; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					shortest_path_distance_clone[i][j] = returnSmaller(shortest_path_distance[i][j],
							shortest_path_distance[i][k] + shortest_path_distance[k][j]);
					if (shortest_shortest_path > shortest_path_distance_clone[i][j]) {
						shortest_shortest_path = shortest_path_distance_clone[i][j];
					}
				}
			}
			shortest_path_distance = shortest_path_distance_clone.clone();
		}
	
		for (int i = 0; i < n; i++) {
			if (shortest_path_distance[i][i] < 0) {
				has_negative_cycle = true;
				break;
			}
		}
		if (has_negative_cycle) {
			System.out.println("Has negative cycle");
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
	
	public static Graph readData() throws IOException {
		FileReader fr = new FileReader(System.getProperty("user.dir") + "/src/dynamic_programming/large.txt");
		BufferedReader br = new BufferedReader(fr);
		Graph graph = new Graph(20001);		
		String line = "";
		while((line = br.readLine()) != null) {						
			graph.addEdge(Integer.parseInt(line.split(" ")[0]), Integer.parseInt(line.split(" ")[1]), 
					Integer.parseInt(line.split(" ")[2]));
		}
		br.close();
		return graph;
	}

}

class Graph{

	int vertex_num;
	LinkedList<Integer>[] edge_list;
	LinkedList<Integer>[] length_list;
	
	public Graph(int vertex_num) {
		this.vertex_num = vertex_num;
		edge_list = new LinkedList[vertex_num];
		for (int i = 0; i < vertex_num; i++) {
			edge_list[i] = new LinkedList<Integer>();
		}
		length_list = new LinkedList[vertex_num];
		for (int i = 0; i < vertex_num; i++) {
			length_list[i] = new LinkedList<Integer>();
		}
	}
	
	public void addEdge(int source, int destination, int length) {
		edge_list[source].addFirst(destination);
		length_list[source].addFirst(length);
//		// add the following line if the graph is a directed one 
//		edge_list[destination].addFirst(source);
	}
	
}
