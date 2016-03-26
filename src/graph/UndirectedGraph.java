package graph;

import java.util.HashSet;
import java.util.Set;

/**
 * Undirected Graph Data Structure This Graph takes Nodes and Edges. Creating a
 * graph data structure.
 * 
 * @author Hisoka
 *
 */
public class UndirectedGraph<T> {
	private Set<T> nodes;
	private Set<Edge<T>> edges;

	/**
	 * Default constructor.
	 */
	public UndirectedGraph() {
		this.nodes = new HashSet<T>();
		this.edges = new HashSet<Edge<T>>();
	}

	public UndirectedGraph(Set<T> nodes, Set<Edge<T>> edges) {
		this.nodes = nodes;
		this.edges = edges;
	}

	/**
	 * Adding a node to the set of nodes.
	 * 
	 * @param node
	 */
	public void addNode(T node) {
		nodes.add(node);
	}

	/**
	 * Adding a new edge object to the list of edges. If it contains the edge,
	 * it will be overwritten.
	 * 
	 * @param a
	 * @param b
	 * @param weight
	 */
	public void addEdge(T a, T b, double weight) {
		if (!edges.contains(new Edge<T>(a, b, weight))) {
			edges.add(new Edge<T>(a, b, weight));
		}
	}

	/**
	 * Adding an edge object to the list of edges. If it contains the edge, it
	 * will be overwritten.
	 * 
	 * @param edge
	 */
	public void addEdge(Edge<T> edge) {
		edges.add(edge);
	}

	public Set<T> getNodes() {
		return nodes;
	}

	public void setNodes(Set<T> nodes) {
		this.nodes = nodes;
	}

	public Set<Edge<T>> getEdges() {
		return edges;
	}

	public void setEdges(Set<Edge<T>> edges) {
		this.edges = edges;
	}
}