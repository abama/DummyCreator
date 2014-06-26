package de.abama.dummycreator.gui.fxml;

import java.io.IOException;

import de.abama.dummycreator.catalogue.CataloguePage;
import de.abama.dummycreator.catalogue.ICatalogueItem;
import de.abama.dummycreator.gui.controller.ControllerContext;
import de.abama.dummycreator.gui.controller.DummyCreator;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class CataloguePageThumbUi extends VBox implements ICatalogueUiItem {
		
	private DummyCreator controller = ControllerContext.getInstance().getMainController();
	
	@FXML
    private ImageView image;

	@FXML
    private TextField label;

	@FXML
    private Label number;
	
	@FXML
    private ImageView linke_seite;	

	@FXML
    private ImageView rechte_seite;		
	
	private CataloguePage page;


	@FXML
	private Pane selection;
	
    public CataloguePageThumbUi(final CataloguePage page) {
    	
    	this.setPage(page);
    	
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/CataloguePageThumbUi.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        	
        	this.setImage(page.getImage(true));
        	this.number.setText(String.valueOf(page.getNumber()));
        	
    		this.linke_seite.setVisible(page.getNumber()%2==0);
    		this.rechte_seite.setVisible(page.getNumber()%2!=0);
    		
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}

    private void setImage(Image image) {
		this.image.setImage(page.getImage(true));
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
	
	/*
	@FXML
	public void mouseClick(MouseEvent event) throws IOException{
		
    	controller.mouseClick(this, event);
    	
    	//System.out.println("Ausgewählte Seite: " +page.getNumber());
    	
    	if(event.getClickCount() >= 2){
    		catalogueManager.setCurrentPage(this.getPage());
    		controller.updateSpreadView();
    	}
	}
	*/
	
	
	public String toString(){
		return page.toString();
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
		
		this.setBlendMode(BlendMode.DIFFERENCE);
		controller.setInsertionPoint(this.page);
		event.consume();
	}
	
	@FXML 
	private void dragExited(Event event) throws IOException{
		this.setBlendMode(null);
		event.consume();
	}
	
	@FXML 
	private void dragOver(DragEvent event){
		event.acceptTransferModes(TransferMode.MOVE);
		event.consume();
	}
}

