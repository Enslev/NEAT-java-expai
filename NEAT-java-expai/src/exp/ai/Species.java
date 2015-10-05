package exp.ai;

import java.util.ArrayList;

public class Species {

	public static final double dtp = 1.0;
	
	public ArrayList<Genome> genomes;
	
	public Species() {
		genomes = new ArrayList<Genome>();
	}
	
	//This is wrong?
	//Should get fitness from a single genome, and divide by amount in species
	public int getAdjustedFitness() {
		int fp = 0;
		for (Genome g : genomes) {
			fp += g.fitness;
		}
		return fp / genomes.size();
	}
	
	public int size(){
		return this.genomes.size();
	}
	
	public int fitness(){
		int sum = 0;
		
		for (Genome g : genomes){
			sum += g.fitness;
		}
		
		return sum / this.size();
	}

/*
 * We decided not to use this
 * 
	public double matchGenomes(Genome g1, Genome g2) {
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
		
		return distance(e, d, w, n);
	}
	
	private double distance(int e, int d, int w, int n) {
		// e: Excess. d: Disjoint. w: matching. n: #of genes in larger genome.
		return (C_1*e)/n+(C_2*d)/n+w*C_3;
	}
*/	
	
}
