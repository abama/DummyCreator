package de.abama.dummycreator.gui.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import de.abama.dummycreator.articles.ArticleManager;
import de.abama.dummycreator.catalogue.Catalogue;
import de.abama.dummycreator.catalogue.CatalogueArticle;
import de.abama.dummycreator.catalogue.CatalogueManager;
import de.abama.dummycreator.config.Configuration;
import de.abama.dummycreator.gui.fxml.CatalogueGroupUi;
import de.abama.dummycreator.gui.fxml.ListArticleUi;
import de.abama.dummycreator.gui.fxml.CataloguePageUi;
import de.abama.dummycreator.gui.utilities.GuiUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DummyCreator implements IController{
	
	@SuppressWarnings("unused")
	private ControllerContext context;

	private CatalogueManager catalogueManager = CatalogueManager.getInstance();
	private Catalogue catalogue = catalogueManager.newFile();
	
	@SuppressWarnings("unused")
	private Clipboard clipboard = Clipboard.getSystemClipboard();
	private Configuration configuration = Configuration.getInstance();

	@FXML
	private Label info_articles;

	// Aktuell angezeigte Doppelseite
	// private int currentPage;

	@FXML
	private Label info_catalogue_filename;
	
	@FXML
	private Label info_catalogue_articles;

	@FXML
	private Label info_catalogue_chapters;

	@FXML
	private Label info_catalogue_groups;

	@FXML
	private Label info_catalogue_pages;

	private ArticleManager masterData = ArticleManager.getInstance();

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
	private ListView<ListArticleUi> search_result;
	
	@FXML
	private VBox spread_left_page;

	@FXML
	private ListView<CatalogueGroupUi> spread_left_groups;

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
	private ListView<CatalogueGroupUi> spread_right_groups;

	@FXML
	private TextField spread_right_keywords;
	
    @FXML
	private Label spread_right_number;
    
    @FXML
	private ListView<CataloguePageUi> view_pages;
    
    @FXML
	private ScrollPane view_Spread;
    
    @FXML
    protected void initialize() throws IOException {
    	context = ControllerContext.getInstance(this);
    }
    
    @FXML
    private void pageListClicked(MouseEvent event) throws IOException{
    	if(event.getClickCount() >= 2){
    		catalogueManager.setCurrentPage(view_pages.getSelectionModel().getSelectedItem().getNumber());
    		updateSpreadView();
    	}
    }
    
    @FXML
    private void pageListKeyPressed(KeyEvent event) throws IOException{
    	if(event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.BACK_SPACE){
    		catalogueManager.deletePage(view_pages.getSelectionModel().getSelectedItem().getNumber());
    		// TODO NullPointer exception
    		updateUiViews();
    	}
    }    

	@FXML
	private void addArticle(ActionEvent event) throws IOException {
		final String articleNumber = search_result.getSelectionModel().getSelectedItem().getNumber();
		catalogueManager.addArticle(new CatalogueArticle(masterData.get(articleNumber)), null, null);
		updateSpreadView();
	}
	
	@FXML
	private void closeFile(ActionEvent event) throws IOException {
		
		updateUiViews();
	}
	
	@FXML
	private void importMasterData(ActionEvent event) throws IOException, URISyntaxException {
		final File file = GuiUtilities.chooseCsvFile(configuration.articleListPath);
		masterData.loadCsv(file);
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
		catalogueManager.newGroup(null);
		updateSpreadView();
	}

	@FXML
	private void newPage(ActionEvent event) throws IOException {
		System.out.println("Neue Seite");
		catalogueManager.addPage(null);
		updateUiViews();
	}

	@FXML
	private void openFile(ActionEvent event) throws IOException, URISyntaxException {
		File file = GuiUtilities.chooseCsvFile(configuration.articleListPath);
		if (file != null) {
			info_catalogue_filename.setText(file.getName());
			catalogue = catalogueManager.loadFile(file);
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
		// TODO articleSelection?
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

	private void updatePagesView() throws IOException {
		view_pages.setItems(catalogueManager.createPageThumbnails());;
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

	public void removeArticle(CatalogueGroupUi articleGroupListEntry, HBox lookup) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	public void leftPageGroupsKeyPressed(KeyEvent event) throws IOException{
		if(event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.BACK_SPACE){
			final int index = spread_left_groups.getSelectionModel().getSelectedIndex();
			if(index>=0)
				catalogueManager.getCurrentLeftPage().getGroups().remove(spread_left_groups.getSelectionModel().getSelectedIndex());
				try{ 
					// TODO Funktioniert nicht
					spread_left_groups.getSelectionModel().select(index);
				} catch (IndexOutOfBoundsException e) { System.out.println("Möööp");};
		}
		updateSpreadView();
	}
	
	@FXML
	public void rightPageGroupsKeyPressed(KeyEvent event) throws IOException{
		if(event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.BACK_SPACE){
			if(spread_right_groups.getSelectionModel().getSelectedIndex()>=0)
				catalogueManager.getCurrentRightPage().getGroups().remove(spread_right_groups.getSelectionModel().getSelectedIndex());
		}
		updateSpreadView();
	}
}
