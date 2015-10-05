package exp.ai;

import java.util.ArrayList;

public class GeneNode {
	private static int idCount = 0;
	public int inputCount;
	public double value;
	public double bias;
	public int id;

	public ArrayList<GeneLink> input;
	public ArrayList<GeneLink> output;
	
	public GeneNode(double bias) {
		this.id = ++GeneNode.idCount;
		this.bias = bias;
		this.inputCount = 0;
		
		this.input = new ArrayList<GeneLink>();
		this.output = new ArrayList<GeneLink>();
	}
	
	public void incValue(double x) {
		value += x;
		inputCount++;
	}
	
	public double calcValue() {
		value =  sigmoid(value + bias);;
		return value;
	}
	
	private double sigmoid(double t) {
		return 1/(1+Math.pow(Math.E, -t));
	}

}
