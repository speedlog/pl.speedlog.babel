package pl.speedlog.babel;

import pl.speedlog.babel.Panel;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

	// desired fps
	private final static int 	MAX_FPS = 50;
	// maximum number of frames to be skipped
	private final static int	MAX_FRAME_SKIPS = 5;
	// the frame period
	private final static int	FRAME_PERIOD = 1000 / MAX_FPS;	

	
	
	private Panel panel;
	private SurfaceHolder surfaceHolder; 
	
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
		Panel.thread_running = running;
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
		long beginTime;		// the time when the cycle begun
		long timeDiff;		// the time it took for the cycle to execute
		int sleepTime;		// ms to sleep (<0 if we're behind)
		int framesSkipped;	// number of frames being skipped 
		sleepTime = 0;

		
	    while(Panel.thread_running) {
	        c = null;
	        try {
	            c = surfaceHolder.lockCanvas(null);
	            
	            synchronized (surfaceHolder) {
	            	
	            	beginTime = System.currentTimeMillis();
					framesSkipped = 0;	// resetting the frames skipped
					
					panel.updateObjects(); // update game state
					panel.onDraw(c); //render
					
					// calculate how long did the cycle take
					timeDiff = System.currentTimeMillis() - beginTime;
					// calculate sleep time
					sleepTime = (int)(FRAME_PERIOD - timeDiff);

					if (sleepTime > 0) {
						// if sleepTime > 0 we're OK
						try {
							// send the thread to sleep for a short period
							// very useful for battery saving
							Thread.sleep(sleepTime);
						} catch (InterruptedException e) {}
					}

					while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
						// we need to catch up
						// update without rendering
						panel.updateObjects();
						// add frame period to check if in next frame
						sleepTime += FRAME_PERIOD;
						framesSkipped++;
					}
	            	
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
