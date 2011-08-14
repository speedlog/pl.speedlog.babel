package pl.speedlog.babel;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameOver extends Activity {
	
	private EditText nick_box;
	private TextView points_label;
	private int points;
	private RecordsManager rm;
	
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        //ca³y ekran, bez paska tytu³owego
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        setContentView(R.layout.game_over); 
        

        //decydujemy czy pokazaæ wpis dla nowego rekordu
        rm=new RecordsManager();
        
    	points=Board.GetPoints();
        if(points>=rm.TopRecord()) {
        	LinearLayout nrl=(LinearLayout) findViewById(R.id.new_record_layout);
        	nrl.setVisibility(LinearLayout.VISIBLE);
        	
        	nick_box=(EditText) findViewById(R.id.nick_editbox);
        	points_label=(TextView) findViewById(R.id.collect_points);
        	points_label.setText(new Integer(points).toString());

        }
    }
	
	/**
	 * klik przycisku "Nowa gra"
	 */
	public void clickNewGame(View view)
	{
		startActivity(new Intent(GameOver.this, Board.class));
	}
	
	/**
	 * klik koniec gry
	 */
	public void clickFinish(View view)
	{
		finish();
	}
	
	/**
	 * klik Zapisz rekord
	 */
	public void clickSaveButton(View view)
	{
		if(nick_box.length()>0)
		{
		rm.AddNewRecord(new Record(nick_box.getText().toString(),points));
		startActivity(new Intent(GameOver.this, RecordsActivity.class));
		}
		else Toast.makeText(this, "Wpisz Twój nick!", Toast.LENGTH_SHORT).show();
	}

}
