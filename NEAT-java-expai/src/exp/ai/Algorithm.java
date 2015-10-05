package exp.ai;

import java.util.Map;
import java.util.HashMap;

public class Algorithm {
		
	public static final double CROSS_MATCH = 0;
	private static final boolean elitism = true;
	
	public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.size, false);

        // decide how much space to give to each species in pop
        int nrOfSpecies = pop.nrOfSpecies();
        
        // pop.size / pop.nrOfSpecies
        
        // Keep our best individual
        if (elitism) {
            newPopulation.saveIndividual(0, pop.getFittest());
        }

        // Crossover population
        int elitismOffset;
        if (elitism) {
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }
        // Loop over the population size and create new individuals with
        // crossover
        for (int i = elitismOffset; i < pop.size(); i++) {
            Genome genome1 = tournamentSelection(pop);
            Genome genome2 = tournamentSelection(pop);
            Genome offspring = crossover(genome1, genome2);
            newPopulation.saveIndividual(i, newIndiv);
        }

        return newPopulation;
    }	
	public static void mutateGenome(Genome genome){
		// copy genome to create genomeCopy
		// run through genome, but make the actual mutations to genomeCopy
		// otherwise we could add things to genome via mutation, and mutate those new things in the same run
		
		
		// loop though nodes and mutate
		
		// loop through links and mutate
		
	}
	
	private static void mutateGeneNode(GeneNode node){
		
	}
	
	// TODO: Test crossover
	private static Genome crossover(Genome parent1, Genome parent2){
		// input size is the same for all genomes for all populations
		Genome child = new Genome(parent1.input.size());
		
		// find the fittest parent
		Genome fittest = parent1;
		Genome weakest = parent2;
		boolean equalParents = parent1.fitness == parent2.fitness;
		if (!equalParents && parent1.fitness < parent2.fitness){
			fittest = parent2;
			weakest = parent1;
		}
		
		// save weakest parent as a map 
		Map<Integer, GeneLink> map = new HashMap<Integer, GeneLink>();
		for (GeneLink link : weakest.links) map.put(link.innovation, link);
		
		// compare innovations
		for (GeneLink fitLink : fittest.links){
			GeneLink weakLink = map.get(fitLink.innovation);
			
			// if one parent is fittest
			if (!equalParents){
			
				// if an innovation is shared
				if (weakLink != null){
					// randomly choose fittest or weakest
					GeneLink childLink = (Math.random() < 0.5) ? fitLink : weakLink;
					child.links.add(childLink);
				}
				// if not shared
				else if (weakLink == null) {
					child.links.add(fitLink);
				}
			}
			// if parents are equally fit
			else if (equalParents){
				// if an innovation is shared
				if (weakLink != null){
					// randomly choose fittest or weakest
					GeneLink childLink = (Math.random() < 0.5) ? fitLink : weakLink;
					child.links.add(childLink);
					map.remove(fitLink.innovation);
				}
				// if not shared
				else if (weakLink == null) {
					// randomly choose to keep fitLink or not
					if (Math.random() < 0.5) child.links.add(fitLink);
				} 
			}
		}
		
		// if parents are equal, randomly decide whether to add weakest innovations
		// remaining in the map
		if (equalParents){
			for (GeneLink weakLink : map.values()){
				if (Math.random() < 0.5) child.links.add(weakLink);
			}
		}
		
		
		
		return child;
	}
	
}
