package de.abama.dummycreator.gui.fxml;

import java.io.IOException;
import java.util.List;

import de.abama.dummycreator.catalogue.CatalogueArticle;
import de.abama.dummycreator.catalogue.CatalogueGroup;
import de.abama.dummycreator.catalogue.CatalogueManager;
import de.abama.dummycreator.catalogue.CataloguePage;
import de.abama.dummycreator.gui.utilities.GuiUtilities;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

public class CatalogueGroupUi extends HBox {
	
	@SuppressWarnings("unused")
	private CatalogueManager catalogueManager = CatalogueManager.getInstance();
	
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
    	
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CatalogueGroupUi.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            setArticles(group.getArticles());
    		setIndex(String.valueOf(group.getIndex()));
    		setTitle(String.valueOf(group.getTitle()));
    		setDescription(String.valueOf(group.getDescription()));
    		setImage(group.getImage(true));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

	@FXML
    public void deleteArticle(KeyEvent event){
		if(event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.BACK_SPACE){
			final String number = this.articles.getSelectionModel().getSelectedItem().getNumber();
			group.removeArticle(number);
		}
    	// TODO removeArticle
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

        this.articles.setItems(GuiUtilities.createArticleGroupArticleListEntries(group.getArticles()));
    }
    
    public ListView<CatalogueArticleUi> getArticleEntries(){
    	return articles;
    }
}
