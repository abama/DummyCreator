package de.abama.dummycreator.catalogue;

import java.util.List;

import javafx.scene.image.Image;
import de.abama.dummycreator.articles.Article;
import de.abama.dummycreator.constants.SU;

public class CatalogueArticle implements ICatalogueItem {

	private static final long serialVersionUID = 4624798489378228851L;

	private Article article = null;
	
	private CatalogueGroup group;
	
	public CatalogueArticle(Article listArticle) {
		super();
		this.article = listArticle;
	}
	
	public CatalogueArticle(CatalogueArticle original) {
		super();
		this.article = original.article;
	}

	public Article getArticle() {
		return article;
	}
	
	public String getDescription(){
		return article.getDescription();
	}

	public String getDescription1() {
		return article.getDescription2();
	}

	public String getDescription2() {
		return article.getDescription2();
	}

	public String getDescription3() {
		return article.getDescription3();
	}

	public CatalogueGroup getGroup(){
		return group;
	}
	
	public char getGroupIndex() {
		return group.getIndex();
	}

	public String getGroupSignature(){
		return article.getTitle()+article.getDescription1();
	}
	
	public Image getImage(boolean load) {
		return article.getImage(load);
	}
	
	public String getNumber(){
		return article.getNumber();
	}
	
	public CataloguePage getPage(){
		return group.getPage();
	}
	
	public int getPageNumber() {
		return group.getPage().getNumber();
	}

	public float getSinglePrice() {
		return article.getSinglePrice();
	}

	public SU getSu() {
		return article.getSu();
	}
	
	public float getSuPrice() {
		return article.getSuPrice();
	}

	public String getTitle() {
		return article.getTitle();
	}
	
	public ICatalogueItem getParent(){
		return group;
	}

	@Override
	public ICatalogueItem remove() {
		return this.getParent().remove(this);
	}
	
	@Override
	public ICatalogueItem remove(ICatalogueItem catalogueItem) {
		System.out.print("Artikel hat keine Unterelemente");
		return null;
	}
	
	public String toString(){
		return "Artikel " + getNumber() + " ("+group+")";
	}

	public void setGroup(CatalogueGroup catalogueGroup) {
		this.group = catalogueGroup;		
	}

	@Override
	public void add(ICatalogueItem selection) {
		// Hier kann nichts hinzugefügt werden
	}

	@Override
	public void addAll(List<ICatalogueItem> selection) {
		// Hier kann nichts hinzugefügt werden
	}
}
