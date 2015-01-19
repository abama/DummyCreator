package de.abama.dummycreator.catalogue;

import java.util.ArrayList;
import java.util.List;

import de.abama.dummycreator.articles.ArticleManager;
import de.abama.dummycreator.gui.image.ImageResource;
import javafx.scene.image.Image;

public class CatalogueGroup implements  ICatalogueItem {
	
	private static final String INDEX = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private static final long serialVersionUID = -5891268528749563927L;
		
	private List<CatalogueArticle> articles = new ArrayList<CatalogueArticle>();
		
	private CataloguePage page;

	public String getGroupSignature(){
		if(articles.size()!=0) return articles.get(0).getGroupSignature();
		return ("Leere Artikelgruppe");
		}
	
	public CatalogueGroup(CataloguePage page){
		super();
		if(page==null);
		this.page = page;
	}
	
	// Kopierkonstruktor
	public CatalogueGroup(CataloguePage page, CatalogueGroup original){
		super();
		this.page = page;
		articles.addAll(original.articles);
	}
	
	public CatalogueArticle add(final CatalogueArticle article){
		if(!contains(article)) {
			System.out.println("Füge Artikel hinzu: " + article.getNumber());
			article.setGroup(this);
			articles.add(article);
			ArticleManager.getInstance().get(article.getNumber()).incrementOccurencies();
		}
		return article;
	}
	
	public boolean contains(final CatalogueArticle article) {
		for(final CatalogueArticle catalogueArticle : articles) {
			if(catalogueArticle.getNumber().equals(article.getNumber())) {
				return true;
			}
		}
		return false;
	}
	
	public String getDescription() {
		if(articles.size()!=0) return articles.get(0).getDescription();
		return ("keine Beschreibung verfügbar");
	}
	
	public char getIndex() {
		try{
			return INDEX.charAt(page.getGroups().indexOf(this));
		} catch(final IndexOutOfBoundsException e){
			return '?';
		}
	}
	
	public CataloguePage getPage(){
		return page;
	}	
	
	public String getTitle() {
		if(articles.size()!=0) return articles.get(0).getTitle();
		return ("Leere Artikelgruppe");
	}
	
	public String getDescription1() {
		if(articles.size()!=0) return articles.get(0).getDescription1();
		return ("Leere Artikelgruppe");
	}

	public void setPage(final CataloguePage page){
		this.page = page;
	}

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

	@Override
	public ICatalogueItem getParent() {
		return page;
	}

	@Override
	public ICatalogueItem remove() {
		return this.getParent().remove(this);
	}

	@Override
	public ICatalogueItem remove(ICatalogueItem catalogueItem) {
		try {
			System.out.println("Lösche " + catalogueItem);
			final int index = articles.indexOf(catalogueItem);
			ArticleManager.getInstance().get(articles.get(index).getNumber()).decrementOccurencies();
			articles.remove(index);
			return articles.get(index);
		}
		catch(final Exception e){
			return null;
		}
	}
	
	public String toString() {
		return "Seite " + page.getNumber() + " - Gruppe " + getIndex();
	}

	@Override
	public void add(ICatalogueItem item) {
		try {
			articles.add((CatalogueArticle)item);
			System.out.println(this + " - Füge hinzu: " + item);
		}
		catch(final ClassCastException e){
			System.out.println("Objekt kann nicht eingefügt werden");
		}
	}

	@Override
	public void addAll(List<ICatalogueItem> selection) {
		for(final ICatalogueItem item : selection){
			this.add(item);
		}
	}

	public List<List<String>> serialize(){
		final List<List<String>> rows = new ArrayList<List<String>>();
		for(final CatalogueArticle article : getArticles()){
			rows.add(article.serialize());
		}
		return rows;
	}

	public void clear() {
		for(final CatalogueArticle article : articles) {
			ArticleManager.getInstance().get(article.getNumber()).decrementOccurencies();
		}
	}
}
