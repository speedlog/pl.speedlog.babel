package pl.speedlog.babel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

public class Rekordy extends Activity {
	
	final static String PLIK_REKORDY="babel_rekordy.txt";
	private String wyniki[];
	private TextView rekordy;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rekordy);
        
        //pobranie odwo³ania do miejsca na rekordy
        rekordy=(TextView) findViewById(R.id.rekordy_lista);
        
        try {
			WczytajRekordy();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    private void WczytajRekordy() throws IOException 
    {

        ObslugaPliku pliki=new ObslugaPliku();
        
		//wyniki=pliki.readLines(PLIK_REKORDY);
        wyniki= new String[]{
        		"Marian     1000",
        		"Elfik     1500",
        		"Test      500"
        		};
        
		for (String rekord : wyniki) {
            rekordy.append(rekord+"\n");
        }
		
        
    }
    
    

}
