package de.abama.dummycreator.gui.utilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import de.abama.dummycreator.articles.ListArticle;
import de.abama.dummycreator.catalogue.Catalogue;
import de.abama.dummycreator.catalogue.CatalogueArticle;
import de.abama.dummycreator.catalogue.CatalogueGroup;
import de.abama.dummycreator.catalogue.CataloguePage;
import de.abama.dummycreator.catalogue.ICatalogueItem;
import de.abama.dummycreator.config.Configuration;
import de.abama.dummycreator.gui.fxml.CatalogueArticleUi;
import de.abama.dummycreator.gui.fxml.CatalogueGroupUi;
import de.abama.dummycreator.gui.fxml.CataloguePageThumbUi;
import de.abama.dummycreator.gui.fxml.ICatalogueUiItem;
import de.abama.dummycreator.gui.fxml.ListArticleUi;

public class GuiUtilities {
	
	public static File chooseCsvFile(final String startFolderPath) throws MalformedURLException, URISyntaxException{
		File startDir = new File(new URI(Configuration.getInstance().articleListPath));
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
	
	public static ListArticleUi createArticleListEntry(final ListArticle article, boolean loadImage) throws IOException{
		
		return new ListArticleUi(article, loadImage);
	}

	public static ObservableList<CatalogueArticleUi> createCatalogueArticleUis(List<CatalogueArticle> articles) {
		final List<CatalogueArticleUi> articleItems = new ArrayList<CatalogueArticleUi>();
		for(final CatalogueArticle article : articles){
			articleItems.add(new CatalogueArticleUi(article, true));
		}
		return FXCollections.observableArrayList(articleItems);
	}
	
	public static ObservableList<ICatalogueUiItem> createICatalogueUis(List<CatalogueArticle> articles) {
		final List<ICatalogueUiItem> articleItems = new ArrayList<ICatalogueUiItem>();
		for(final CatalogueArticle article : articles){
			articleItems.add(new CatalogueArticleUi(article, true));
		}
		return FXCollections.observableArrayList(articleItems);
	}
	
	public static ObservableList<CatalogueGroupUi> createCatalogueGroupUis(CataloguePage page) {
		final List<CatalogueGroupUi> groupItems = new ArrayList<CatalogueGroupUi>();
		for(final CatalogueGroup group : page.getGroups()){
			groupItems.add(new CatalogueGroupUi(group));
		}
		return FXCollections.observableArrayList(groupItems);
	}

	public static CatalogueGroupUi createGroupListEntry(final CatalogueGroup group) throws IOException{
		
		CatalogueGroupUi articleGroupBox = new CatalogueGroupUi(group);

		return articleGroupBox;
	}

	public static ObservableList<CataloguePageThumbUi> createPageThumbnails(final Catalogue catalogue) throws IOException {
		final List<CataloguePageThumbUi> pages = new ArrayList<CataloguePageThumbUi>();
		for(final CataloguePage page : catalogue.getPages()){
			CataloguePageThumbUi pageThumbnail = new CataloguePageThumbUi(page);
			pages.add(pageThumbnail);
		}		
		return FXCollections.observableList(pages);
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
	
	public static ObservableList<ListArticleUi> createArticleListGroupEntries(final Collection<ListArticle> articles, boolean loadImages) {
		List<ListArticleUi> list = new ArrayList<ListArticleUi>();
		
		//TODO Progress Window
			
		//final ProgressWindow progressWindow = new ProgressWindow("Artikel importieren", "",0,articles.size());
		//progressWindow.show();
		
		//int i=0;
			
		for(final ListArticle article : articles){
			//i++;
			//progressWindow.setProgress(i);
			try {
				list.add(createArticleListEntry(article, loadImages));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		ObservableList<ListArticleUi> observableList = FXCollections.observableList(list);
		
		//progressWindow.close();
		
		return observableList;
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
}
