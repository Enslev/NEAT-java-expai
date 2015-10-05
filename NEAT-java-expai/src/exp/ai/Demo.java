package exp.ai;

import java.util.ArrayList;
import java.util.Random;

public class Demo {

	public static final double C_1 = 1.0;
	public static final double C_2 = 1.0;
	public static final double C_3 = 1.0;
	
	public static void main(String[] args) {
		Random rand = new Random();
		ArrayList<Integer> color = new ArrayList<Integer>();
		
		for (int i = 0; i < 10; i++) {
			//System.out.println(new Node(i, new Genome(1)).id);
			Integer x = rand.nextInt(255);
			color.add(x);
		}

		Genome gen1 = new Genome(10);	
		Genome gen2 = new Genome(10);		
		gen1.addLink(gen1.input.get(0), gen1.output.get(0));
		//gen2.addLink(gen1.input.get(3), gen1.output.get(0));
		gen1.addLink(gen1.input.get(1), gen1.output.get(1));
		
		gen1.links.get(1).weight = 0.5;

		ArrayList<GeneNode> out = gen1.run(color);

		//System.out.println(out.get(0).value);
		//System.out.println(out.get(1).value);
		
		//System.out.println(matchGenomes(gen1, gen2));
		
		System.out.print(gen1.toString());

	}	
	
	public static double matchGenomes(Genome g1, Genome g2) {
		ArrayList<Integer> g1Innos = g1.getSortedInnovation();
		ArrayList<Integer> g2Innos = g2.getSortedInnovation();
		int g1Size = g1Innos.size();
		int g2Size = g2Innos.size();
		int n = (g1Size >= g2Size) ? g1Size : g2Size;
		int w, d, e;
		w = d = e = 0;

		int g1HigestInnovation = g1Innos.get(g1Size-1);
		int g2HigestInnovation = g2Innos.get(g2Size-1);
		
		for (Integer g1Inno : g1Innos) {
			if (g2Innos.contains(g1Inno)) {
				w++;
			} else if (g1Inno < g2HigestInnovation) {
				d++;
			} else {
				e++;
			}
		}
		
		for (Integer g2Inno: g2Innos) {
			if (!g1Innos.contains(g2Inno)) {
				if (g2Inno < g1HigestInnovation) {
					d++;
				} else {
					e++;
				}
			}
		}
		
		System.out.printf("%d %d %d %d\n",w,d,e,n);
		
		return distance(e, d, w, n);
	}
	
	private static double distance(int e, int d, int w, int n) {
		// e: Excess. d: Disjoint. w: matching. n: #of genes in larger genome.
		return (C_1*e)/n+(C_2*d)/n+w*C_3;
	}
	


	
}
