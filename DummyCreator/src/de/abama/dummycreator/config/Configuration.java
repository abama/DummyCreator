package de.abama.dummycreator.config;

import java.text.NumberFormat;
import java.util.Locale;

public class Configuration {
	
	//public static String articleListPath = "file://abamasrv/Marketing/Artikel/Listen/Katalog/";
	public static String articleListPath = "file:/Volumes/Marketing/Artikel/Listen/Kataloge/";
		
	//public static String imageBasePath = "file://abamasrv/Marketing/Bilder/Produktbilder/Einzelbilder/RGB-PNG-S/";
	public static String imageBasePath = "file:/Volumes/Marketing/Bilder/Produktbilder/Einzelbilder/RGB-PNG-S/";
	
	public static boolean loadImages = false;
		
	public static Locale locale = new Locale("de", "DE");
	
	public static NumberFormat numberFormat = NumberFormat.getInstance(locale);
	
	
	// Singleton-Klasse
	private static Configuration configuration = null;
	
	public static Configuration getInstance(){
		if(configuration == null) configuration = new Configuration();
		return configuration;
	}

	private Configuration(){}
	
}
