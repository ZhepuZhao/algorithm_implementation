package np_complete;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;


public class TravelingSalesmanProblem {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Point[] point = readData();
		Graph graph = new Graph(point.length);
		for (int i = 0; i < graph.vertex_num; i++) {
			for (int j = 0; j < graph.vertex_num; j++) {
				if (i != j) {
					graph.edge_list[i].addFirst(j);
					graph.length_list[i].addFirst(point[i].distanceTo(point[j]));
				}
			}
		}
		
		// Construct the subset of S
//		LinkedList<Integer>[]  s = new LinkedList[4096];
//		LinkedList<Integer>[]  s = new LinkedList[8192];
		LinkedList<Integer>[]  s = new LinkedList[16777216];
		int k = 2;
		s[0] = new LinkedList<Integer>();
		s[0].addFirst(1);
		for (int i = 1; i < point.length; i++) {
			s[i] = new LinkedList<Integer>();
			s[i].addFirst(k);
			k++;
		}		
		int previous_first_index = 1;
		int previous_last_index = point.length-1;
		int[] element_index = new int[point.length];
		int q = 1;
		k = point.length;
		while(k < s.length) {
			for (int j = previous_first_index; j <= previous_last_index; j++) {
				for (int p = s[j].getFirst()+1; p <= point.length; p++) {
					s[k] = new LinkedList<Integer>();
					s[k].addAll(s[j]);
					s[k].addFirst(p);
					k++;
				}
			}
			previous_first_index = previous_last_index + 1;
			previous_last_index = k - 1;
		}
		for (int i = 1; i < s.length; i++) {
			s[i].addLast(1);
		}
		int a = s[0].size();
		for (int i = 0; i < s.length; i++) {
			if (a != s[i].size()) {
				element_index[q] = i;
				q++;
			}
			a = s[i].size();
		}
		
//		int temp = 0;
//		System.out.println(Math.pow(2, 24));
		// dynamic programming algorithm
//		double[][] distance = new double[4096][14];
//		double[][] distance = new double[8192][15];
		double[][] distance = new double[16777216][26];
		double min = 100000000;
		distance[0][1] = 0;
		for (int i = 1; i < distance.length; i++) {
			distance[i][1] = 100000000;
		}
		for (int i = 1; i < s.length; i++) { // index of subset, at least 2 elements
			for (int j = 0; j < s[i].size(); j++) { // for each destination in the choose subset i
				int destination = s[i].get(j); // the destination
				if (destination != 1) {
					for (int l = 0; l < s[i].size(); l++) { //  l is index of k in algorithm
						if (s[i].size() > 2) {
							if (s[i].get(l) != destination && s[i].get(l) != 1) {							
								for (int m = element_index[s[i].size()-2]; 
										m < element_index[s[i].size()-1]; m++) {
									LinkedList<Integer> temp = new LinkedList<Integer>();
									temp.addAll(s[m]);
									temp.add(destination);
									if (!s[m].contains(destination) && temp.containsAll(s[i])) {
										if (min > distance[m][s[i].get(l)] + point[s[i].get(l)-1].distanceTo(point[destination-1])) {
											min = distance[m][s[i].get(l)] + point[s[i].get(l)-1].distanceTo(point[destination-1]);
										}
									}
								}
							}
						} else {
							if (s[i].get(l) != destination) {							
								for (int m = element_index[s[i].size()-2]; 
										m < element_index[s[i].size()-1]; m++) {
									LinkedList<Integer> temp = new LinkedList<Integer>();
									temp.addAll(s[m]);
									temp.add(destination);
									if (!s[m].contains(destination) && temp.containsAll(s[i])) {
										if (min > distance[m][s[i].get(l)] + point[s[i].get(l)-1].distanceTo(point[destination-1])) {
											min = distance[m][s[i].get(l)] + point[s[i].get(l)-1].distanceTo(point[destination-1]);
										}
									}
								}
							}
						}						
					}
					distance[i][destination] = min;
					min = 100000000; // remember to set the minimum to maximum
				}
			}
		}
		min = 100000000;
		for (int j = 1; j <= point.length; j++) {
			if (min > distance[distance.length-1][j] + point[j-1].distanceTo(point[0])) {
				min = distance[distance.length-1][j] + point[j-1].distanceTo(point[0]);
			}
		}
//		System.out.println("distance between 12 and 13: " + point[11].distanceTo(point[12]));
		System.out.println(min);
		// 14662.0046407879
		// 14409.202165641733
		double result = 14662.0046407879 + 14409.202165641733 - 2 * 1314.2382487374398;  
		System.out.println(result);
		
	}

	public static Point[] readData() throws IOException {
		FileReader fr = new FileReader(System.getProperty("user.dir") + "/src/np_complete/tsp.txt");
		BufferedReader br = new BufferedReader(fr);
//		Point[] point = new Point[3];
		Point[] point = new Point[25];	
		String line = "";
		int i = 0;
		while((line = br.readLine()) != null) {						
			point[i] = new Point(Double.parseDouble(line.split(" ")[0]),
					Double.parseDouble(line.split(" ")[1]));
			i++;
		}
		br.close();
		return point;
	}
	
}

class Graph{

	int vertex_num;
	LinkedList<Integer>[] edge_list;
	LinkedList<Double>[] length_list;
	
	public Graph(int vertex_num) {
		this.vertex_num = vertex_num;
		edge_list = new LinkedList[vertex_num];
		for (int i = 0; i < vertex_num; i++) {
			edge_list[i] = new LinkedList<Integer>();
		}
		length_list = new LinkedList[vertex_num];
		for (int i = 0; i < vertex_num; i++) {
			length_list[i] = new LinkedList<Double>();
		}
	}
	
	public void addEdge(int source, int destination, double length) {
		edge_list[source].addFirst(destination);
		length_list[source].addFirst(length);
//		// add the following line if the graph is a directed one 
//		edge_list[destination].addFirst(source);
	}
	
}

class Point{
	boolean visited;
	double x;
	double y;
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
		visited = false;
	}
	
	public double distanceTo(Point b) {
		return Math.sqrt(Math.pow(x-b.x, 2) + Math.pow(y-b.y, 2));
	}
}
