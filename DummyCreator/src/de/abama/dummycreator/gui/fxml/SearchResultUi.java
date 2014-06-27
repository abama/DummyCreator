package de.abama.dummycreator.gui.fxml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.abama.dummycreator.articles.ListArticle;
import de.abama.dummycreator.catalogue.CatalogueArticle;
import de.abama.dummycreator.gui.controller.ControllerContext;
import de.abama.dummycreator.gui.controller.ApplicationUI;
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

public class SearchResultUi<T> extends ListView<T> {
	
	private ApplicationUI controller = null;
	
    public SearchResultUi() {
    	
    	controller = ControllerContext.getInstance().getMainController();
    	
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
        
		Dragboard db = this.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        content.putString("Bla");
        db.setContent(content);
        
        event.consume();
	}
	
	@FXML
	private void listSelection(Event event){
		final List<ICatalogueUiItem> selection = new ArrayList<ICatalogueUiItem>();
		for(final T listArticleUi : this.getSelectionModel().getSelectedItems()) {
			final ListArticle listArticle = ((ListArticleUi) listArticleUi).getArticle();
			final CatalogueArticle catalogueArticle = new CatalogueArticle(listArticle);
			final CatalogueArticleUi catalogueArticleUi = new CatalogueArticleUi(catalogueArticle, false);
			selection.add(catalogueArticleUi);
		}
		
		controller.setSelection(selection);
		
		event.consume();
	}
	
	@FXML 
	private void dragDone(DragEvent event) throws IOException{
		
		// TODO dragDone
		
		event.consume();
	}
}