package de.abama.dummycreator.gui.fxml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.abama.dummycreator.catalogue.CatalogueManager;
import de.abama.dummycreator.catalogue.CataloguePage;
import de.abama.dummycreator.catalogue.ICatalogueItem;
import de.abama.dummycreator.gui.controller.ControllerContext;
import de.abama.dummycreator.gui.controller.ApplicationUI;
import de.abama.dummycreator.gui.utilities.GuiUtilities;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class CataloguePageUi extends VBox implements ICatalogueUiItem {
	
	@SuppressWarnings("unused")
	private CatalogueManager catalogueManager = CatalogueManager.getInstance();
	
	private ApplicationUI controller = ControllerContext.getInstance().getMainController();

	@FXML
	private Pane page;
	
	@FXML
    private ListView<CatalogueGroupUi> groups;

	@FXML
    private Label number;	
	
	private CataloguePage cataloguePage;

	
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
            	//groups.setPrefHeight(Math.min(groups.getItems().size() * 115, this.page.getHeight()));
            	groups.setPrefHeight(groups.getItems().size() * 115);
            	groups.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        	}
       } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
    
    public void adaptSize(){
    	System.out.println(page);
    	System.out.println(groups);
    	groups.setPrefHeight(Math.min(groups.getItems().size() * 115, page.getHeight()));
    }

	@Override
	public ICatalogueItem getCatalogueItem() {
		return cataloguePage;
	}

	public String getNumber(){
    	return String.valueOf(cataloguePage.getNumber());
    }

    public CataloguePage getPage() {
		return cataloguePage;
	}
    
    @FXML
	public void listSelection(Event event) throws IOException{
		if(groups.isFocused()){
			final List<ICatalogueUiItem> selection = new ArrayList<ICatalogueUiItem>();
			for(final ICatalogueUiItem item : groups.getSelectionModel().getSelectedItems()) selection.add(item);
			controller.setSelection(selection);
		}
	}

	public void setPage(CataloguePage page) {
		this.cataloguePage = page;
	}
	
	public String toString(){
		return cataloguePage.toString();
	}
	
	@FXML 
	private void dragDetected(Event event) throws IOException{
		
		listSelection(event);
		
        Dragboard db = this.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        content.putString(getCatalogueItem().toString());
        db.setContent(content);
        
        event.consume();
	}
	
	@FXML 
	private void dragDone(DragEvent event) throws IOException{
		controller.removeSelection();
		System.out.println("Drag done");
		event.consume();
	}
	
	@FXML
	private void dragDropped(DragEvent event) throws IOException{
		//System.out.println("Drop.");

		controller.dropItems(this);
		controller.removeSelection();
		//controller.setSelection(items);
		event.setDropCompleted(true);
		event.consume();
	}
	
	@FXML 
	private void dragEntered(Event event) throws IOException{
		
		page.setBlendMode(BlendMode.DIFFERENCE);
		event.consume();
	}
	
	@FXML 
	private void dragExited(Event event) throws IOException{
		page.setBlendMode(null);
		event.consume();
	}
	
	@FXML 
	private void dragOver(DragEvent event){
		event.acceptTransferModes(TransferMode.MOVE);
		event.consume();
	}
	
	private void setNumber(int number) {
		this.number.setText(number>0 ? String.valueOf(cataloguePage.getNumber()) : "--");
	}	
}

