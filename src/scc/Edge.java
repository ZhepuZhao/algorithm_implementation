package scc;

public class Edge {

	private Vertex tail;
	private Vertex head;
	private Vertex second_head;
	
	public Edge(Vertex tail, Vertex head) {
		// TODO Auto-generated constructor stub
		this.tail = tail;
		this.head = head;
		this.second_head = new Vertex(0);
	}

	public Vertex getTail() {
		return tail;
	}

	public void setTail(Vertex tail) {
		this.tail = tail;
	}

	public Vertex getHead() {
		return head;
	}

	public void setHead(Vertex head) {
		this.head = head;
	}
	
	public Vertex getSecondHead() {
		return second_head;
	}
	
	public void setSecondHead(Vertex head) {
		this.second_head = head;
	}
	
	
}
