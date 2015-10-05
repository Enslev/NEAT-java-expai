package exp.ai;

import java.util.ArrayList;
import java.util.Random;

public class Demo {

	public static final double C_1 = 1.0;
	public static final double C_2 = 1.0;
	public static final double C_3 = 1.0;
	
	public static void main(String[] args) {
		ArrayList<Double> color = new ArrayList<Double>();
		
		for (int i = 0; i < 10; i++) {
			//System.out.println(new Node(i, new Genome(1)).id);
			//int x = rand.nextInt(255);
			double x = 100.0/255.0;
			color.add(x);
		}

		
		/* Test
		 * input0 = input1 = 0,392156
		 * output1 = 0.8009363507708791
		 * output2 = 0.688449439880974
		 * hiddenNode = 0.596802
		 * 
		 * (in0, b=0) -[1]-> (out0, b=1)
		 * (in1, b=0) -[0.5]-> (out1, b=0)
		 * (in1, b=0) -[1]-> (hidden, b=0)
		 * (hidden, b=0) -[1]-> (out1, b=0)
		 * 
		 */
		Genome gen1 = new Genome(10);	
		//Genome gen2 = new Genome(10);		
		gen1.addLink(gen1.input.get(0), gen1.output.get(0));
		//gen2.addLink(gen1.input.get(3), gen1.output.get(0));
		GeneLink link = gen1.addLink(gen1.input.get(1), gen1.output.get(1));
		
		gen1.links.get(1).weight = 0.5;
		
		gen1.addNode(link);
		
		link.enabled = true;
		gen1.output.get(0).bias = 1;
		
/*
		ArrayList<GeneNode> out = gen1.run(color);

		System.out.println(out.get(0).value);
		System.out.println(out.get(1).value);
*/	
		//System.out.println(matchGenomes(gen1, gen2));
		
		//System.out.print(gen1.toString());

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
