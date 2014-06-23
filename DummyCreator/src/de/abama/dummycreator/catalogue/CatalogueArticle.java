package de.abama.dummycreator.catalogue;

import javafx.scene.image.Image;
import de.abama.dummycreator.constants.SU;

public class CatalogueArticle extends Article  implements ICatalogueItem {

	private static final long serialVersionUID = 4624798489378228851L;

	private ListArticle article = null;
	
	private CatalogueGroup group;
	
	public CatalogueArticle(ListArticle listArticle) {
		super();
		this.article = listArticle;
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
}
