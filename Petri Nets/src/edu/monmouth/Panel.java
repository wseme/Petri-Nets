package edu.monmouth;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import edu.monmouth.petrinet.Arc;
import edu.monmouth.petrinet.Place;
import edu.monmouth.petrinet.Transition;



/**
 * Custom SurfaceView to handle everything we need from physics to drawings.
 * 
 * @author Walter
 */
public class Panel extends SurfaceView implements SurfaceHolder.Callback{

	private PetriNetsThread thread;

	private ArrayList<Transition> transitions = new ArrayList<Transition>();
	private ArrayList<Place> places = new ArrayList<Place>();
	private ArrayList<Arc> arcs = new ArrayList<Arc>();

	private int backgroundColor = Color.WHITE;// use for later changes if wanted

	// don't use this, use enum where possible
	private Transition currentTransition;
	private Place currentPlace;
	private Arc currentArc;

	private MenuItemSelected currentItem;
	


	/**
	 * Constructor called on instantiation.
	 * 
	 * @param context
	 *            Context of calling activity.
	 */
	public Panel(Context context) {
		super(context);
		init();
	}

	public Panel(Context context, AttributeSet set) {
		super(context, set);
		init();
	}

	public Panel(Context context, AttributeSet set, int defStyle) {
		super(context, set, defStyle);
		init();
	}

	public void init() {
		currentItem = MenuItemSelected.Place;
		getHolder().addCallback(this);
		thread = new PetriNetsThread(this);
		setFocusable(true);
	}

	public void setSelectedItem(MenuItemSelected selected) {
		if (selected != null)
			currentItem = selected;
	}
	


