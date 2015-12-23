

public class Edge {
	private Vertex start;
	private Vertex end;
	private int weight;
	
	public Edge(Vertex start, Vertex end, int weight) {
		this.start = start;
		this.end = end;
		this.weight = weight;
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

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}
