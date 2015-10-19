package exp.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Population {
	 
	public int size;
	public ArrayList<Species> species;
	public double fitness;
	
	static final Comparator<Genome> FITNESS_ORDER = new Comparator<Genome>(){
		public int compare(Genome genome1, Genome genome2){
			if (genome1.adjustedFitness < genome2.adjustedFitness){
				return -1;
			}
			else if ( genome1.adjustedFitness == genome2.adjustedFitness ){
				return 0;
			}
			else {
				return 1;
			}
		};
	};
	
	public Population(int popSize) {
		size = popSize;
		species = new ArrayList<Species>();
	}

	public void adjustFitness(){
		double totalFitness = 0.0;
		for (Species sp : species){
			sp.adjustFitness();
			totalFitness += sp.fitness;
		}
		fitness = totalFitness;
	}
	
	public void removeExtinctSpecies(){
		for (Species sp: species){
			if (sp.genomes.size() == 0){
				this.species.remove(sp);
			}
		}
	}
	
	public void calcOffspring(){
		for (Species sp : this.species){
			// TODO: does this really round as we expect, ie: 0.1-0.5 ==> 0.0 AND 0.6-0.9 ==> 1.0
			sp.nrOffspring = (int) Math.rint( (this.fitness / sp.fitness) * this.size);
		}
		
	}
	
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
	
	// combine genomes from all species
	private ArrayList<Genome> getAllGenomes(){
		ArrayList<Genome> allGenomes = new ArrayList<Genome>();
		
		for (Species sp : species){
			for (Genome genome : sp.genomes){
				allGenomes.add(genome);
			}
		}
		return allGenomes;
	}
}
