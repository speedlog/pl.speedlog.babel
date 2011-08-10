package pl.speedlog.babel;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class BabelGameActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    //klik "O autorze"
    public void auhorsButtonClick(View view) {
    	Toast.makeText(this, "Mariusz Wyszomierski - 2011", Toast.LENGTH_SHORT).show();
	}
    
    //klik "Koniec gry"
    public void theendButtonClick(View view)
    {
    	finish();
    }

}