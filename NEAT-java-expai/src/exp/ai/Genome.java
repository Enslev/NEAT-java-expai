package exp.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Genome {
	public static final double DEFAULT_WEIGHT = 1.0;
	public static final double DEFAULT_BIAS = 0.0;
	
	public static int idCount = 0;
	public int id;
	
	public ArrayList<GeneNode> input;
	public ArrayList<GeneNode> output;
	public ArrayList<GeneNode> hidden;
	public ArrayList<GeneLink> links;
	private Queue<GeneNode> queue;
	public double fitness;
		
	public Genome(int inputSize, int outputSize) {
		this.id = Genome.idCount++;
		
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
	
	// TODO: Remember to reset the entire networks values to 0 afterwards
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
		
		// TODO: Calling getGeneNodeLinks could slow performance too much
		// alternative: cache results before sending anything through the network
		for (GeneLink link : this.getGeneNodeLinks(node, "out")) {
			if (link.enabled) {
				GeneNode target = link.target;
				
				// TODO: this is going to get slow really fast, having to recalculate the input size every time. Fix it!
				int nodeInputSize = this.getGeneNodeLinks(node, "in").size();
				target.addToValue(value * link.weight, nodeInputSize);
				
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
		
		//source.output.add(link);
		//target.input.add(link);
		links.add(link);
	}
	
	// can only add hidden nodes
	public void addNode(GeneLink link) {
		link.enabled = false;
		GeneNode newHiddenNode = new GeneNode(DEFAULT_BIAS);
		hidden.add(newHiddenNode);

		GeneLink inLink = new GeneLink(link.source, newHiddenNode, DEFAULT_WEIGHT);	
		GeneLink outLink = new GeneLink(newHiddenNode, link.target, DEFAULT_WEIGHT);
		links.add(inLink);
		links.add(outLink);
	}
	
	public boolean connectionExists(GeneLink testLink) {
		for (GeneLink existingLink: links) {
			if (existingLink.target == testLink.target &&
				existingLink.source == testLink.source) {
				
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<GeneNode> getAllNodes(){
		ArrayList<GeneNode> all = this.input;
		all.addAll(this.hidden);
		all.addAll(this.output);
		return all;
	}
	
	public ArrayList<Integer> getSortedInnovation() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for (GeneLink link : links) {
			list.add(link.innovation);
		}
		Collections.sort(list);
		return list;
	}
	
	public ArrayList<GeneLink> getGeneNodeLinks(GeneNode node, String type){
		ArrayList<GeneLink> links = new ArrayList<GeneLink>();
		
		for (GeneLink link : this.links){
			if (type.equals("in")){
				if (link.target.id == node.id){
					links.add(link);
				}
			}
			if (type.equals("out")){
				if (link.source.id == node.id){
					links.add(link);
				}
			}
		}
		return links;
	}
	
	public Genome copy(){
		Genome copy;
		
		for (GeneNode inNode : this.input){
			GeneNode copyNode;
			copyNode.id = inNode.id;
			copyNode.bias = inNode.bias;
						
			//copy.input.add(  );
		}
		
		return copy;
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
			for (GeneLink outLink : this.getGeneNodeLinks(node, "out")){
				GeneNode outNode = outLink.target;
				str += outNode.id + " ";
			}
			str += "\n";
		}
		
		
		return str;
	}
	
	public String toStringGenotype(){
		String str = "";
		
		for (GeneLink link : links){
			str = (link.enabled) ? "" : "!";
			str += link.innovation + "(" + link.source.id + "-" + link.target.id + ")";
		}
		return str;
	}

}
