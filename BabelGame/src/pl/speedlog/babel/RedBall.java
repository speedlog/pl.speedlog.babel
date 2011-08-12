package pl.speedlog.babel;

import android.graphics.Color;
import pl.speedlog.babel.Board.Panel;

public class RedBall extends Ball {

	public RedBall(Panel game_panel, Board board, int x, int y, int speed) {
		super(game_panel, board, Config.POINTS_FOR_RED, Color.RED, x, y, speed);
		
	}
	
	/**
	 * Ball falled down - out of screen
	 */
	@Override
	public void OutOfScreen()
	{
		super.GetBoard().AddMissedBall();
		super.OutOfScreen();
	}

}
