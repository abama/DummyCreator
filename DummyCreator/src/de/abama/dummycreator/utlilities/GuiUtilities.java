package de.abama.dummycreator.utlilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import de.abama.dummycreator.entities.Catalogue;

public class GuiUtilities {
	
	public static int getLeftPage(int page){
		if(page%2==0) return page; else return page-1;
	}
	
	public static int getRightPage(int page) {
		if(page%2==0) return page+1; else return page;
	}

	public static Pane getRightPageContent(Catalogue catalogue, int currentPage) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static File chooseCsvFile(final String startFolderPath) throws MalformedURLException, URISyntaxException{
        FileChooser fileChooser = new FileChooser();    
        fileChooser.setInitialDirectory(new File(new URI("file:/Volumes/Marketing/Artikel/Listen/Kataloge/")));
        
        //Set extension filter
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV-Datei (*.csv)", "*.csv");
        FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("TXT-Datei (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(csvFilter);
        fileChooser.getExtensionFilters().add(txtFilter);
        
        final File test = fileChooser.showOpenDialog(null);
        
        return test;
	}
	
	public static Stage getProgressWindow(final String title, final String text, final int min, final int max){

        Group root = new Group();
        Scene scene = new Scene(root, 300, 150);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(title);
        
        final VBox vb = new VBox();
        vb.setSpacing(5);
        vb.getChildren().add(new Label(text));
        final ProgressBar pb = new ProgressBar();
        vb.getChildren().add(pb);

        scene.setRoot(vb);
		return stage;
	}
}
