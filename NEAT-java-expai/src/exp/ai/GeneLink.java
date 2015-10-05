package exp.ai;

public class GeneLink {
	private static int count = 0;
	public GeneNode in;
	public GeneNode out;
	public double weight;
	public int innovation;
	public boolean enabled = true;
	
	public GeneLink(GeneNode in, GeneNode out, double weight) {
		this.in = in;
		this.out = out;
		this.weight = weight;
		this.innovation = ++count;
	}
	
	public GeneLink(GeneNode in, GeneNode out, double weight, int innovation) {
		this.in = in;
		this.out = out;
		this.weight = weight;
		this.innovation = innovation;
	}
	
}
