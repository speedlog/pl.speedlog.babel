package pl.speedlog.babel;

import pl.speedlog.babel.Board.Panel;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class Ball implements OnClickListener  {
	
	private Panel panel;
	private Board board;
	private int x;
	private int y;
	private int r=Config.BALL_RADIUS;
	private boolean visible=true;
	private int FallingSpeed;
	


	private final int points;
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

	public Ball(Panel game_panel, Board board, int points, int x, int y, int speed) {
	
		this.panel=game_panel;
		this.board=board;
		this.points=points;
        this.x = x;
        this.y = y;
        this.FallingSpeed=speed;
        
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
		y+=FallingSpeed;
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

	@Override
	public void onClick(View arg0) {
		
		SetVisible(false);
		board.AddPoints(points);
	}
	

}
