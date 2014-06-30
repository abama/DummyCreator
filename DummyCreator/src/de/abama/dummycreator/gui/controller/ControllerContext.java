package de.abama.dummycreator.gui.controller;

import javafx.scene.input.DataFormat;

public class ControllerContext {
		public static DataFormat catalogueArticleFormat = new DataFormat("de.abama.dummycreator.catalogue.CatalogueArticle");
	public static DataFormat catalogueGroupFormat = new DataFormat("de.abama.dummycreator.catalogue.CatalogueGroup");
	public static DataFormat cataloguePageFormat = new DataFormat("de.abama.dummycreator.catalogue.CataloguePage");
	
	
	private static ControllerContext instance = null;;
	
	public static ControllerContext getInstance(){
		if(instance == null){
			instance = new ControllerContext();
		}
		return instance;
	}
	
	public static ControllerContext getInstance(ApplicationUI mainController){
		if(instance == null){
			instance = new ControllerContext();
			instance.mainController = mainController;
			System.out.println("Controller initialisiert: " + mainController.getClass().getName());
		}
		return instance;
	}
	
	private ApplicationUI mainController = null;
	
	private ControllerContext(){}
	
	public ApplicationUI getMainController(){
		return mainController;
	}
}
