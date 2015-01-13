package de.abama.dummycreator.gui.fxml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.abama.dummycreator.catalogue.CataloguePage;
import de.abama.dummycreator.catalogue.ICatalogueItem;
import de.abama.dummycreator.gui.controller.ControllerContext;
import de.abama.dummycreator.gui.controller.ApplicationUI;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class CataloguePageThumbUi extends VBox implements ICatalogueUiItem {
		
	private static final long serialVersionUID = -338695967450729330L;

	private ApplicationUI controller = ControllerContext.getInstance().getMainController();
	
	@FXML
    private ImageView image;

	@FXML
    private TextField label;

	@FXML
    private ImageView linke_seite;
	
	@FXML
    private Label number;	

	private CataloguePage page;		
	
	@FXML
    private ImageView rechte_seite;


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

    @Override
	public ICatalogueItem getCatalogueItem() {
		return page;
	}

	public String getNumber(){
    	return String.valueOf(page.getNumber());
    }

    public CataloguePage getPage() {
		return page;
	}
    
    public void setPage(CataloguePage page) {
		this.page = page;
	}

	public String toString(){
		return page.toString();
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
	
	
	@SuppressWarnings("unchecked")
	@FXML
	private void dragDropped(DragEvent event) throws IOException{
				
		List<ICatalogueItem> items = null;
		
		if(event.getDragboard().hasContent(ControllerContext.catalogueGroupFormat)){
			items = (List<ICatalogueItem>) event.getDragboard().getContent(ControllerContext.catalogueGroupFormat);
		}
		else if(event.getDragboard().hasContent(ControllerContext.catalogueArticleFormat)) {
			items = (List<ICatalogueItem>) event.getDragboard().getContent(ControllerContext.catalogueArticleFormat);
		}
		
		if(items!=null){
			
			getPage().addAll(items);
			
			if(event.getTransferMode() == TransferMode.MOVE) {
				((CataloguePageUi) event.getGestureSource()).removeSelection();
			}
			final List<ICatalogueItem> selection = new ArrayList<ICatalogueItem>();
			selection.addAll(items);
			controller.setSelection(selection);
			controller.updateUiViews();
			event.setDropCompleted(true);
		}
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
		if(event.getGestureSource()!=this){
			if(event.getDragboard().hasContent(ControllerContext.catalogueGroupFormat))
				event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
			else if(event.getDragboard().hasContent(ControllerContext.catalogueArticleFormat))
				event.acceptTransferModes(TransferMode.LINK);
		}
		event.consume();
	}
	
	@FXML 
	private void dragDone(DragEvent event) throws IOException{
		System.out.println("Drag done");
		event.consume();
	}
	
	@FXML
	private void mouseClick(MouseEvent event) throws IOException{
		if(event.getClickCount()>=2) {
			controller.setCurrentPage(this.getPage());
			controller.setInsertionPoint(this.getPage());
		}
	}
	
	private void setImage(Image image) {
		this.image.setImage(page.getImage(true));
	}
}

