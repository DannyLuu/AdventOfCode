package advent09;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Dijkstra's algorithm. https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
 * 
 * Let the node at which we are starting be called the initial node. Let the
 * distance of node Y be the distance from the initial node to Y. Dijkstra's
 * algorithm will assign some initial distance values and will try to improve
 * them step by step.
 * 
 * 1. Assign to every node a tentative distance value: set it to zero for our
 * initial node and to infinity for all other nodes.
 * 
 * 2. Set the initial node as current. Mark all other nodes unvisited. Create a
 * set of all the unvisited nodes called the unvisited set.
 * 
 * 3. For the current node, consider all of its unvisited neighbors and
 * calculate their tentative distances. Compare the newly calculated tentative
 * distance to the current assigned value and assign the smaller one. For
 * example, if the current node A is marked with a distance of 6, and the edge
 * connecting it with a neighbor B has length 2, then the distance to B (through
 * A) will be 6 + 2 = 8. If B was previously marked with a distance greater than
 * 8 then change it to 8. Otherwise, keep the current value.
 * 
 * 4. When we are done considering all of the neighbors of the current node,
 * mark the current node as visited and remove it from the unvisited set. A
 * visited node will never be checked again.
 * 
 * 5. If the destination node has been marked visited (when planning a route
 * between two specific nodes) or if the smallest tentative distance among the
 * nodes in the unvisited set is infinity (when planning a complete traversal;
 * occurs when there is no connection between the initial node and remaining
 * unvisited nodes), then stop. The algorithm has finished.
 * 
 * 6. Otherwise, select the unvisited node that is marked with the smallest
 * tentative distance, set it as the new "current node", and go back to step 3.
 * 
 * @author Hisoka
 *
 */
public class DijkstrasAlgorithm {
	Node initialNode = new Node();
	Set<Node> unvisited = new HashSet<Node>();
	DirectedGraph<Node> graph = new DirectedGraph<Node>();
	// DirectedGraph<Node> shortestPath = new DirectedGraph<Node>();

	/**
	 * The DijkstrasAlgorithm will get a graph with nodes and edges already
	 * populated.
	 * 
	 * @param graph
	 */
	public DijkstrasAlgorithm(DirectedGraph<Node> graph) {
		if (graph != null) {
			this.graph = graph;
		}
		unvisited = graph.getVertices();
	}

	/**
	 * The DijkstrasAlgorithm will get a graph with nodes and edges already
	 * populated.
	 * 
	 * @param graph
	 */
	public DijkstrasAlgorithm(DirectedGraph<Node> graph, Node initialNode) {
		if (graph != null) {
			this.graph = graph;
		}
		this.unvisited = graph.getVertices();
		this.initialNode = graph.get(initialNode);
	}

	public void run() {
		Node currNode = initialNode;
		assignTenitiveDistance(currNode);

		while (!unvisited.isEmpty()) {
			System.out.println("Currently on Node: " + currNode.toString());
			calculateTenativeDistances(currNode);
			Node temp = getShortestDistanceToNextNode(currNode);
			if (temp == null || containsNoOutConnections(temp)) {
				if (temp != null) {
					System.out.println(temp.toString());
				}
			} else {
				unvisited.remove(currNode);
				currNode = temp;
			}
		}
	}

	/**
	 * Assigning to every node a tentative distance value: set it to zero for
	 * our initial node and to infinity for all other nodes.
	 */
	public void assignTenitiveDistance(Node initialNode) {
		for (Node currNode : unvisited) {
			HashMap<Node, Double> edges = graph.getEdgesFor(currNode);
			for (Node edgeNode : unvisited) {
				if (currNode.equals(initialNode) && edgeNode.equals(initialNode)) {
					graph.addEdge(initialNode, initialNode, 0d);
				} else if (!currNode.equals(edgeNode) && !edges.containsKey(edgeNode)) {
					graph.addEdge(currNode, edgeNode, Double.POSITIVE_INFINITY);
				}
			}
		}
	}

