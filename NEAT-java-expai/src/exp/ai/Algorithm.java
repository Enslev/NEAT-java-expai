package exp.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class Algorithm {

	
	public static final double CROSS_MATCH = 0;
	public static final double ADD_LINK_CHANCE = 0.0;
	
	
    private static final double uniformRate = 0.5;
    private static final double mutationRate = 0.4; // 0.015
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;
    private static final double learningrate = 0.3;
    private static final double layerMutationRate = 0.5;
	
	
    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.size(), false);
        Genome champion = pop.getFittest();
        
        // Keep the champion
        if (elitism) {
            newPopulation.genomes[0] = champion;
        }

        // Crossover population
        int elitismOffset;
        if (elitism) {
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }
        
        // half of the new population is the champion and mutations of him
        int championOffspringSize = (int) Math.floor( pop.size() / 2 );
        
        // champion mates with X nr of other genomes
        for (int i = elitismOffset; i < championOffspringSize; i++){
        	
        	// choose a random mate for the champion
        	Random random = new Random();
        	Genome randomGenome = pop.genomes[ random.nextInt( pop.size() - 1 ) + 1 ]; // + 1 to not select champion
        	Genome child = crossover(champion, randomGenome);
        	mutateGenome(child);
        	newPopulation.genomes[i] = child;
        }

        // the rest of the population mates via a tournament 
        for (int i = championOffspringSize; i < pop.size(); i++) {
            
            Genome[] localChampions = tournamentSelection(pop);
            Genome child = crossover(localChampions[0], localChampions[1]);
            mutateGenome(child);
            newPopulation.genomes[i] = child;
        }
        
        return newPopulation;
    }
	
    // TODO: Test crossover
 	private static Genome crossover(Genome parent1, Genome parent2){
 		// input/output size is the same for all genomes for all populations
 		Genome child = new Genome(parent1.input.size(), parent1.output.size());
 		
 		// find the fittest parent
 		Genome fittest = parent1;
 		Genome weakest = parent2;
 		boolean equalParents = parent1.fitness == parent2.fitness;
 		if (!equalParents && parent1.fitness < parent2.fitness){
 			fittest = parent2;
 			weakest = parent1;
 		}
 		
 		System.out.println(parent1.toStringGenotype());
 		
 		// save weakest parent as a map 
 		HashMap<Integer, GeneLink> map = new HashMap<Integer, GeneLink>();
 		for (GeneLink link : weakest.links) map.put(link.innovation, link);
 		
 		// compare innovations
 		for (GeneLink fitLink : fittest.links){
 			GeneLink weakLink = map.get(fitLink.innovation); // null if the fit innovation is not in weak innovation
 			
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
	
	public static void mutatePopulation(){}
	
	public static void mutateGenome(Genome genome){

		Genome copy = genome.copy();
		mutateGeneNodeBias(genome);
		mutateGeneLinkWeights(genome);

		mutateAddLinks(genome, copy);
		mutateAddNodes(genome, copy);
		
		
	}
	
	private static void mutateGeneNode(GeneNode node, Genome genome, String type){
		
	}

	private static void mutateAddLinks(Genome original, Genome copy){
		// run through copy, but make the actual mutations to the original genome
		// otherwise we could add things to genome via mutation, and mutate those new things in the same run
		
		for (GeneNode node : copy.input){
			// chance to add
			if (Math.random() < ADD_LINK_CHANCE){
				// get available nodes
				ArrayList<GeneNode> availNodes = original.availableNodes(node);
				
				// choose a random available node
				Random random = new Random();
				int randomIndex = random.nextInt( availNodes.size() );
				GeneNode randomNode = availNodes.get( randomIndex );
				
			}

			
			// check if the innovation already exists, and assign that nr or a new one to this link
			// save this link to list of innovations
			// save this link to original

		}
		
	}
	
	private static void mutateAddNodes(Genome original, Genome copy){
		
	}
	
	// Select Genomes for crossover
    private static Genome[] tournamentSelection(Population pop) {
        Population tournament = new Population(tournamentSize, false);
        Genome[] localChampions = new Genome[2];
        
        // For each place in the tournament get a random Genome
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.size());
            tournament.genomes[i] = pop.genomes[randomId];
        }
        
        // get the fittest and second fittest
        Genome first = tournament.genomes[0];
        Genome second = tournament.genomes[1];

        for (int i = 2; i < tournamentSize; i++){
        	Genome current = tournament.genomes[i];
        	
        	if (first.fitness < current.fitness){
        		second = first;
        		first = current;
        	}
        	else if (second.fitness < current.fitness && current.fitness <= first.fitness ){
        		second = current;
        	}
        	
        }
        localChampions[0] = first;
        localChampions[1] = second;
        
        return localChampions;
    }
}
