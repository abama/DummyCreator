package de.abama.dummycreator.gui.controller;

public class ControllerContext {
	private static ControllerContext instance = null;
	
	private ControllerContext(){};
	
	private DummyCreator mainController = null;
	
	public static ControllerContext getInstance(DummyCreator mainController){
		if(instance == null){
			instance = new ControllerContext();
			instance.mainController = mainController;
			System.out.println("Controller initialized: " + mainController.getClass().getName());
		}
		return instance;
	}
	
	public static ControllerContext getInstance(){
		if(instance == null){
			instance = new ControllerContext();
		}
		return instance;
	}
	
	public DummyCreator getMainController(){
		return mainController;
	}	
}
