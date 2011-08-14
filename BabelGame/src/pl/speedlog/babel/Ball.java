package pl.speedlog.babel;

import pl.speedlog.babel.Panel;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public abstract class Ball  {
	
	public Panel panel;
	private Board board;
	private final int points;
	private final int x;	//wspó³rzêdna X œrodka kulki
	private int y; 			//wspó³rzêdna Y œrodka kulki
	private final int r;
	private boolean visible=true;
	private int FallingSpeed;



	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

	public Ball(Panel game_panel, Board board, int points, int color, int x, int y, int speed) {
	
		this.panel=game_panel;
		this.board=board;
		this.points=points;
        this.x = x;
        this.y = y;
        this.r=Config.BALL_RADIUS;
        this.FallingSpeed=speed;
        
        paint.setColor(color);
	}
	
	/**
	 * Getter X coord
	 * @return
	 */
	public int GetX()
	{
		return x;
	}
	
	/**
	 * Getter Board
	 */
	public Board GetBoard()
	{
		return board;
	}
	
	
	/**
	 * Getter Y coord
	 * @return
	 */
	public int GetY()
	{
		return y;
	}
	
	/**
	 * Getter R 
	 * @return
	 */
	public int GetR()
	{
		return r;
	}
	
	/**
	 * Getter Paint
	 * @return
	 */
	public Paint GetPaint()
	{
		return paint;
	}
	
	
	/**
	 * Ball falled down - out of screen
	 */
	public void OutOfScreen()
	{
		SetVisible(false);
	}
	
	/**
	 * Move ball down
	 */
	public void MoveDown()
	{
		if(GetVisible())
		{
		y+=FallingSpeed;
		
		//sprawdzamy czy kulka wysz³a po za ekran
		if(y-GetR()>panel.getHeight()) OutOfScreen();
		}
	}
	
	/**
	 * Getter visible state
	 */
	public boolean GetVisible()
	{
		return this.visible;
	}
	
	/**
	 * Setter visible state
	 * @param visible
	 * @return
	 */
	public void SetVisible(boolean visible)
	{
		this.visible=visible;
	}

	/**
	 * Funkcja odpowiadaj¹ca akcji po trafieniu kulki
	 */
	public void Shooted() {
		
		SetVisible(false);
		board.AddPoints(points);
	}
	
	/**
	 * Funkcja sprawdza czy wspó³rzêdne nacisniêtego ekranu odpowiadaja
	 * wspó³rzêdnym kulki (czy trafiliœmy w kólkê :) )
	 * @param screen_x
	 * @param y
	 */
	public boolean CheckShoot(int screen_x, int screen_y)
	{
		if(!GetVisible()) return false;
		//Log.d("CHECK_X",screen_x+ " >= " + (GetX()-GetR()) + " && " + screen_x + " <= " + (GetX()+GetR()));
		//Log.d("CHECK_Y",screen_y+ " >= " + (GetY()-GetR()) + " && " + screen_y + " <= " + (GetY()+GetR()));
		
		if(screen_x>=GetX()-GetR() && screen_x <= GetX()+GetR()
		&&  screen_y>=GetY()-GetR() &&  screen_y <= GetY()+GetR()) return true;
		else return false;
	}
	

}
