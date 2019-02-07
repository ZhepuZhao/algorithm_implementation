package scc;

public class Vertex {

	private int vertex_num;
	private boolean is_explored = false;
	private Vertex leader;
	private int finishing_time;
	
	public Vertex(int vertex_num) {
		// TODO Auto-generated constructor stub
		this.vertex_num = vertex_num;
		this.finishing_time = 0;
	}

	public int getVertex_num() {
		return vertex_num;
	}

	public void setVertex_num(int vertex_num) {
		this.vertex_num = vertex_num;
	}

	public boolean isIs_explored() {
		return is_explored;
	}

	public void setIs_explored(boolean is_explored) {
		this.is_explored = is_explored;
	}

	public Vertex getLeader() {
		return leader;
	}

	public void setLeader(Vertex leader) {
		this.leader = leader;
	}

	public int getFinishing_time() {
		return finishing_time;
	}

	public void setFinishing_time(int finishing_time) {
		this.finishing_time = finishing_time;
	}

}
