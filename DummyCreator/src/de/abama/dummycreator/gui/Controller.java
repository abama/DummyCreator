package de.abama.dummycreator.gui;

import java.io.IOException;

import de.abama.dummycreator.CatalogueManager;
import de.abama.dummycreator.entities.Catalogue;
import de.abama.dummycreator.entities.Page;
import de.abama.dummycreator.masterdata.MasterData;
import de.abama.dummycreator.utlilities.ControllerUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Controller {
	
	private MasterData masterData = new MasterData();
	
	private CatalogueManager fileManager = new CatalogueManager();
	private Catalogue catalogue = fileManager.newFile();
	
	private Clipboard clipboard = Clipboard.getSystemClipboard();

	private Page currentPage;
	
	// Aktuell angezeigte Doppelseite
	//private int currentPage;
	
	// Aktuelle Position in der Seitenübersicht
	private int currentPageRange;
	
    @FXML
    private Pane spread_right_content;

    @FXML
    private Pane spread_left_content;

    @FXML
    private MenuItem menu_file_new;

    @FXML
    private Button pages_prev_btn;

    @FXML
    private Button spread_prev_btn;

    @FXML
    private Button pages_next_btn;

    @FXML
    private Label spread_left_number;

    @FXML
    private Button spread_next_btn;

    @FXML
    private TextField spread_right_keywords;

    @FXML
    private TextField spread_left_keywords;

    @FXML
    private Label spread_right_number;
    
    @FXML
    private HBox view_Pages;
    
    @FXML
    private ScrollPane view_Spread;
    
    @FXML
    private ListView<?> search_result;
    
    @FXML
    private Label info_articles;
    
    @FXML
    private Label info_groups;
    
    @FXML
    private MenuItem menu_masterdata_import;

    @FXML
    private void newFile(ActionEvent event) {
    	//System.out.println("Neue Datei");
    	catalogue = fileManager.newFile();
    }

    @FXML
    private void openFile(ActionEvent event) {
        fileManager.openFile(ControllerUtilities.chooseCsvFile());
        updateCatalogueViews();
        //labelFile.setText(file.getPath());
    	///System.out.println("Öffnen");
    }
    
    private void importCsv(ActionEvent event) {
    	
    }

	@FXML
    private void closeFile(ActionEvent event) {
    	System.out.println("Schließen");
    	updateCatalogueViews();
	}
    
    @FXML
    private void newChapter(ActionEvent event) {
    	System.out.println("Neues Kapitel");
    	// TODO: catalogue.addChapter();
    	updateCatalogueViews();
    }

    @FXML
    private void newPage(ActionEvent event) throws IOException {
    	System.out.println("Neue Seite");
    	currentPage = catalogue.newPage(currentPage);
    	System.out.println("Seiten: " + catalogue.getPages().size());
    	updateCatalogueViews();
    	
    	VBox pageThumbnail = new VBox();
    	
		pageThumbnail = FXMLLoader.load(getClass().getResource("PageThumbnail.fxml"));
		((Label) pageThumbnail.lookup("#number")).setText(Integer.toString(currentPage.getNumber()));
		//((TextField) pageThumbnail.lookup("#label")).setText("Neue Seite");
		
		//PageThumbnail controller = (PageThumbnail) pageThumbnail.getController();

    	
    	view_Pages.getChildren().add(pageThumbnail);
    }
    
    @FXML
    private void newGroup(ActionEvent event) {
    	System.out.println("Neue Gruppe");
    	updateSpreadView();
    }
    
    private void updatePagesView() {
    	
    }

    private void updateSpreadView() {
    	//spread_right_content = ControllerUtilities.getRightPageContent(catalogue, currentPage);
    }
    private void updateCatalogueViews() {
    	updateSpreadView();
    	updatePagesView();
		
	}
}
