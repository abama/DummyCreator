package de.abama.dummycreator.utlilities;

import java.util.ArrayList;
import java.util.List;

import de.abama.dummycreator.csv.CSV;
import de.abama.dummycreator.entities.CatalogueArticle;
import de.abama.dummycreator.entities.ListArticle;

public class ArticleUtilities {
	
	//private static Configuration configuration = Configuration.getInstance();
		
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
				articles.add(article);
			} catch(final Exception e){
				System.out.println("Artikel konnte nicht angelegt werden: " + csv.getRow(i));
			}
		}
		return articles;
	}
	
	public static List<CatalogueArticle> createCatalogueArticles(final CSV csv){
		List<CatalogueArticle> articles = new ArrayList<CatalogueArticle>();
		for(int i = 0; i<csv.getRowCount();i++){
			try {
				final CatalogueArticle article = new CatalogueArticle();
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

	public static CatalogueArticle createCatalogueArticles(ListArticle listArticle) {
		final CatalogueArticle article = new CatalogueArticle();
		article.setNumber(listArticle.getNumber());
		article.setTitle(listArticle.getTitle());
		article.setDescription1(listArticle.getDescription1());
		article.setDescription2(listArticle.getDescription2());
		article.setDescription3(listArticle.getDescription3());
		return article;
	}
}
