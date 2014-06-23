package de.abama.dummycreator.catalogue;

import java.io.File;
import java.util.Collections;
import java.util.List;

import de.abama.dummycreator.articles.ArticleManager;
import de.abama.dummycreator.articles.ListArticle;

public class CatalogueManager {
	
	private static CatalogueManager instance = null;
	
	public static CatalogueManager getInstance(){
		if(instance==null) instance = new CatalogueManager();
		return instance;
	}
	
	private Catalogue catalogue = new Catalogue();
		
	private CataloguePage currentPage = null;
	private CatalogueManager(){ super(); }
	
	
	public void addArticle(final CatalogueArticle article, CataloguePage page, CatalogueGroup group) {
		if(page == null) page = currentPage;
		if(page == null) page = currentPage = catalogue.addPage();
		
		if(group == null) group = page.addGroup(new CatalogueGroup());
		group.add(article);
	}
	
	
	public CataloguePage addPage(CataloguePage previousPage) {
		currentPage = catalogue.newPage(previousPage);
		return currentPage;
	}

	public void deletePage(CataloguePage page) {
		catalogue.getPages().remove(page);
	}
	
	public Catalogue getCatalogue(){
		return catalogue;
	}

	public CataloguePage getCurrentLeftPage() {
		if(currentPage == null) return new CataloguePageStub();
		if(currentPage.getNumber() %2 != 0) return catalogue.getPage(currentPage.getNumber()-1);
		else return currentPage;
	}
	
	public CataloguePage getCurrentRightPage() {
		if(currentPage == null) return new CataloguePageStub();
		if(currentPage.getNumber() %2 != 1) return catalogue.getPage(currentPage.getNumber()+1);
		else return currentPage;
	}

	public Catalogue loadFile(final File file) {
				
		final List<ListArticle> articles = ArticleManager.getInstance().loadCsv(file);
		Collections.sort(articles);
		
		catalogue.setFirstPage(articles.get(0).getPageNumber());
		
		for(final ListArticle article : articles){
			final CataloguePage page = catalogue.getOrCreatePage(article.getPageNumber());
			//System.out.println("Page: " + page);
			final CatalogueGroup group = page.getOrCreateGroup(article.getGroupIndex());
			//System.out.println("Group: " + group);
			group.add(new CatalogueArticle(article));
		}
		
		currentPage = catalogue.getFirstPage();

		return catalogue;
	}

	public Catalogue newFile() {
		catalogue = new Catalogue();
		return catalogue;
	}

	public void newGroup(Object object) {
		CataloguePage page = currentPage;
		if(page == null) page = currentPage = catalogue.addPage();
		page.addGroup(new CatalogueGroup());
	}

	public void nextSpread() {
		if(catalogue != null) {
			final int currentPageNumber = Math.min(catalogue.getLastPageNumber(), currentPage.getNumber()+2);
			currentPage = catalogue.getPage(currentPageNumber);
		}
	}

	public void previousSpread() {
		if(catalogue != null) {
			final int currentPageNumber = Math.max(catalogue.getFirstPageNumber(), currentPage.getNumber()-2);
			currentPage = catalogue.getPage(currentPageNumber);
		}
	}
	
	public void save(){
		
	}

	public void setCurrentPage(CataloguePage cataloguePage) {
		currentPage = cataloguePage;
		
	}
}
