package de.abama.dummycreator.articles.utlilities;

import java.util.ArrayList;
import java.util.List;

import de.abama.dummycreator.articles.Article;
import de.abama.dummycreator.catalogue.CatalogueArticle;
import de.abama.dummycreator.csv.CSV;
import de.abama.dummycreator.gui.fxml.ListArticleUi;

public class ArticleUtilities {
	
	//private static Configuration configuration = Configuration.getInstance();
		
	public static List<Article> createListArticles(final CSV csv){
		List<Article> articles = new ArrayList<Article>();
		for(int i = 0; i<csv.getRowCount();i++){
			try {
				final Article article = new Article();
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
	
	public static List<CatalogueArticle> createCatalogueArticles(List<ListArticleUi> listArticleUis) {
		final List<CatalogueArticle> catalogueArticles = new ArrayList<CatalogueArticle>();
		for(final ListArticleUi listArticleUi : listArticleUis){
			catalogueArticles.add(new CatalogueArticle(listArticleUi.getListArticle()));
		}
		return catalogueArticles;
	}
}
