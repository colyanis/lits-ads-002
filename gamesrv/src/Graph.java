

import java.util.List;

public class Graph {
	private List<Edge> edges;	
	private List<Vertex> vertices;

	public Graph(List<Vertex> verticies, List<Edge> edges) {
		this.vertices = verticies;
		this.edges = edges;
	}
	
	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}

	public List<Vertex> getVertices() {
		return vertices;
	}

	public void setVertices(List<Vertex> vertices) {
		this.vertices = vertices;
	}
}
