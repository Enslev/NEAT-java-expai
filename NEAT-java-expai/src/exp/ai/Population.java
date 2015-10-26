package exp.ai;

import java.util.ArrayList;
import java.util.Random;

public class Population {
	 
	public static Genome innovationGenome;
	
	public Genome[] genomes;
	
	public Population(int popSize, boolean initialise) {
		this.genomes = new Genome[popSize];
		
		if (initialise) {            
        	int nrInput = 5;
        	int nrOutput = 1;
                	
        	innovationGenome = new Genome(nrInput, nrOutput);
        	
        	ArrayList<GeneNode> allNodes = innovationGenome.getAllNodes();
        	
        	for (int i = 0; i < nrInput + nrOutput; i++){
        		allNodes.get(i).id = i;
        	}
        	
            for (int i = 0; i < size(); i++) {
                genomes[i] = new Genome(nrInput, nrOutput);
            }
        }
	}
	
    public Genome getFittest() {
    	Genome fittest = genomes[0];
        // Loop through individuals to find fittest
        
    	for (int i = 0; i < size(); i++) {
            if (fittest.fitness <= genomes[i].fitness) {
                fittest = genomes[i];
            }
        }
        return fittest;
    }
    
    public int size(){
    	return this.genomes.length;
    }
    

	/*
	public void removeWorst(int nrWorst){
		ArrayList<Genome> allGenomes = getAllGenomes(); 
		Collections.sort(allGenomes, FITNESS_ORDER);
		
		// TODO: Check the list is sorted with the worst at 0,1,2... and so on
		// add X from the bottom
		List<Genome> theWorst = allGenomes.subList(0, nrWorst);
		
		// keep those X's ids and remove them from the species
		for (Species sp : species){
			for (Genome genome : sp.genomes){
				if (theWorst.contains(genome)){
					sp.genomes.remove(genome);
				}
			}
		}
	}
	*/
	
}
