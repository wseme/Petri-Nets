package edu.monmouth.petrinet;

import edu.monmouth.shapes.Arrow;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;

public class Arc extends Arrow{

	private int amount =1;//can have more than one arc
	//TODO: use the Path class
	private ShapeDrawable from = null;
	private ShapeDrawable to = null;
	private Paint paint;
//	private Circle circle;
	
	public Arc(){
		
	}
	
	public Arc(int startingX, int startingY){
		
	}
	
	
	public void setFromShape(ShapeDrawable start){
		if(start != null)
			from = start;
	}
	
	public ShapeDrawable getFromShape(){
		return from;
	}
	
	
	public void setToShape(ShapeDrawable end){
		if(end!= null)
			to = end;
	}
	
	public ShapeDrawable getToShape(){
		return to;
	}

	
	public void draw(Canvas canvas){
		super.draw(canvas);	
		//TODO here is where you add numbers like 2 tokens, or reverse arrows
	}
	
}


