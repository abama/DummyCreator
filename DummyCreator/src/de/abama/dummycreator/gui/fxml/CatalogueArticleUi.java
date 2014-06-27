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
	
	private CatalogueArticle article;
	
	@FXML
    private Label description;

	@FXML
    private ImageView image;

	@FXML
	private Label number;

	@FXML
	private Pane selection;

	@FXML
    private Label title;
	
	@FXML
    private Label single_price;
	
	public CatalogueArticleUi(final CatalogueArticle article, boolean loadImage) {
    	
    	this.setArticle(article);
    	
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/CatalogueArticleMicroUi.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        	
        	if(loadImage) this.setImage(article.getImage(true));
        	this.number.setText(String.valueOf(article.getNumber()));
        	//this.title.setText(article.getTitle());
        	this.description.setText(String.valueOf(article.getDescription3()));
        	this.single_price.setText(String.valueOf(article.getSinglePrice()).replace(".", ",")+" €");
    		
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}
	
	public CatalogueArticle getCatalogueArticle() {
		return article;
	}


	@Override
	public ICatalogueItem getCatalogueItem() {
		//if(article == null) article = new CatalogueArticle(original)
		return article;
	}
	
    public Label getDescription() {
		return description;
	}

    public ImageView getImage() {
		return image;
	}

	public String getNumber(){
		return article.getNumber();
	}

	public Pane getSelection() {
		return selection;
	}

	public Label getTitle() {
		return title;
	}

	public void setArticle(CatalogueArticle article) {
		this.article = article;
	}

	public void setDescription(Label description) {
		this.description = description;
	}

	public void setImage(ImageView image) {
		this.image = image;
	}

	public void setNumber(Label number) {
		this.number = number;
	}

	public void setSelection(Pane selection) {
		this.selection = selection;
	}
	
	public void setTitle(Label title) {
		this.title = title;
	}

	public String toString(){
		return article.toString();
	}
	
	private void setImage(Image image) {
		this.image.setImage(article.getImage(true));
	}
}

