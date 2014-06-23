package de.abama.dummycreator.gui.fxml;

import java.io.IOException;

import javafx.scene.input.MouseEvent;
import de.abama.dummycreator.catalogue.ICatalogueItem;

public interface ICatalogueUiItem {
	
	ICatalogueItem getCatalogueItem();
	
	void mouseClick(MouseEvent event)  throws IOException;
	
	//void keyPressed(KeyEvent event)  throws IOException;
}
