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
import de.abama.dummycreator.entities.ListArticle;

public class ArticleUtilities {
	
	private static String imageBasePath = Configuration.imageBasePath;
	
	public static List<ListArticle> createProtoArticles(final CSV csv){
		List<ListArticle> articles = new ArrayList<ListArticle>();
		for(int i = 0; i<csv.getRowCount();i++){
			try {
				final ListArticle article = new ListArticle();
				article.setPage(csv.getField(i, "Seite"));
				article.setGroup(csv.getField(i, "Gruppe"));
				article.setNumber(csv.getField(i, "Artikelnummer"));
				article.setTitle(csv.getField(i, "Titel"));
				article.setDescription1(csv.getField(i, "Subtitle"));
				article.setDescription2(csv.getField(i, "SubSubTitle"));
				article.setDescription3(csv.getField(i, "Farbe"));
				//article.setImage(new Image(imageBasePath+article.getNumber()+".png", false));
				articles.add(article);
			} catch(final Exception e){
				System.out.println("Artikel konnte nicht angelegt werden: " + csv.getRow(i));
			}
		}
		return articles;
	}
	
	public ObservableList<HBox> createListItems(List<Article> articles) throws IOException{
		List<HBox> list = new ArrayList<HBox>();
		
		for(final Article article : articles){
			HBox articleBox = FXMLLoader.load(getClass().getResource("../gui/fxml/article.fxml"));
			((Label) articleBox.lookup("#title")).setText(article.getTitle());
			((Label) articleBox.lookup("#number")).setText(article.getNumber());
			((Label) articleBox.lookup("#description")).setText(article.getDescription());
			((ImageView) articleBox.lookup("#image")).setImage(new Image(imageBasePath+article.getNumber()+".png", true));
			list.add(articleBox);
		}
		
		ObservableList<HBox> observableList = FXCollections.observableList(list);
		return observableList;
	}

	public static Article createArticle(List<String> row) {
		// TODO Auto-generated method stub
		return null;
	}
}
