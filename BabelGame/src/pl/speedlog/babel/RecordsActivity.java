package pl.speedlog.babel;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class RecordsActivity extends Activity {
	
	private TextView records;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.records);
        
        //pobranie odwo³ania do miejsca na rekordy
        records=(TextView) findViewById(R.id.records_list);
        
        RecordsManager rm=new RecordsManager();
        String allrec=rm.GetAllRecords();
        if(allrec.length()==0) records.setText("brak rekordów...");
        else records.setText(allrec);
    }
    
    

}
