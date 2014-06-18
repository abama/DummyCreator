package de.abama.dummycreator.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class PageThumbnail {
	
	@SuppressWarnings("unused")
	private ControllerContext context = ControllerContext.getInstance();
	
	@FXML
	private void initialize(){
		//System.out.println("PONG");
	}
		
	@FXML
	private Pane selection;
	
    @FXML
    private ImageView image;

    @FXML
    private Label number;

    @FXML
    private TextField label;
}

