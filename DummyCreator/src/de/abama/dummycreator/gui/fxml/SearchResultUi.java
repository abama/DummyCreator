package de.abama.dummycreator.gui.fxml;

import java.io.IOException;
import de.abama.dummycreator.gui.controller.ControllerContext;
import de.abama.dummycreator.gui.controller.DummyCreator;
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
	
	@SuppressWarnings("unused")
	private DummyCreator controller = null;
	
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
	private void dragDetected(Event event){
        Dragboard db = this.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        content.putString("Bla");
        db.setContent(content);
        
        event.consume();
	}
	
	@FXML
	private void listSelection(MouseEvent event){
		/*
		final List<ListArticleUi> uiItems = new ArrayList<ListArticleUi>();
		for(final T item : this.getSelectionModel().getSelectedItems()) uiItems.add((ListArticleUi)item);
		final List<ICatalogueUiItem> catalogueUis = GuiUtilities.createICatalogueUis(ArticleUtilities.createCatalogueArticles(uiItems));
		controller.setSelection(catalogueUis);
		*/
		// TODO
		event.consume();
	}
	
	@FXML 
	private void dragEntered(Event event) throws IOException{
		/*
		listSelection(event);
		this.setFocused(isFocused());
		groups.setBlendMode(BlendMode.DIFFERENCE);
		//System.out.println("Drag entered.");
		*/
		event.consume();
	}
	
	@FXML 
	private void dragDone(DragEvent event) throws IOException{
		/*
		controller.removeSelection();
		System.out.println("Drag done");
		*/
		event.consume();
	}
}