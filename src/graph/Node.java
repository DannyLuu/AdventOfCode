package graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Node object - Nodes must be initialized with a name.
 * @author Hisoka
 *
 */
public class Node {
	private String node;
	
	public Node() {
		node = null;
	}
	
	public Node(String node) {
		this.node = node;
	}
	
	/**
	 * Overriding equals for Node object.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (this == obj) {
			return true;
		}

		if (!Node.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		
		Node n = (Node) obj;		
		if ((this.node == null) ? (n.node != null) : !this.node.equals(n.node)) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 137;
		int result = 1;
		result = prime * result + ((node == null) ? 0 : node.hashCode());
		return result;
	}
	
	public String toString() {
		return node;
	}
}
