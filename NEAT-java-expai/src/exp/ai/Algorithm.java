package exp.ai;

public class Algorithm {

	// GA parameters
	public static final double CROSS_MATCH = 0;
	/*
    private static final double uniformRate = 0.5;
    private static final double mutationRate = 0.4; // 0.015
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;
    private static final double learningrate = 0.3;
    private static final double layerMutationRate = 0.5;
	*/
	
	public static Population evolvePopulation(Population pop){
		// save the current population maybe?
		
		// new population
		Population newPop = new Population(pop.size);
		
		// calc adjusted fitness for genomes
		// calc total fitness for species
		// calc total population fitness
		pop.adjustFitness();
		
		// remove X lowest performing genomes in the entire population
		int nrOfWorstToRemove = 0;
		
		pop.removeWorst(nrOfWorstToRemove);
		
		// delete possible empty species
		pop.removeExtinctSpecies();
		
		// assign nr of offspring to each species, based on species fitness
		pop.calcOffspring();
		
		// each offspring is assigned to a species, either one that already exists or a new one
		// if one that already exists, then the id of that species is copied to a new one in the new population, to keep track of it through generations
		// if new one, just create it with new species id
		
		// if a species only has 1 member, 
		// mate with closest genome from other species OR mutate it??
		
		return pop;
	}
	
	public static Genome crossover(Genome genome1, Genome genome2){
		return genome1;
	}
	
	public static void mutatePopulation(){}
	
	public static void mutateGenome(Genome genome){
		// copy genome to create genomeCopy
		// run through genome, but make the actual mutations to genomeCopy
		// otherwise we could add things to genome via mutation, and mutate those new things in the same run
		
		
		// loop though nodes and mutate
		
		// loop through links and mutate
		
	}
	
	private static void mutateGeneNode(GeneNode node){
		
	}
	
	//public static Genome tournamentSelection(Population pop){}
}
