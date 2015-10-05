package exp.ai;

import java.util.ArrayList;

public class Algorithm {

	public static final double CROSS_MATCH = 0;
	public static final double BIAS_CHANCE = 0;
	public static final double LINK_CHANCE = 0;
	public static final double BIAS_LEARNING_RATE = 0;
	
	public static Population evolvePopulation(Population pop){
		return pop;
	}
	
	public static void mutateGenome(Genome genome){
		// copy genome to create genomeCopy
		// run through genome, but make the actual mutations to genomeCopy
		// otherwise we could add things to genome via mutation, and mutate those new things in the same run
		
		
		// loop though nodes and mutate
		
		// loop through links and mutate
		
	}
	
	private static void mutateGeneNode(GeneNode node, Genome genome, String type){
		double chance;
		boolean mutateBias = !type.equals("input");
		boolean newLink = !type.equals("output");
		
				
		chance = Math.random();
		if (mutateBias && chance >= BIAS_CHANCE) {
			mutateBias(node);
		}
		
		chance = Math.random();
		if (newLink && chance >= LINK_CHANCE) {
			newLink(node, genome);
		}
		
	}
	
	private static void mutateBias(GeneNode node) {
		node.bias += BIAS_LEARNING_RATE;
	}
	
	private static void newLink(GeneNode node, Genome genome) {
		GeneNode newLinkNode = genome.getViableNode(node);
		
		
		
		
	}
	
}
