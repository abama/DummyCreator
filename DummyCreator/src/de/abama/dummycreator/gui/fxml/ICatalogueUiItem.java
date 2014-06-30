package de.abama.dummycreator.gui.fxml;

import java.io.Serializable;

import de.abama.dummycreator.catalogue.ICatalogueItem;

public interface ICatalogueUiItem extends Serializable {
	
	ICatalogueItem getCatalogueItem();
	
	//void add(ICatalogueItem item);

	//void addAll(List<ICatalogueUiItem> items);
	
	//void mouseClick(MouseEvent event)  throws IOException;
	
	//void keyPressed(KeyEvent event)  throws IOException;
}
