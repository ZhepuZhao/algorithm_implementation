package kruskal_mst_algorithm_clustering;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class KruskalMSTAlgorithm {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		// load data 
		Pair[] distances = readData();
		
		// sort the distance in increasing order
		QuickSort(distances, 0, distances.length-1);
		
		// number of vertices
		Map<Integer,ArrayList<Integer>> map = new HashMap<Integer,ArrayList<Integer>>();
		for (int i = 0; i <= 500; i++) {
			map.put(i, new ArrayList<Integer>());	
			map.get(i).add(i);
		}
		int spacing = 0;
		
		for (int i = 0; i < distances.length; i++) {
			if (map.size() > 5) {
				union(map, distances[i].getX(), distances[i].getY());	
			} else {
				if (find(map, distances[i].getX()) != find(map, distances[i].getY())) {	
					if (spacing == 0) {
						spacing = distances[i].getDistance();
					} else {
						spacing = min(distances[i].getDistance(),spacing);
					}					
				}
				
			}						
		}
		System.out.println(spacing);
		
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
	
	public static Pair[] readData() throws IOException {
		FileReader fr = new FileReader(System.getProperty("user.dir") + "/src/kruskal_mst_algorithm_clustering/clustering1.txt");
		BufferedReader br = new BufferedReader(fr);
		Pair[] pairs = new Pair[124750];	
		int position = 0;
		String line = "";
		while((line = br.readLine()) != null) {						
			pairs[position] = new Pair(Integer.parseInt(line.split(" ")[0]),Integer.parseInt(line.split(" ")[1]),
					Integer.parseInt(line.split(" ")[2]));
			position++;
		}
		br.close();
		return pairs;
	}

	public static void QuickSort(Pair[] inputArray, int start, int end) {
		if ((end-start) == 0) return;
		int splitBoundary = partition(inputArray, start, end);
		if(start < splitBoundary) {
			QuickSort(inputArray, start, splitBoundary-1);
		}
		if(splitBoundary+1 < end) {
			QuickSort(inputArray, splitBoundary+1, end);
		}
	}

	public static int partition(Pair[] inputArray, int start, int end) {
		int i = start+1;
		int pivot = start; // take the first element of each array as the pivot element
		Pair temp = new Pair(0,0,0);
		for (int j = start+1; j <= end; j++) {
			if (inputArray[j].getDistance() < inputArray[pivot].getDistance()) {
				temp = inputArray[j];
				inputArray[j] = inputArray[i];
				inputArray[i] = temp;
				i++;
			}	
		}
		temp = inputArray[i-1];
		inputArray[i-1] = inputArray[pivot];
		inputArray[pivot] = temp;
		return i-1;
	}

}

class Pair{
	private int x;
	private int y;
	private int distance;
	public Pair(int x, int y, int d) {
		this.x = x;
		this.y = y;
		this.distance = d;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
		
	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

}
