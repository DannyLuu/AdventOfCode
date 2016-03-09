package advent09;

import java.util.ArrayList;
import java.util.List;

/**
 * Node with multiple edges.
 * @author Hisoka
 *
 */
public class Node {
	private String node = "";

	/**
	 * 
	 */
	public Node() {
		node = "node";
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
		
		if (!Node.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		
		Node n = (Node) obj;
		
		if ((this.node == null) ? (n.node != null) : !this.node.equals(n.node)) {
			return false;
		}
		return true;
	}
	
	public String toString() {
		return node;
	}
}
