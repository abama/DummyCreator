package de.abama.dummycreator.gui.fxml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.abama.dummycreator.catalogue.CatalogueArticle;
import de.abama.dummycreator.catalogue.ICatalogueItem;
import de.abama.dummycreator.gui.controller.ControllerContext;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class SearchResultUi<T> extends ListView<ListArticleUi> {
	
	public SearchResultUi() {
    	
    	ControllerContext.getInstance().getMainController();
    	
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/SearchResultUi.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
        	fxmlLoader.load();
        	this.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
       } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
    
	@FXML 
	private void dragDetected(MouseEvent event){
		
		listSelection(event);
		
		final List<CatalogueArticle> articles = getSelectedItems();
        
		Dragboard db = this.startDragAndDrop(TransferMode.LINK);
        ClipboardContent content = new ClipboardContent();
        content.put(ControllerContext.catalogueArticleFormat, articles);
        db.setContent(content);
        
        event.consume();
	}
	
	@FXML
	private void listSelection(Event event){
		final List<ICatalogueItem> selection = new ArrayList<ICatalogueItem>();
		for(final CatalogueArticle item : getSelectedItems()) selection.add(item);
		event.consume();
	}
	
	@FXML 
	private void dragDone(DragEvent event) throws IOException{		
		event.consume();
	}
	
	public List<CatalogueArticle> getSelectedItems(){
		final List<CatalogueArticle> selected = new ArrayList<CatalogueArticle>();
		for(final ListArticleUi articleUi : getSelectionModel().getSelectedItems()) 
			selected.add(new CatalogueArticle(articleUi.getNumber()));
		return selected;
	}
}