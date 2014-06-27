package de.abama.dummycreator.config;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Configuration {
	
	// Singleton-Klasse
	private static Configuration configuration = null;
		
	public static Configuration getInstance(){
		if(configuration == null) configuration = new Configuration();
		return configuration;
	}
	
	//public static String articleListPath = "file://abamasrv/Marketing/Artikel/Listen/Katalog/";
	public String articleListPath = "file:/Volumes/Marketing/Artikel/Listen/Kataloge/";
		
	public String br = "\r\n";
	
	//public static String imageBasePath = "file://abamasrv/Marketing/Bilder/Produktbilder/Einzelbilder/RGB-PNG-S/";
	public String imageBasePath = "file:/Volumes/Marketing/Bilder/Produktbilder/Einzelbilder/RGB-PNG-S/";
	
	public boolean loadImages = false;

	public Locale locale = new Locale("de", "DE");
	
	
	public NumberFormat numberFormat = new DecimalFormat(",#00.00");
	
	private String sep = "\t";

	private Configuration(){}

	public String getLineBreak() {
		return br;
	}
	
	public String getSeparator() {
		return sep ;
	}
}
