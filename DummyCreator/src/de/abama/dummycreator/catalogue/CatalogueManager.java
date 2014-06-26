package de.abama.dummycreator.catalogue;

import java.io.File;
import java.util.ArrayList;
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

	private ICatalogueItem insertionPoint;
	private CatalogueManager(){ super(); }
	
	
	public void addArticle(final CatalogueArticle article, CataloguePage page, CatalogueGroup group) {
		if(page == null) page = currentPage;
		if(page == null) page = currentPage = catalogue.addPage();
		
		if(group == null) group = page.add(new CatalogueGroup(page));
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
		
		catalogue = new Catalogue();
				
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
		page.add(new CatalogueGroup(page));
	}

	public void nextSpread() {
		if(catalogue != null) {
			final int currentPageNumber = Math.min(catalogue.getLastPageNumber(), currentPage.getNumber()+2);
			currentPage = catalogue.getPage(currentPageNumber);
			insertionPoint = currentPage;
		}
	}

	public void previousSpread() {
		if(catalogue != null) {
			final int currentPageNumber = Math.max(catalogue.getFirstPageNumber(), currentPage.getNumber()-2);
			currentPage = catalogue.getPage(currentPageNumber);
			insertionPoint = currentPage;
		}
	}
	
	public void save(){
		
	}

	public void setCurrentPage(CataloguePage cataloguePage) {
		currentPage = cataloguePage;
		
	}
	
	public void removeEmptyGroups(){
		final List<ICatalogueItem> itemsToDelete = new ArrayList<ICatalogueItem>();
		for(final CataloguePage page : catalogue.getPages()){
			for(final CatalogueGroup group : page.getGroups()){
				if (group.getArticlesCount()==0) itemsToDelete.add(group);
			}
		}
		for(final ICatalogueItem group : itemsToDelete){
			group.remove();
		}
	}


	public void setInsertionPoint(ICatalogueItem insertionPoint) {
		this.insertionPoint = insertionPoint;
	}
	
	public ICatalogueItem getInsertionPoint() {
		if(insertionPoint==null) insertionPoint = getOrCreatePage();
		return getOrCreatePage();
	}


	public void addArticles(List<CatalogueArticle> articles) {
		for(final CatalogueArticle article : articles){
			final CataloguePage page = getOrCreatePage();
			final CatalogueGroup group = page.getOrCreateGroup(article.getGroupSignature());
			//System.out.println("Group: " + group);
			group.add(new CatalogueArticle(article));
		}
		
	}

	private CataloguePage getOrCreatePage() {
		if(catalogue.getPagesCount()>0) return currentPage;
		
		final CataloguePage page = catalogue.getOrCreatePage(1);
		currentPage = page;
		return page;
	}
}
