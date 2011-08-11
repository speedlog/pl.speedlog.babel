package pl.speedlog.babel;

import java.util.HashMap;

/**
 * Klucze konfiguracji:
 * opoznienie_min Minimalna liczba ms. po kt�rej kulka przesunie si� w d� (szybko�� kulki)
 * opoznienie_mas Maksymalna liczba_ms po kt�rej kulka przesunie si� w d� (szybko�� kulki)
 * kolejna_kulka_min Minimalna liczba ms po kt�rej pojawi si� kolejna kulka
 * kolejna_kulka_max Maksymalna liczba ms po kt�rej pojawi si� kolejna kulka
 * kolejny_poziom Pr�g punktowy, po kt�rym nast�puje kolejny poziom (0 <=> niesko�czono��)
 * procent_czerwonych Procent czerwonych kulek (1-100, 50 <=> 50% czerwonych, 50% czarnych)
 */
public class Config {
	
	public static HashMap<String,Integer>[] list;
	
	final static int GAME_OVER_MISS=10; 	// liczba kulek, kt�re gdy spadn� to nast�pi GAME OVER
	final static int POINTS_FOR_RED=10;		// punkty po klikni�ciu czerwonej kulki
	final static int POINTS_FOR_BLACK=-15;	// punkty po klikni�ciu czarnej kulki
	
	static
	{
		//konfiguracja LVL1
		list[1]= new HashMap<String,Integer>();
		list[1].put("opoznienie_min", 100);
		list[1].put("opoznienie_max", 150);
		list[1].put("kolejna_kulka_min", 2000);
		list[1].put("kolejna_kulka_max", 2500);
		list[1].put("kolejny_poziom", 250);
		list[1].put("procent_czerwonych", 100);
		
		//konfiguracja LVL2
		list[2]= new HashMap<String,Integer>();
		list[2].put("opoznienie_min", 80);
		list[2].put("opoznienie_max", 120);
		list[2].put("kolejna_kulka_min", 1500);
		list[2].put("kolejna_kulka_max", 2000);
		list[2].put("kolejny_poziom", 600);
		list[2].put("procent_czerwonych", 80);
		
		//konfiguracja LVL3
		list[3]= new HashMap<String,Integer>();
		list[3].put("opoznienie_min", 60);
		list[3].put("opoznienie_max", 90);
		list[3].put("kolejna_kulka_min", 900);
		list[3].put("kolejna_kulka_max", 1500);
		list[3].put("kolejny_poziom", 1100);
		list[3].put("procent_czerwonych", 65);
		
		//konfiguracja LVL4
		list[4]= new HashMap<String,Integer>();
		list[4].put("opoznienie_min", 40);
		list[4].put("opoznienie_max", 70);
		list[4].put("kolejna_kulka_min", 600);
		list[4].put("kolejna_kulka_max", 1200);
		list[4].put("kolejny_poziom", 0);
		list[4].put("procent_czerwonych", 50);

	 }
	
	/**
	 * Zwraca warto�� konfiguracji wg. klucza
	 * @param lvl Numer poziomu
	 * @param key Nazwa klucza
	 * @return Warto�� pola konfiguracji
	 */
	static int Get(int lvl, String key)
	{
		return list[lvl].get(key);
	}
	

}
