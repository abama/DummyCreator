package de.abama.dummycreator.catalogue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import de.abama.dummycreator.articles.ArticleManager;
import de.abama.dummycreator.articles.utlilities.ArticleUtilities;
import de.abama.dummycreator.csv.CSV;
import de.abama.dummycreator.csv.CsvFileUtility;
import de.abama.dummycreator.gui.utilities.GuiUtilities;

public class CatalogueManager {
	
	private static CatalogueManager instance = null;
	
	private CatalogueManager(){ super(); }
	
	public static CatalogueManager getInstance(){
		if(instance==null) instance = new CatalogueManager();
		return instance;
	}
	
	private Catalogue catalogue = new Catalogue();
	
	private CataloguePage currentPage = null;
	private CatalogueGroup currentGroup = null;
	private CatalogueArticle currentCatalogueArticle = null;
		
	public void addArticle(final CatalogueArticle article, CataloguePage page, CatalogueGroup group) {
		if(page == null) page = currentPage;
		if(page == null) page = currentPage = catalogue.addPage();
		
		if(group == null) group = page.addGroup(new CatalogueGroup());
		group.add(article);
	}
	
	public void newGroup(Object object) {
		CataloguePage page = currentPage;
		if(page == null) page = currentPage = catalogue.addPage();
		page.addGroup(new CatalogueGroup());
	}
	
	public CataloguePage addPage(CataloguePage previousPage) {
		currentPage = catalogue.newPage(previousPage);
		return currentPage;
	}

	public ObservableList<VBox> createPageThumbnails() throws IOException {
		final List<VBox> pages = new ArrayList<VBox>();
		for(final CataloguePage page : catalogue.getPages()){
			VBox pageThumbnail = FXMLLoader.load(getClass().getResource("../gui/fxml/PageThumbnail.fxml"));
			((Label) pageThumbnail.lookup("#number")).setText(Integer.toString(page.getNumber()));
			if(page.getImage(true)!=null) ((ImageView) pageThumbnail.lookup("#image")).setImage(page.getImage(true));
			((ImageView) pageThumbnail.lookup("#linke_seite")).setVisible(page.getNumber()%2==0);
			((ImageView) pageThumbnail.lookup("#rechte_seite")).setVisible(page.getNumber()%2!=0);
			pages.add(pageThumbnail);
		}		
		return FXCollections.observableList(pages);
	}
	
	public void deletePage(String text) {
		currentPage = catalogue.removePage(Integer.valueOf(text));
	}

	public CataloguePage getCurrentLeftPage() {
		if(currentPage == null) return new CataloguePageStub();
		if(currentPage.getNumber() %2 != 0) return catalogue.getPage(currentPage.getNumber()-1);
		else return currentPage;
	}

	public ObservableList<HBox> getCurrentLeftPageGroups() throws IOException {
		return createPageGroupEntries(getCurrentLeftPage());
	}

	public String getCurrentLeftPageKeywords() {
		return getCurrentLeftPage().getKeywords().toString();
	}

	public String getCurrentLeftPageNumber() {
		if(getCurrentLeftPage().getNumber()<0) return "--";
		else return Integer.toString(getCurrentLeftPage().getNumber());
	}

	public int getCurrentPageNumber() {
		return currentPage.getNumber();
	}

	public CataloguePage getCurrentRightPage() {
		if(currentPage == null) return new CataloguePageStub();
		if(currentPage.getNumber() %2 != 1) return catalogue.getPage(currentPage.getNumber()+1);
		else return currentPage;
	}

	public ObservableList<HBox> getCurrentRightPageGroups() throws IOException {
		return createPageGroupEntries(getCurrentRightPage());
	}
	
	public String getCurrentRightPageKeywords() {
		return getCurrentRightPage().getKeywords().toString();
	}

	public String getCurrentRightPageNumber() {
		if(getCurrentRightPage().getNumber()<0) return "--";
		else return Integer.toString(getCurrentRightPage().getNumber());
	}
	
	public Catalogue newFile() {
		catalogue = new Catalogue();
		return catalogue;
	}

	public void nextSpread() {
		if(catalogue != null) {
			final int currentPageNumber = Math.min(catalogue.getLastPageNumber(), currentPage.getNumber()+2);
			currentPage = catalogue.getPage(currentPageNumber);
		}
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

	public void previousSpread() {
		if(catalogue != null) {
			final int currentPageNumber = Math.max(catalogue.getFirstPageNumber(), currentPage.getNumber()-2);
			currentPage = catalogue.getPage(currentPageNumber);
		}
	}

	public void save(){
		
	}

	public void setCurrentPage(String text) {
		currentPage = catalogue.getPage(Integer.valueOf(text));
		
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
}
