package de.abama.dummycreator.catalogue;

import java.util.ArrayList;
import java.util.List;

public class Catalogue {
	
	private int firstPageNumber = 1;
	
	private List<CataloguePage> pages = new ArrayList<CataloguePage>();
	
	private List<CatalogueChapter> chapters  = new ArrayList<CatalogueChapter>();
	
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
	
	public CataloguePage removePage(final int number){
		try { pages.remove(getPage(number)); } catch(Exception e) {};
		return getPage(number);
	}
	
	public CataloguePage getPage(final int number){
		int index = number - firstPageNumber;
		if(index < 0) index = 0;
		if(index > pages.size()-1) index = pages.size()-1;
		try {
			return pages.get(index);
		}
		catch(IndexOutOfBoundsException e){
			return new CataloguePageStub();
		}
	}

	public CataloguePage getFirstPage() {
		return pages.get(0);
	}
	
	public CataloguePage getLastPage() {
		return pages.get(pages.size()-1);
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
		if(pages.size()==0) firstPageNumber = number;
		while(pages.size()+firstPageNumber-1 < number){
			final CataloguePage page = new CataloguePage();
			page.setCatalogue(this);
			this.add(page);
			System.out.println("Erstelle Seite " + page.getNumber());
		}
		return pages.get(number-firstPageNumber);
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

	public void setFirstPage(int page) {
		this.firstPageNumber = page;
	}

	public int getFirstPageNumber() {
		return firstPageNumber;
	}
	
	public int getLastPageNumber() {
		return firstPageNumber+pages.size()-1;
	}

	public int getChaptersCount() {
		return chapters.size();
	}

	public CataloguePage addPage() {
		final CataloguePage page = getOrCreatePage(firstPageNumber);
		return page;
	}
}
