package exp.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Genome {
	public static final double DEFAULT_WEIGHT = 1.0;
	public static final double DEFAULT_BIAS = 0.0;
	
	public ArrayList<GeneNode> input;
	public ArrayList<GeneNode> output;
	public ArrayList<GeneNode> hidden;
	public ArrayList<GeneLink> links;
	private Queue<GeneNode> queue;
	public double fitness;
	
	public Genome(int inputSize, int outputSize) {
		input = new ArrayList<GeneNode>();
		output = new ArrayList<GeneNode>();
		hidden = new ArrayList<GeneNode>();
		links = new ArrayList<GeneLink>();
		queue = new LinkedList<GeneNode>();
		
		fitness = 0.0;
		
		for (int i = 0; i < inputSize; i++) {
			input.add(new GeneNode(DEFAULT_BIAS));
		}
		
		for (int i = 0; i < outputSize; i++) {
			output.add(new GeneNode(DEFAULT_BIAS));
		}
		
	}
	
	public ArrayList<GeneNode> sendThroughNetwork(ArrayList<Integer> color) {
		// TODO: should be more flexible, color might change
		// assign final color values to all input nodes
		for (int i = 0; i < input.size(); i++) {
			GeneNode inputNode = input.get(i); 
			inputNode.value = color.get(i) / 255.0;
			
			inputNode.hasFinalValue = true; // not necessary, but informative
			queue.add(inputNode);
		}
		
		GeneNode node = queue.poll();
		while (node != null) {
			feedForward(node);
			node = queue.poll();
		}
		return output;
	}

	// send the node value through all its outbound links
	public void feedForward(GeneNode node){
		double value = node.getFinalValue();
		
		for (GeneLink link : node.output) {
			if (link.enabled) {
				GeneNode target = link.out;
				target.addToValue(value * link.weight);
				
				if (target.hasFinalValue){
					queue.add(target);
				}
			}
		}
	}
	
	public void addLink(GeneNode source, GeneNode target) {
		GeneLink link = new GeneLink(source, target, DEFAULT_WEIGHT);
		
		if (connectionExists(link)) {
			return;
		}
		
		source.output.add(link);
		target.input.add(link);
		links.add(link);
	}
	
	// can only add hidden nodes
	public void addNode(GeneLink link) {
		link.enabled = false;
		GeneNode newHiddenNode = new GeneNode(DEFAULT_BIAS);
		hidden.add(newHiddenNode);

		GeneLink inLink = new GeneLink(link.in, newHiddenNode, DEFAULT_WEIGHT);	
		GeneLink outLink = new GeneLink(newHiddenNode, link.out, DEFAULT_WEIGHT);
		links.add(inLink);
		links.add(outLink);
	}
	
	public boolean connectionExists(GeneLink testLink) {
		for (GeneLink existingLink: links) {
			if (existingLink.out == testLink.out &&
				existingLink.in == testLink.in) {
				
				return true;
			}
		}
		
		return false;
	}
	
	public ArrayList<Integer> getSortedInnovation() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for (GeneLink link : links) {
			list.add(link.innovation);
		}
		Collections.sort(list);
		return list;
	}
	
	public String toString(){
		String str = "";
		
		ArrayList<GeneNode> allNodes = new ArrayList<GeneNode>();
		allNodes.addAll(this.input);
		allNodes.addAll(this.output);
		allNodes.addAll(this.hidden);
		
		str += "INPUT: ";
		for (GeneNode inNode : input){
			str += inNode.id + " ";
		}
		str += "\n";
		
		str += "OUTPUT: ";
		for (GeneNode outNode : output){
			str += outNode.id + " ";
		}
		str += "\n";
		
		for (GeneNode node : allNodes){
			str += node.id + ": ";
			for (GeneLink outLink : node.output){
				GeneNode outNode = outLink.out;
				str += outNode.id + " ";
			}
			str += "\n";
		}
		
		
		return str;
	}

}
