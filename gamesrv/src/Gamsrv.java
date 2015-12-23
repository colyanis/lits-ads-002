import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

import edu.princeton.cs.algs4.IndexMinPQ;

public class Gamsrv {
	private List<Vertex> clients;
	
	private long[] distTo;
	private Vertex[] parentVertex;
	private IndexMinPQ<Long> pq;
	
	public static void main(String[] args) throws IOException {
		String inputFile = "gamsrv.in";
		String outputFile = "gamsrv.out";
		
		if (args.length > 0) {
			inputFile = args[0];
			outputFile = args[1];
		}
		
		Gamsrv gamsrv = new Gamsrv();
		gamsrv.execute(inputFile, outputFile);
	}
	
	
	public void execute(String inputFile, String outputFile) throws IOException {
		Graph graph = readFileToGraph(inputFile);
		
		dijkstra(graph, graph.getVertices().get(0));
		
		long maxGlobal = Long.MAX_VALUE;
		
		for (Vertex server : graph.getVertices()) {
			if (isClient(server, clients)) {
				continue;
			}
			
			long maxLocal = 0L;
			for (Vertex client : clients) {				
				long current = calculateMinDistance(graph, client, server);
				if (current > maxLocal) {
					maxLocal = current;
				}
			}
			
			if (maxLocal < maxGlobal) {
				maxGlobal = maxLocal;
			}
		}
		
		writeToFile(outputFile, maxGlobal);
	}
	
	private void writeToFile(String outputFile, long string) throws IOException {
		File file = new File(outputFile);
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(String.valueOf(string));
		fileWriter.close();
	}
	
	private long calculateMinDistance(Graph graph, Vertex a, Vertex b) {
		dijkstra(graph, a);
		long result = distTo[ (b.getLable() - 1)];
		return result;
	}
	
	private void dijkstra(Graph g, Vertex v) {
		for (int i = 0; i < g.getVertices().size(); i++) {
			distTo[i] = Long.MAX_VALUE;
		}
		
		distTo[ (v.getLable() - 1)] = 0;
		
		pq.insert( (v.getLable() - 1), 0L);
		while (!pq.isEmpty()) {
			relax(g, pq.delMin());
		}
	}
	
	private void relax(Graph g, int v) {
		List<Edge> adj = g.getVertices().get(v).getOutboundEdges();
		for (Edge edge : adj) {
			Vertex end = edge.getEnd();
			if (distTo[ (end.getLable() - 1)] > distTo[v] + edge.getWeight()) {
				distTo[ (end.getLable() - 1)] = distTo[v] + edge.getWeight();
				parentVertex[ (end.getLable() - 1)] = g.getVertices().get(v);
				
				if (pq.contains( (end.getLable() - 1))) {
					pq.change( (end.getLable() - 1), distTo[ (end.getLable() - 1)]);
				} else {
					pq.insert( (end.getLable() - 1), distTo[ (end.getLable() - 1)]);
				}
			}
		}
	}
	
	private boolean isClient(Vertex v, List<Vertex> clients) {
		return clients.contains(v);
	}
	
	private Graph readFileToGraph(String inputFile) throws FileNotFoundException {
		File file = new File(inputFile);
		Scanner scanner = new Scanner(file);
		
		int vertexCount = scanner.nextInt();
		int edgeCount = scanner.nextInt();		
        List<Vertex> vertices = new ArrayList<>(vertexCount);
        List<Edge> edges = new ArrayList<>(edgeCount);
        distTo = new long[vertexCount];
        parentVertex = new Vertex[vertexCount];
        pq = new IndexMinPQ<>(vertexCount);
		
        for (int i = 1; i <= vertexCount; i++) {
        	vertices.add(new Vertex(i));
        }
        
        // Read clients
        scanner.nextLine();
        String[] clientsString = scanner.nextLine().split(" ");
        clients = new ArrayList<>(clientsString.length);
        for (String str : clientsString) {
        	int clientLable = Integer.parseInt(str);
        	clients.add(vertices.get(clientLable - 1));
        }
        
        // Read edges
        for (int e = 0; e < edgeCount; e++) {
            int startVertexLabel = scanner.nextInt();
            int endVertexLabel = scanner.nextInt();
            int weight = scanner.nextInt();

            Edge edge = new Edge(vertices.get(startVertexLabel - 1), vertices.get(endVertexLabel - 1), weight);
            edges.add(edge);
            vertices.get(startVertexLabel - 1).getOutboundEdges().add(edge);
            Edge reverseEdge = new Edge(vertices.get(endVertexLabel - 1), vertices.get(startVertexLabel - 1), weight);
            edges.add(reverseEdge);
            vertices.get(endVertexLabel - 1).getOutboundEdges().add(reverseEdge);
        }
		
		return new Graph(vertices, edges);
	}
}