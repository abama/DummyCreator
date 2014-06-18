package de.abama.dummycreator.gui.controller;

import de.abama.dummycreator.catalogue.CatalogueManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

public class ArticleGroupListEntry {
	
	@SuppressWarnings("unused")
	private ControllerContext context = ControllerContext.getInstance();
	
	private CatalogueManager catalogueManager = CatalogueManager.getInstance();
	
    @FXML
    private ImageView image;

    @FXML
    private Label index;

    @FXML
    private Label description;

    @FXML
    private Label title;

    @FXML
    private ListView<HBox> articles;
	
	
	@FXML
	private void initialize(){
		//System.out.println("PING");
	}
	
    @FXML
    void deleteArticle(KeyEvent event) {
    
    	if(event.getCode()==KeyCode.DELETE || event.getCode()==KeyCode.BACK_SLASH) {
    		final int pageNumber = Integer.valueOf(articles.getId().split("_")[0]);
    		final char groupIndex = articles.getId().split("_")[1].charAt(0);
    		final int articleIndex = articles.getSelectionModel().getSelectedIndex();
    		if(articleIndex>=0) catalogueManager.getCatalogue().getPage(pageNumber).getGroup(groupIndex).getArticles().remove(articleIndex);
    	}
    }
}
