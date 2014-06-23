package de.abama.dummycreator.gui.utilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import de.abama.dummycreator.catalogue.Article;
import de.abama.dummycreator.catalogue.Catalogue;
import de.abama.dummycreator.catalogue.CatalogueGroup;
import de.abama.dummycreator.gui.fxml.ArticleGroupListEntry;
import de.abama.dummycreator.gui.fxml.ArticleSearchBoxEntry;

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
		File startDir = new File(new URI("file:/Volumes/Marketing/Artikel/Listen/Kataloge/"));
		if(!startDir.exists()) startDir = null;
        FileChooser fileChooser = new FileChooser();    
        fileChooser.setInitialDirectory(startDir);
        
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

	public static ArticleSearchBoxEntry createArticleListEntry(final Article article, boolean loadImage) throws IOException{
		
		return new ArticleSearchBoxEntry(article, loadImage);
	}
	
	public static ArticleGroupListEntry createGroupListEntry(final CatalogueGroup group) throws IOException{
		
		ArticleGroupListEntry articleGroupBox = new ArticleGroupListEntry(group.getPage());
		articleGroupBox.setIndex(String.valueOf(group.getIndex()));
		articleGroupBox.setTitle(String.valueOf(group.getTitle()));
		articleGroupBox.setDescription(String.valueOf(group.getDescription()));
		articleGroupBox.setImage(group.getImage(true));
		
		final List<String> articles = new ArrayList<String>();
		for(final Article article : group.getArticles()){
			articles.add(article.getNumber() + " " /*+ article.getDescription2() */+ " " + article.getDescription3());
		}
		articleGroupBox.setArticles(articles);
		return articleGroupBox;
	}	
}
