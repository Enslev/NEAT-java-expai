package exp.ai;

import java.util.ArrayList;

public class GeneNode {
	private static int idCount = 0;
	public int inputCount;
	public double value;
	public double bias;
	public int id;

	public boolean hasFinalValue = false;
	
	public ArrayList<GeneLink> input;
	public ArrayList<GeneLink> output;
	
	public GeneNode(double bias) {
		this.id = ++GeneNode.idCount;
		this.bias = bias;
		this.inputCount = 0;
		
		this.input = new ArrayList<GeneLink>();
		this.output = new ArrayList<GeneLink>();
	}
	
	public void addToValue(double x) {
		value += x;
		inputCount++;
		
		if (inputCount == input.size()){
			value = activationFunction( value + bias );
			hasFinalValue = true;
		}
	}
	
	// return final value and reset value to 0
	public double getFinalValue(){
		double finalValue = value;
		value = 0; // important, so values dont carry over to the next calculation of the network
		hasFinalValue = false;
		return finalValue;
	}
	
	private double activationFunction(double t) {
		// sigmoid
		return 1/(1+Math.pow(Math.E, -t));
	}

}
