package de.abama.dummycreator.utlilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import de.abama.dummycreator.config.Configuration;
import de.abama.dummycreator.csv.CSV;
import de.abama.dummycreator.entities.Article;

public class ArticleUtilities {
	
	private static String imageBasePath = Configuration.imageBasePath;
	
	public static List<Article> createArticles(final CSV csv){
		System.out.println(csv.getRows().size());
		List<Article> articles = new ArrayList<Article>();
		for(int i = 0; i<csv.getRowCount();i++){
			final Article article = new Article();
			article.setNumber(Integer.parseInt(csv.getField(i, "Artikelnummer")));
			article.setTitle(csv.getField(i, "Titel"));
			article.setDescription2(csv.getField(i, "Subtitle"));
			article.setDescription3(csv.getField(i, "SubSubTitle"));
			article.setImage(new Image(imageBasePath+article.getNumber()+".png", true));
			articles.add(article);
		}
		return articles;
	}
	
	public ObservableList<HBox> createListItems(List<Article> articles) throws IOException{
		List<HBox> list = new ArrayList<HBox>();
		
		for(final Article article : articles){
			HBox articleBox = FXMLLoader.load(getClass().getResource("../gui/article.fxml"));
			((Label) articleBox.lookup("#title")).setText(article.getTitle());
			((Label) articleBox.lookup("#number")).setText(Integer.toString(article.getNumber()));
			((Label) articleBox.lookup("#description")).setText(article.getDescription2()+article.getDescription3());
			final String imagePath = imageBasePath+article.getNumber()+".png";
			//System.out.println("Lade " + imagePath + "...");
			((ImageView) articleBox.lookup("#image")).setImage(article.getImage());
			list.add(articleBox);
		}
		
		ObservableList<HBox> observableList = FXCollections.observableList(list);
		return observableList;
	}
}
