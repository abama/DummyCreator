package de.abama.dummycreator.gui.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import de.abama.dummycreator.articles.ArticleManager;
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
import de.abama.dummycreator.gui.utilities.GuiUtilities;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class DummyCreator {
	
	List<ICatalogueUiItem> selection = FXCollections.observableArrayList();

	private Catalogue catalogue;
	
	@FXML
	private AnchorPane catalogue_pages;
	
	private CatalogueManager catalogueManager = CatalogueManager.getInstance();
	
	@SuppressWarnings("unused")
	private Clipboard clipboard = Clipboard.getSystemClipboard();
		
	private Configuration configuration = Configuration.getInstance();

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
	private Label info_selection;

	@FXML
	private AnchorPane left_page;
	 
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
	private AnchorPane right_page;
	
	@FXML
    private Button search_all;

	@FXML
    private TextArea search_by_description;
	
	@FXML
    private TextArea search_by_keywords;

	@FXML
    private TextArea search_by_number;
	
	@FXML
	private ListView<ListArticleUi> search_result;
	
	@FXML
	private Button spread_next_btn;
	    
    @FXML
	private Button spread_prev_btn;
    
    public void removeSelection() throws IOException {
		for(final ICatalogueUiItem UiItem : selection){
			try {
				ICatalogueItem catalogueItem = UiItem.getCatalogueItem();
				catalogueItem.remove();
			}
			catch(final Exception e){
				System.out.println("Löschen fehlgeschlagen!");
			}
		}		
		selection.clear();
		updateUiViews();
	}   

	public void setSelection(List<ICatalogueUiItem> items) {
		//System.out.println(items.size());
		selection = items;
		updateSelectionInfo();
	}
	
	private void updateSelectionInfo() {
		String selectionInfo = "";
		for(final ICatalogueUiItem item : selection){
			selectionInfo += item + "\r\n";
		}
		info_selection.setText(selectionInfo);		
	}

	public void updateSpreadView() throws IOException {
		
		left_page.getChildren().clear();
		left_page.getChildren().add(new CataloguePageUi(catalogueManager.getCurrentLeftPage()));

		right_page.getChildren().clear();
		right_page.getChildren().add(new CataloguePageUi(catalogueManager.getCurrentRightPage()));		
	}
	
	public void updateUiViews() throws IOException{//(Class<? extends ICatalogueItem> operation) throws IOException {
		//if (operation == Catalogue.class ||operation == CataloguePage.class || operation == CatalogueChapter.class) 
		updatePagesView();
		updateSpreadView();
		updateInfoPanel();
		updateSelectionInfo();
	}

	@FXML
    protected void initialize() throws IOException {
		search_result.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	context = ControllerContext.getInstance(this);
    	newFile(new ActionEvent());
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
	}

	@FXML
	private void newPage(ActionEvent event) throws IOException {
		System.out.println("Neue Seite");
		try {
			catalogueManager.addPage((CataloguePage) selection.get(0));}
		catch(final Exception e) {
			catalogueManager.addPage(null);
		}
		updateUiViews();
	}
	

	@FXML
	private void openFile(ActionEvent event) throws IOException, URISyntaxException {
		File file = GuiUtilities.chooseCsvFile(configuration.articleListPath);
		if (file != null) {
			catalogue = catalogueManager.loadFile(file);
			info_catalogue_filename.setText(file.getName());
			updateUiViews();
		}
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
		catalogue_pages.getChildren().clear();
		catalogue_pages.getChildren().add(new CatalogueUi(catalogue));
	}

	public void setCurrentPage(CataloguePageThumbUi cataloguePageThumbUi) throws IOException {
		catalogueManager.setCurrentPage(cataloguePageThumbUi.getPage());
		updateSpreadView();
	}
	
	@FXML
	public void clearSelection(){
		selection.clear();
		updateSelectionInfo();
	}
}