	/**
	 * Process the MotionEvent.
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		synchronized (getHolder()) {
			 final int action = event.getAction();
			if (action == MotionEvent.ACTION_DOWN) {
				handleDownEvent(event);

			} else if (action == MotionEvent.ACTION_MOVE) {
				handleMoveEvent(event);

			} else if (action == MotionEvent.ACTION_UP) {
				handleUpEvent(event);
			}		
			
			return true;
		}
	}

	private void handleDownEvent(MotionEvent event) {
		// act differently on each menuItemSelected
		if (currentItem == MenuItemSelected.Transition) {
			currentTransition = new Transition(event.getX(), event.getY());
		} else if (currentItem == MenuItemSelected.Place) {
			currentPlace = new Place(event.getX(), event.getY());
		} else if (currentItem == MenuItemSelected.Arc) {
			currentArc = new Arc();
			currentArc.getCoordinates().setCoords((int) event.getX(),
					(int) event.getY(), (int) event.getX(), (int) event.getY());

			// sets the arcs from and to shapes... try to do this in ctor of arc
			for (Transition t : transitions) {
				Rect bounds = t.getBounds();
				if (bounds.contains((int) event.getX(), (int) event.getY())) {
					currentArc.setFromShape(t);
					break;
				}
			}
			if (currentArc.getFromShape() == null) {
				for (Place p : places) {
					Rect bounds = p.getBounds();
					if (bounds.contains((int) event.getX(), (int) event.getY())) {
						currentArc.setFromShape(p);
						break;
					}
				}
			}
		} else if (currentItem == MenuItemSelected.AddToken) {

			for (Place setPlace : places) {
				Rect bounds = setPlace.getBounds();
				if (bounds.contains((int) event.getX(), (int) event.getY())) {
					setPlace.addToken();
					return;
				}
			}
		} else if (currentItem == MenuItemSelected.MinusToken) {

			for (Place place : places) {
				Rect bounds = place.getBounds();
				if (bounds.contains((int) event.getX(), (int) event.getY())) {
					place.removeToken();
					return;
				}
			}
		} else if (currentItem == MenuItemSelected.fire) {
			for (Transition transition : transitions) {
				Rect bounds = transition.getBounds();
				if (bounds.contains((int) event.getX(), (int) event.getY())) {

					transition.fire();
					transition.setColor(transition.canFire());// change this
																// when done
																// testing
					return;
				}
			}
		} else if (currentItem == MenuItemSelected.move) {
			for (Transition t : transitions) {
				Rect bounds = t.getBounds();
				if (bounds.contains((int) event.getX(), (int) event.getY())) {
					currentTransition = t;
					break;
				}
			}

			if (currentTransition == null) {
				for (Place p : places) {
					Rect bounds = p.getBounds();
					if (bounds.contains((int) event.getX(), (int) event.getY())) {
						currentPlace = p;
						break;
					}
				}
			}
		} else if (currentItem == MenuItemSelected.delete) {

			// XXX can not remove an ArrayList entry while iteratoring over that
			// ArrayList, throws concurrent exception
			// so I stored deleted entries and delete them after iteration
			// not most efficient manner, but works

			Transition transitionToDelete = null;
			ArrayList<Arc> arcsToDelete = new ArrayList<Arc>();
			for (Transition transition : transitions) {
				Rect bounds = transition.getBounds();
				if (bounds.contains((int) event.getX(), (int) event.getY())) {

					for (Arc arc : arcs) {
						ShapeDrawable from = arc.getFromShape();
						if (from == transition) {
							arcsToDelete.add(arc);
						}

						ShapeDrawable to = arc.getToShape();

						if (to == transition) {
							arcsToDelete.add(arc);
						}
					}

					transitionToDelete = transition;
					break;
				}
			}

			if (transitionToDelete != null)
				transitions.remove(transitionToDelete);

			// need to remove arcs from transitions when places are deleted

			Place placeToDelete = null;
			for (Place place : places) {
				Rect bounds = place.getBounds();
				if (bounds.contains((int) event.getX(), (int) event.getY())) {
					ArrayList<Transition> outputs = place.getOutputs();// TODO
																		// test,
																		// make
																		// sure
																		// cleaned
																		// up
																		// properly
					ArrayList<Transition> inputs = place.getInputs();

					// needs to update and remove this place from connecting
					// transitions
					for (Transition t : transitions) {
						if (outputs.contains(t)) {
							t.removeInput(place);
						}

						if (inputs.contains(t)) {
							t.removeOutput(place);
						}
					}

					for (Arc arc : arcs) {
						ShapeDrawable from = arc.getFromShape();
						if (from == place) {
							arcsToDelete.add(arc);
						}

						ShapeDrawable to = arc.getToShape();

						if (to == place) {
							arcsToDelete.add(arc);
						}
					}
					placeToDelete = place;
					break;
				}

			}

			if (placeToDelete != null) {
				places.remove(placeToDelete);
			}

			for (Arc deleteArc : arcsToDelete) {
				arcs.remove(deleteArc);// TODO finish deleting here by getting
										// the shapes inputs and outputs
			}

			arcsToDelete = null;// i just want to make sure it gets cleaned up
								// by gc
			
			
			
		}
	}

	private void handleMoveEvent(MotionEvent event) {

		if (currentItem == MenuItemSelected.Transition) {
			currentTransition.setBounds(event.getX(), event.getY());
		} else if (currentItem == MenuItemSelected.Place) {
			currentPlace.setBounds(event.getX(), event.getY());
		} else if (currentItem == MenuItemSelected.Arc) {

			currentArc.getCoordinates().setEndX((int) event.getX());
			currentArc.getCoordinates().setEndY((int) event.getY());
		} else if (currentItem == MenuItemSelected.move) {
			// for(Transition t: transitions){
			if (currentTransition != null) {
				Rect bounds = currentTransition.getBounds();
				if (bounds.contains((int) event.getX(), (int) event.getY())) {
					currentTransition.setBounds(event.getX(), event.getY());
					// currentTransition.get

					for (Arc a : arcs) {
						ShapeDrawable fromShape = a.getFromShape();
						ShapeDrawable toShape = a.getToShape();

						if (fromShape == currentTransition) {
							int startX = a.getCoordinates().getStartX();
							int startY = a.getCoordinates().getStartY();

							if (bounds.contains(startX, startY)) {
								a.getCoordinates()
										.setStartX((int) event.getX());
								a.getCoordinates()
										.setStartY((int) event.getY());
							}
						}

						if (toShape == currentTransition) {

							int endX = a.getCoordinates().getEndX();
							int endY = a.getCoordinates().getEndY();

							if (bounds.contains(endX, endY)) {
								a.getCoordinates().setEndX((int) event.getX());
								a.getCoordinates().setEndY((int) event.getY());
							}
						}
					}
				}
			} else if (currentTransition == null && currentPlace != null) {
				// for(Place p: places){
				Rect bounds = currentPlace.getBounds();
				if (bounds.contains((int) event.getX(), (int) event.getY())) {
					currentPlace.setBounds(event.getX(), event.getY());

					for (Arc a : arcs) {
						ShapeDrawable fromShape = a.getFromShape();
						ShapeDrawable toShape = a.getToShape();

						if (fromShape == currentPlace) {
							int startX = a.getCoordinates().getStartX();
							int startY = a.getCoordinates().getStartY();

							if (bounds.contains(startX, startY)) {
								a.getCoordinates()
										.setStartX((int) event.getX());
								a.getCoordinates()
										.setStartY((int) event.getY());
							}
						}

						if (toShape == currentPlace) {

							int endX = a.getCoordinates().getEndX();
							int endY = a.getCoordinates().getEndY();

							if (bounds.contains(endX, endY)) {
								a.getCoordinates().setEndX((int) event.getX());
								a.getCoordinates().setEndY((int) event.getY());
							}
						}
					}
					// break;
				}
			}

		}

	}

	private void handleUpEvent(MotionEvent event) {

		if (currentItem == MenuItemSelected.Transition) {
			currentTransition.setBounds(event.getX(), event.getY());

			if (checkCollisions(currentTransition))
				transitions.add(currentTransition);
			currentTransition = null;
		} else if (currentItem == MenuItemSelected.Place) {
			currentPlace.setBounds(event.getX(), event.getY());
			if (checkCollisions(currentPlace))
				places.add(currentPlace);
			currentPlace = null;
		} else if (currentItem == MenuItemSelected.Arc) {

			currentArc.getCoordinates().setEndX((int) event.getX());
			currentArc.getCoordinates().setEndY((int) event.getY());

			// add outputs
			for (Transition t : transitions) {
				Rect bounds = t.getBounds();
				if (bounds.contains((int) event.getX(), (int) event.getY())) {
					currentArc.setToShape(t);
					break;
				}
			}
			if (currentArc.getToShape() == null) {
				for (Place p : places) {
					Rect bounds = p.getBounds();
					if (bounds.contains((int) event.getX(), (int) event.getY())) {
						currentArc.setToShape(p);
						break;
					}
				}
			}
			// set Transition and Place inputs and outputs
			if (currentArc.getFromShape() != null
					&& currentArc.getToShape() != null && setInputsAndOutputs())
				arcs.add(currentArc);
			currentArc = null;
		} else if (currentItem == MenuItemSelected.move) {
			currentTransition = null;
			currentPlace = null;
		}

	}

	/**
	 * Sets the Inputs and Outputs from the currentArc to the Transition and
	 * Place
	 */
	private boolean setInputsAndOutputs() {
		ShapeDrawable from = currentArc.getFromShape();
		ShapeDrawable to = currentArc.getToShape();
		if (from instanceof Transition && to instanceof Place) {
			Transition transition = (Transition) from;
			Place place = (Place) to;

			transition.addOutput(place);
			place.addInput(transition);

			return true;
		} else if (from instanceof Place && to instanceof Transition) {
			Transition transition = (Transition) to;
			Place place = (Place) from;

			transition.addInput(place);
			place.addOutput(transition);
			return true;
		}
		return false;
	}

