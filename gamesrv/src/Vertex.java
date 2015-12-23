

import java.util.ArrayList;
import java.util.List;

public class Vertex {
	private int lable;
	private List<Edge> outboundEdges;
	
	public Vertex(int lable) {
		this.setLable(lable);
		this.outboundEdges = new ArrayList<Edge>();
	}
	
	public void setLable(int lable) {
		this.lable = lable;
	}
	
	public int getLable() {
		return this.lable;
	}
	
	public List<Edge> getOutboundEdges() {
		return outboundEdges;
	}
	
	public void setOutboundEdge(List<Edge> outboundEdges) {
		this.outboundEdges = outboundEdges;
	}
}
