package de.abama.dummycreator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	@Override
	public void start(Stage window) {
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("./gui/DummyCreator.fxml"));
			window.setScene(new Scene(fxml));
			window.setTitle("DummyCreator");
			window.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
