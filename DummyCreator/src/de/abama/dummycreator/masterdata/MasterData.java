package de.abama.dummycreator.masterdata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import de.abama.dummycreator.config.Configuration;
import de.abama.dummycreator.csv.CSV;
import de.abama.dummycreator.csv.CsvFileUtility;
import de.abama.dummycreator.entities.Article;
import de.abama.dummycreator.entities.CatalogueGroup;
import de.abama.dummycreator.entities.ListArticle;
import de.abama.dummycreator.utlilities.ArticleUtilities;
import de.abama.dummycreator.utlilities.GuiUtilities;

public class MasterData {
	
	private TreeMap<String, ListArticle> articles = new TreeMap<String, ListArticle>();
	private List<CatalogueGroup> groups = new ArrayList<CatalogueGroup>();
	
	@SuppressWarnings("unused")
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
			if(key.contains(text) || text.contains(key)){
				final ListArticle article = articles.get(key);
				list.add(GuiUtilities.createArticleListEntry(article, true));
			}
		}
		ObservableList<HBox> observableList = FXCollections.observableList(list);
		return observableList;
	}

	public ObservableList<HBox> searchByDescription(String text) throws IOException {
		text = text.toLowerCase();
		List<HBox> list = new ArrayList<HBox>();
		for(final ListArticle article : articles.values()){
			if(article.getTitle().toLowerCase().contains(text)){
				list.add(GuiUtilities.createArticleListEntry(article, true));
			}
			else if(article.getDescription().toLowerCase().contains(text)){
				list.add(GuiUtilities.createArticleListEntry(article, true));
			}
		}
		ObservableList<HBox> observableList = FXCollections.observableList(list);
		return observableList;
	}
	
	public ObservableList<HBox> searchByKeywords(String text) {
		// TODO ObservableList<HBox> searchByKeywords(String text)
		return null;
	}
	
	public ObservableList<HBox> createArticleListGroupEntries(boolean loadImages) throws IOException {
		List<HBox> list = new ArrayList<HBox>();
		
		//TODO Progress Window
			
		//final ProgressWindow progressWindow = new ProgressWindow("Artikel importieren", "",0,articles.size());
		//progressWindow.show();
		
		//int i=0;
			
		for(final ListArticle article : articles.values()){
			//i++;
			//progressWindow.setProgress(i);
			list.add(GuiUtilities.createArticleListEntry(article, loadImages));
		}
		
		ObservableList<HBox> observableList = FXCollections.observableList(list);
		
		//progressWindow.close();
		
		return observableList;
	}

	public void importCsv(final File file) {
		if (file != null) {
			final CSV csv = CsvFileUtility.read(file);
			add(ArticleUtilities.createListArticles(csv));
		}
	}
	
	
}
