package de.abama.dummycreator.entities;

import java.util.ArrayList;
import java.util.List;

public class Catalogue {
	
	private List<Page> pages = new ArrayList<Page>();
	
	public List<Page> getPages(){
		return pages;
	}

	public Page newPage(final Page currentPage) {
		Page page = new Page(this);
		return insertPage(page, currentPage);
	}
	
	public Page insertPage(final Page insertPage, final Page previousPage) {
		int insertionIndex = pages.size();
		if(previousPage != null) insertionIndex = pages.indexOf(previousPage)+1;
		pages.add(insertionIndex, insertPage);
		return insertPage;
	}
}
