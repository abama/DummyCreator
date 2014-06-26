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
import javafx.scene.effect.BlendMode;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
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
	public void listSelection(Event event) throws IOException{
		if(groups.isFocused()){
			final List<ICatalogueUiItem> selection = new ArrayList<ICatalogueUiItem>();
			for(final ICatalogueUiItem item : groups.getSelectionModel().getSelectedItems()) selection.add(item);
			controller.setSelection(selection);
		}
	}
	
	public String toString(){
		return page.toString();
	}
	
	@FXML 
	private void dragDetected(Event event){
        Dragboard db = this.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        content.putString(getCatalogueItem().toString());
        db.setContent(content);
        
        event.consume();
	}
	
	@FXML 
	private void dragEntered(Event event) throws IOException{
		listSelection(event);
		this.setFocused(isFocused());
		groups.setBlendMode(BlendMode.DIFFERENCE);
		//System.out.println("Drag entered.");
		event.consume();
	}
	
	@FXML 
	private void dragExited(Event event) throws IOException{
		//System.out.println("Drag exited.");
		groups.setBlendMode(null);
		event.consume();
	}
	
	@FXML 
	private void dragOver(DragEvent event){
		event.acceptTransferModes(TransferMode.MOVE);
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
	private void dragDone(DragEvent event) throws IOException{
		controller.removeSelection();
		System.out.println("Drag done");
		event.consume();
	}	
}

