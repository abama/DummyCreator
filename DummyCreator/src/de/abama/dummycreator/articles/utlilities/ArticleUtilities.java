package de.abama.dummycreator.articles.utlilities;

import java.util.ArrayList;
import java.util.List;

import de.abama.dummycreator.articles.ListArticle;
import de.abama.dummycreator.catalogue.CatalogueArticle;
import de.abama.dummycreator.csv.CSV;
import de.abama.dummycreator.gui.fxml.ListArticleUi;

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
				article.setSinglePrice(Float.parseFloat(csv.getField(i, "Einzelpreis (Kat)").replace(",", ".")));
				article.setCurrency(csv.getField(i, "PreisEinheit"));
				article.setSuPrice(Float.parseFloat(csv.getField(i, "VE-Preis (Kat)").replace(",", ".")));
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
			catalogueArticles.add(new CatalogueArticle(listArticleUi.getListArticle().getNumber()));
		}
		return catalogueArticles;
	}
}
