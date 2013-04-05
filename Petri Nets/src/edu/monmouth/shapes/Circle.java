package edu.monmouth.shapes;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

public class Circle  extends ShapeDrawable{

    /**
     * Contains the coordinates of the instance.
     * TODO needs a radius for anti aliasing and is a circle not square
     * @author Walter
     */
    public class Coordinates {
        private int x = 0;
        private int y = 0;
 
        /**
         * @return The x coordinate of the upper left corner.
         */
        public int getX() {
            return x;
        }
 
        /**
         * @return The x coordinate of the center.
         */
        public int getTouchedX() {
            return x + 40;
        }
 
        /**
         * @param value The new x coordinate of the upper left corner.
         */
        public void setX(int value) {
            x = value;
        }
 
        /**
         * @param value The new x coordinate of the center.
         */
        public void setTouchedX(int value) {
        	
            x = value - 40;
        }
 
        /**
         * @return The y coordinate of the upper left corner.
         */
        public int getY() {
            return y;
        }
 
        /**
         * @return The y coordinate of the center.
         */
        public int getTouchedY() {
            return y + 40;
        }
 
        /**
         * @param value The new y coordinate of the upper left corner.
         */
        public void setY(int value) {
            y = value;
        }
 
        /**
         * @param value The new y coordinate of the center.
         */
        public void setTouchedY(int value) {
            y = value - 40;
        }
 
        /**
         * Helper method for debugging.
         */
        public String toString() {
            return "Coordinates: (" + x + ", " + y + ")";
        }
    }
 

    /**
     * Coordinates on which the shape should be drawn.
     */
    private Coordinates coordinates;
	
	
	public Circle(){
		setShape(new OvalShape());//there is a better way to do this for a circle
		Paint paint = getPaint();
		paint.setAntiAlias(true);
		paint.setColor(Color.YELLOW);
		
		coordinates = new Coordinates();
	}
	
	public Coordinates getCoordinates(){
		return this.coordinates;
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
	
	
}
