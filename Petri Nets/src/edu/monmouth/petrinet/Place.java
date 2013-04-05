package edu.monmouth.petrinet;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import edu.monmouth.shapes.Circle;

public class Place extends Circle{
	
	private String name;
	private ArrayList<Transition> inputs;
	private ArrayList<Transition> outputs;
	private int amountOfTokens;
	
	
	private Paint paint;//XXX redundant, shapedrawable has a Paint, but I couldn't find setter and old paint would not show text
	private static int pAmount = 0;
	private static String NAME = "p" + pAmount; 
	
	public Place(float x, float y){
		super();
		
		inputs = new ArrayList<Transition>();
		outputs = new ArrayList<Transition>();
		
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setLinearText(true);
		paint.setTextAlign(Align.CENTER);
		paint.setTextSize((float)30);
		
		amountOfTokens = 0;
		pAmount++;
		NAME = "p" +pAmount;
		name = NAME;
		setBounds(x, y);
	}
	
	public void addToken(){
		if(amountOfTokens <9)
			amountOfTokens++;
	}
	
	public void removeToken(){
		if(amountOfTokens >0)
			amountOfTokens--;
	}
	
	public void addInput(Transition t){
		inputs.add(t);
	}
	
	public void removeInput(Transition t){
		inputs.remove(t);
	}
	
	public ArrayList<Transition> getInputs(){
		return inputs;
	}
	
	public ArrayList<Transition> getOutputs(){
		return outputs;
	}
	
	public void addOutput(Transition t){
		outputs.add(t);
	}
	public void removeOutput(Transition t){
		outputs.remove(t);
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getTokenAmount(){
		return amountOfTokens;
	}
	
	public boolean hasEnoughTokens(){
		outputs.trimToSize();
		
		if(amountOfTokens > 0){
			return true;
		}
		return false;
	}
	
	public boolean hasMaxTokens(){
		inputs.trimToSize();
		outputs.trimToSize();
		
		if(amountOfTokens< 9){
			return true;
		}
		return false;
	}

	
	public void draw(Canvas canvas){
		super.draw(canvas);
		canvas.drawPosText(""+amountOfTokens, new float[]{getCoordinates().getTouchedX(), getCoordinates().getTouchedY()+5}, paint);
	
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("\n" +this.name + ":\n\tInputs:");
		
		for(Transition input: inputs){
			builder.append("\n\t\t"+input.getName());
		}
		builder.append("\n\tOutputs:");
		
		for(Transition output: outputs){
			builder.append("\n\t\t" + output.getName());
		}
		
		builder.append("\n\n");
		return builder.toString();
	}
}
