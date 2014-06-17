package de.abama.dummycreator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import de.abama.dummycreator.csv.CSV;
import de.abama.dummycreator.csv.CsvFileUtility;
import de.abama.dummycreator.entities.CatalogueGroup;
import de.abama.dummycreator.entities.Catalogue;
import de.abama.dummycreator.entities.CataloguePage;
import de.abama.dummycreator.entities.CataloguePageStub;
import de.abama.dummycreator.entities.ListArticle;
import de.abama.dummycreator.masterdata.MasterData;
import de.abama.dummycreator.utlilities.ArticleUtilities;
import de.abama.dummycreator.utlilities.GuiUtilities;

public class CatalogueManager {
	
	private Catalogue catalogue = new Catalogue();
	
	private CataloguePage currentPage = null;
		
	public Catalogue newFile() {
		catalogue = new Catalogue();
		return catalogue;
	}
	
	public Catalogue openFile(final File file) {
		final CSV csv = CsvFileUtility.read(file);
		
		final List<ListArticle> articles = ArticleUtilities.createListArticles(csv);
		Collections.sort(articles);
		
		catalogue.setFirstPage(articles.get(0).getPage());
		
		for(final ListArticle article : articles){
			final CataloguePage page = catalogue.getOrCreatePage(article.getPage());
			//System.out.println("Page: " + page);
			final CatalogueGroup group = page.getOrCreateGroup(article.getGroup());
			//System.out.println("Group: " + group);
			group.add(ArticleUtilities.createCatalogueArticles(article));
		}
		
		currentPage = catalogue.getFirstPage();

		return catalogue;
	}
	
	public void save(){
		
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

	public ObservableList<HBox> getCurrentLeftPageGroups() throws IOException {
		return createPageGroupEntries(getCurrentLeftPage());
	}

	private ObservableList<HBox> createPageGroupEntries(CataloguePage page) throws IOException {
		final List<HBox> groupEntries = new ArrayList<HBox>();
		//System.out.println("Suche Artikelgruppen für Seite " + page);
		//System.out.println("Seite " + page.getNumber());
		for(final CatalogueGroup group : page.getGroups()){
			groupEntries.add(GuiUtilities.createGroupListEntry(group));
			//System.out.println("Suche Gruppen für Seite " + page.getNumber());
		}
		return FXCollections.observableList(groupEntries);
	}

	public ObservableList<HBox> getCurrentRightPageGroups() throws IOException {
		return createPageGroupEntries(getCurrentRightPage());
	}

	public int getCurrentPageNumber() {
		return currentPage.getNumber();
	}

	public void previousSpread() {
		if(catalogue != null) {
			final int currentPageNumber = Math.max(catalogue.getFirstPageNumber(), currentPage.getNumber()-2);
			currentPage = catalogue.getPage(currentPageNumber);
		}
	}

	public void nextSpread() {
		if(catalogue != null) {
			final int currentPageNumber = Math.min(catalogue.getLastPageNumber(), currentPage.getNumber()+2);
			currentPage = catalogue.getPage(currentPageNumber);
		}
	}

	public String getCurrentLeftPageNumber() {
		if(getCurrentLeftPage().getNumber()<0) return "--";
		else return Integer.toString(getCurrentLeftPage().getNumber());
	}
	
	public String getCurrentRightPageNumber() {
		if(getCurrentRightPage().getNumber()<0) return "--";
		else return Integer.toString(getCurrentRightPage().getNumber());
	}

	public String getCurrentLeftPageKeywords() {
		return getCurrentLeftPage().getKeywords().toString();
	}
	
	public String getCurrentRightPageKeywords() {
		return getCurrentRightPage().getKeywords().toString();
	}

	public void addArticle(final ListArticle article, CataloguePage page, CatalogueGroup group) {
		if(page == null) page = currentPage;
		if(page == null) page = currentPage = catalogue.addPage();
		
		if(group == null) group = page.addGroup(new CatalogueGroup());
		//group.add(article);
	}
}
