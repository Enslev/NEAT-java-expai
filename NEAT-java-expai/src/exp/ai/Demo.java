package exp.ai;

import java.util.ArrayList;
import java.util.Random;

public class Demo {

	public static final double C_1 = 1.0;
	public static final double C_2 = 1.0;
	public static final double C_3 = 1.0;
	
	public static void main(String[] args) {
		
		Population pop = new Population();
		// generations
		for (int i = 0; i < 5; i++){
			for (Genome genome : pop.){
				genome.sendThroughNetwork(color);
			}
			pop = Algorithm.evolvePopulation(pop);
		}

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
