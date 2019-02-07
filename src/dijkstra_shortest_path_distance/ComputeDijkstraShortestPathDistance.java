package dijkstra_shortest_path_distance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComputeDijkstraShortestPathDistance {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		
		List<Integer> processedVerticesNumber = new ArrayList<Integer>();
		processedVerticesNumber.add(1);
		List<Integer> shortestPathDistances = new ArrayList<Integer>();
		shortestPathDistances.add(0);
		
		Graph graph = constructGraph();
		// main loop
		int min = 100000;
		int minPosition = 0;
		while(processedVerticesNumber.size() < graph.getVertices().size()) {
			// iterate all the vertices in the processed vertices
			for (int i = 0; i < processedVerticesNumber.size(); i++) {
				// iterate all the edges of a vertex in the processed vertices
				for (Map.Entry<Integer, Integer> entry: graph.getVertices().get(processedVerticesNumber.get(i)-1).getLengthPair().entrySet()) {
					if (!processedVerticesNumber.contains(entry.getKey())) {
						if (min > shortestPathDistances.get(i) + entry.getValue()) {
							min = shortestPathDistances.get(i) + entry.getValue();
							minPosition = entry.getKey();
						}
					} 
				}
			}			
			processedVerticesNumber.add(minPosition);
			shortestPathDistances.add(min);
			min = 100000;
		}
		
//		for (int i = 0; i < processedVerticesNumber.size(); i++) {
//			System.out.println(processedVerticesNumber.get(i) + ": " + shortestPathDistances.get(i));
//
//		}
		for (int i = 0; i < processedVerticesNumber.size(); i++) {
			switch(processedVerticesNumber.get(i)){
			case 7:
				System.out.println("7: " + shortestPathDistances.get(i));
				break;
			case 37:
				System.out.println("37: " + shortestPathDistances.get(i));
				break;
			case 59:
				System.out.println("59: " + shortestPathDistances.get(i));
				break;
			case 82:
				System.out.println("82: " + shortestPathDistances.get(i));
				break;
			case 99:
				System.out.println("99: " + shortestPathDistances.get(i));
				break;
			case 115:
				System.out.println("115: " + shortestPathDistances.get(i));
				break;
			case 133:
				System.out.println("133: " + shortestPathDistances.get(i));
				break;
			case 165:
				System.out.println("165: " + shortestPathDistances.get(i));
				break;
			case 188:
				System.out.println("188: " + shortestPathDistances.get(i));
				break;
			case 197:
				System.out.println("197: " + shortestPathDistances.get(i));
				break;
			}			
		}		
		
	}
	
	/*
	 * load the data and construct the graph
	 */
	public static Graph constructGraph() throws IOException {

		FileReader fr;
		String line = "";
		int vertexNum = 0;
		int neighborVertex = 0;
		int edgeLength = 0;
		
//		fr = new FileReader(System.getProperty("user.dir") + "/src/dijkstraShortestPath/dijkstraData.txt");
		fr = new FileReader(System.getProperty("user.dir") + "/src/dijkstraShortestPath/dijkstraData.txt");
		BufferedReader br = new BufferedReader(fr);
		
		Graph graph = new Graph();
		while((line = br.readLine()) != null) {
			String[] vertexInfo = line.split("	");
			vertexNum = Integer.parseInt(vertexInfo[0]);
			Vertex newVertex = new Vertex(vertexNum);			
			for (int i = 1; i < vertexInfo.length; i++) {
				String[] neighborInfo = vertexInfo[i].split(",");
				neighborVertex = Integer.parseInt(neighborInfo[0]);
				edgeLength = Integer.parseInt(neighborInfo[1]);
				newVertex.getLengthPair().put(neighborVertex, edgeLength);
			}
			graph.addVertex(newVertex);			
		}
		br.close();
		return graph;
	}
}

class Vertex{
	
	private int number;
	private Map<Integer, Integer> lengthPair;
	
	public Vertex(int number) {
		this.number = number;
		this.lengthPair = new HashMap<Integer, Integer>();
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Map<Integer, Integer> getLengthPair() {
		return lengthPair;
	}

	public void setLengthPair(Map<Integer, Integer> lengthPair) {
		this.lengthPair = lengthPair;
	}	
	
}

class Graph{
	
	private List<Vertex> vertices;
	public Graph() {
		this.vertices = new ArrayList<Vertex>();
	}
	public List<Vertex> getVertices() {
		return vertices;
	} 
	
	public void addVertex(Vertex v) {
		this.vertices.add(v);
	}
}
