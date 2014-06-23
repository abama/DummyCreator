package de.abama.dummycreator.gui.dialogs;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProgressWindow extends Stage{
	
	private int min = 0, max = 100;
	
	private ProgressBar progressBar = new ProgressBar();
	private Label label = new Label("Fortschritt");
	
	public ProgressWindow(final String title, final String text, final int min, final int max){
		
		super();
		
		this.initModality(Modality.WINDOW_MODAL);
		
		this.min = min;
		this.max = max;
		
		final AnchorPane root = new AnchorPane();
        
        final VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.getChildren().add(progressBar);
        vBox.getChildren().add(label);
        vBox.setAlignment(Pos.CENTER);
        
        root.getChildren().add(vBox);
        
        System.out.println(root.getChildren());

        Scene scene = new Scene(root, 500, 100);
        this.setScene(scene);
        this.setTitle(title);
        
        this.sizeToScene();     
	}
	
	public void setProgress(final int value){
		final double current = (value-min)/(max-min);
		this.progressBar.setProgress(current);
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public double getProgress() {
		return this.progressBar.getProgress();
	}

	public String getStatusText() {
		return label.getText();
	}

	public void setStatusText(String text) {
		this.label.setText(text);
	}
}
