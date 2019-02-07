package scc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class SCC_Others {
	private ArrayList<ArrayList<Integer>> vertices; // graph
	private ArrayList<ArrayList<Integer>> reverse; // graph with all arcs reversed
	private int[] labels; // ordering
	private int[] leader; // count how many nodes are of the same leader
	private int time; // DFS finishing time
	private int source; // leader
	private boolean[] explored; // track which nodes have been explored in DFS
	
	/**
	 * Read graph from input file.
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	
	public SCC_Others(String inputFileName) throws FileNotFoundException{
		vertices = new ArrayList<ArrayList<Integer>>();
		reverse = new ArrayList<ArrayList<Integer>>();
		Scanner in = new Scanner(new File(inputFileName));
		//add all vertices
		while (in.hasNextInt()){
			 int tail = in.nextInt();
			 int head = in.nextInt();
			 int max = Math.max(tail, head);
			 while (vertices.size() < max){
				 vertices.add(new ArrayList<Integer>());
				 reverse.add(new ArrayList<Integer>());
			 }
			 vertices.get(tail-1).add(head-1);
			 reverse.get(head-1).add(tail-1);
			 //System.out.println("Added " + tail + "->" +  head);
		}
	}
	
	/**
	 * Computes SCCs. 
	 * @return top5 an integer array of size 5, containing the sizes of the 5 
	 * largest SCCs in the given graph, in decreasing order of sizes.
	 */
	
	public int[] computeSCC(){
		int[] top5 = new int[5];
		DFSLoop1();
		DFSLoop2();
		Arrays.sort(leader);
		for (int i = 0; i < 5; i++){
			top5[i] = leader[leader.length - i - 1];
		}
		return top5;
	}
	
	/**
	 * The first DFS loop will DFS the reversed graph and labeling each nodes
	 * by the finishing time.
	 */
	
	public void DFSLoop1(){
		time = 0;
		explored = new boolean[reverse.size()];
		labels = new int[vertices.size()];
		for (int i = reverse.size()-1; i >= 0; i--){
			if (explored[i] == false){
				DFS1(i);
			}
		}
	}
	
	/**
	 * The second DFS loop will DFS the original graph. At the beginning of
	 * each loop, it will choose the largest label to begin and mark all the
	 * explored nodes' leader with this label.
	 */
	
	public void DFSLoop2(){
		explored = new boolean[vertices.size()];
		leader = new int[vertices.size()];
		for (int i = labels.length - 1; i >= 0; i--){
			int node = labels[i];
			if (explored[node] == false){
				source = node;
				DFS2(node);
			}
		}
	}
	
	/**
	 * Part of the first DFS loop.
	 * @param node
	 */
	
	public void DFS1(int node){
		explored[node] = true;
		for (int head : reverse.get(node)){
			if (explored[head] == false){
				DFS1(head);
			}
		}
		labels[time] = node;
		time++;
	}
	
	/**
	 * Part of the second DFS loop.
	 * @param node
	 */
	
	public void DFS2(int node){
		explored[node] = true;
		leader[source] ++;
		for (int head : vertices.get(node)){
			if (explored[head] == false){
				DFS2(head);
			}
		}
	}
	
	/*
	public void printGraph(){
		for (int i = 0; i < vertices.size(); i++){
			System.out.println(i + ": ");
			System.out.print("outgoings: ");
			for (int j = 0; j < vertices.get(i).size(); j++){
				System.out.print(i + "->" + vertices.get(i).get(j) + " ");
			}
			System.out.println();
			System.out.print("incommings: ");
			for (int j = 0; j < reverse.get(i).size(); j++){
				System.out.print(i + "<-" + reverse.get(i).get(j) + " ");
			}		
			System.out.println();
		}
		System.out.println();
	}
	*/
	
	public static void main(String[] args) throws FileNotFoundException{
		//Graph g = new Graph("SimpleInput.txt");
		SCC_Others g = new SCC_Others(System.getProperty("user.dir") + "/src/scc/SCC.txt");
		int[] topSCCs = g.computeSCC();
		for (int n : topSCCs){
			System.out.print(n + " ");
		}
	}
}
