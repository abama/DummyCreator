package de.abama.dummycreator.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import de.abama.dummycreator.catalogue.CataloguePage;
import de.abama.dummycreator.gui.fxml.ArticleGroupListEntry;

public class Test extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		ArticleGroupListEntry test = new ArticleGroupListEntry(new CataloguePage());
		stage.setScene(new Scene(test));
		stage.setTitle("Custom Control");
		stage.setWidth(300);
		stage.setHeight(200);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
