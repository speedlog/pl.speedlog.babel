package pl.speedlog.babel;

import java.util.ArrayList;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;

/**
 * Klasa powierzchni, na której bêdziemy rysowaæ obiekty
 * @author Mariusz
 */
 public class Panel extends SurfaceView implements SurfaceHolder.Callback, Runnable {
	
	MainThread mThread;
	private ArrayList<Ball> balls = new ArrayList<Ball>();
	private Board board;
	public static boolean thread_running;
	public Thread NewBallTheard=new Thread(this);
	
    
    public Panel(Context context) {
    	  super(context);
    	  // TODO Auto-generated constructor stub
    	  init();
		  board=(Board)context;
    }

    public Panel(Context context, AttributeSet attrs) {
    	  super(context, attrs);
    	  // TODO Auto-generated constructor stub
    	  init();
		  board=(Board)context;
    }

    public Panel(Context context, AttributeSet attrs, int defStyle) {
    	  super(context, attrs, defStyle);
    	  // TODO Auto-generated constructor stub
    	  init();
		  board=(Board)context;
    }
    
    public void init() {

    	getHolder().addCallback(this);
    	
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
        	//Log.d("TOUCH","x="+X+" y="+Y);
        	
            for (Ball ball : balls) {

                if (ball.CheckShoot(X, Y))
                {
                ball.Shooted();
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

        	}
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
    
    
	/**
	 * Stop thread that insert new balls
	 */
	public static void ThreadStop()
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
		
		//pobieramy zmienne dla konfiguracji danego lvlu
		int ball_speed_min=Config.Get(board.level, "szybkosc_spadania_min");
		int ball_speed_max=Config.Get(board.level, "szybkosc_spadania_max");
		int screen_width=this.getWidth();
		int chance_for_red=Config.Get(board.level, "procent_czerwonych");
		
		while(thread_running)
		{
	    	synchronized (mThread.getSurfaceHolder())
	    	{
	
	    		//losujemy szybkoœæ spadania
	    		int falling_speed=board.RandomNumber(ball_speed_min,ball_speed_max);
	    		
	    		//losujemy koordynatê X, na której mamy ustawiæ kulkê
	    		//minimalna odleg³oœæ od lewej i prawej krawêdzi to promieñ kulki
	    		int coordX=board.RandomNumber(Config.BALL_RADIUS,screen_width-Config.BALL_RADIUS);
	    		
	    		//losujemy rodzaj kulki
	    		int if_red=board.RandomNumber(1,100);
	    		
	    			//tworzymy czerwona kulkê
	    			if(if_red<=chance_for_red)
	    			{
			    		RedBall ball = new RedBall(this,board,coordX, 0, falling_speed);
				        balls.add(ball);
	    			}
	    			
	    			//tworzymy czarn¹ kulkê
	    			else
	    			{
	    				BlackBall ball = new BlackBall(this,board,coordX, 0, falling_speed);
				        balls.add(ball);
	    			}
	    	}
			
			try {
				
				//losujemy czas po którym ma spaœæ kolejna kulka
				int next_ball_min=Config.Get(board.level, "kolejna_kulka_min");
				int next_ball_max=Config.Get(board.level, "kolejna_kulka_max");
						
				int next_ball_time=board.RandomNumber(next_ball_min,next_ball_max);
				Thread.sleep(next_ball_time);
				
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
