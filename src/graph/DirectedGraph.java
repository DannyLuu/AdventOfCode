package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Directional Graph Data Structure This Graph takes Nodes and Directed Edges. Creating a
 * directed graph data structure.
 * 
 * @author Hisoka
 * 
 * @param <T>
 */
public class DirectedGraph<T> {

	private final HashMap<T, HashMap<T, Double>> graph;

	public DirectedGraph() {
		graph = new HashMap<T, HashMap<T, Double>>();
	}

	/**
	 * Add a node to the graph. This should be called at the beginning to set
	 * all of the vertices.
	 * @param node
	 */
	public void addNode(T node) {
		if (!graph.containsKey(node)) {
			HashMap<T, Double> edges = new HashMap<T, Double>();
			graph.put(node, edges);
		}
	}

	/**
	 * Adds a edge from one node to another.
	 * 
	 * A -> B with distance C, where A and B are of Type <T> and C is of type
	 * Double.
	 * 
	 * @param initial
	 * @param destination
	 * @param distance
	 */
	public void addEdge(T initial, T destination, Double distance) {
		if (!graph.containsKey(initial)) {
			addNode(initial);
		}
		if (!graph.containsKey(destination)) {
			addNode(destination);
		}

		HashMap<T, Double> edges = graph.get(initial);
		edges.put(destination, distance);
		graph.put(initial, edges);
	}

	@Override
	public String toString() {
		String word = "";
		for (T type : graph.keySet()) {
			HashMap<T, Double> edges = graph.get(type);
			word += "Node: " + type.toString();
			if (!edges.isEmpty()) {
				for (T e : edges.keySet()) {
					word += "\tEdge: " + type.toString() + " -> " + e.toString() + " = " + edges.get(e);
				}
			}
			word += "\n";
		}
		return word;
	}

	public Set<T> getVertices() {
		return graph.keySet();
	}

	public HashMap<T, Double> getEdges(T node) {
		return graph.get(node);
	}

	/**
	 * Checks to see if the graph contains a specific vertex or not.
	 * @param v
	 * @return true if graph contains vertex.
	 */
	public boolean contains(T node) {
		return graph.containsKey(node);
	}

	/**
	 * Checks if the graph has no nodes.
	 * @return
	 */
	public boolean isEmpty() {
		return graph.isEmpty();
	}
	
	/**
	 * Number of verticies.
	 * @return
	 */
	public int numOfVerticies() {
		return graph.size();
	}
	
	/**
	 * Number of edges.
	 * @return
	 */
	public int numOfEdges() {
		int edges = 0;
		
		for (T node : graph.keySet()) {
			edges += graph.get(node).size();
		}
		
		return edges;
	}
	
	public Iterator<Entry<T,Double>> getEdgesOutFrom(T node) {
		return graph.get(node).entrySet().iterator();
	}
	
	public Iterator<Entry<T, Double>> allEdges() {
		HashMap<T, Double> edges = new HashMap<T, Double>();
		for (T node : graph.keySet()) {
			edges.putAll(graph.get(node));
		}
		
		return edges.entrySet().iterator();	
	}
}