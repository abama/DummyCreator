package de.abama.dummycreator.catalogue;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import de.abama.dummycreator.articles.ListArticle;
import de.abama.dummycreator.config.Configuration;
import de.abama.dummycreator.constants.SU;

public class CatalogueArticle implements ICatalogueItem {

	private static final long serialVersionUID = 4624798489378228851L;

	private ListArticle article = null;
	
	private CatalogueGroup group;
	
	public CatalogueArticle(CatalogueArticle original) {
		super();
		this.article = original.article;
	}

	public CatalogueArticle(ListArticle original) {
		super();
		this.article = original;
	}	
	
	
	@Override
	public void add(ICatalogueItem selection) {
		// Hier kann nichts hinzugefügt werden
	}
	
	@Override
	public void addAll(List<ICatalogueItem> selection) {
		// Hier kann nichts hinzugefügt werden
	}

	public ListArticle getArticle() {
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
	
	public ICatalogueItem getParent(){
		return group;
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
	
	@Override
	public ICatalogueItem remove() {
		return this.getParent().remove(this);
	}

	@Override
	public ICatalogueItem remove(ICatalogueItem catalogueItem) {
		System.out.print("Artikel hat keine Unterelemente");
		return null;
	}

	public void setGroup(CatalogueGroup catalogueGroup) {
		this.group = catalogueGroup;		
	}

	public String toString(){
		return "Artikel " + getNumber() + " ("+group+")";
	}

	public List<String> toCsvRow() {
		final List<String> headings = new ArrayList<String>();
		headings.add(getNumber());
		headings.add(String.valueOf(getPageNumber()));
		headings.add(String.valueOf(getGroupIndex()));
		headings.add(getTitle());
		headings.add(getDescription1());
		headings.add(getDescription2());
		headings.add(getDescription3());
		headings.add(getDescription2());
		headings.add(getSu().singular());
		headings.add(Configuration.numberFormat.format(getSinglePrice()));
		headings.add("€");
		headings.add(Configuration.numberFormat.format(getSuPrice()));
		headings.add("€");
		return headings;
	}
}
