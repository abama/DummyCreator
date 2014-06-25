package de.abama.dummycreator.gui.fxml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.abama.dummycreator.catalogue.CatalogueManager;
import de.abama.dummycreator.catalogue.CataloguePage;
import de.abama.dummycreator.catalogue.ICatalogueItem;
import de.abama.dummycreator.gui.controller.ControllerContext;
import de.abama.dummycreator.gui.controller.DummyCreator;
import de.abama.dummycreator.gui.utilities.GuiUtilities;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
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
    	
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/CataloguePageUi.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
        	if(page.getNumber()>0) {
        		fxmlLoader.load();
            	this.setNumber(page.getNumber());	
            	this.groups.setItems(GuiUtilities.createCatalogueGroupUis(page));        	
            	groups.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        	}
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
	public void itemSelection(MouseEvent event) throws IOException{
		ICatalogueUiItem selected = groups.getSelectionModel().getSelectedItem();
		final List<ICatalogueUiItem> selection = new ArrayList<ICatalogueUiItem>();
		selection.add(selected);
		controller.setSelection(selection);    
	}
	
	@FXML
	public void listSelection(Event event) throws IOException{
		final List<ICatalogueUiItem> selection = new ArrayList<ICatalogueUiItem>();
		for(final ICatalogueUiItem item : groups.getSelectionModel().getSelectedItems()) selection.add(item);
		controller.setSelection(selection);
	}
	
	public String toString(){
		return page.toString();
	}
}

