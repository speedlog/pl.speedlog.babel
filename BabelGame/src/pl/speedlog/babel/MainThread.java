package pl.speedlog.babel;

import pl.speedlog.babel.Board.Panel;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

	
	private Panel panel;
	private SurfaceHolder surfaceHolder; 
	private boolean running;
	
	public MainThread(SurfaceHolder surfaceHolder, Panel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.panel = gamePanel;
	}
	
	
	/**
	 * Setter a game state
	 * @param running
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	/**
	 * Getter of surface holder
	 * @return
	 */
	public SurfaceHolder getSurfaceHolder() {
	    return surfaceHolder;
	}
	
	@Override
	public void run() {
		
		Canvas c;
	    while(running) {
	        c = null;
	        try {
	            c = surfaceHolder.lockCanvas(null);
	            synchronized (surfaceHolder) {
	                panel.onDraw(c);
	            }
	        }
	        finally {
	            // do this in a finally so that if an exception is thrown
	            // during the above, we don't leave the Surface in an
	            // inconsistent state
	            if (c != null) {
	                surfaceHolder.unlockCanvasAndPost(c);
	            }
	       }
	    }
		
	}

}
