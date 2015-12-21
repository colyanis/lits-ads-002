import java.util.ArrayList;
import java.util.List;

public class Vertex {
	private String name;
	private List<Edge> outboundEdges;
	
	public Vertex(String name) {
		this.setName(name);
		this.outboundEdges = new ArrayList<Edge>();
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<Edge> getOutboundEdges() {
		return outboundEdges;
	}
	
	public void setOutboundEdge(List<Edge> outboundEdges) {
		this.outboundEdges = outboundEdges;
	}
}
