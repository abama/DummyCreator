package de.abama.dummycreator.gui.fxml;

import java.io.IOException;

import de.abama.dummycreator.catalogue.CatalogueArticle;
import de.abama.dummycreator.catalogue.ICatalogueItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class CatalogueArticleUi extends HBox implements ICatalogueUiItem {
	
	@FXML
	private Label number;
	
	@FXML
    private ImageView image;

	@FXML
    private Label description;
	
	@FXML
    private Label title;
	
	private CatalogueArticle article;


	@FXML
	private Pane selection;
	
    public CatalogueArticleUi(final CatalogueArticle article, boolean loadImage) {
    	
    	this.setArticle(article);
    	
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/CatalogueArticleMicroUi.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        	
        	if(loadImage) this.setImage(article.getImage(true));
        	this.number.setText(String.valueOf(article.getNumber()));
        	this.title.setText(article.getTitle());
        	this.description.setText(String.valueOf(article.getDescription()));
    		
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}

    public ImageView getImage() {
		return image;
	}

	public void setImage(ImageView image) {
		this.image = image;
	}

	public Label getDescription() {
		return description;
	}

	public void setDescription(Label description) {
		this.description = description;
	}

	public Label getTitle() {
		return title;
	}

	public void setTitle(Label title) {
		this.title = title;
	}

	private void setImage(Image image) {
		this.image.setImage(article.getImage(true));
	}

	public String getArticle() {
		return article.getNumber();
	}

	public void setArticle(CatalogueArticle article) {
		this.article = article;
	}
	
	public String getNumber(){
		return article.getNumber();
	}

	@Override
	public ICatalogueItem getCatalogueItem() {
		return article;
	}
	
	public String toString(){
		return article.toString();
	}
}

