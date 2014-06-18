package de.abama.dummycreator.articles.utlilities;

import java.util.ArrayList;
import java.util.List;

import de.abama.dummycreator.catalogue.Article;
import de.abama.dummycreator.catalogue.Article;
import de.abama.dummycreator.catalogue.Article;
import de.abama.dummycreator.csv.CSV;

public class ArticleUtilities {
	
	//private static Configuration configuration = Configuration.getInstance();
		
	public static List<Article> createArticles(final CSV csv){
		List<Article> articles = new ArrayList<Article>();
		for(int i = 0; i<csv.getRowCount();i++){
			try {
				final Article article = new Article();
				article.setPageNumber(csv.getField(i, "Seite"));
				article.setGroupIndex(csv.getField(i, "Gruppe"));
				article.setNumber(csv.getField(i, "Artikelnummer"));
				article.setTitle(csv.getField(i, "Titel"));
				article.setDescription1(csv.getField(i, "Subtitle"));
				article.setDescription2(csv.getField(i, "SubSubTitle"));
				article.setDescription3(csv.getField(i, "Farbe"));
				articles.add(article);
			} catch(final Exception e){
				System.out.println("Artikel konnte nicht angelegt werden: " + csv.getRow(i));
			}
		}
		return articles;
	}
}
