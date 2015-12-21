import java.util.*;

public class TarjanTopologicalSorter {

    private enum VisitedStatus {
        NotVisited,
        Visited,
        VisitedAndResolved
    }

    private List<Vertex> order;
    private Set<Vertex> unvisitedVertices;
    private HashMap<String, VisitedStatus> visitedStatus;

    public List<Vertex> getTopologicalOrder(Graph graph) throws Exception {
        order = new ArrayList<>();
        unvisitedVertices = new HashSet<>(graph.getVertices().size());
        visitedStatus = new HashMap<>(graph.getVertices().size());

        HashMap<String, Vertex> listVerticies = graph.getVertices();
        
        for (String key : listVerticies.keySet() ) {
            unvisitedVertices.add(listVerticies.get(key));
            visitedStatus.put(key, VisitedStatus.NotVisited);
        }

        // Visit any unvisited vertex until there are no unvisited vertices left.
        while (unvisitedVertices.size() > 0) {
            Vertex unvisitedVertex = unvisitedVertices.stream().findFirst().get();
            dfsRecursive(unvisitedVertex);
        }

        return order;
    }

    private void dfsRecursive(Vertex vertex) throws Exception {
        if (visitedStatus.get(vertex.getName()) == VisitedStatus.Visited) {
            throw new Exception();
        }

        if (visitedStatus.get(vertex.getName()) == VisitedStatus.NotVisited) {
            visitedStatus.put(vertex.getName(), VisitedStatus.Visited);
            unvisitedVertices.remove(vertex);

            // Getting all dependencies of the current vertex.
            List<Vertex> neighbors = new ArrayList<>();
            for (Edge edge: vertex.getOutboundEdges()) {
                neighbors.add(edge.getEnd());
            }

            for (Vertex neighbor: neighbors) {
                dfsRecursive(neighbor);
            }

            visitedStatus.put(vertex.getName(), VisitedStatus.VisitedAndResolved);
            order.add(vertex);
        }
    }
}
