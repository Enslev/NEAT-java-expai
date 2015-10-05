package exp.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

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
	
	public ArrayList<GeneNode> run(ArrayList<Integer> color) {
		// TODO: should be more flexible, color might change
		for (int i = 0; i < input.size(); i++) {
			input.get(i).value = color.get(i) / 255.0;
		}
		
		for (GeneNode node: input) {
			double value = node.value;
			for (GeneLink c: node.output) {
				c.out.incValue(value * c.weight);
				
				if (c.out.inputCount >= c.out.input.size()) {
					queue.add(c.out);
				}
			}
		}
		 
		GeneNode node = queue.poll();
		while (node != null) {
			double value = node.calcValue();
			for (GeneLink c: node.output) {
				if (c.enabled) {
					c.out.incValue(value * c.weight);
					
					if (c.out.inputCount >= c.out.input.size()) {
						queue.add(node);
					}
				}
			}			
			node = queue.poll();
		}
		
		return output;
	}
	
	public void addLink(GeneNode n1, GeneNode n2) {
		GeneLink link = new GeneLink(n1, n2, DEFAULT_WEIGHT);
		
		if (connectionExists(link)) {
			return;
		}
		
		n1.output.add(link);
		n2.input.add(link);
		links.add(link);
	}
	
	public void addNode(GeneLink link) {
		link.enabled = false;
		GeneNode n = new GeneNode(DEFAULT_BIAS);
		hidden.add(n);

		GeneLink link1 = new GeneLink(link.in, n, DEFAULT_WEIGHT);	
		GeneLink link2 = new GeneLink(n, link.out, DEFAULT_WEIGHT);
		links.add(link1);
		links.add(link2);
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
