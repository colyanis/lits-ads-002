import java.util.HashMap;
import java.util.List;

public class Graph {
	private List<Edge> edges;	
	private HashMap<String, Vertex> vertices;

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}

	public HashMap<String, Vertex> getVertices() {
		return vertices;
	}

	public void setVertices(HashMap<String, Vertex> vertices) {
		this.vertices = vertices;
	}
}