	/**
	 * Returns false when the input is on top of a Place of Transition
	 * 
	 * @param shape
	 * @return
	 */
	private boolean checkCollisions(ShapeDrawable shape) {

		Rect bounds = shape.getBounds();

		for (Transition transition : transitions) {
			if (bounds.intersect(transition.getBounds()))
				return false;
		}

		for (Place place : places) {
			if (bounds.intersect(place.getBounds()))
				return false;
		}

		return true;
	}

	/**
	 * Draw on the SurfaceView. Order:
	 * <ul>
	 * <li>Background image</li>
	 * <li>Items on the panel</li>
	 * <li>Item moved by hand</li>
	 * </ul>
	 */
	@Override
	public void onDraw(Canvas canvas) {
		// draw the background
		if(canvas == null)
			return;
		canvas.drawColor(backgroundColor);

		// draw the normal items
		for (Arc arc : arcs) {
			arc.draw(canvas);
		}
		for (Transition transition : transitions) {
			transition.draw(canvas);
			transition.setColor(transition.canFire());
		}

		for (Place place : places) {
			place.draw(canvas);
		}

		// draw current graphic last..
		if (currentTransition != null)
			currentTransition.draw(canvas);

		if (currentPlace != null)
			currentPlace.draw(canvas);

		if (currentArc != null)
			currentArc.draw(canvas);

		//for zooming and panning
//	       canvas.save();
//	        canvas.translate(mPosX, mPosY);
//	        mIcon.draw(canvas);
//	        canvas.restore();
		
	}

	/**
	 * Called if you change the configuration like open the keypad.
	 */
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	/**
	 * Called on creation of the SurfaceView. Which could be on first start or
	 * relaunch.
	 */
	public void surfaceCreated(SurfaceHolder holder) {
		if (!thread.isAlive()) {
			thread = new PetriNetsThread(this);
		}
		thread.setRunning(true);
		thread.start();
	}

	/**
	 * Called if the SurfaceView should be destroyed. We try to finish the game
	 * loop thread here.
	 */
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// we will try it again and again...
			}
		}
		Log.i("thread", "Thread terminated...");
	}
	


}