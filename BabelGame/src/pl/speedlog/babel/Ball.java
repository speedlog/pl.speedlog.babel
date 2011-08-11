package pl.speedlog.babel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class Ball extends View implements Runnable {
	
	private Board board;
	private final int points;
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

	public Ball(Context context,Board board, int points) {
		super(context);
		
		this.board=board;
		this.points=points;
	}
	
	 @Override
	 protected void onDraw(Canvas canvas) {
	     super.onDraw(canvas);
	     //canvas.drawCircle(x, y, r, paint);
	 }
	
	/**
	 * Przeci¹¿ona funkcja nacisniêcia na kulkê
	 */
	public boolean onTouchEvent(MotionEvent event)
	{
		//je¿eli naciœniêto
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{
			board.AddPoints(points);
		}
		
		return super.onTouchEvent(event);
	}


	/**
	 * W¹tek - opadanie kulki
	 */
	public void run() {
		
		
	}
	
	

}
