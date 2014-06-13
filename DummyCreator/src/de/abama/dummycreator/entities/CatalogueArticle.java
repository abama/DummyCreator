package de.abama.dummycreator.entities;

import java.io.Serializable;

public class CatalogueArticle extends Article  implements Serializable {

	private static final long serialVersionUID = 4624798489378228851L;

	private CatalogueGroup group;
	
	public CatalogueGroup getGroup(){
		return group;
	}
	
	public CataloguePage getPage(){
		return group.getPage();
	}
	
	public void setGroup(final CatalogueGroup group){
		this.group = group;
	}
	
	
}
