package de.abama.dummycreator.gui.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import de.abama.dummycreator.articles.ArticleManager;
import de.abama.dummycreator.articles.utlilities.ArticleUtilities;
import de.abama.dummycreator.catalogue.Catalogue;
import de.abama.dummycreator.catalogue.CatalogueArticle;
import de.abama.dummycreator.catalogue.CatalogueManager;
import de.abama.dummycreator.catalogue.CataloguePage;
import de.abama.dummycreator.catalogue.ICatalogueItem;
import de.abama.dummycreator.config.Configuration;
import de.abama.dummycreator.gui.fxml.CataloguePageThumbUi;
import de.abama.dummycreator.gui.fxml.CataloguePageUi;
import de.abama.dummycreator.gui.fxml.CatalogueUi;
import de.abama.dummycreator.gui.fxml.ICatalogueUiItem;
import de.abama.dummycreator.gui.fxml.ListArticleUi;
import de.abama.dummycreator.gui.fxml.RestrictiveTextInput;
import de.abama.dummycreator.gui.fxml.SearchResultUi;
import de.abama.dummycreator.gui.utilities.GuiUtilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class DummyCreator {

	protected ArticleManager articleManager = ArticleManager.getInstance();

	protected Catalogue catalogue;

	protected CatalogueManager catalogueManager = CatalogueManager
			.getInstance();

	protected Clipboard clipboard = Clipboard.getSystemClipboard();

	protected Configuration configuration = Configuration.getInstance();

	protected RestrictiveTextInput search_by_description;

	protected RestrictiveTextInput search_by_keywords;

	protected RestrictiveTextInput search_by_number;

	protected List<ICatalogueUiItem> selection = FXCollections
			.observableArrayList();

	@FXML
	private AnchorPane catalogue_pages;

	@SuppressWarnings("unused")
	private ControllerContext context;

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
	private MenuItem menu_masterdata_import;

	@FXML
	private Button pages_next_btn;

	@FXML
	private Button pages_prev_btn;

	@FXML
	private AnchorPane right_page;

	@FXML
	private VBox search_panel;

	// @FXML
	private ListView<ListArticleUi> search_result;

	@FXML
	private Button spread_next_btn;

	@FXML
	private Button spread_prev_btn;

	@FXML
	public void addArticles(ActionEvent event) throws IOException {
		final List<ListArticleUi> listArticles = search_result
				.getSelectionModel().getSelectedItems();
		final List<CatalogueArticle> catalogueArticles = ArticleUtilities
				.createCatalogueArticles(listArticles);
		// TODO Artikel hinzufügen
		catalogueManager.addArticles(catalogueArticles);
		// catalogueManager.addArticle(new
		// CatalogueArticle(articleManager.get(articleNumber)), null, null);
		updateUiViews();
	}

	public void dropItems(ICatalogueUiItem catalogueUiItem) throws IOException {
		System.out.println("Drop auf: " + catalogueUiItem);
		final List<ICatalogueItem> catalogueItems = getCatalogueItems(selection);
		try {
			catalogueUiItem.getCatalogueItem().addAll(catalogueItems);
		} catch (final Exception e) {
			System.out.println("Das ging daneben :-p");
		}
		updateUiViews();
		// return catalogueItems;
	}

	public List<ICatalogueItem> getCatalogueItems(List<ICatalogueUiItem> UIitems) {
		final List<ICatalogueItem> catalogItems = new ArrayList<ICatalogueItem>();
		for (final ICatalogueUiItem item : UIitems) {
			// System.out.println("UI:  " + item);
			final ICatalogueItem catalogueItem = item.getCatalogueItem();
			// System.out.println("Cat:  " + catalogueItem);
			catalogItems.add(catalogueItem);
		}
		return catalogItems;
	}

	public void removeSelection() throws IOException {
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

	public void setCurrentPage(CataloguePageThumbUi cataloguePageThumbUi)
			throws IOException {
		catalogueManager.setCurrentPage(cataloguePageThumbUi.getPage());
		updateSpreadView();
	}

	public void setInsertionPoint(ICatalogueItem item) {
		catalogueManager.setInsertionPoint(item);
		info_insertionpoint.setText(catalogueManager.getInsertionPoint()
				.toString());
	}

	public void setSelection(List<ICatalogueUiItem> items) {
		selection = items;
		System.out.println("Auswahl geändert");
		if (selection.size() > 0)
			setInsertionPoint(selection.get(selection.size() - 1)
					.getCatalogueItem());
		updateSelectionInfo();
	}

	public void updateInfoPanel() {
		info_articles
				.setText(Integer.toString(articleManager.getArticleCount()));
		info_catalogue_chapters.setText(Integer.toString(catalogue
				.getChaptersCount()));
		info_catalogue_pages
				.setText(Integer.toString(catalogue.getPagesCount()));
		info_catalogue_groups.setText(Integer.toString(catalogue
				.getGroupsCount()));
		info_catalogue_articles.setText(Integer.toString(catalogue
				.getArticlesCount()));
	}

	public void updatePagesView() throws IOException {
		catalogue_pages.getChildren().clear();
		catalogue_pages.getChildren().add(new CatalogueUi(catalogue));
	}

	public void updateSelectionInfo() {
		String selectionInfo = "";
		for (final ICatalogueUiItem item : selection) {
			selectionInfo += item + "\r\n";
		}
		info_selection.setText(selectionInfo);
	}

	public void updateSpreadView() throws IOException {

		final CataloguePageUi leftPage = new CataloguePageUi(catalogueManager.getCurrentLeftPage());
		left_page.getChildren().clear();
		left_page.getChildren().add(leftPage);

		final CataloguePageUi rightPage = new CataloguePageUi(catalogueManager.getCurrentRightPage());
		right_page.getChildren().clear();
		right_page.getChildren().add(rightPage);
	}

	public void updateUiViews() throws IOException {
		updatePagesView();
		updateSpreadView();
		updateInfoPanel();
		updateSelectionInfo();
	}

	@FXML
	protected void initialize() throws IOException {
		context = ControllerContext.getInstance(this);
		search_by_number = new RestrictiveTextInput("Artikelnummer", "[0-9]");
		search_by_number.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					try {
						searchByNumber(event);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		search_by_description = new RestrictiveTextInput("Beschreibung", "[0-9a-zA-Z.,:; ]");
		search_by_description.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					try {
						searchByDescription(event);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		search_result = new SearchResultUi<ListArticleUi>();
		search_panel.getChildren().add(search_by_number);
		search_panel.getChildren().add(search_by_description);
		search_panel.getChildren().add(search_result);
		newFile(new ActionEvent());
	}

	@FXML
	private void clearSelection() {
		selection.clear();
		updateSelectionInfo();
	}

	@FXML
	private void closeFile(ActionEvent event) throws IOException {
		updateUiViews();
	}

	@FXML
	private void importMasterData(ActionEvent event) throws IOException,
			URISyntaxException {
		final File file = GuiUtilities
				.chooseCsvFile(configuration.articleListPath);
		articleManager.loadCsv(file);
		updateInfoPanel();
	}

	@FXML
	private void listDrag(ActionEvent event) throws IOException {
		System.out.println("Liste - Drag & Drop");
		updateSpreadView();
	}

	@FXML
	private void newChapter(ActionEvent event) throws IOException {
		System.out.println("Neues Kapitel");
		// TODO: catalogue.addChapter();
		updateUiViews();
	}

	@FXML
	private void newFile(ActionEvent event) throws IOException {
		catalogue = catalogueManager.newFile();
		updateUiViews();
	}

	@FXML
	private void newGroup(ActionEvent event) throws IOException {
		System.out.println("Neue Gruppe");
		catalogueManager.newGroup(null);
		updateSpreadView();
		updateInfoPanel();
	}

	@FXML
	private void newPage(ActionEvent event) throws IOException {
		System.out.println("Neue Seite");
		try {
			catalogueManager.addPage((CataloguePage) selection.get(0));
		} catch (final Exception e) {
			catalogueManager.setInsertionPoint(catalogueManager.addPage(null));
		}
		updateUiViews();
	}

	@FXML
	private void openFile(ActionEvent event) throws IOException,
			URISyntaxException {
		File file = GuiUtilities.chooseCsvFile(configuration.articleListPath);
		if (file != null) {
			catalogue = catalogueManager.loadFile(file);
			info_catalogue_filename.setText(file.getName());
			updateUiViews();
		}
	}

	@FXML
	private void removeEmptyGroups() throws IOException {
		catalogueManager.removeEmptyGroups();
		updateUiViews();
	}

	@FXML
	private void searchAll(ActionEvent event) throws IOException {
		search_result.setItems(articleManager.searchAll());
	}

	@FXML
	private void searchByDescription(KeyEvent event) throws IOException {
		if (event.getCode() == KeyCode.ENTER) {
			search_result.setItems(articleManager.searchByDescription(search_by_description.getText()));
			search_by_description.clear();
		}
	}

	@FXML
	private void searchByGroupSignature(ActionEvent event) throws IOException {
		final ObservableList<ListArticleUi> searchResult = search_result
				.getSelectionModel().getSelectedItems();
		if (searchResult.size() == 1) {
			final String articleNumber = ((Label) (searchResult.get(0)
					.lookup("#number"))).getText();
			search_result.setItems(articleManager
					.searchByGroupSignature(articleNumber));
		}
	}

	@FXML
	private void searchByKeywords(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			search_result.setItems(articleManager
					.searchByKeywords(search_by_keywords.getText()));
		}
	}

	@FXML
	private void searchByNumber(KeyEvent event) throws IOException {
		if (event.getCode() == KeyCode.ENTER) {
			// String text = search_by_number.getText().replace("\n",
			// "").replace("\r", "");
			// search_by_number.setText(text);
			// search_by_number.clear();
			search_result.setItems(articleManager
					.searchByNumber(search_by_number.getText()));
			search_by_number.clear();
		}
	}

	@FXML
	private void spreadDown(ActionEvent event) throws IOException {
		catalogueManager.previousSpread();
		updateSpreadView();
	}

	@FXML
	private void spreadUp(ActionEvent event) throws IOException {
		catalogueManager.nextSpread();
		updateSpreadView();
	}
}
