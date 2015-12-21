import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Govern {
	public static void main(String[] args) throws Exception {
		String inputFile = "govern.in";
		String outputFile = "govern.out";
		
		if (args.length > 0) {
			inputFile = args[0];
			outputFile = args[1];
		}
		
		Govern govern = new Govern();
		govern.execute(inputFile, outputFile);
	}
	
	
	public void execute(String inputFile, String outputFile) throws Exception {
		Graph graph = readFileToGraph(inputFile);
		TarjanTopologicalSorter sorter = new TarjanTopologicalSorter();
        List<Vertex> orderedVerticies = sorter.getTopologicalOrder(graph);
        writeToFile(outputFile, orderedVerticies);
        
        		
	}
	
	private void writeToFile(String outputFile, List<Vertex> orderedVerticies) throws IOException {
		File file = new File(outputFile);
		FileWriter fileWriter = new FileWriter(file);
		String lineBreak = System.getProperty("line.separator");

		
		
		for (Vertex vertex : orderedVerticies) {
			fileWriter.write(vertex.getName() + lineBreak);
		}
		
		fileWriter.close();
	}
	
	private Graph readFileToGraph(String inputFile) throws FileNotFoundException {
		File file = new File(inputFile);
		Graph graph = new Graph(); 
		HashMap<String, Vertex> verticies = new HashMap<>(); 
		List<Edge> edges = new ArrayList<>();
		
		String startVertex = "";
		String endVertex = "";
		Scanner scanner = new Scanner(file);
		while (scanner.hasNext()) {
			startVertex = scanner.next();
			endVertex = scanner.next();
					
			if (!verticies.containsKey(startVertex)) {
				verticies.put(startVertex, new Vertex(startVertex));
			}
			
			if (!verticies.containsKey(endVertex)) {
				verticies.put(endVertex, new Vertex(endVertex));
			}
			
			Edge edge = new Edge(verticies.get(startVertex), verticies.get(endVertex));
			edges.add(edge);
			
			verticies.get(startVertex).getOutboundEdges().add(edge);
		}
		
		graph.setVertices(verticies);
		graph.setEdges(edges);
		
		return graph;
	}
}
