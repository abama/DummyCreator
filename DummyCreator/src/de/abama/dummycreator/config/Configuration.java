package de.abama.dummycreator.config;

public class Configuration {
	
	// Singleton-Klasse
	
	private static Configuration configuration = null;
		
	public static String imageBasePath = "file://abamasrv/Marketing/Bilder/Produktbilder/Einzelbilder/RGB-PNG-S/";
	
	private Configuration(){}
	
	public static Configuration getInstance(){
		if(configuration == null) configuration = new Configuration();
		return configuration;
	}
	
	

}
