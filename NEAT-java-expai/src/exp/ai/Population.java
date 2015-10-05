package exp.ai;

import java.util.ArrayList;

public class Population {
	 
	public ArrayList<Species> species;
	
	public int size;
	
	public Population(int popSize, boolean initialise) {
		this.size = popSize;
		species = new ArrayList<Species>();
	}
	
	public int nrOfSpecies(){
		return this.species.size();
	}

}
