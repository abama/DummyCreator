package de.abama.dummycreator.gui.fxml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.abama.dummycreator.catalogue.CatalogueArticle;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;

public class CatalogueGroupUi extends HBox implements ICatalogueUiItem {
	
	private static final long serialVersionUID = 7766655443921809968L;

	@SuppressWarnings("unused")
	private CatalogueManager catalogueManager = CatalogueManager.getInstance();
	
	private ApplicationUI controller = ControllerContext.getInstance().getMainController();
	
	@FXML
    private Label description;
	
    @FXML
    private Label index;
    
    @FXML 
    private ListView<CatalogueArticleUi> articles;
	
	private CatalogueGroup group = null;
	
	@FXML 
	private ImageView image;

	@FXML
    private Label title;

	public CatalogueGroupUi(CatalogueGroup group) {
    	
		this.setGroup(group);
    	
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/CatalogueGroupUi.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            setArticles(group.getArticles());
            articles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    		setIndex(String.valueOf(group.getIndex()));
    		setTitle(String.valueOf(group.getTitle()));
    		setDescription(String.valueOf(group.getDescription()));
    		setImage(group.getImage(true));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

	public Label getDescription() {
		return description;
	}

	public Label getIndex() {
		return index;
	}

	public CataloguePage getPage() {
		return group.getPage();
	}

	public Label getTitle() {
		return title;
	}
    
    public void setDescription(String description) {
		this.description.setText(description);
	}
    
    public void setIndex(String index) {
		this.index.setText(index);
	}
       
    public void setGroup(CatalogueGroup group) {
		this.group = group;
	}

    public void setTitle(String title) {
		this.title.setText(title);
	}
    
    public void setImage(final Image image){
    	this.image.setImage(image);
    }
    
    public void setArticles(final List<CatalogueArticle> articles){

        this.articles.setItems(GuiUtilities.createCatalogueArticleUis(group.getArticles()));
    }
    
    public ListView<CatalogueArticleUi> getArticleEntries(){
    	return articles;
    }

	@Override
	public ICatalogueItem getCatalogueItem() {
		return group;
	}
	
	@FXML
	public void listSelection(Event event) throws IOException{
		if(articles.isFocused()){
			final List<ICatalogueUiItem> selection = new ArrayList<ICatalogueUiItem>();
			for(final ICatalogueUiItem item : articles.getSelectionModel().getSelectedItems()) selection.add(item);
			controller.setSelection(selection);
		}
	}
	
	@Override
	public String toString(){
		return group.toString();
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
		//controller.removeSelection();
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
		
		this.setBlendMode(BlendMode.DIFFERENCE);
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
