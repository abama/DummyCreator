package de.abama.dummycreator.gui.controller;

import java.io.File;
import java.io.IOException;

import de.abama.dummycreator.CatalogueManager;
import de.abama.dummycreator.csv.CSV;
import de.abama.dummycreator.csv.CsvFileUtility;
import de.abama.dummycreator.entities.Catalogue;
import de.abama.dummycreator.entities.CataloguePage;
import de.abama.dummycreator.masterdata.MasterData;
import de.abama.dummycreator.utlilities.ArticleUtilities;
import de.abama.dummycreator.utlilities.ControllerUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class DummyCreator {

	private CatalogueManager catalogueManager = new CatalogueManager();
	private Catalogue catalogue = catalogueManager.newFile();

	private Clipboard clipboard = Clipboard.getSystemClipboard();
	private CataloguePage currentPage;

	// Aktuelle Position in der Seiten�bersicht
	private int currentPageRange;

	// Aktuell angezeigte Doppelseite
	// private int currentPage;

	@FXML
	private Label info_articles;

	@FXML
	private Label info_catalogue_articles;

	@FXML
	private Label info_catalogue_chapters;

	@FXML
	private Label info_catalogue_groups;

	@FXML
	private Label info_catalogue_pages;

	private MasterData masterData = new MasterData();
	/*
	 * @FXML private MenuItem menu_file_new;
	 * 
	 * @FXML private MenuItem menu_file_open;
	 * 
	 * @FXML private MenuItem menu_masterdata_import;
	 */
	@FXML
	private Button pages_next_btn;

	@FXML
	private Button pages_prev_btn;

	@FXML
	private ListView<HBox> search_result;

	@FXML
	private Pane spread_left_content;

	@FXML
	private TextField spread_left_keywords;

	@FXML
	private Label spread_left_number;

	@FXML
	private Button spread_next_btn;

	@FXML
	private Button spread_prev_btn;

	@FXML
	private Pane spread_right_content;

	@FXML
	private TextField spread_right_keywords;

	@FXML
	private Label spread_right_number;

	@FXML
	private HBox view_Pages;

	@FXML
	private ScrollPane view_Spread;

	@FXML
	private void addArticle(ActionEvent event) {
		System.out.println("Artikel hinzufügen");

		search_result.getSelectionModel().getSelectedItem();

		updateSpreadView();
	}

	@FXML
	private void closeFile(ActionEvent event) {
		System.out.println("Schlie�en");
		updateUiViews();
	}

	@FXML
	private void importCSV(ActionEvent event) throws IOException {
		final File file = ControllerUtilities.chooseCsvFile();
		if (file != null) {
			final CSV csv = CsvFileUtility.read(file);
			masterData.add(ArticleUtilities.createProtoArticles(csv));
			updateInfoPanel();
			// search_result.setItems(FXCollections.observableArrayList(masterData.getArticleNumbers()));

			search_result.setItems(new ArticleUtilities()
					.createListItems(masterData.getArticles()));
		}
	}

	@FXML
	private void listDrag(ActionEvent event) {
		System.out.println("Liste - Drag & Drop");
		updateSpreadView();
	}

	@FXML
	private void newChapter(ActionEvent event) {
		System.out.println("Neues Kapitel");
		// TODO: catalogue.addChapter();
		updateUiViews();
	}

	@FXML
	private void newFile(ActionEvent event) {
		// System.out.println("Neue Datei");
		catalogue = catalogueManager.newFile();
	}

	@FXML
	private void newGroup(ActionEvent event) {
		System.out.println("Neue Gruppe");
		updateSpreadView();
	}

	@FXML
	private void newPage(ActionEvent event) throws IOException {
		System.out.println("Neue Seite");
		currentPage = catalogue.newPage(currentPage);

		VBox pageThumbnail = FXMLLoader.load(getClass().getResource("../fxml/PageThumbnail.fxml"));
		((Label) pageThumbnail.lookup("#number")).setText(Integer.toString(currentPage.getNumber()));
		view_Pages.getChildren().add(pageThumbnail);
		
		// PageThumbnail controller = (PageThumbnail)
		// pageThumbnail.getController();

		

		updateUiViews();
	}

	@FXML
	private void openFile(ActionEvent event) throws IOException {
		File file = ControllerUtilities.chooseCsvFile();
		if (file != null) {
			catalogue = catalogueManager.openFile(file);
			for(final CataloguePage page : catalogue.getPages()){
				VBox pageThumbnail = FXMLLoader.load(getClass().getResource("../fxml/PageThumbnail.fxml"));
				((Label) pageThumbnail.lookup("#number")).setText(Integer.toString(page.getNumber()));
				view_Pages.getChildren().add(pageThumbnail);
			}
			updateUiViews();
		}
	}

	@FXML
	private void removeArticle(ActionEvent event) {
		System.out.println("Artikel löschen");
		updateSpreadView();
	}

	private void updateInfoPanel() {
		info_articles.setText(Integer.toString(masterData.getArticleCount()));
//		info_catalogue_chapters.setText(Integer.toString(catalogue.getChaptersCount()));
		info_catalogue_pages.setText(Integer.toString(catalogue.getPagesCount()));
		info_catalogue_groups.setText(Integer.toString(catalogue.getGroupsCount()));
		info_catalogue_articles.setText(Integer.toString(catalogue.getArticlesCount()));

	}

	private void updatePagesView() {

	}

	private void updateSpreadView() {
		// spread_right_content =
		// ControllerUtilities.getRightPageContent(catalogue, currentPage);
	}

	private void updateUiViews() {
		updateSpreadView();
		updatePagesView();
		updateInfoPanel();
	}
}
