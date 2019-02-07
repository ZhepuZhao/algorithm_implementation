package scc;

import java.util.ArrayList;
import java.util.List;

public class EdgeList {

	private Vertex tail;
	private List<Edge> edges;
	private int original_length;
	
	public EdgeList(Vertex tail) {
		// TODO Auto-generated constructor stub
		this.tail = tail;
		this.edges = new ArrayList<Edge>();
		this.original_length = edges.size();
	}
	
	public Vertex getTail() {
		return this.tail;
	}
	
	public void setTail(Vertex v) {
		this.tail = v;
	}
	
	public void addEdge(Edge e) {
		edges.add(e);
	}
	
	public List<Edge> getEdges(){
		return this.edges;
	}
	
	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}
	
	public int getOriginalLength() {
		return this.original_length;
	}
	
	public void setOrginalLength(int length) {
		this.original_length = length;
	}

}
