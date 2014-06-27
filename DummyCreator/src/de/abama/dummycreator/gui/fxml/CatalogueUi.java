package de.abama.dummycreator.gui.fxml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.abama.dummycreator.catalogue.Catalogue;
import de.abama.dummycreator.catalogue.CatalogueArticle;
import de.abama.dummycreator.catalogue.CatalogueManager;
import de.abama.dummycreator.catalogue.ICatalogueItem;
import de.abama.dummycreator.gui.controller.ControllerContext;
import de.abama.dummycreator.gui.controller.ApplicationUI;
import de.abama.dummycreator.gui.utilities.GuiUtilities;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class CatalogueUi extends AnchorPane implements ICatalogueUiItem {
	
	private Catalogue catalogue = null;
	
	@SuppressWarnings("unused")
	private CatalogueManager catalogueManager = CatalogueManager.getInstance();
    
    private ApplicationUI controller;

    @FXML 
    private ListView<CataloguePageThumbUi> pages;

	public CatalogueUi(Catalogue catalogue) {
    	
		this.setCatalogue(catalogue);
    	
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/CatalogueUi.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            setPages(catalogue);
            pages.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            controller = ControllerContext.getInstance().getMainController();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

	public Catalogue getCatalogue() {
		return catalogue;
	}
    
	@Override
	public ICatalogueItem getCatalogueItem() {
		return catalogue;
	}
    
    public ListView<CataloguePageThumbUi> getPages() {
		return pages;
	}
    
    @FXML
	public void listSelection(MouseEvent event) throws IOException{
		final List<ICatalogueUiItem> selection = new ArrayList<ICatalogueUiItem>();
		for(final ICatalogueUiItem item : pages.getSelectionModel().getSelectedItems()) selection.add(item);
		//System.out.println(ControllerContext.getInstance().getMainController());
		controller.setSelection(selection);
	}
    
	@FXML
	public void listSelectionAll(KeyEvent event) throws IOException{
		final List<ICatalogueUiItem> selection = new ArrayList<ICatalogueUiItem>();
		for(final ICatalogueUiItem item : pages.getSelectionModel().getSelectedItems()) selection.add(item);
		controller.setSelection(selection);
	}
	
	public void setArticles(final List<CatalogueArticle> articles){

        
    }
	
	public void setCatalogue(Catalogue catalogue) {
		this.catalogue = catalogue;
	}

	public void setPages(final Catalogue catalogue) throws IOException{
    	this.pages.setItems(GuiUtilities.createPageThumbnails(catalogue));
    }

	public void setPages(ListView<CataloguePageThumbUi> pages) {
		this.pages = pages;
	}
	
	public String toString(){
		return catalogue.toString();
	}
}
