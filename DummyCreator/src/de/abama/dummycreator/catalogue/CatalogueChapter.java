package de.abama.dummycreator.catalogue;

import java.util.ArrayList;
import java.util.List;

public class CatalogueChapter implements ICatalogueItem {

	private static final long serialVersionUID = 85208869643683546L;

	private List<CataloguePage> pages = new ArrayList<CataloguePage>();
	
	public void addPage(final CataloguePage page){
		pages.add(page);
	}
	
	public void removePage(final CataloguePage page){
		pages.remove(page);
	}
}
