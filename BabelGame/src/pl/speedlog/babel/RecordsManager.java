package pl.speedlog.babel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import android.util.Log;

public class RecordsManager {
	
	public static ArrayList<Record> record_list=new ArrayList<Record>();
	final static int MAX_RECORDS=10;
	final static String FILE_RECORDS="babel_rekordy.txt";

	public RecordsManager() {

		try {
			if(record_list.isEmpty()) LoadRecords();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Dodaje nowy rekord do listy
	 * @param rec
	 */
	public void AddNewRecord(Record rec)
	{
		record_list.add(rec);
		Log.d("NEW_RECORD_ADD","dodajemy nowy rekord "+rec.toString());
    	//najpierw sortujemy rekordy
    	SortRecords();
    	
    	try {
			SaveRecords();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Funkcja sortuje rekordy od najwy¿szego do najni¿szego
	 */
	public void SortRecords()
	{
		Collections.sort(record_list,Collections.reverseOrder());
	}
	
	/**
	 * Funkcja pobiera 10 najwu¿szych rekordów
	 * @return
	 */
    public String GetAllRecords()
    {

    	String toFile="";
    	int counter=1;
    	Iterator<Record> it=record_list.iterator();
    	
    	while(it.hasNext())
    	{
    		if(counter==MAX_RECORDS) break;
    		
    		toFile+=it.next().toString();
    		counter++;
    	}
    	
    	return toFile;
        
    }
    
    /**
     * Funkcja ³aduje rekordy z pliku
     * @throws IOException 
     * @throws NumberFormatException 
     */
    private void LoadRecords() throws NumberFormatException, IOException
    {
    	File plik=new File(FILE_RECORDS);
    	if(!plik.exists()) return;
    	
		FileReader fileReader = new FileReader(plik);
	    BufferedReader bufferedReader = new BufferedReader(fileReader);

		    String line = null;
		    while ((line = bufferedReader.readLine()) != null)
		    {
		    	String data[]=line.split("\t");
		    	Record new_record=new Record(data[0],new Integer(data[1]));
		    	record_list.add(new_record);
		    }
	    bufferedReader.close();
	    
    	//najpierw sortujemy rekordy
    	SortRecords();

	}
    
    /**
     * Funkcja zapisuje do pliku 10 najwy¿szych rekordów
     * @throws IOException 
     */
    public void SaveRecords() throws IOException
    {
        FileOutputStream fos = new FileOutputStream(FILE_RECORDS, false);
        fos.write(GetAllRecords().getBytes());
        fos.close();

    }
    
    /**
     * Return top points in list records
     * @return
     */
    public int TopRecord()
    {
    	if(record_list.isEmpty()) return 0;
    	else
    	{
    	Record top_rec=(Record)record_list.iterator().next();
    	return top_rec.GetPoints();
    	}
    }
}
