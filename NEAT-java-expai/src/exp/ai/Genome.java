package exp.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Genome {
	public static final double DEFAULT_WEIGHT = 1;
	public static final double DEFAULT_BIAS = 0;
	
	public ArrayList<GeneNode> input;
	public ArrayList<GeneNode> output;
	public ArrayList<GeneNode> hidden;
	public ArrayList<GeneLink> links;
	private Queue<GeneNode> queue;
	public int fitness;
	
	public Genome(int inputSize) {
		input = new ArrayList<GeneNode>();
		output = new ArrayList<GeneNode>();
		hidden = new ArrayList<GeneNode>();
		links = new ArrayList<GeneLink>();
		queue = new LinkedList<GeneNode>();
		
		fitness = 0;
		
		for (int i = 0; i < inputSize; i++) {
			input.add(new GeneNode(DEFAULT_BIAS));
		}
		
		// TODO: make output size a parameter
		for (int i = 0; i < 2; i++) {
			output.add(new GeneNode(DEFAULT_BIAS));
		}
		
	}
	
	public ArrayList<GeneNode> run(ArrayList<Double> color) {
		// TODO: should be more flexible, color might change
		for (int i = 0; i < input.size(); i++) {
			input.get(i).value = color.get(i);
		}
		
		for (GeneNode node: input) {
			double value = node.value;
			for (GeneLink link: node.output) {
				if (link.enabled) {
					link.out.incValue(value * link.weight);
					
					if (link.out.inputCount >= link.out.input.size()) {
						queue.add(link.out);
					}					
				}
			}
		}
		 
		GeneNode node = queue.poll();
		while (node != null) {
			double value = node.calcValue();
			for (GeneLink link: node.output) {
				if (link.enabled) {
					link.out.incValue(value * link.weight);
					
					if (link.out.inputCount >= link.out.input.size()) {
						queue.add(link.out);
					}
				}
			}			
			node = queue.poll();
		}
		
		return output;
	}
	
	public GeneLink addLink(GeneNode n1, GeneNode n2) {
		GeneLink link = new GeneLink(n1, n2, DEFAULT_WEIGHT);
		
		if (connectionExists(link)) {
			return null;
		}
		
		n1.output.add(link);
		n2.input.add(link);
		links.add(link);
		return link;
	}
	
	public GeneNode addNode(GeneLink link) {
		link.enabled = false;
		GeneNode n = new GeneNode(DEFAULT_BIAS);
		hidden.add(n);

		GeneLink link1 = new GeneLink(link.in, n, DEFAULT_WEIGHT);	
		GeneLink link2 = new GeneLink(n, link.out, DEFAULT_WEIGHT);
		
		link.in.output.add(link1);
		n.input.add(link1);
		
		n.output.add(link2);
		link.out.input.add(link2);
		
		links.add(link1);
		links.add(link2);
		
		return n;
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
	
	
	// TODO Jeppe Skal lige lave den færdig
	// Remove ancestors fra viableNodes
	public GeneNode getViableNode(GeneNode gn) {
		Random rand = new Random();
		ArrayList<GeneNode> viableNodes = new ArrayList<GeneNode>();
		
		viableNodes.addAll(hidden);
		viableNodes.addAll(queue);
		
		ArrayList<GeneNode> ancestors = getAncestors(gn);
		
		int index = rand.nextInt(viableNodes.size());
		return viableNodes.get(index);
		
	}
	
	// TODO  Might be implemented more efficiently
	public static ArrayList<GeneNode> getAncestors(GeneNode node) {
		ArrayList<GeneNode> ancestors = new ArrayList<GeneNode>();
		
		for (GeneLink inLink : node.input) {
			GeneNode inNode = inLink.in;
			
			if (!ancestors.contains(inNode)) {
				
				ancestors.add(inNode);
				ArrayList<GeneNode> anc = getAncestors(inNode);
				
				for (GeneNode gn: anc) {
					if (!ancestors.contains(gn)) ancestors.add(gn);
				}
				
			}
		}
		
		
		return ancestors;
		
	}
	
	
	
	// Can be done more effective?
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
				str += "(" + outNode.id + ", " + outLink.weight + ", " + outLink.enabled + ") ";
			}
			str += "\n";
		}
		
		
		return str;
	}

}
