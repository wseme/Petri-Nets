package edu.monmouth.petrinet;

import java.util.ArrayList;

import edu.monmouth.shapes.Square;

import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.util.Log;

public class Transition extends Square {

	private boolean fire;
	private ArrayList<Place> inputs;
	private ArrayList<Place> outputs;
	private String name;
	
	private static int tAmount = 0;
	private static String NAME = "t" + tAmount; 
	
	public Transition(float x, float y){
		fire = false;
		
		inputs = new ArrayList<Place>();
		outputs = new ArrayList<Place>();
		
		tAmount++;
		NAME = "t" + tAmount;
		this.name = NAME;
		setBounds(x, y);
	}
	
	
	public ArrayList<Place> getInputs(){
		return inputs;
	}
	
	public ArrayList<Place> getOutputs(){
		return outputs;
	}

	public void addInput(Place p){
		inputs.add(p);
	}
	
	public void removeInput(Place p){
		inputs.remove(p);
	}
	
	public void addOutput(Place p){
		outputs.add(p);
	}
	public void removeOutput(Place p){
		outputs.remove(p);
	}
	
	public void setColor(boolean canFire){//TODO finish testing color switch
		if (canFire){
			fire = true;
			setColorFilter(Color.RED, Mode.SRC);
		}else{
			fire = false;
			setColorFilter(Color.GREEN, Mode.SRC);
		}
	}
	
	public boolean canFire(){
		
		//can consume but not give out tokens from a place when fired
		if( inputs.size() <= 0 )
			return false;

		
//		Log.d("PetriNet", this.toString());
		//I don't like this there is a better way but I'll fix it later
		//checks for duplicates of an input for same transition, if it does, increases that count
		int inputCount = 0;
		Log.d("CheckFire", "Inputs:");
		for (Place input : inputs) { 
//			Log.d("CheckFire", input.getName());
			ArrayList<Transition> placeOutputs = input.getOutputs();
			//TODO getInputs and outputs here, subtract  in if statement
			for (Transition checkPlaceOutputs : placeOutputs) {
				if (checkPlaceOutputs.getName().equals(this.getName())) {//terrible way to do this, wanted to check if this was the problem b4

					inputCount++;
				}
			}
			
			//TODO more testing with this - not sure if this harmed more than did good
//			ArrayList<Transition> placeInputs = input.getInputs();
//			for (Transition checkPlaceInputs : placeInputs) {
//				if (checkPlaceInputs.getName().equals(this.getName())) {
//					inputCount--;
//				}
//			}
			
			Log.d("CheckFire", "INPUT TOKENS:" +input.getTokenAmount() + ", inputCount" + inputCount);
			if (input.getTokenAmount() - inputCount < 0) {
				return false;
			}
			inputCount = 0;
		}

		int outputCount = 0;
		
		
		
		Log.d("CheckFire", "Outputs:");
		for (Place output : outputs) {

			Log.d("CheckFire", output.getName());
			ArrayList<Transition> placeInputs = output.getInputs();
			for (Transition checkPlaceInputs : placeInputs) {
				if (checkPlaceInputs.getName().equals(this.getName())) {
					outputCount++;
				}
			}
			ArrayList<Transition> placeOutputs = output.getOutputs(); // if input is also output decrease the count
			for(Transition checkPlaceOutputs: placeOutputs){
				if (checkPlaceOutputs.getName().equals(this.getName())) {
					outputCount--;
				}
			}

			Log.d("CheckFire", "OUTPUT TOKENS:" + output.getTokenAmount() + ", outputCount" + outputCount);
			if (output.getTokenAmount() + outputCount > 9) {
				return false;
			}
			outputCount = 0;

		}
		return true;
	}

	
	public void fire() {

		if (canFire()) {
			for (Place input : inputs) {
				input.removeToken();
			}

			for (Place output : outputs) {
				output.addToken();
			}
		}
	}
	
	public void setBounds(float touchedX, float touchedY){
		
		Coordinates coords = this.getCoordinates();
		coords.setTouchedX((int)touchedX);
		coords.setTouchedY((int)touchedY);
		
		int left = coords.getX();
		int top = coords.getY();
		int bottom = top + 80;
		int right = left + 80;
		
		setBounds(left, top, right, bottom);
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("\n" +this.name + ":\n\tInputs:");
		
		for(Place input: inputs){
			builder.append("\n\t\t"+input.getName());
		}
		builder.append("\n\tOutputs:");
		
		for(Place output: outputs){
			builder.append("\n\t\t" + output.getName());
		}
		
		builder.append("\n\n");
		return builder.toString();
	}
	
	public String getName(){
		return this.name;
	}
	

}
