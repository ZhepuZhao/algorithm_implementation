package scheduling_application;

public class Job {

	private int weight;
	private int length;
	private int priority;
	private float optimal_priority;
	
	public Job(int weight, int length) {
		// TODO Auto-generated constructor stub
		this.weight = weight;
		this.length = length;
		this.priority = weight - length;
		this.optimal_priority = (float)weight / (float)length;
	}
	
	public int getWeight() {
		return this.weight;		
	}
	
	public int getLength() {
		return this.length;
	}
	
	public int getPriority() {
		return this.priority;
	}
	
	public float getOptimalPriority() {
		return this.optimal_priority;
	}
	
}
