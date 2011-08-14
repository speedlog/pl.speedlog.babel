package pl.speedlog.babel;

import java.util.Hashtable;

import android.util.Log;

/**
 * Klucze konfiguracji:
 * szybkosc_spadania_min Minimalna szybkoœæ spadania
 * szybkosc_spadania_max Maksymalna szybkoœæ spadania
 * kolejna_kulka_min Minimalna liczba ms po której pojawi siê kolejna kulka
 * kolejna_kulka_max Maksymalna liczba ms po której pojawi siê kolejna kulka
 * kolejny_poziom Próg punktowy, po którym nastêpuje kolejny poziom (0 <=> nieskoñczonoœæ)
 * procent_czerwonych Procent czerwonych kulek (1-100, 50 <=> 50% czerwonych, 50% czarnych)
 */
public class Config {
	
	private static Hashtable<Integer, Hashtable> list=new Hashtable<Integer, Hashtable>();
	
	final static int GAME_OVER_MISS=10; 	// liczba kulek, które gdy spadn¹ to nast¹pi GAME OVER
	final static int POINTS_FOR_RED=10;		// punkty po klikniêciu czerwonej kulki
	final static int POINTS_FOR_BLACK=-15;	// punkty po klikniêciu czarnej kulki
	final static int BALL_RADIUS=15;
	
	static void Config()
	{
		//konfiguracja LVL1
		Hashtable<String,Integer> newLvl=new Hashtable<String,Integer>();
		newLvl.put("szybkosc_spadania_min", 1);
		newLvl.put("szybkosc_spadania_max", 2);
		newLvl.put("kolejna_kulka_min", 2000);
		newLvl.put("kolejna_kulka_max", 2500);
		newLvl.put("kolejny_poziom", 200);
		newLvl.put("procent_czerwonych", 100);
		list.put(1, newLvl);
		
		//konfiguracja LVL2
		newLvl=new Hashtable<String,Integer>();
		newLvl.put("szybkosc_spadania_min", 1);
		newLvl.put("szybkosc_spadania_max", 4);
		newLvl.put("kolejna_kulka_min", 1500);
		newLvl.put("kolejna_kulka_max", 2000);
		newLvl.put("kolejny_poziom", 600);
		newLvl.put("procent_czerwonych", 80);
		list.put(2, newLvl);
		
		//konfiguracja LVL3
		newLvl=new Hashtable<String,Integer>();
		newLvl.put("szybkosc_spadania_min", 2);
		newLvl.put("szybkosc_spadania_max", 5);
		newLvl.put("kolejna_kulka_min", 900);
		newLvl.put("kolejna_kulka_max", 1500);
		newLvl.put("kolejny_poziom", 1100);
		newLvl.put("procent_czerwonych", 65);
		list.put(3, newLvl);
		
		//konfiguracja LVL4
		newLvl=new Hashtable<String,Integer>();
		newLvl.put("szybkosc_spadania_min", 3);
		newLvl.put("szybkosc_spadania_max", 6);
		newLvl.put("kolejna_kulka_min", 600);
		newLvl.put("kolejna_kulka_max", 1200);
		newLvl.put("kolejny_poziom", 0);
		newLvl.put("procent_czerwonych", 50);
		list.put(4, newLvl);

	 }
	
	/**
	 * Zwraca wartoœæ konfiguracji wg. klucza
	 * @param lvl Numer poziomu
	 * @param key Nazwa klucza
	 * @return Wartoœæ pola konfiguracji
	 */
	public synchronized static int Get(int lvl, String key)
	{
		return (Integer)(list.get(lvl).get(key));
	}
	

}
