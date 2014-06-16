package de.abama.dummycreator.entities;

import java.util.ArrayList;
import java.util.List;

public class Catalogue {
	
	private int firstPage = 1;
	
	private List<CataloguePage> pages = new ArrayList<CataloguePage>();
	
	public List<CataloguePage> getPages(){
		return pages;
	}

	public CataloguePage newPage(final CataloguePage currentPage) {
		CataloguePage page = new CataloguePage();
		page.setCatalogue(this);
		return insertPage(page, currentPage);
	}
	
	public CataloguePage insertPage(final CataloguePage insertPage, final CataloguePage previousPage) {
		int insertionIndex = pages.size();
		if(previousPage != null) insertionIndex = pages.indexOf(previousPage)+1;
		pages.add(insertionIndex, insertPage);
		return insertPage;
	}
	
	public CataloguePage getPage(final int number){
		try {
			return pages.get(number - 1);
		}
		catch(Exception e){
			return null;
		}
	}

	public List<Article> getGroups() {
		// TODO getGroups()
		return null;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public CataloguePage add(CataloguePage page) {
		pages.add(page);
		return pages.get(pages.size()-1);
	}
	
	public int getArticlesCount(){
		int articleCount = 0;
		for(CataloguePage page : pages){
			articleCount+=page.getArticlesCount();
		}
		return articleCount;
	}

	public CataloguePage getOrCreatePage(int number) {
		if(pages.size()==0) firstPage = number;
		//System.out.println("Suche Seite: " + number);
		while(pages.size()+firstPage-1 < number){
			final CataloguePage page = new CataloguePage();
			page.setCatalogue(this);
			this.add(page);
			//System.out.println("Erstelle neue Seite.");
		}
		return pages.get(number-firstPage);
	}

	public int getPagesCount() {
		return pages.size();
	}

	public int getGroupsCount() {
		int groupCount = 0;
		for(CataloguePage page : pages){
			groupCount+=page.getGroupsCount();
		}
		return groupCount;
	}
}
