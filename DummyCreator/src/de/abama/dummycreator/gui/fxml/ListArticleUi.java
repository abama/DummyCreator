package de.abama.dummycreator.gui.fxml;

import java.io.IOException;

import de.abama.dummycreator.articles.ListArticle;
import de.abama.dummycreator.catalogue.ICatalogueItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ListArticleUi extends HBox implements ICatalogueUiItem {
	
	private static final long serialVersionUID = -5483451502063236521L;

	private ListArticle article;
	
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
	
    public ListArticleUi(final ListArticle article, boolean loadImage) {
    	
    	this.setArticle(article);
    	
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/ListArticleUi.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            
            //System.out.println(article.getNumber());
        	
        	if(loadImage) this.setImage(article.getImage(true));
        	this.number.setText(String.valueOf(article.getNumber()));
        	this.title.setText(article.getTitle());
        	this.description.setText(String.valueOf(article.getFullDescription()));
    		
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}

    public ListArticle getArticle() {
		return article;
	}

	@Override
	public ICatalogueItem getCatalogueItem() {
		return null;
	}

	public Label getDescription() {
		return description;
	}

	public ImageView getImage() {
		return image;
	}

	public ListArticle getListArticle() {
		return article;
	}

	public String getNumber(){
		return article.getNumber();
	}

	public Label getTitle() {
		return title;
	}

	public void setArticle(ListArticle article) {
		this.article = article;
	}

	public void setDescription(Label description) {
		this.description = description;
	}
	
	public void setImage(ImageView image) {
		this.image = image;
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

