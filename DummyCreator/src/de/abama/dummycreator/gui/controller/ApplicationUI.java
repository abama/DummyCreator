package de.abama.dummycreator.gui.controller;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import de.abama.dummycreator.articles.ArticleManager;
import de.abama.dummycreator.articles.ListArticle;
import de.abama.dummycreator.articles.utlilities.ArticleUtilities;
import de.abama.dummycreator.catalogue.Catalogue;
import de.abama.dummycreator.catalogue.CatalogueArticle;
import de.abama.dummycreator.catalogue.CatalogueManager;
import de.abama.dummycreator.catalogue.CataloguePage;
import de.abama.dummycreator.catalogue.ICatalogueItem;
import de.abama.dummycreator.config.Configuration;
import de.abama.dummycreator.gui.fxml.CataloguePageUi;
import de.abama.dummycreator.gui.fxml.CatalogueUi;
import de.abama.dummycreator.gui.fxml.ICatalogueUiItem;
import de.abama.dummycreator.gui.fxml.ListArticleUi;
import de.abama.dummycreator.gui.fxml.RestrictiveTextInput;
import de.abama.dummycreator.gui.fxml.SearchResultUi;
import de.abama.dummycreator.gui.utilities.GuiUtilities;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class ApplicationUI {

	@FXML HBox spread_view;

	protected ArticleManager articleManager = ArticleManager.getInstance();

	protected Catalogue catalogue;

	protected CatalogueManager catalogueManager = CatalogueManager.getInstance();

	protected Clipboard clipboard = Clipboard.getSystemClipboard();

	protected Configuration configuration = Configuration.getInstance();

	protected ControllerContext context;

	protected RestrictiveTextInput search_by_description;

	protected RestrictiveTextInput search_by_keywords;

	protected RestrictiveTextInput search_by_number;

	protected List<ICatalogueItem> selection = new ArrayList<ICatalogueItem>();
	
	@FXML
	private AnchorPane catalogue_pages;

	@FXML
	private Label info_articles;

	@FXML
	private Label info_catalogue_articles;

	@FXML
	private Label info_catalogue_chapters;

	@FXML
	private Label info_catalogue_filename;

	@FXML
	private Label info_catalogue_groups;

	@FXML
	private Label info_catalogue_pages;

	@FXML
	private Label info_insertionpoint;

	@FXML
	private Label info_selection;

	@FXML
	private AnchorPane left_page;

	@FXML
	private MenuItem menu_file_new;

	@FXML
	private MenuItem menu_file_open;

	@FXML
	private MenuItem menu_file_properties;

	@FXML
	private MenuItem menu_file_save;

	@FXML
	private MenuItem menu_file_saveas;

	@FXML
	private MenuItem menu_masterdata_clear;	
	
	@FXML
	private MenuItem menu_masterdata_import;

	@FXML
	private Button pages_next_btn;

	@FXML
	private Button pages_prev_btn;

	@FXML
	private AnchorPane right_page;

	@FXML
	private VBox search_panel;
	
	@FXML
	private CheckBox auto_grouping;
	
	@FXML
	private CheckBox unused_articles;

	// @FXML
	private ListView<ListArticleUi> search_result;

	@FXML
	private Button spread_next_btn;
	
	@FXML
	private Button spread_prev_btn;

	public void dropItems(ICatalogueUiItem catalogueUiItem) {
		System.out.println("Drop auf: " + catalogueUiItem);
		final List<ICatalogueItem> catalogueItems = selection;
		try {
			catalogueUiItem.getCatalogueItem().addAll(catalogueItems);
		} catch (final Exception e) {
			System.out.println("Das ging daneben :-p");
		}
		updateUiViews();
		// return catalogueItems;
	}
	
	/*
	public void removeSelection() {
		for
		for (final ICatalogueUiItem UiItem : selection) {
			try {
				ICatalogueItem catalogueItem = UiItem.getCatalogueItem();
				catalogueItem.remove();
			} catch (final Exception e) {
				System.out.println("Löschen fehlgeschlagen!");
			}
		}
		selection.clear();
		updateUiViews();
	}
	*/

	public void setCurrentPage(CataloguePage page) {
		catalogueManager.setCurrentPage(page);
		updateSpreadView();
	}

	public void setInsertionPoint(ICatalogueItem item) {
		catalogueManager.setInsertionPoint(item);
		info_insertionpoint.setText(catalogueManager.getInsertionPoint().toString());
	}

	public void updateUiViews() {
		// TODO UPDATE-PERFORMANCE
		updatePagesView();
		updateSpreadView();
		updateInfoPanel();
		updateSelectionInfo();
	}

	@FXML
	protected void initialize() {
		context = ControllerContext.getInstance(this);
		search_by_number = new RestrictiveTextInput("Artikelnummer", "[0-9]");
		search_by_number.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					searchByNumber(event);
				}
			}
		});
		search_by_description = new RestrictiveTextInput("Beschreibung", "[0-9a-zA-Z.,:; äöüßÄÖÜ]");
		search_by_description.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					searchByDescription(event);
				}
			}
		});
		search_result = new SearchResultUi<ListArticleUi>();
		search_panel.getChildren().add(search_by_number);
		search_panel.getChildren().add(search_by_description);
		search_panel.getChildren().add(search_result);
		newFile(new ActionEvent());
		
		auto_grouping.setSelected(configuration.autoGrouping);
		unused_articles.setSelected(configuration.showOnlyUnusedArticles);
	}

	@FXML
	private void addArticles(ActionEvent event) {
		final List<ListArticleUi> listArticles = search_result.getSelectionModel().getSelectedItems();
		final List<CatalogueArticle> catalogueArticles = ArticleUtilities.createCatalogueArticles(listArticles);
		// TODO Artikel hinzufügen
		catalogueManager.addArticles(catalogueArticles);
		// catalogueManager.addArticle(new
		// CatalogueArticle(articleManager.get(articleNumber)), null, null);
		updateUiViews();
	}

	@FXML
	private void clearMasterData(){
		articleManager.clear();
		updateMasterData();
	}

	@FXML
	public void clearSelection() {
		selection.clear();
		updateSelectionInfo();
	}

	@FXML
	private void importMasterData(ActionEvent event) throws URISyntaxException, MalformedURLException {
		final File file = GuiUtilities.chooseCsvFile(Configuration.getInstance().articleListPath);
		articleManager.loadCsv(file);
		updateMasterData();
	}

	@FXML
	private void listDrag(ActionEvent event) {
		System.out.println("Liste - Drag & Drop");
		updateSpreadView();
	}

	@FXML
	private void loadFile(ActionEvent event) {
		File file;
		try {
			file = GuiUtilities.chooseCsvFile(Configuration.getInstance().articleListPath);
			if (file != null) {
				catalogue = catalogueManager.loadFile(file);
				updateFile(file);
				updateUiViews();
			}
		} catch (MalformedURLException | URISyntaxException e) {
			e.printStackTrace();
		}
		updateMasterData();
	}

	@FXML
	private void newChapter(ActionEvent event) {
		System.out.println("Neues Kapitel");
		// TODO: catalogue.addChapter();
		updateUiViews();
	}

	@FXML
	private void newFile(ActionEvent event) {
		catalogue = catalogueManager.newCatalogue();
		updateFile(null);
		updateUiViews();
	}

	@FXML
	private void newGroup(ActionEvent event) {
		System.out.println("Neue Gruppe");
		catalogueManager.newGroup(null);
		updateSpreadView();
		updateInfoPanel();
	}

	@FXML
	private void newPage(ActionEvent event) {
		System.out.println("Neue Seite");
		try {
			catalogueManager.addPage(catalogueManager.getCurrentPage());
		} catch (final Exception e) {
			catalogueManager.setInsertionPoint(catalogueManager.addPage(null));
		}
		updateUiViews();
	}

	@FXML
	private void removeEmptyGroups() {
		catalogueManager.removeEmptyGroups();
		updateUiViews();
	}

	@FXML
	private void saveFile(ActionEvent event) {
		if (catalogueManager.getFile() == null)
			saveFileAs(event);
		else
			catalogueManager.saveFile(catalogueManager.getFile());
	}

	@FXML
	private void saveFileAs(ActionEvent event) {
		final FileChooser fileChooser = new FileChooser();
		try {
			final File initialDirectory = catalogueManager.getFile().getParentFile();
			if(catalogueManager.getFile()!=null) fileChooser.setInitialDirectory(initialDirectory);
		} catch(final NullPointerException e) {}
		final File file = fileChooser.showSaveDialog(null);
		if (file != null) {
			updateFile(file);
			catalogueManager.saveFile(file);
		}
	}

	@FXML
	private void searchAll(ActionEvent event) {
		search_result.setItems(GuiUtilities.createArticleListGroupEntries(articleManager.getAll(), true));
	}

	@FXML
	private void searchByDescription(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			final String searchTerm = search_by_description.getText();
			System.out.println("Suche nach: " + searchTerm);
			final List<ListArticle> articles = articleManager.getByDescription(searchTerm);
			search_result.setItems(GuiUtilities.createArticleListGroupEntries(articles, true));
			search_by_description.clear();
		}
	}

	@FXML
	private void searchByGroupSignature(ActionEvent event) {
		final ObservableList<ListArticleUi> searchResult = search_result.getSelectionModel().getSelectedItems();
		if (searchResult.size() == 1) {
			final String articleNumber = ((Label) (searchResult.get(0).lookup("#number"))).getText();
			final List<ListArticle> articles = articleManager.searchByGroupSignature(articleNumber);
			search_result.setItems(GuiUtilities.createArticleListGroupEntries(articles, true));
		}
	}

	@FXML
	private void searchByKeywords(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			final List<ListArticle> articles = articleManager.searchByKeywords(search_by_keywords.getText());
			search_result.setItems(GuiUtilities.createArticleListGroupEntries(articles, true));
		}
	}

	@FXML
	private void searchByNumber(KeyEvent event){
		if (event.getCode() == KeyCode.ENTER) {
			final List<ListArticle> articles = articleManager.searchByNumber(search_by_number.getText());
			search_result.setItems(GuiUtilities.createArticleListGroupEntries(articles, true));
		}
	}

	@FXML
	private void spreadDown(ActionEvent event){
		catalogueManager.previousSpread();
		updateSpreadView();
	}

	@FXML
	private void spreadUp(ActionEvent event) {
		catalogueManager.nextSpread();
		updateSpreadView();
	}
	
	@FXML
	private void switchAutoGrouping(ActionEvent event){
		configuration.autoGrouping = auto_grouping.isSelected();
		System.out.println("Auto-Gruppierung: " + String.valueOf(configuration.autoGrouping ? "an" : "aus"));
	}
	
	@FXML
	private void switchShowOnlyUnusedArticles(ActionEvent event){
		configuration.showOnlyUnusedArticles = unused_articles.isSelected();
		System.out.println("Nur unbenutzte Artikel anzeigen: " + String.valueOf(configuration.showOnlyUnusedArticles ? "an" : "aus"));
	}

	private void updateFile(final File file){
		catalogueManager.setFile(file);
		menu_file_save.setDisable(file==null);
		info_catalogue_filename.setText(file != null ? file.getName() : "");
	}

	private void updateInfoPanel() {
		info_articles.setText(Integer.toString(articleManager.getArticleCount()));
		info_catalogue_chapters.setText(Integer.toString(catalogue.getChaptersCount()));
		info_catalogue_pages.setText(Integer.toString(catalogue.getPagesCount()));
		info_catalogue_groups.setText(Integer.toString(catalogue.getGroupsCount()));
		info_catalogue_articles.setText(Integer.toString(catalogue.getArticlesCount()));
	}

	private void updateMasterData() {
		menu_masterdata_clear.setDisable(articleManager.getArticleCount()==0);
		updateInfoPanel();
	}

	private void updatePagesView() {
		catalogue_pages.getChildren().clear();
		catalogue_pages.getChildren().add(new CatalogueUi(catalogue));
	}
	
	private void updateSelectionInfo() {
		String selectionInfo = "";
		for (final ICatalogueItem item : selection) {
			selectionInfo += item + "\r\n";
		}
		info_selection.setText(selectionInfo);
		info_insertionpoint.setText(catalogueManager.getInsertionPoint().toString());
	}
	
	private void updateSpreadView() {
		
		//final CataloguePageUi leftPage = GuiUtilities.createPageUi()
				
		final CataloguePageUi leftPage = new CataloguePageUi(catalogueManager.getCurrentLeftPage());
		left_page.getChildren().clear();
		left_page.getChildren().add(leftPage);
		//System.out.println("UPDATE");
		if(leftPage.getGroups()!=null) {
			//System.out.println(spread_view.getHeight());
			leftPage.getGroups().setPrefHeight(Math.min(leftPage.getGroups().getItems().size() * 115, spread_view.getHeight()-50));
		}
		//leftPage.getGroups().setPrefHeight(Math.min(leftPage.getGroups().getItems().size() * 115, left_page.getHeight()));
		//leftPage.getList().setPrefHeight(arg0);

		final CataloguePageUi rightPage = new CataloguePageUi(catalogueManager.getCurrentRightPage());
		right_page.getChildren().clear();
		right_page.getChildren().add(rightPage);
		if(rightPage.getGroups()!=null) {
			//System.out.println(spread_view.getHeight());
			rightPage.getGroups().setPrefHeight(Math.min(rightPage.getGroups().getItems().size() * 115, spread_view.getHeight()-50));
		}
	}

	public void setSelection(List<ICatalogueItem> items) {
		selection = items;
		catalogueManager.setInsertionPoint(items.get(items.size()-1));
		updateSelectionInfo();
	}

	public void removeSelection() {
		for(final ICatalogueItem item : selection){
			item.remove();
		}
		updateUiViews();
	}

	public void setSelection(ICatalogueItem item) {
		final List<ICatalogueItem> selection = new ArrayList<ICatalogueItem>();
		selection.add(item);
		setSelection(selection);	
	}
}
