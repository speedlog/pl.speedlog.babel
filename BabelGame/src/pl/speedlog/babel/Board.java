package pl.speedlog.babel;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Board extends Activity  { //implements Runnable
	
	private boolean game_remain;
	private int level;
	private int points;
	private int missed_balls;
	private TextView lvl_label;
	private TextView points_label;
	private TextView missed_label;
	

	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board);
        
		lvl_label=(TextView) findViewById(R.id.lvl_label);
		points_label=(TextView) findViewById(R.id.points_label);
		missed_label=(TextView) findViewById(R.id.missed_label);
        
		game_remain=true;
		level=1;
		points=0;
		missed_balls=0;
    }
	
	/**
	 * Funkcja ustawiaj�ca flag� oznaczaj�c� koniec gry
	 */
	public void GameOver()
	{
		game_remain=false;
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
	 * Funkcja dodaje liczb� punkt�w do ��cznego wyniku
	 * @param points liczba dodawanych punkt�w
	 */
	public void AddPoints(int points)
	{
		this.points+=points;
		points_label.setText(new Integer(this.points).toString());
	}
	
	
	/**
	 * Funkcja zlicza kolejn� nietrafion� kulk�
	 */
	public void AddMissedBall()
	{
		missed_balls++;
		missed_label.setText(new Integer(Config.GAME_OVER_MISS-missed_balls).toString());
		
		if(missed_balls>=Config.GAME_OVER_MISS) GameOver();
	}
	
	
	/**
	 * Funkcja losuje licz� w danym zakresie
	 * @param min liczba min.
	 * @param max liczba max.
	 * @return
	 */
	public int RandomNumber(int min, int max)
	{
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	
	
	/**
	 * W�tek wstawiaj�cy nowe kulki
	 
	public void run() {
		
		//niesko�czona p�tla
		while(game_remain)
		{
			
			//losujemy koordynat� X
			int coordX=RandomNumber(0,100);	
					
			
		}
	}*/
	

}
