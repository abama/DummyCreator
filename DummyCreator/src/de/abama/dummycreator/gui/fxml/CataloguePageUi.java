package de.abama.dummycreator.gui.fxml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.abama.dummycreator.catalogue.CatalogueGroup;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class CataloguePageUi extends VBox implements ICatalogueUiItem {
	
	private static final long serialVersionUID = 449750810017074485L;

	@SuppressWarnings("unused")
	private CatalogueManager catalogueManager = CatalogueManager.getInstance();
	
	private CataloguePage cataloguePage;

	private ApplicationUI controller = ControllerContext.getInstance().getMainController();
	
	@FXML
    private ListView<CatalogueGroupUi> groups;

	@FXML
    private Label number;	
	
	@FXML
	private Pane page;

	
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
    }

	@Override
	public ICatalogueItem getCatalogueItem() {
		return cataloguePage;
	}

	public ListView<CatalogueGroupUi> getGroups() {
		// TODO Auto-generated method stub
		return groups;
	}

    public String getNumber(){
    	return String.valueOf(cataloguePage.getNumber());
    }
    
    
    public CataloguePage getPage() {
		return cataloguePage;
	}
	

	public List<CatalogueGroup> getSelectedItems(){
		final List<CatalogueGroup> selected = new ArrayList<CatalogueGroup>();
		for(final CatalogueGroupUi groupUi : groups.getSelectionModel().getSelectedItems()) 
			selected.add(groupUi.getCatalogueGroup());
		return selected;
	}
	
	@FXML
	public void listSelection(Event event) throws IOException{
		if(groups.isFocused()){
			final List<ICatalogueItem> selection = new ArrayList<ICatalogueItem>();
			for(final ICatalogueUiItem item : groups.getSelectionModel().getSelectedItems()) selection.add(item.getCatalogueItem());
			controller.setSelection(selection);
		}
	}
	
	/*
	@FXML
	public void keyPressed(KeyEvent event) {
		if(event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.BACK_SPACE)
			removeSelection();
		event.consume();
	}
	*/
	
	public void removeSelection(){
		final List<CatalogueGroupUi> selected = groups.getSelectionModel().getSelectedItems();
		for(final CatalogueGroupUi groupUi : selected){
			groupUi.getCatalogueGroup().remove();		
		}
		controller.updateUiViews();
	}
	
	public void setPage(CataloguePage page) {
		this.cataloguePage = page;
	}
	
	public String toString(){
		return cataloguePage.toString();
	}

	@FXML 
	private void dragDetected(MouseEvent event) throws IOException{
		
		//listSelection(event);
		
		Dragboard dragboard;
		
        if(event.isShiftDown())	dragboard = this.startDragAndDrop(TransferMode.COPY);
        else dragboard = this.startDragAndDrop(TransferMode.MOVE);
        
        ClipboardContent content = new ClipboardContent();        
        content.put(ControllerContext.catalogueGroupFormat, getSelectedItems());        
        dragboard.setContent(content);
        
        event.consume();
	}
	
	@FXML 
	private void dragDone(DragEvent event) throws IOException{
		System.out.println("Drag done");
		event.consume();
	}
	
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
			
			getCataloguePage().addAll(items);
			
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
	private void dragEntered(DragEvent event) throws IOException{
		if(event.getGestureSource()!=this){
			page.setBlendMode(BlendMode.DIFFERENCE);
			event.consume();
		}
	}
	
	@FXML 
	private void dragExited(DragEvent event) throws IOException{
		page.setBlendMode(null);
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

	private CataloguePage getCataloguePage() {
		return cataloguePage;
	}

	@FXML
	private void mouseClick(MouseEvent event) throws IOException{
		controller.setSelection(getPage());
		controller.setCurrentPage(getCataloguePage());
	}
	
	private void setNumber(int number) {
		this.number.setText(number>0 ? String.valueOf(cataloguePage.getNumber()) : "--");
	}
}

