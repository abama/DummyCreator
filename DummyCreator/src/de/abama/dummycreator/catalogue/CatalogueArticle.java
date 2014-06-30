package de.abama.dummycreator.catalogue;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import de.abama.dummycreator.articles.ArticleManager;
import de.abama.dummycreator.config.Configuration;
import de.abama.dummycreator.constants.SU;

public class CatalogueArticle implements ICatalogueItem {

	private static final long serialVersionUID = 4624798489378228851L;
	
	private CatalogueGroup group;
	
	private String number;
	
	// Kopierkonstruktor
	public CatalogueArticle(CatalogueArticle original) {
		super();
		this.number = original.number;
	}	
	
	public CatalogueArticle(String number) {
		this.number = number;
	}

	@Override
	public void add(ICatalogueItem selection) {
		// Hier kann nichts hinzugefügt werden
	}
	
	@Override
	public void addAll(List<ICatalogueItem> selection) {
		// Hier kann nichts hinzugefügt werden
	}

	public String getDescription(){
		return ArticleManager.getInstance().get(number).getDescription();
	}

	public String getDescription1() {
		return ArticleManager.getInstance().get(number).getDescription2();
	}

	public String getDescription2() {
		return ArticleManager.getInstance().get(number).getDescription2();
	}
	
	public String getDescription3() {
		return ArticleManager.getInstance().get(number).getDescription3();
	}

	public CatalogueGroup getGroup(){
		return group;
	}
	
	public char getGroupIndex() {
		return group.getIndex();
	}
	
	public String getGroupSignature(){
		return ArticleManager.getInstance().get(number).getGroupSignature();
	}
	
	public Image getImage(boolean load) {
		return ArticleManager.getInstance().get(number).getImage(load);
	}
	
	public String getNumber(){
		return ArticleManager.getInstance().get(number).getNumber();
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
		return ArticleManager.getInstance().get(number).getSinglePrice();
	}
	
	public SU getSu() {
		return ArticleManager.getInstance().get(number).getSu();
	}

	public float getSuPrice() {
		return ArticleManager.getInstance().get(number).getSuPrice();
	}
	
	public String getTitle() {
		return ArticleManager.getInstance().get(number).getTitle();
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

	public List<String> serialize() {
		final List<String> row = new ArrayList<String>();
		row.add(getNumber());
		//values.add(String.valueOf(getChapterNumber()));
		row.add(String.valueOf(getPageNumber()));
		row.add(String.valueOf(getGroupIndex()));
		row.add(getTitle());
		row.add(getDescription1());
		row.add(getDescription2());
		row.add(getDescription3());
		row.add(getDescription2());
		row.add(getSu().singular());
		row.add(Configuration.getInstance().numberFormat.format(getSinglePrice()));
		row.add(getCurrency());
		row.add(Configuration.getInstance().numberFormat.format(getSuPrice()));
		row.add(getCurrency());
		
		return row;
	}

	public String getCurrency() {
		return ArticleManager.getInstance().get(number).getCurrency();
	}

	public int getChapterNumber() {
		return getPage().getChapter().getNumber();
	}
}
