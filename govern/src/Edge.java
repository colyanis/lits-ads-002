public class Edge {
	private Vertex start;
	private Vertex end;
	
	public Edge(Vertex start, Vertex end) {
		this.start = start;
		this.end = end;
	}
	
	public Vertex getStart() {
		return start;
	}
	
	public void setStart(Vertex start) {
		this.start = start;
	}
	
	public Vertex getEnd() {
		return end;
	}
	
	public void setEnd(Vertex end) {
		this.end = end;
	}
}