	/**
	 * For the current node, consider all of its unvisited neighbors and
	 * calculate their tentative distances. Compare the newly calculated
	 * tentative distance to the current assigned value and assign the smaller
	 * one. For example, if the current node A is marked with a distance of 6,
	 * and the edge connecting it with a neighbor B has length 2, then the
	 * distance to B (through A) will be 6 + 2 = 8. If B was previously marked
	 * with a distance greater than 8 then change it to 8. Otherwise, keep the
	 * current value.
	 */
	public void calculateTenativeDistances(Node currNode) {
		HashMap<Node, Double> currNodeEdges = graph.getEdgesFor(currNode);
		Set<Node> unvistedNeighbor = new HashSet<Node>();
		
		unvistedNeighbor.addAll(unvisited);	
		unvistedNeighbor.remove(currNode);

		for (Node n : unvistedNeighbor) {
			HashMap<Node, Double> neighbourEdges = graph.getEdgesFor(n);
			
			if (neighbourEdges != null) {
				Double distance = currNodeEdges.get(n);
				
				for (Node m : neighbourEdges.keySet()) {
					Double d = neighbourEdges.get(m) + distance;
					
					if (currNodeEdges.containsKey(m) && d < currNodeEdges.get(m)) {
						System.out.println("**** Current distance from " + currNode.toString() + " to " + m.toString() + " is "
								+ currNodeEdges.get(m) + " ****");
						System.out.println("\tShorter distance found from: " + currNode.toString() + " - > "
								+ n.toString() + "(" + currNodeEdges.get(n) + ") -> " + m.toString() + "("
								+ neighbourEdges.get(m) + ")");
						System.out.println("\tSetting " + currNode.toString() + " -> " + m.toString()
								+ " with new distance: " + d);
						currNodeEdges.put(m, d);
					}
				}
			}
		}

		graph.put(currNode, currNodeEdges);
	}

	/**
	 * Returns the next shortest distance node to the current node.
	 * @param currNode
	 * @return Node if exists, otherwise null.
	 */
	public Node getShortestDistanceToNextNode(Node currNode) {
		HashMap<Node, Double> edges = graph.getEdgesFor(currNode);
		Double shortestDistance = Double.POSITIVE_INFINITY;
		Node shortestNode = null;

		if (!edges.isEmpty()) {
			for (Entry<Node, Double> entry : edges.entrySet()) {
				if (!entry.getKey().equals(currNode) && entry.getValue() < shortestDistance) {
					shortestDistance = entry.getValue();
					shortestNode = entry.getKey();
				}
			}
		}
		return shortestNode;
	}
	
	/**
	 * Gets the next shortest distance node from the current node.
	 * @param currNode
	 * @return Node if exists, otherwise null.
	 */
	public Node getShortestDistanceToNextNode(Node currNode, HashMap<Node, Double> edges) {
		Double shortestDistance = Double.POSITIVE_INFINITY;
		Node shortestNode = null;

		if (!edges.isEmpty()) {
			for (Entry<Node, Double> entry : edges.entrySet()) {
				if (!entry.getKey().equals(currNode) && entry.getValue() < shortestDistance) {
					shortestDistance = entry.getValue();
					shortestNode = entry.getKey();
				}
			}
		}
		return shortestNode;
	}
	
	/**
	 * Gets the second shortest distance from the current node.
	 * @param currNode
	 * @return Node if exists, otherwise null.
	 */
	public Node getSecondShortestNode(Node currNode) {
		HashMap<Node, Double> edges = new HashMap<Node, Double>();
		edges.putAll(graph.getEdgesFor(currNode));
		edges.remove(getShortestDistanceToNextNode(currNode));
		
		return getShortestDistanceToNextNode(currNode, edges);
	}

	/**
	 * Checks to see if the node has any outgoing edges. If the smallest value
	 * is infinity, stop the algorithm.
	 * 
	 * @param edges
	 * @return
	 */
	public boolean containsNoOutConnections(Node node) {
		HashMap<Node, Double> edges = graph.getEdgesFor(node);
		for (Node n : edges.keySet()) {
			if (edges.get(n) < Double.POSITIVE_INFINITY) {
				return false;
			}
		}
		System.out.println(edges.size() + "TRUE");
		return true;
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	public boolean isVisted(Node n) {
		return !unvisited.contains(n);
	}
}
