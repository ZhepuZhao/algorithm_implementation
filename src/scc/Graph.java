package scc;

import java.util.ArrayList;
import java.util.List;

public class Graph {
	
	private List<EdgeList> edgesList;
	public Graph() {
		// TODO Auto-generated constructor stub
		edgesList = new ArrayList<EdgeList>();
	}
	
	public void addEdgeList(EdgeList e) {
		edgesList.add(e);
	}
	
	public List<EdgeList> getEdgesList(){
		return edgesList;
	}

}
