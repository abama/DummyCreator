package de.abama.dummycreator.config;

public class Configuration {
	
	// Singleton-Klasse
	private static Configuration configuration = null;
		
	//public static String imageBasePath = "file://abamasrv/Marketing/Bilder/Produktbilder/Einzelbilder/RGB-PNG-S/";
	public String imageBasePath = "file:/Volumes/Marketing/Bilder/Produktbilder/Einzelbilder/RGB-PNG-S/";
	
	//public static String articleListPath = "file://abamasrv/Marketing/Artikel/Listen/Katalog/";
	public String articleListPath = "file:/Volumes/Marketing/Artikel/Listen/Kataloge/";
	
	public boolean loadImages = false;
	
	private Configuration(){}
	
	
	public static Configuration getInstance(){
		if(configuration == null) configuration = new Configuration();
		return configuration;
	}
	
}
