package scc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StronglyConnectedComponents {

	public static void main(String[] args) {
		
		// load the data and represent the graph
//		Scanner sc = new Scanner(System.in);
		Graph graph = new Graph();
		List<Integer> tails = new ArrayList<Integer>();
		List<Integer> heads = new ArrayList<Integer>();
		int j = 0;
		int k = 0;
//		while(j < 8) {
//			tails.add(sc.nextInt());
//			heads.add(sc.nextInt());
//			j++;
//		}
	
		try {
			File file = new File(System.getProperty("user.dir") + "/src/scc/SCC.txt");
			BufferedReader input = new BufferedReader(new FileReader(file));
			String line = input.readLine();
			while (line != null) {
				String[] nodes = line.split(" ");
				tails.add(Integer.parseInt(nodes[0]));
				heads.add(Integer.parseInt(nodes[1]));				
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < tails.size(); i++) {
			if ((graph.getEdgesList().isEmpty()) || tails.get(i) != graph.getEdgesList()
					.get(k-1).getTail().getVertex_num()) {
				graph.getEdgesList().add(new EdgeList(new Vertex(tails.get(i))));
				graph.getEdgesList().get(k).addEdge(new Edge(new Vertex(tails.get(i)), 
						new Vertex(heads.get(i))));
				k++;
			} else {
				graph.getEdgesList().get(k-1).addEdge(new Edge(new Vertex(tails.get(i)), 
						new Vertex(heads.get(i))));
			}
		}
		
		// Kosaraju's two pass algorithm
		int finishing_time = 0;
		Vertex s = null;
		// Step 1: reverse the original graph		
		for (int i = 0; i < graph.getEdgesList().size(); i++) {
			graph.getEdgesList().get(i).getEdges().clear();
			for (int p = 0; p < heads.size(); p++) {
				if (heads.get(p)==graph.getEdgesList().get(i).getTail().getVertex_num()) {
					graph.getEdgesList().get(i).addEdge(new Edge(graph.getEdgesList().get(i).getTail(), 
						graph.getEdgesList().get(tails.get(p)-1).getTail()));
				}
			}
		}
		// Step 2: run DFS-loop on reversed graph
		for (int i = graph.getEdgesList().size()-1; i >= 0; i--) {
			if (!graph.getEdgesList().get(i).getTail().isIs_explored()) {
				s = graph.getEdgesList().get(i).getTail();
				finishing_time = depthFirstSearch(graph, i, s, finishing_time);
			}
		}
		

		// Step 3: run DFS-loop on original graph
		finishing_time = 0;
		s = null;
		k = 0;
		for (int i = 0; i < graph.getEdgesList().size(); i++) {
			graph.getEdgesList().get(i).getEdges().clear();
			graph.getEdgesList().get(i).getTail().setIs_explored(false);
		}
		for (int i = 0; i < heads.size(); i++) {
			if (graph.getEdgesList().get(k).getTail().getVertex_num() ==
					tails.get(i)) {
				graph.getEdgesList().get(k).addEdge(new Edge(graph.getEdgesList().get(k).getTail(),
						graph.getEdgesList().get(heads.get(i)-1).getTail()));
				
			} else {
				k++;
				graph.getEdgesList().get(k).addEdge(new Edge(graph.getEdgesList().get(k).getTail(),
						graph.getEdgesList().get(heads.get(i)-1).getTail()));
			}
		}
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < graph.getEdgesList().size(); i++) {
			map.put(graph.getEdgesList().get(i).getTail().getFinishing_time(), 
					graph.getEdgesList().get(i).getTail().getVertex_num());
		}
		k = 1;
		for (int i = 0; i < graph.getEdgesList().size(); i++) {
			graph.getEdgesList().get(i).setOrginalLength(graph.getEdgesList().get(i).getEdges().size());
		}
		while (k <= graph.getEdgesList().size()) {
			for (int i = 0; i < graph.getEdgesList().get(map.get(k)-1).getOriginalLength(); i++) {
				if (graph.getEdgesList().get(map.get(k)-1).getOriginalLength() <  
						graph.getEdgesList().get(k-1).getOriginalLength() && 
						i > graph.getEdgesList().get(map.get(k)-1).getOriginalLength()-1) {
					graph.getEdgesList().get(k-1).getEdges().remove(i);
				} else if (i+1 > graph.getEdgesList().get(k-1).getEdges().size() &&
						graph.getEdgesList().get(map.get(k)-1).getOriginalLength() >= 
						graph.getEdgesList().get(k-1).getEdges().size()) {
					graph.getEdgesList().get(k-1).addEdge(new Edge(graph.getEdgesList().get(k-1).getTail(),
							new Vertex(0)));
					graph.getEdgesList().get(k-1).setOrginalLength(
							graph.getEdgesList().get(k-1).getEdges().size()-1);
					graph.getEdgesList().get(k-1).getEdges().get(i).setSecondHead(graph.getEdgesList().
							get(graph.getEdgesList().get(map.get(k)-1).getEdges().get(i).
									getHead().getFinishing_time()-1).getTail());
				} else {
					graph.getEdgesList().get(k-1).getEdges().get(i).setSecondHead(graph.getEdgesList().
							get(graph.getEdgesList().get(map.get(k)-1).getEdges().get(i).
									getHead().getFinishing_time()-1).getTail());
				}
			}			
			k++;
		}		
		for (int i = graph.getEdgesList().size()-1; i >= 0; i--) {
			if (!graph.getEdgesList().get(i).getTail().isIs_explored()) {
				s = graph.getEdgesList().get(i).getTail();
				finishing_time = depthFirstSearchSecond(graph, i, s, finishing_time);
			}
		}
		k = 0;
		Map<Integer, Integer> result = new HashMap<Integer, Integer>();
		for (int i = 0; i < graph.getEdgesList().size(); i++) {
			if (!result.containsKey(graph.getEdgesList().get(i).
					getTail().getLeader().getVertex_num())) {
				result.put(graph.getEdgesList().get(i).
						getTail().getLeader().getVertex_num(), 1);
			} else {
				result.replace(graph.getEdgesList().get(i).
						getTail().getLeader().getVertex_num(), result.get(graph.getEdgesList().get(i).
						getTail().getLeader().getVertex_num())+1);
			}
		}
		System.out.println(result.values());
	}
	
	public static int depthFirstSearch(Graph graph, int position, Vertex leader, int finishing_time) {
		graph.getEdgesList().get(position).getTail().setIs_explored(true);
		graph.getEdgesList().get(position).getTail().setLeader(leader);
		for (int i = 0; i < graph.getEdgesList().get(position).getEdges().size(); i++) {
			if (!graph.getEdgesList().get(position).getEdges().get(i).getHead().isIs_explored()) {
				finishing_time = depthFirstSearch(graph, graph.getEdgesList().get(position).getEdges().
						get(i).getHead().getVertex_num()-1, leader, finishing_time);
			}
		}
		finishing_time++;
		graph.getEdgesList().get(position).getTail().setFinishing_time(finishing_time);
		return finishing_time;
	}
	
	public static int depthFirstSearchSecond(Graph graph, int position, Vertex leader, int finishing_time) {
		graph.getEdgesList().get(position).getTail().setIs_explored(true);
		graph.getEdgesList().get(position).getTail().setLeader(leader);
		for (int i = 0; i < graph.getEdgesList().get(position).getEdges().size(); i++) {
			if (graph.getEdgesList().get(position).getEdges().get(i).getSecondHead()
					.getVertex_num() == 0) {
				continue;
			}
			if (!graph.getEdgesList().get(position).getEdges().get(i).getSecondHead().isIs_explored()) {
				finishing_time = depthFirstSearchSecond(graph, graph.getEdgesList().get(position).getEdges().
						get(i).getSecondHead().getVertex_num()-1, leader, finishing_time);
			}
		}
		finishing_time++;
		graph.getEdgesList().get(position).getTail().setFinishing_time(finishing_time);
		return finishing_time;
	}

}
