package de.abama.dummycreator.masterdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import de.abama.dummycreator.config.Configuration;
import de.abama.dummycreator.entities.Article;
import de.abama.dummycreator.entities.CatalogueGroup;
import de.abama.dummycreator.entities.ListArticle;
import de.abama.dummycreator.utlilities.ProgressWindow;

public class MasterData {
	
	private TreeMap<String, ListArticle> articles = new TreeMap<String, ListArticle>();
	private List<CatalogueGroup> groups = new ArrayList<CatalogueGroup>();
	
	private Configuration configuration = Configuration.getInstance();
	
	public int getArticleCount(){
		return articles.size();
	}
	
	public int getGroupCount(){
		return groups.size();
	}
	
	public void clear(){
		articles.clear();
		groups.clear();
	}
	
	public void add(final List<ListArticle> list){
		for(final ListArticle article : list){
			this.articles.put(article.getNumber(), article);
			this.articles = new TreeMap<String, ListArticle>(this.articles);
		}
		System.out.println("Artikel: " + list.size());
	}

	public List<String> getArticleNumbers() {
		return new ArrayList<String>(articles.keySet());
	}

	public List<Article> getArticles() {
		return new ArrayList<Article>(articles.values());
	}

	public ObservableList<HBox> searchByNumber(String text) throws IOException {
		List<HBox> list = new ArrayList<HBox>();
		for(final String key : articles.keySet()){
			if(text.contains(key)){
				final ListArticle article = articles.get(key);
				list.add(createArticleListEntry(article, true));
			}
		}
		ObservableList<HBox> observableList = FXCollections.observableList(list);
		return observableList;
	}

	public ObservableList<HBox> searchByDescription(String text) throws IOException {
		List<HBox> list = new ArrayList<HBox>();
		for(final ListArticle article : articles.values()){
			if(article.getTitle().contains(text)){
				list.add(createArticleListEntry(article, true));
			}
			else if(article.getDescription().contains(text)){
				list.add(createArticleListEntry(article, true));
			}
		}
		ObservableList<HBox> observableList = FXCollections.observableList(list);
		return observableList;
	}
	
	public ObservableList<HBox> searchByKeywords(String text) {
		// TODO ObservableList<HBox> searchByKeywords(String text)
		return null;
	}
	
	public ObservableList<HBox> getAll(boolean loadImages) throws IOException {
		List<HBox> list = new ArrayList<HBox>();
		
		//TODO Progress Window
			
		//final ProgressWindow progressWindow = new ProgressWindow("Artikel importieren", "",0,articles.size());
		//progressWindow.show();
		
		int i=0;
			
		for(final ListArticle article : articles.values()){
			i++;
			//progressWindow.setProgress(i);
			list.add(createArticleListEntry(article, loadImages));
		}
		
		ObservableList<HBox> observableList = FXCollections.observableList(list);
		
		//progressWindow.close();
		
		return observableList;
	}
	
	private HBox createArticleListEntry(final ListArticle article, boolean loadImage) throws IOException{
		
		HBox articleBox = FXMLLoader.load(getClass().getResource("../gui/fxml/article.fxml"));
		((Label) articleBox.lookup("#title")).setText(article.getTitle());
		((Label) articleBox.lookup("#number")).setText(article.getNumber());
		((Label) articleBox.lookup("#description")).setText(article.getDescription());
		if(loadImage) {
			((ImageView) articleBox.lookup("#image")).setImage(new Image(configuration.imageBasePath+article.getNumber()+".png", true));
		}
		return articleBox;
	}
}
