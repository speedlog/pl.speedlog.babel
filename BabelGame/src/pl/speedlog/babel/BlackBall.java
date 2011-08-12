package pl.speedlog.babel;

import android.graphics.Color;
import pl.speedlog.babel.Board.Panel;

public class BlackBall extends Ball {

	public BlackBall(Panel game_panel, Board board, int x, int y, int speed) {
		super(game_panel, board, Config.POINTS_FOR_BLACK, Color.BLACK, x, y, speed);
		
	}
	

}
