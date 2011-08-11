package pl.speedlog.babel;

import java.util.HashMap;

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
	
	public static HashMap<String,Integer>[] list;
	
	final static int GAME_OVER_MISS=10; 	// liczba kulek, które gdy spadn¹ to nast¹pi GAME OVER
	final static int POINTS_FOR_RED=10;		// punkty po klikniêciu czerwonej kulki
	final static int POINTS_FOR_BLACK=-15;	// punkty po klikniêciu czarnej kulki
	final static int BALL_RADIUS=5;
	
	static
	{
		//konfiguracja LVL1
		list[1]= new HashMap<String,Integer>();
		list[1].put("szybkosc_spadania_min", 2);
		list[1].put("szybkosc_spadania_max", 5);
		list[1].put("kolejna_kulka_min", 2000);
		list[1].put("kolejna_kulka_max", 2500);
		list[1].put("kolejny_poziom", 250);
		list[1].put("procent_czerwonych", 100);
		
		//konfiguracja LVL2
		list[2]= new HashMap<String,Integer>();
		list[2].put("szybkosc_spadania_min", 4);
		list[2].put("szybkosc_spadania_max", 6);
		list[2].put("kolejna_kulka_min", 1500);
		list[2].put("kolejna_kulka_max", 2000);
		list[2].put("kolejny_poziom", 600);
		list[2].put("procent_czerwonych", 80);
		
		//konfiguracja LVL3
		list[3]= new HashMap<String,Integer>();
		list[3].put("szybkosc_spadania_min", 6);
		list[3].put("szybkosc_spadania_max", 8);
		list[3].put("kolejna_kulka_min", 900);
		list[3].put("kolejna_kulka_max", 1500);
		list[3].put("kolejny_poziom", 1100);
		list[3].put("procent_czerwonych", 65);
		
		//konfiguracja LVL4
		list[4]= new HashMap<String,Integer>();
		list[4].put("szybkosc_spadania_min", 8);
		list[4].put("szybkosc_spadania_max", 10);
		list[4].put("kolejna_kulka_min", 600);
		list[4].put("kolejna_kulka_max", 1200);
		list[4].put("kolejny_poziom", 0);
		list[4].put("procent_czerwonych", 50);

	 }
	
	/**
	 * Zwraca wartoœæ konfiguracji wg. klucza
	 * @param lvl Numer poziomu
	 * @param key Nazwa klucza
	 * @return Wartoœæ pola konfiguracji
	 */
	public synchronized static int Get(int lvl, String key)
	{
		return list[lvl].get(key);
	}
	

}
