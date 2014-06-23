package de.abama.dummycreator.gui.fxml;

import java.io.IOException;

import de.abama.dummycreator.catalogue.CatalogueManager;
import de.abama.dummycreator.catalogue.CataloguePage;
import de.abama.dummycreator.catalogue.ICatalogueItem;
import de.abama.dummycreator.gui.controller.ControllerContext;
import de.abama.dummycreator.gui.controller.DummyCreator;
import de.abama.dummycreator.gui.utilities.GuiUtilities;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class CataloguePageUi extends VBox implements ICatalogueUiItem {
	
	@SuppressWarnings("unused")
	private CatalogueManager catalogueManager = CatalogueManager.getInstance();
	
	private DummyCreator controller = ControllerContext.getInstance().getMainController();

	@FXML
    private ListView<CatalogueGroupUi> groups;

	@FXML
    private Label number;	
	
	private CataloguePage page;

	
    public CataloguePageUi(final CataloguePage page) {
    	
    	this.setPage(page);
    	
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CataloguePageUi.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        	
        	this.setNumber(page.getNumber());
        	
        	this.groups.setItems(GuiUtilities.createCatalogueGroupUis(page));
        	    		
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}

	private void setNumber(int number) {
		
		this.number.setText(number>0 ? String.valueOf(page.getNumber()) : "--");
		
	}

	public CataloguePage getPage() {
		return page;
	}

    public void setPage(CataloguePage page) {
		this.page = page;
	}
    
    public String getNumber(){
    	return String.valueOf(page.getNumber());
    }

	@Override
	public ICatalogueItem getCatalogueItem() {
		return page;
	}
	
	@FXML
	public void mouseClick(MouseEvent event) throws IOException{	
    	controller.mouseClick(this, event);    
	}
}

