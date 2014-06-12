package de.abama.dummycreator.utlilities;

import java.io.File;

import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import de.abama.dummycreator.entities.Catalogue;

public class ControllerUtilities {
	
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
	
	public static File chooseCsvFile(){
        FileChooser fileChooser = new FileChooser();
        
        //Set extension filter
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV-Datei (*.csv)", "*.csv");
        FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("TXT-Datei (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(csvFilter);
        fileChooser.getExtensionFilters().add(txtFilter);
        
        return fileChooser.showOpenDialog(null);
	}
}
