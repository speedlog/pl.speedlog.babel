package pl.speedlog.babel;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
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
	class Panel extends SurfaceView implements SurfaceHolder.Callback, Runnable {
		
		private MainThread mThread;
		private ArrayList<Ball> balls = new ArrayList<Ball>();
		private Board board;
		
		
	    public Panel(Context context) {
			super(context);
			board=(Board)context;
			getHolder().addCallback(this);
			mThread = new MainThread(getHolder(), this);
			setFocusable(true);
			
			Thread watek=new Thread(this);
			watek.start();

		}
	    
	    public boolean onTouch(View v, MotionEvent event) {
	        
            int eventaction = event.getAction(); 

            int X = (int)event.getX(); 
            int Y = (int)event.getY(); 
            
            switch (eventaction ) { 

            case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on a ball

                for (Ball ball : balls) {

                    int x =X;
                    int y =Y;

                    if (x > ball.GetX() && x < ball.GetX()+Config.BALL_RADIUS && y > ball.GetY() && y < ball.GetY()+Config.BALL_RADIUS)
                    ball.onClick(v);
                }

                 break; 
            }
            
            return true;
	    }

	    

	    /**
	     * Update state of objects
	     */
	    public void updateObjects()
	    {
            //update states
            for (Ball ball : balls) {
            	if(ball.GetVisible()) ball.MoveDown();
            	else ball=null;
            }
	    }
	    
	    @Override
        public void onDraw(Canvas canvas) {
            canvas.drawColor(Color.GRAY);
            
            //redraw
            for (Ball ball : balls) {
            	if(ball.GetVisible()) canvas.drawCircle(ball.GetX(), ball.GetY(), ball.GetR(), ball.GetPaint());
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
	    
		@Override
		public void run() {
			
			while(true)
			{
		    	synchronized (mThread.getSurfaceHolder())
		    	{
		
		    		//losujemy szybkoœæ spadania
		    		//int falling_speed=RandomNumber(Config.Get(level, "szybkosc_spadania_min"),
		    		//							   Config.Get(level, "szybkosc_spadania_max"));
		    		int falling_speed=RandomNumber(2,5);
		    		
		    		//losujemy koordynatê X, na której mamy ustawiæ kulkê
		    		//minimalna odleg³oœæ od lewej i prawej krawêdzi to promieñ kulki
		    		//int coordX=RandomNumber(Config.BALL_RADIUS,this.getWidth()-Config.BALL_RADIUS);
		    		int coordX=RandomNumber(Config.BALL_RADIUS,this.getWidth()-Config.BALL_RADIUS);
		    		
		    		//losujemy rodzaj kulki
		    		//czerwona
		    		
			        Ball ball = new Ball(this,board,15, coordX, 0, falling_speed);
			        balls.add(ball);
		    	}
				
				try {
					
					//losujemy czas po którym ma spaœæ kolejna kulka
					
					/*synchronized(this)
					{
						int next_ball_min=Config.Get(level, "kolejna_kulka_min");
						int next_ball_max=Config.Get(level, "kolejna_kulka_max");
						
						int next_ball_time=RandomNumber(next_ball_min,next_ball_max);
						Thread.sleep(next_ball_time);
					}*/
					
					int next_ball_time=RandomNumber(1500,2500);
					Thread.sleep(next_ball_time);
					
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}


}
