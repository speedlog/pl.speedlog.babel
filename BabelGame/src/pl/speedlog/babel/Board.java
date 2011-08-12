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
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Board extends Activity{
	
	private boolean thread_running;
	private int level;
	private int points;
	private int missed_balls;
	
	private TextView lvl_label;
	private TextView points_label;
	private TextView missed_label;
	Thread NewBallTheard;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        //ca³y ekran, bez paska tytu³owego
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        //FrameLayout main=(FrameLayout) findViewById(R.id.MainLayout);
        //main.addView(new Panel(this));
        
        //setContentView(R.layout.board); 
        
        setContentView(new Panel(this));
        
		lvl_label=(TextView) findViewById(R.id.lvl_label);
		points_label=(TextView) findViewById(R.id.points_label);
		missed_label=(TextView) findViewById(R.id.missed_label);
        
		level=1;
		points=0;
		missed_balls=0;

		//³adujemy konfiguracjê
		Config.Config();
    }
	
	
	/**
	 * Funkcja ustawiaj¹ca flagê oznaczaj¹c¹ koniec gry
	 */
	public void GameOver()
	{
		thread_running=false;
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
		Log.d("BOARD.class","add missed");
		missed_balls++;
		//missed_label.setText(new Integer(Config.GAME_OVER_MISS-missed_balls).toString());
		
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
				Thread NewBallTheard=new Thread(this);
				
			    public Panel(Context context) {
			    	
			    	super(context);
			    	getHolder().addCallback(this);
			    	
					board=(Board)context;
					mThread = new MainThread(getHolder(), this);
					setFocusable(true);
					setClickable(true);
					ThreadStart();
		
				}
			    
			    
			    synchronized public boolean onTouch(MotionEvent event) {
			        
		            int eventaction = event.getAction(); 
		
		
		            switch (eventaction ) { 
		
		            case MotionEvent.ACTION_DOWN: 
		
		                int X = (int)event.getX(); 
		                int Y = (int)event.getY(); 
		
		            	Log.d("TOUCH","x="+X+" y="+Y);
		            	
		                for (Ball ball : balls) {
		
		                    if (ball.CheckShoot(X, Y))
		                    {
		                    ball.Shooted();
		                    Log.d("SHOOTED","w kulke");	
		                    }
		                }
		                 break; 
		            }        
		
		            
		            return true;
			    }
		
			    
			    @Override
			    public boolean dispatchTouchEvent ( MotionEvent ev )
			    {
			        try
			        {
			            Thread.sleep( 32 );
			        }
			        catch ( InterruptedException e )
			        {           
			            e.printStackTrace();
			        }
			        synchronized ( ev )
			        {
			        	onTouch(ev);
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
		            	if(ball!=null)
		            	{
		            	if(ball.GetVisible()) ball.MoveDown();
		            	else ball=null;
		            	}
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
			    
			    
				/**
				 * Stop thread that insert new balls
				 */
				public void ThreadStop()
				{
					thread_running=false;
				}
				
				/**
				 * Start thread that insert new balls
				 */
				public void ThreadStart()
				{
					thread_running=true;
					NewBallTheard.start();
				}
				
			    
				@Override
				public void run() {
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					while(thread_running)
					{
				    	synchronized (mThread.getSurfaceHolder())
				    	{
				
				    		//losujemy szybkoœæ spadania
				    		int falling_speed=RandomNumber(Config.Get(level, "szybkosc_spadania_min"),
				    									   Config.Get(level, "szybkosc_spadania_max"));
				    		
				    		//losujemy koordynatê X, na której mamy ustawiæ kulkê
				    		//minimalna odleg³oœæ od lewej i prawej krawêdzi to promieñ kulki
				    		int coordX=RandomNumber(Config.BALL_RADIUS,this.getWidth()-Config.BALL_RADIUS);
				    		Log.d("COORD_X","wylosowano wspolrzedna x="+coordX);
				    		
				    		//losujemy rodzaj kulki
				    		//czerwona
				    		
				    		RedBall ball = new RedBall(this,board,coordX, 0, falling_speed);
					        balls.add(ball);
				    	}
						
						try {
							
							//losujemy czas po którym ma spaœæ kolejna kulka
							int next_ball_min=Config.Get(level, "kolejna_kulka_min");
							int next_ball_max=Config.Get(level, "kolejna_kulka_max");
									
							int next_ball_time=RandomNumber(next_ball_min,next_ball_max);
							Thread.sleep(next_ball_time);
							
							
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
				
			}


}
