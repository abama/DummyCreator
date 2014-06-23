package de.abama.dummycreator.catalogue;

import java.util.ArrayList;
import java.util.List;

import de.abama.dummycreator.gui.image.ImageResource;
import javafx.scene.image.Image;

public class CatalogueGroup implements  ICatalogueItem {
	
	private static final String INDEX = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private static final long serialVersionUID = -5891268528749563927L;
		
	private List<CatalogueArticle> articles = new ArrayList<CatalogueArticle>();
		
	private CataloguePage page;
	
	public void add(final CatalogueArticle article){
		if(!articles.contains(article)) articles.add(article);
		System.out.println("Füge Artikel hinzu: " + article.getNumber());
	}	
	
	public void addAll(final CatalogueArticle[] articles){
		for(CatalogueArticle article : articles){
			add(article);
		}
		//updateGroupData();
	}
	
	public String getDescription() {
		if(articles.size()!=0) return articles.get(0).getDescription();
		return ("keine Beschreibung verfügbar");
	}
	
	public char getIndex() {
		return INDEX.charAt(page.getGroups().indexOf(this));
	}
	
	public CataloguePage getPage(){
		return page;
	}	
	
	public String getTitle() {
		if(articles.size()!=0) return articles.get(0).getTitle();
		return ("Leere Artikelgruppe");
	}
	
	public void removeArticle(final String number){
		articles.remove(getArticle(number));	
	}

	public void removeArticles(final Article[] articles){
		for(Article article : articles){
			this.articles.remove(article);
		}
		//updateGroupData();
	}

	public void setPage(final CataloguePage page){
		this.page = page;
	}

	/*
	public void updateGroupData(){
		if(articles.size() == 0) {
			index = null;
			title = null;
		} else {
			index = articles.get(0).getNumber();
			title = articles.get(0).getTitle();
		}
	}
	*/
	
	public int getArticlesCount(){
		return articles.size();
	}

	public Image getImage(boolean loadImage) {
		if(articles.size()!=0) return articles.get(0).getImage(true);
		else return new Image(ImageResource.class.getResourceAsStream("no_image.png"));
	}

	public List<CatalogueArticle> getArticles() {
		return articles;
	}
	
	public CatalogueArticle getArticle(final String number){
		for(final CatalogueArticle article : articles){
			if(article.getNumber()==number) return article;
		}
		return null;
	}
}
