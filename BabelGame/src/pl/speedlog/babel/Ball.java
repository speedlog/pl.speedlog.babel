package pl.speedlog.babel;

import pl.speedlog.babel.Board.Panel;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class Ball  {
	
	private Panel panel;
	private int x;
	private int y;
	private int r=10;


	private final int points;
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

	public Ball(Panel game_panel, int points, int x, int y) {
	
		this.panel=(Panel) game_panel;
		this.points=points;
        this.x = x;
        this.y = y;
        
        paint.setColor(0xFFFF0000);
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
	 * Move ball down
	 */
	public void MoveDown()
	{
		y++;
	}
	
	/**
	 * Przeci¹¿ona funkcja nacisniêcia na kulkê
	 */
	public boolean onTouchEvent(MotionEvent event)
	{
		//je¿eli naciœniêto
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{
			//board.AddPoints(points);
		}
		
		return true;
	}	

}
