package de.abama.dummycreator;

import de.abama.dummycreator.gui.controller.ControllerContext;
import de.abama.dummycreator.gui.controller.DummyCreator;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		
		try {
			Parent fxml = FXMLLoader.load(getClass().getResource("./gui/fxml/DummyCreator.fxml"));
			stage.setScene(new Scene(fxml));
			stage.setTitle("DummyCreator");
			stage.show();
			
			final DummyCreator controller = ControllerContext.getInstance().getMainController();
			
	        stage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent keyEvent)
                {
            		if(keyEvent.getCode() == KeyCode.DELETE || keyEvent.getCode() == KeyCode.BACK_SPACE){
            			try {
            				controller.removeSelection();
            			}
            			catch(final Exception e){
            				//System.out.println(e);
            				System.out.println("Kein Element ausgewählt");
            			}
            		}
                }
            });

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
