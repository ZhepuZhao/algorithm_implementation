package scc;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

class Node {
	int id; // vertex id
	boolean explored = false;
	int checked = 0;
	int time = -1; // finishing time for this node
	int leader = -1; // leader id for this node
	LinkedList<Integer> edges = new LinkedList<Integer>(); // a list for
															// outgoing edges

	Node(int id) {
		this.id = id;
	}

	public void addEdge(Integer nodeId) {
		edges.add(nodeId);
	}

	public void removeEdge(Integer nodeId) {
		edges.remove(nodeId);
	}

	@Override
	public String toString() {
		return "leader:" + leader + " time:" + time + " edge:" + edges;
	}
}

class DirectedGraph {
	int time; // a global variable for finishing time in the 1st pass
	int leader; // a global variable for leaders in the second pass
	int NUM; // number of nodes
	Node[] vertexs; // an array for all the nodes

	DirectedGraph(int num) {
		NUM = num;
		vertexs = new Node[NUM];
	}

	public void addNode(int id, Node newNode) {
		if (id >= 0 && id < NUM)
			vertexs[id - 1] = newNode;

	}

	public void removeNode(int id) {
		vertexs[id - 1] = null;
	}

	public void printGraph() {
		System.out.println("graph:");
		for (int i = 1; i <= NUM; i++) {
			System.out.println("node " + i + ":" + vertexs[i - 1]);
		}
	}
}

public class ComputeSCC {
	public static int NUM = 875714;
	public static int[] array = new int[NUM + 1];

	public static DirectedGraph readGraph(DirectedGraph graph)
			throws IOException {
		String str;
		Node newNode;
		int newId, newEdge;

		BufferedReader buffer = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/scc/SCC.txt"));

		while ((str = buffer.readLine()) != null) {
			Scanner sca = new Scanner(str);
			newId = sca.nextInt();
			newEdge = sca.nextInt();

			if (graph.vertexs[newId - 1] != null) {
				graph.vertexs[newId - 1].addEdge(newEdge);
			} else {
				newNode = new Node(newId);
				newNode.addEdge(newEdge);
				graph.addNode(newId, newNode);
			}
		}

		return graph;

	}

	public static DirectedGraph readInvGraph(DirectedGraph graph)
			throws IOException {
		String str;
		Node newNode;
		int newId, newEdge;

		BufferedReader buffer = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/scc/SCC.txt"));

		while ((str = buffer.readLine()) != null) {
			Scanner sca = new Scanner(str);
			newEdge = sca.nextInt();
			newId = sca.nextInt();

			if (graph.vertexs[newId - 1] == null) {
				newNode = new Node(newId);
				newNode.addEdge(newEdge);
				graph.addNode(newId, newNode);
			} else {
				graph.vertexs[newId - 1].addEdge(newEdge);

			}
		}

		return graph;

	}

	public static void DFSLoop(DirectedGraph graph) {
		Node node;

		graph.time = 0;
		graph.leader = 0;

		for (int i = NUM; i > 0; i--) {
			node = graph.vertexs[array[i] - 1];
			if (node != null) {
				if (!node.explored) {
					graph.leader = node.id;
					DFS(graph, node);
				}

			}
		}
	}

	public static void DFS(DirectedGraph graph, Node node) {
		Stack<Node> stack = new Stack<Node>();
		stack.push(node);

		node.explored = true;

		while (!stack.isEmpty()) {
			node = stack.peek();

			while (node.checked < node.edges.size()) {
				int ids = node.edges.get(node.checked);
				node.checked++;
				Node edgeNode = graph.vertexs[ids - 1];
				if (!edgeNode.explored) {
					edgeNode.explored = true;
					stack.push(edgeNode);
					node = edgeNode;
				}
			}
			node = stack.pop();
			graph.time++;
			node.time = graph.time;
			node.leader = graph.leader;
		}
	}

	public static void sortGraph(DirectedGraph graph) {
		for (int i = 1; i <= NUM; i++) {
			Node node = graph.vertexs[i - 1];
			// System.out.println(node.time);
			array[node.time] = node.id;
		}
	}

	public static void findBigScc(DirectedGraph graph) {
		Node node;
		int[] count = new int[NUM + 1];
		for (int i = 1; i <= NUM; i++) {
			node = graph.vertexs[i - 1];
			count[node.leader]++;
		}

		Arrays.sort(count);
		System.out.println("Output:");
		for (int i = 0; i <= 5; i++) {
			System.out.println(count[NUM - i]);
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		DirectedGraph graph = new DirectedGraph(NUM);
		DirectedGraph invGraph = new DirectedGraph(NUM);

		// Initialization
		for (int i = 0; i < NUM; i++) {
			array[i + 1] = i + 1;
			graph.vertexs[i] = new Node(i + 1);
			invGraph.vertexs[i] = new Node(i + 1);
		}

		readGraph(graph);
		readInvGraph(invGraph);

		DFSLoop(invGraph);
		sortGraph(invGraph);
		DFSLoop(graph);

		findBigScc(graph);
	}
}