package de.abama.dummycreator.gui.controller;

public class ControllerContext {
	private static ControllerContext instance = null;
	
	private ControllerContext(){};
	
	private ApplicationUI mainController = null;
	
	public static ControllerContext getInstance(ApplicationUI mainController){
		if(instance == null){
			instance = new ControllerContext();
			instance.mainController = mainController;
			System.out.println("Controller initialisiert: " + mainController.getClass().getName());
		}
		return instance;
	}
	
	public static ControllerContext getInstance(){
		if(instance == null){
			instance = new ControllerContext();
		}
		return instance;
	}
	
	public ApplicationUI getMainController(){
		return mainController;
	}
}
