package graph;

/**
 * Edge object with two vertices and a edge weight;
 * @author Hisoka
 *
 * @param <T>
 */
public class Edge<T> {
	private T a;
	private T b;
	private Double weight;
	
	public Edge(T a, T b, Double weight) {
		this.a = a;
		this.b = b;
		this.weight = weight;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Edge other = (Edge) obj;
		
		if (a == null) {
			if (other.a != null)
				return false;
		} else if (!a.equals(other.a)) {
			// Edge (a,b) == (b,a)
			if (b != null && other.b != null && a.equals(other.b) && b.equals(other.a)) {
				return true;
			}
			return false;
		}
		
		if (b == null) {
			if (other.b != null)
				return false;
		} else if (!b.equals(other.b)) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		return result;
	}
}
