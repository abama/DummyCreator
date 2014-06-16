package de.abama.dummycreator.utlilities;

import java.util.ArrayList;
import java.util.List;

import de.abama.dummycreator.config.Configuration;
import de.abama.dummycreator.csv.CSV;
import de.abama.dummycreator.entities.Article;
import de.abama.dummycreator.entities.ListArticle;

public class ArticleUtilities {
	
	private static Configuration configuration = Configuration.getInstance();
	
	private static String imageBasePath = configuration.imageBasePath;
	
	public static List<ListArticle> createListArticles(final CSV csv){
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

	public static Article createArticle(List<String> row) {
		// TODO createArticle(List<String> row)
		return null;
	}
}
