package exp.ai;

public class GeneLink {
	private static int count = 0;
	public GeneNode source;
	public GeneNode target;
	public double weight;
	public int innovation;
	public boolean enabled = true;
	
	public GeneLink(GeneNode in, GeneNode out, double weight) {
		this.source = in;
		this.target = out;
		this.weight = weight;
		this.innovation = ++count;
	}
	
	public GeneLink(GeneNode in, GeneNode out, double weight, int innovation) {
		this.source = in;
		this.target = out;
		this.weight = weight;
		this.innovation = innovation;
	}
	
}
