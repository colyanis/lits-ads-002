import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;
import java.util.Stack;

public class DepthFirstOrder {
	private Queue<Vertex> pre;
	private Queue<Vertex> post;
	private Stack<Vertex> reverseOrder;
	private HashMap<String, Boolean> visited;
	
	public DepthFirstOrder(Graph graph) {
		pre = new ArrayDeque<>();
		post = new ArrayDeque<>();
		reverseOrder = new Stack<>();
		visited = new HashMap<>();
		HashMap<String, Vertex> verticies = graph.getVertices();
		
		
		// Initialize visited
		for (String key : verticies.keySet()) {
			visited.put(key, Boolean.FALSE);
		}
		
		// Iterate through each vertex
		for (String key : verticies.keySet()) {
			if (!visited.get(key)) {
				dfs(graph, verticies.get(key));
			}
		}
		
		System.out.println("DepthFirstOrder.DepthFirstOrder()");
	}
	
	private void dfs(Graph graph, Vertex vertex) {
		pre.add(vertex);
		visited.put(vertex.getName(), true);
		
		for (Edge edge : vertex.getOutboundEdges()) {
			Vertex endVertex = edge.getEnd();
			if (!visited.get(endVertex.getName())) {
				dfs(graph, endVertex);
			}
		}
		
		post.add(vertex);
		reverseOrder.push(vertex);
	}
}
