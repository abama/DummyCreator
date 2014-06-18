package de.abama.dummycreator.articles;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import de.abama.dummycreator.articles.utlilities.ArticleUtilities;
import de.abama.dummycreator.catalogue.Article;
import de.abama.dummycreator.catalogue.CatalogueGroup;
import de.abama.dummycreator.catalogue.ListArticle;
import de.abama.dummycreator.config.Configuration;
import de.abama.dummycreator.csv.CSV;
import de.abama.dummycreator.csv.CsvFileUtility;
import de.abama.dummycreator.gui.utilities.GuiUtilities;

public class ArticleManager {
	
	private static ArticleManager instance = null;
	
	private ArticleManager(){ super(); }
	
	public static ArticleManager getInstance(){
		if(instance==null) instance = new ArticleManager();
		return instance;
	}
	
	private TreeMap<String, ListArticle> articles = new TreeMap<String, ListArticle>();
	private List<CatalogueGroup> groups = new ArrayList<CatalogueGroup>();
		
	@SuppressWarnings("unused")
	private Configuration configuration = Configuration.getInstance();
	
	public void add(final List<ListArticle> list){
		for(final ListArticle article : list){
			this.articles.put(article.getNumber(), article);
			this.articles = new TreeMap<String, ListArticle>(this.articles);
		}
		System.out.println("Artikel: " + list.size());
	}
	
	public void clear(){
		articles.clear();
		groups.clear();
	}
	
	public ObservableList<HBox> createArticleListGroupEntries(final Collection<ListArticle> articles, boolean loadImages) throws IOException {
		List<HBox> list = new ArrayList<HBox>();
		
		//TODO Progress Window
			
		//final ProgressWindow progressWindow = new ProgressWindow("Artikel importieren", "",0,articles.size());
		//progressWindow.show();
		
		//int i=0;
			
		for(final ListArticle article : articles){
			//i++;
			//progressWindow.setProgress(i);
			list.add(GuiUtilities.createArticleListEntry(article, loadImages));
		}
		
		ObservableList<HBox> observableList = FXCollections.observableList(list);
		
		//progressWindow.close();
		
		return observableList;
	}
	
	public int getArticleCount(){
		return articles.size();
	}

	public List<String> getArticleNumbers() {
		return new ArrayList<String>(articles.keySet());
	}

	public List<Article> getArticles() {
		return new ArrayList<Article>(articles.values());
	}

	public int getGroupCount(){
		return groups.size();
	}

	public void importCsv(final File file) {
		if (file != null) {
			final CSV csv = CsvFileUtility.read(file);
			add(ArticleUtilities.createListArticles(csv));
		}
	}
	
	public ObservableList<HBox> searchAll() throws IOException {
		return createArticleListGroupEntries(articles.values(), false);
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
		return FXCollections.observableList(list);
	}
	
	public ObservableList<HBox> searchByGroupSignature(String articleNumber) throws IOException {
		;
		List<HBox> list = new ArrayList<HBox>();
		for(final ListArticle article : articles.values()){
			if(article.getGroupSignature().equals(articles.get(articleNumber).getGroupSignature())){
				list.add(GuiUtilities.createArticleListEntry(article, true));
			}
		}
		return FXCollections.observableList(list);
	}

	public ObservableList<HBox> searchByKeywords(String text) {
		// TODO searchByKeywords
		return null;
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

	public ListArticle get(String articleNumber) {
		if(articles.containsKey(articleNumber))	return articles.get(articleNumber);
		return null;
	}


}
