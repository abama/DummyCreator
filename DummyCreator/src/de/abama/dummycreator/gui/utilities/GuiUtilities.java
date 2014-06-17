package de.abama.dummycreator.gui.utilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import de.abama.dummycreator.catalogue.Catalogue;
import de.abama.dummycreator.catalogue.CatalogueArticle;
import de.abama.dummycreator.catalogue.CatalogueGroup;
import de.abama.dummycreator.catalogue.ListArticle;

public class GuiUtilities {
	
	public static int getLeftPage(int page){
		if(page%2==0) return page; else return page-1;
	}
	
	public static int getRightPage(int page) {
		if(page%2==0) return page+1; else return page;
	}

	public static Pane getRightPageContent(Catalogue catalogue, int currentPage) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static File chooseCsvFile(final String startFolderPath) throws MalformedURLException, URISyntaxException{
        FileChooser fileChooser = new FileChooser();    
        fileChooser.setInitialDirectory(new File(new URI("file:/Volumes/Marketing/Artikel/Listen/Kataloge/")));
        
        //Set extension filter
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV-Datei (*.csv)", "*.csv");
        FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("TXT-Datei (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(csvFilter);
        fileChooser.getExtensionFilters().add(txtFilter);
        
        final File test = fileChooser.showOpenDialog(null);
        
        return test;
	}
	
	public static Stage getProgressWindow(final String title, final String text, final int min, final int max){

        Group root = new Group();
        Scene scene = new Scene(root, 300, 150);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(title);
        
        final VBox vb = new VBox();
        vb.setSpacing(5);
        vb.getChildren().add(new Label(text));
        final ProgressBar pb = new ProgressBar();
        vb.getChildren().add(pb);

        scene.setRoot(vb);
		return stage;
	}

	public static HBox createArticleListEntry(final ListArticle article, boolean loadImage) throws IOException{
		
		HBox articleBox = FXMLLoader.load(article.getClass().getResource("../gui/fxml/article.fxml"));
		((Label) articleBox.lookup("#title")).setText(article.getTitle());
		((Label) articleBox.lookup("#number")).setText(article.getNumber());
		((Label) articleBox.lookup("#description")).setText(article.getDescription());
		if(article.getImage(false) != null || loadImage) {
			((ImageView) articleBox.lookup("#image")).setImage(article.getImage(true));
		}
		return articleBox;
	}
	
	@SuppressWarnings("unchecked")
	public static HBox createGroupListEntry(final CatalogueGroup group) throws IOException{
		
		HBox articleGroupBox = FXMLLoader.load(group.getClass().getResource("../gui/fxml/ArticleGroupListEntry.fxml"));
		((Label) articleGroupBox.lookup("#index")).setText(String.valueOf(group.getIndex()));
		((Label) articleGroupBox.lookup("#title")).setText(group.getTitle());
		((Label) articleGroupBox.lookup("#description")).setText(group.getDescription());
		if(group.getImage(true) != null) ((ImageView) articleGroupBox.lookup("#image")).setImage(group.getImage(true));
		final List<String> articles = new ArrayList<String>();
		for(final CatalogueArticle article : group.getArticles()){
			articles.add(article.getNumber() + " " /*+ article.getDescription2() */+ " " + article.getDescription3());
		}
		((ListView<String>) articleGroupBox.lookup("#articles")).setItems(FXCollections.observableArrayList(articles));
		//System.out.println(group.getTitle() + group.getDescription());
		return articleGroupBox;
	}	
}
