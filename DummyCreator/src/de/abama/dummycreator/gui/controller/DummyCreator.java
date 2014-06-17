package de.abama.dummycreator.gui.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import de.abama.dummycreator.CatalogueManager;
import de.abama.dummycreator.config.Configuration;
import de.abama.dummycreator.entities.Catalogue;
import de.abama.dummycreator.entities.CatalogueArticle;
import de.abama.dummycreator.entities.CataloguePage;
import de.abama.dummycreator.entities.ListArticle;
import de.abama.dummycreator.masterdata.MasterData;
import de.abama.dummycreator.utlilities.GuiUtilities;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DummyCreator {

	private CatalogueManager catalogueManager = new CatalogueManager();
	private Catalogue catalogue = catalogueManager.newFile();
	
	private Clipboard clipboard = Clipboard.getSystemClipboard();
	private Configuration configuration = Configuration.getInstance();

	private CataloguePage currentPage;
	// Aktuelle Position in der Seiten�bersicht
	private int currentPageRange;
	
	@FXML
	private Label info_articles;

	// Aktuell angezeigte Doppelseite
	// private int currentPage;

	@FXML
	private Label info_catalogue_articles;

	@FXML
	private Label info_catalogue_chapters;

	@FXML
	private Label info_catalogue_groups;

	@FXML
	private Label info_catalogue_pages;

	private MasterData masterData = new MasterData();

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
    private TextArea search_by_description;

	@FXML
    private TextArea search_by_keywords;

	@FXML
    private TextArea search_by_number;
	
	@FXML
    private Button search_all;

	@FXML
	private ListView<HBox> search_result;
	
	@FXML
	private VBox spread_left_page;

	@FXML
	private ListView<HBox> spread_left_groups;

	@FXML
	private TextField spread_left_keywords;

	@FXML
	private Label spread_left_number;

	@FXML
	private Button spread_next_btn;

	@FXML
	private Button spread_prev_btn;
	
	@FXML
	private VBox spread_right_page;

	@FXML
	private ListView<HBox> spread_right_groups;

	@FXML
	private TextField spread_right_keywords;
	
    @FXML
	private Label spread_right_number;
    
    @FXML
	private HBox view_Pages;
    
    @FXML
	private ScrollPane view_Spread;
    
    @FXML
    public void initialize() throws IOException {
    	updateUiViews();
    }

	@FXML
	private void addArticle(ActionEvent event) throws IOException {
		final String articleNumber = ((Label)(search_result.getSelectionModel().getSelectedItem().lookup("#number"))).getText();
		final ListArticle article = masterData.get(articleNumber);
		catalogueManager.addArticle(article, null, null);
		updateSpreadView();
	}
	
	@FXML
	private void closeFile(ActionEvent event) throws IOException {
		updateUiViews();
	}
	
	@FXML
	private void importMasterData(ActionEvent event) throws IOException, URISyntaxException {
		final File file = GuiUtilities.chooseCsvFile(configuration.articleListPath);
		masterData.importCsv(file);
		updateInfoPanel();
		//searchAll(null);
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
	private void newFile(ActionEvent event) {
		// System.out.println("Neue Datei");
		catalogue = catalogueManager.newFile();
	}

	@FXML
	private void newGroup(ActionEvent event) throws IOException {
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
	private void openFile(ActionEvent event) throws IOException, URISyntaxException {
		File file = GuiUtilities.chooseCsvFile(configuration.articleListPath);
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
	private void removeArticle(ActionEvent event) throws IOException {
		//System.out.println("Artikel löschen");
		updateSpreadView();
	}
	
	@FXML
	private void searchAll(ActionEvent event) throws IOException {
		search_result.setItems(masterData.searchAll());
	}

	@FXML
	private void searchByDescription(KeyEvent event) throws IOException {
	    if (event.getCode() == KeyCode.ENTER) {
	    	search_result.setItems(masterData.searchByDescription(search_by_description.getText()));
	    }
	}

	@FXML
	private void searchByGroupSignature(ActionEvent event) throws IOException {
		final String articleNumber = ((Label)(search_result.getSelectionModel().getSelectedItem().lookup("#number"))).getText();
		search_result.setItems(masterData.searchByGroupSignature(articleNumber));
	}
	

	@FXML
	private void searchByKeywords(KeyEvent event) {
	    if (event.getCode() == KeyCode.ENTER) {
	    	search_result.setItems(masterData.searchByKeywords(search_by_keywords.getText()));
	    }
	}

	@FXML
	private void searchByNumber(KeyEvent event) throws IOException {
	    if (event.getCode() == KeyCode.ENTER) {
	    	//String text = search_by_number.getText().replace("\n", "").replace("\r", "");
	    	//search_by_number.setText(text);
	    	//search_by_number.clear();
	    	search_result.setItems(masterData.searchByNumber(search_by_number.getText()));
	    }
	}
	
	@FXML
	private void spreadDown(ActionEvent event) throws IOException{
		catalogueManager.previousSpread();
		updateSpreadView();
	}

	@FXML
	private void spreadUp(ActionEvent event) throws IOException{
		catalogueManager.nextSpread();
		updateSpreadView();
	}	
	
	private void updateInfoPanel() {
		info_articles.setText(Integer.toString(masterData.getArticleCount()));
		info_catalogue_chapters.setText(Integer.toString(catalogue.getChaptersCount()));
		info_catalogue_pages.setText(Integer.toString(catalogue.getPagesCount()));
		info_catalogue_groups.setText(Integer.toString(catalogue.getGroupsCount()));
		info_catalogue_articles.setText(Integer.toString(catalogue.getArticlesCount()));

	}

	private void updatePagesView() {

	}

	private void updateSpreadView() throws IOException {
		// Linke Seite
		spread_left_groups.setItems(catalogueManager.getCurrentLeftPageGroups());
		spread_left_number.setText(catalogueManager.getCurrentLeftPageNumber());
		//spread_left_keywords.setText(catalogueManager.getCurrentLeftPageKeywords());
		// Rechte Seite
		spread_right_groups.setItems(catalogueManager.getCurrentRightPageGroups());
		spread_right_number.setText(catalogueManager.getCurrentRightPageNumber());
		// Navigation
		spread_prev_btn.setDisable(catalogueManager.getCurrentLeftPage().getNumber()<0);
		spread_next_btn.setDisable(catalogueManager.getCurrentRightPage().getNumber()<0);
		
	}

	private void updateUiViews() throws IOException {
		updateSpreadView();
		updatePagesView();
		updateInfoPanel();
	}
}
