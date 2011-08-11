package pl.speedlog.babel;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Board extends Activity{
	
	private boolean game_running;
	private int level;
	private int points;
	private int missed_balls;
	
	private TextView lvl_label;
	private TextView points_label;
	private TextView missed_label;
	

	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        //ca³y ekran, bez paska tytu³owego
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        //setContentView(R.layout.board); 
        //this.addContentView(new Panel(this),null);
        setContentView(new Panel(this));
        
		lvl_label=(TextView) findViewById(R.id.lvl_label);
		points_label=(TextView) findViewById(R.id.points_label);
		missed_label=(TextView) findViewById(R.id.missed_label);
        
		game_running=true;
		level=1;
		points=0;
		missed_balls=0;
    }
	
	/**
	 * Funkcja ustawiaj¹ca flagê oznaczaj¹c¹ koniec gry
	 */
	public void GameOver()
	{
		game_running=false;
	}
	
	/**
	 * Funkcja ustawia numer poziomu (level)
	 * @param lvl Numer poziomu
	 */
	public void SetLevel(int lvl)
	{
		this.level=lvl;
		lvl_label.setText(new Integer(lvl).toString());
	}
	
	/**
	 * Funkcja dodaje liczbê punktów do ³¹cznego wyniku
	 * @param points liczba dodawanych punktów
	 */
	public void AddPoints(int points)
	{
		this.points+=points;
		points_label.setText(new Integer(this.points).toString());
	}
	
	
	/**
	 * Funkcja zlicza kolejn¹ nietrafion¹ kulkê
	 */
	public void AddMissedBall()
	{
		missed_balls++;
		missed_label.setText(new Integer(Config.GAME_OVER_MISS-missed_balls).toString());
		
		if(missed_balls>=Config.GAME_OVER_MISS) GameOver();
	}
	
	
	/**
	 * Funkcja losuje liczê w danym zakresie
	 * @param min liczba min.
	 * @param max liczba max.
	 * @return
	 */
	public int RandomNumber(int min, int max)
	{
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	
	
	/**
	 * Klasa powierzchni, na której bêdziemy rysowaæ obiekty
	 * @author Mariusz
	 */
	class Panel extends SurfaceView implements SurfaceHolder.Callback {
		
		private MainThread mThread;
		private ArrayList<Ball> balls = new ArrayList<Ball>();

		
	    public Panel(Context context) {
			super(context);
			getHolder().addCallback(this);
			mThread = new MainThread(getHolder(), this);
			setFocusable(true);

		}
	    
	    public boolean onTouchEvent(MotionEvent event) {
	    	
	    	synchronized (mThread.getSurfaceHolder())
	    	{
	        Ball ball = new Ball(this,15,(int) event.getX() - (20 / 2), (int) event.getY() - (20 / 2));
	        return balls.add(ball);
	    	}
	    }

	    
	    @Override
        public void onDraw(Canvas canvas) {
            canvas.drawColor(Color.GRAY);
            
            //update states
            for (Ball ball : balls) {
                ball.MoveDown();
            }
            
            //redraw
            for (Ball ball : balls) {
                canvas.drawCircle(ball.GetX(), ball.GetY(), ball.GetR(), ball.GetPaint());
            }

        }

		@Override
	    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	        // TODO Auto-generated method stub
	    }
	 
	    @Override
	    public void surfaceCreated(SurfaceHolder holder) {
	    	mThread.setRunning(true);
	    	mThread.start();
	    }
	 
	    @Override
	    public void surfaceDestroyed(SurfaceHolder holder) {
	    	
	    	//z aplikacji LunarLander
	    	boolean retry = true;
	    	mThread.setRunning(false);
	        while (retry) {
	            try {
	            	mThread.join();
	                retry = false;
	            } catch (InterruptedException e) {
	                // we will try it again and again...
	            }
	        }

	    }
	}
	

}
