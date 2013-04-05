package edu.monmouth.shapes;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;

public class Square extends ShapeDrawable {
	
 
    /**
     * Contains the coordinates of the instance.
     * 
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
    
    public Square(){
    	setShape(new RectShape());
    	setColorFilter(Color.GREEN, Mode.SRC);
    	
    	coordinates = new Coordinates();
    }
    

    /**
     * @return The coordinates of the instance.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }
    



}
