package edu.monmouth.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;

public class Arrow extends ShapeDrawable{

	private Coordinates coords = new Coordinates();
	private Paint paint = new Paint();
	
	public Arrow(){
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);//needed for triangle part
		paint.setAntiAlias(true);
	}
	
	public Coordinates getCoordinates(){
		return coords;
	}
	 
    /**
     * Contains the coordinates of the instance.
     * 
     * @author Walter
     */
    public class Coordinates {
        private int startX = 0;
        private int startY = 0;
        private int endX = 0;
        private int endY = 0;
        
        
        public int getStartX() {
            return startX;
        }
 
        public void setStartX(int value) {
            startX = value;
        }
 

        public int getStartY() {
            return startY;
        }
 

        public void setStartY(int value) {
            startY = value;
        }
 
        
        public int getEndX() {
            return endX;
        }
 
       
        public void setEndX(int value) {
            endX = value;
        }
        
        public int getEndY() {
            return endY;
        }
 
       
        public void setEndY(int value) {
            endY = value;
        }
        
        public void setCoords(int startX, int startY, int endX, int endY){
        	this.startX = startX;
        	this.startY = startY;
        	this.endX = endX;
        	this.endY = endY;
        }
        
 
        /**
         * Helper method for debugging.
         */
        public String toString() {
            return "Coordinates: (" + startX + ", " + startY +  ", " + endX + ", " + endY + ")";
        }
    }


	public void draw(Canvas canvas){
		super.draw(canvas);
		
		float startX = getCoordinates().getStartX();
		float startY = getCoordinates().getStartY();
		
		float endX = getCoordinates().getEndX();
		float endY = getCoordinates().getEndY();
		
		canvas.drawLine(startX, startY, endX, endY, paint);

		float xCenter = (endX + startX )/2;
		float yCenter = (endY + startY )/2;
		
		//I don't want the line to extend all the way to begining so I took half the distance to draw arrow lines
		float arrowEndX = (xCenter + startX )/2;
		float errowEndY = (yCenter + startY )/2;
		
		canvas.save();//save state so I can draw previous lines with out changing rotation
		canvas.rotate(30, xCenter, yCenter);
		canvas.drawLine(xCenter, yCenter, arrowEndX , errowEndY , paint);
		
		canvas.rotate(300, xCenter, yCenter);
		canvas.drawLine(xCenter, yCenter, arrowEndX , errowEndY, paint);
		
		canvas.restore(); //restore to last save

	}
	

}
