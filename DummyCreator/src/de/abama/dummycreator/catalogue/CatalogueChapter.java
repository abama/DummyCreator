package de.abama.dummycreator.catalogue;

import java.util.ArrayList;
import java.util.List;

public class CatalogueChapter implements ICatalogueItem {

	private static final long serialVersionUID = 85208869643683546L;

	private List<CataloguePage> pages = new ArrayList<CataloguePage>();
	
	private Catalogue catalogue;

	private String name;
	
	public void addPage(final CataloguePage page){
		pages.add(page);
	}
	
	public void removePage(final CataloguePage page){
		pages.remove(page);
	}
	
	public ICatalogueItem getParent(){
		return catalogue;
	}

	@Override
	public ICatalogueItem remove() {
		return this.getParent().remove(this);
	}

	@Override
	public ICatalogueItem remove(ICatalogueItem catalogueItem) {
	try {
			System.out.println("Lösche " + catalogueItem);
			final int index = pages.indexOf(catalogueItem);
			pages.remove(index);
			return pages.get(index);
		}
		catch(final Exception e){
			return null;
		}
	}
	
	public String toString(){
		return "Kapitel " + name;
	}

	@Override
	public void add(ICatalogueItem selection) {
		// add(ICatalogueItem selection)
		
	}

	@Override
	public void addAll(List<ICatalogueItem> selection) {
		// TODO addAll(List<ICatalogueItem> selection)
		
	}
}
