package prim_mst_algorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class PrimMSTByNodeInHeap {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		// construct the graph
		int[][] edges = readData();		
		Graph graph = new Graph(7);
		for (int i = 0; i < edges.length; i++) {
			graph.addEdge(edges[i][0], edges[i][1], edges[i][2]);
		}
		graph.printGraph();
				
		// necessary variables
		int total_cost = 0;
		List<Integer> x = new ArrayList<Integer>();		
		x.add(1);		
		PriorityQueue<Pair> remaining_vertices = new PriorityQueue<Pair>(new LengthComparator());
		Map<Integer, Integer> cost_vertex_pair = new HashMap<Integer, Integer>();
		cost_vertex_pair.put(1, 0);
		
		// main while loop
		while (x.size() != 10) {
			
			// add values into heap
			for (int i = 1; i < graph.getVertexNum(); i++) {
				if (x.contains(i)) {
					for(int j = 0; j < graph.getList()[i].size(); j++) {
						List<Pair> list = graph.getList()[i];
						if (!x.contains(list.get(j).getKey())){
							remaining_vertices.add(new Pair(list.get(j).getKey(),list.get(j).getValue()));
							cost_vertex_pair.put(list.get(j).getKey(), list.get(j).getValue());
						}
					}
				}
			}
			
			// add the cheapest pair's key into x
			x.add(remaining_vertices.peek().getKey());
			System.out.println(remaining_vertices.peek().getKey() + " " + remaining_vertices.peek().getValue());
			// add the cost to the total variable, and delete the cheapest pair
			total_cost += remaining_vertices.poll().getValue();
			
			// update the cost value of each pair
			for (int i = 1; i < graph.getVertexNum(); i++) {
				if (x.get(x.size()-1) == i) {
					for(int j = 0; j < graph.getList()[i].size(); j++) {
						List<Pair> list = graph.getList()[i];
						if (!x.contains(list.get(j).getKey())) {
							remaining_vertices.remove(list.get(j));
							if (cost_vertex_pair.containsKey(list.get(j).getKey())) {
								remaining_vertices.add(new Pair(list.get(j).getKey(),
										min(list.get(j).getValue(),cost_vertex_pair.get(list.get(j).getKey()))));
							} else {
								remaining_vertices.add(new Pair(list.get(j).getKey(),list.get(j).getValue()));
							}
							
						}
					}
				}
			}
			
			
		}

		total_cost++;
	}
	
	public static int min(int a, int b) {
		if (a >= b) {
			return b; 
		} else {
			return a;
		}
	}
	
	public static int[][] readData() throws IOException {
		FileReader fr = new FileReader(System.getProperty("user.dir") + "/src/greedy_algorithms/edges_test.txt");
		BufferedReader br = new BufferedReader(fr);
//		int[][] edges = new int[2184][3];	
		int[][] edges = new int[10][3];
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

class LengthComparator implements Comparator<Pair>{

	@Override
	public int compare(Pair p1, Pair p2) {
		// TODO Auto-generated method stub
		int value1 = p1.getValue();
		int value2 = p2.getValue();
		return value1-value2;
	}
	
}
class Pair {
	private int key;
	private int value;
	
	public Pair(int key, int value) {
		this.key = key;
		this.value = value;
	}
	
	public int getKey() {
		return this.key;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public void setKey(int key) {
		this.key = key;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
}
class Vertex {
	private int vertex_value;
	
	public Vertex(int num) {
		this.vertex_value = num;
	}
	
	public int getVertexNum() {
		return this.vertex_value;
	}
	
	public void setVertexNum(int num) {
		this.vertex_value = num;
	}
}

class Graph {
	private int vertex_num;
	private LinkedList<Pair>[] list;
	
	public Graph(int num) {
		this.vertex_num = num;
		this.list = new LinkedList[num];
		for (int i = 0; i < num; i++) {
			list[i] = new LinkedList<>();
		}		
	}
	
	public void addEdge(int source, int destination, int length) {
		// for directed graph
		list[source].addFirst(new Pair(destination,length));;
		// for undirected graph, add the following one
		list[destination].addFirst(new Pair(source, length));;
	}
	
	public int getVertexNum() {
		return this.vertex_num;
	}
	
	public List<Pair>[] getList(){
		return this.list;
	}
	
    public void printGraph(){
        for (int i = 0; i < this.vertex_num ; i++) {
            if(list[i].size()>0) {
                System.out.print("Vertex " + i + " is connected to: ");
                for (int j = 0; j < list[i].size(); j++) {
                    System.out.print(list[i].get(j).getKey() + "(" +list[i].get(j).getValue() +  ")" + " ");
                }
                System.out.println();
            }
        }
    }
}
