package de.abama.dummycreator.catalogue;

import java.util.ArrayList;
import java.util.List;

public class CatalogueChapter {
	private List<CataloguePage> pages = new ArrayList<CataloguePage>();
	
	public void addPage(final CataloguePage page){
		pages.add(page);
	}
	
	public void removePage(final CataloguePage page){
		pages.remove(page);
	}
}
