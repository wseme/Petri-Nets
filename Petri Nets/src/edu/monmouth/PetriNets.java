package edu.monmouth;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * 
 * @author Walter
 * @version 6/26/11
 * */
public class PetriNets extends Activity {
	
	private Panel panel;
	
	private Button transition;
	private Button place;
	private Button arc;
	private Button plusToken;
	private Button minusToken;
	private Button fire;
	private Button move;
	private Button delete;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setViews();

	}

    private void setViews(){
    	panel = (Panel) findViewById(R.id.Panel);

    	transition = (Button)findViewById(R.id.TransitionButton);
    	place      = (Button)findViewById(R.id.PlaceButton);
    	arc        = (Button)findViewById(R.id.ArcButton);
    	plusToken  = (Button)findViewById(R.id.PlusButton);
    	minusToken = (Button)findViewById(R.id.MinusButton);
    	fire       = (Button)findViewById(R.id.FireButton);
    	move       = (Button)findViewById(R.id.MoveButton);
    	delete     = (Button)findViewById(R.id.DeleteButton);
	}


	public void onClick(View v) {
		
		//reset push, more effecient to put in switch, but this is easier to read atm
		transition.setBackgroundResource(R.drawable.btn_normal);
		     place.setBackgroundResource(R.drawable.btn_normal);
		       arc.setBackgroundResource(R.drawable.btn_normal);
		 plusToken.setBackgroundResource(R.drawable.btn_normal);
		minusToken.setBackgroundResource(R.drawable.btn_normal);
		      fire.setBackgroundResource(R.drawable.btn_normal);
		      move.setBackgroundResource(R.drawable.btn_normal);
		    delete.setBackgroundResource(R.drawable.btn_normal);

		
		switch (v.getId()) {
			case R.id.TransitionButton:
				panel.setSelectedItem(MenuItemSelected.Transition);
				transition.setBackgroundResource(R.drawable.btn_pressed);
				break;
			case R.id.PlaceButton:
				place.setBackgroundResource(R.drawable.btn_pressed);
				panel.setSelectedItem(MenuItemSelected.Place);
				break;
			case R.id.ArcButton:
				panel.setSelectedItem(MenuItemSelected.Arc);
				arc.setBackgroundResource(R.drawable.btn_pressed);
				break;
			case R.id.PlusButton:
				panel.setSelectedItem(MenuItemSelected.AddToken);
				plusToken.setBackgroundResource(R.drawable.btn_pressed);
				break;
			case R.id.MinusButton:
				panel.setSelectedItem(MenuItemSelected.MinusToken);
				minusToken.setBackgroundResource(R.drawable.btn_pressed);
				break;
			case R.id.FireButton:
				panel.setSelectedItem(MenuItemSelected.fire);
				fire.setBackgroundResource(R.drawable.btn_pressed);
				break;
			case R.id.MoveButton:
				panel.setSelectedItem(MenuItemSelected.move);
				move.setBackgroundResource(R.drawable.btn_pressed);
				break;
			case R.id.DeleteButton:
				panel.setSelectedItem(MenuItemSelected.delete);
				delete.setBackgroundResource(R.drawable.btn_pressed);
				break;
		}
	}
   
     
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
    	//TODO change to save, load, etc
        switch (item.getItemId()) {
        case R.id.background:
        	return true;
        }
        return false;
    }
    
}