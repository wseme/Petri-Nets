package edu.monmouth;

import android.graphics.Canvas;

/**
 * Thread class to perform the so called "game loop".
 * 
 * @author Walter
 */
class PetriNetsThread extends Thread {
    private Panel _panel;
    private boolean _run = false;
 
    /**
     * Constructor.
     * 
     * @param panel View class on which we trigger the drawing.
     */
    public PetriNetsThread(Panel panel) {
        _panel = panel;
    }
 
    /**
     * @param run Should the game loop keep running? 
     */
    public void setRunning(boolean run) {
        _run = run;
    }
 
    /**
     * @return If the game loop is running or not.
     */
    public boolean isRunning() {
        return _run;
    }
 
    /**
     * Perform the game loop.
     * Order of performing:
     * 1. update physics
     * 2. draw everything
     */
    @Override
    public void run() {
        Canvas c;
        while (_run) {
            c = null;
            try {
                c = _panel.getHolder().lockCanvas(null);
                synchronized (_panel.getHolder()) {
                    _panel.onDraw(c);
                }
            } finally {
                // do this in a finally so that if an exception is thrown
                // during the above, we don't leave the Surface in an
                // inconsistent state
                if (c != null) {
                    _panel.getHolder().unlockCanvasAndPost(c);
                }
            }
        }
    }
}