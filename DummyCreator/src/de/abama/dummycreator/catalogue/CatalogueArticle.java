package de.abama.dummycreator.catalogue;

import java.io.Serializable;

public class CatalogueArticle extends Article  implements Serializable {

	private static final long serialVersionUID = 4624798489378228851L;

	private CatalogueGroup group;
	
	public CatalogueArticle(ListArticle listArticle) {
		super();
		this.image = listArticle.image;
		this.number = listArticle.number;
		this.title = listArticle.title;
		this.description1 = listArticle.description1;
		this.description2 = listArticle.description2;
		this.description3 = listArticle.description3;
		this.singlePrice = listArticle.singlePrice;
		this.suPrice = listArticle.suPrice;
		this.su = listArticle.su;
	}
	
	public CatalogueArticle(){}

	public CatalogueGroup getGroup(){
		return group;
	}
	
	public CataloguePage getPage(){
		return group.getPage();
	}	
}
