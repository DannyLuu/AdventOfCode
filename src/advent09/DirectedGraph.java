package advent09;

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
 * Directional Graph Object
 * 
 * @author Hisoka
 *
 */
public class DirectedGraph<T> {

	private final Map<T, HashMap<T, Double>> graph;

	public DirectedGraph() {
		graph = new HashMap<T, HashMap<T, Double>>();
	}

	/**
	 * Add a node to the graph. This should be called at the beginning to set
	 * all of the vertices.
	 * 
	 * @param v
	 */
	public void addNode(T node) {
		if (!contains(node)) {
			graph.put(node, new HashMap<T, Double>());
		}
	}

	public boolean isEmpty() {
		return graph.isEmpty();
	}

	/**
	 * Checks to see if the graph contains a specific vertex or not.
	 * 
	 * @param v
	 * @return true if graph contains vertex.
	 */
	public boolean contains(T node) {
		for (T n : graph.keySet()) {
			if (node == null) {
				return false;
			}
			if (node.equals(n)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets the object T within the Directed Graph, null if no object T found.
	 * @param v
	 * @return
	 */
	public T get(T node) {
		for (T n : graph.keySet()) {
			if (node == null) {
				return null;
			}
			if (node.equals(n)) {
				return n;
			}
		}
		return null;
	}
	
	/**
	 * Adds a node with edges into the graph.
	 * @param node
	 * @param edges
	 */
	public void put(T node, HashMap<T, Double> edges) {	
		T n = get(node);
		for (Entry<T, Double> edge : edges.entrySet()) {
			T e = get(edge.getKey());
			addEdge(n, e, edge.getValue());
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
		if (!contains(initial)) {
			addNode(initial);
		}
		if (!contains(destination)) {
			addNode(destination);
		}
		
		if (contains(initial) && contains(destination)) {
			// Since the initial and destination are new Objects,
			// The graph will think that they do not exist and return null edges.
			// Doing this will grab the right T object.
			T init = get(initial);
			T dest = get(destination);
			HashMap<T, Double> edges = graph.get(init);

			edges.put(dest, distance);
			graph.put(init, edges);
		}
	}

	/**
	 * Returns the set of Vertices within the graph.
	 * 
	 * @return
	 */
	public Set<T> getVertices() {
		return graph.keySet();
	}
	
	public HashMap<T, Double> getEdgesFor(T v) {
		T node = get(v);
		return graph.get(node);
	}

	/**
	 * Prints a mapping of nodes with edges.
	 */
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
}
