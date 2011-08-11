package pl.speedlog.babel;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Records extends Activity {
	
	final static String FILE_RECORDS="babel_rekordy.txt";
	private String results[];
	private TextView records;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.records);
        
        //pobranie odwo³ania do miejsca na rekordy
        records=(TextView) findViewById(R.id.records_list);
        
        try {
			LoadRecords();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    private void LoadRecords() throws IOException 
    {

        //FilesHandling fh=new FilesHandling();
        
		//results=fh.readLines(FILE_RECORDS);
        results= new String[]{
        		"Marian     1000",
        		"Elfik     1500",
        		"Test      500"
        		};
        
		for (String record : results) {
            records.append(record+"\n");
        }
		
        
    }
    
    

}
