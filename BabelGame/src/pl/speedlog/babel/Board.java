package pl.speedlog.babel;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Board extends Activity{
	
	public int level;
	private static int points;
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        setContentView(R.layout.board); 
        
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
		Panel.ThreadStop();
		startActivity(new Intent(Board.this, GameOver.class));
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
	 * Get points 
	 */
	public static int GetPoints()
	{
		return points;
	}
	
	
	/**
	 * Funkcja zlicza kolejn¹ nietrafion¹ kulkê
	 */
	public void AddMissedBall()
	{
		missed_balls++;
		runOnUiThread(new Runnable() {
		     public void run() {
		 		missed_label.setText(new Integer(Config.GAME_OVER_MISS-missed_balls).toString());
		    }
		});
		
		if(missed_balls>=Config.GAME_OVER_MISS) {
			GameOver();
		}
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
			

}
