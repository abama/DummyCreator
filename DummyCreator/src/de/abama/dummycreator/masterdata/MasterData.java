package de.abama.dummycreator.masterdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;




import java.util.Set;
import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import de.abama.dummycreator.entities.Article;
import de.abama.dummycreator.entities.ArticleGroup;

public class MasterData {
	private TreeMap<Integer, Article> articles = new TreeMap<Integer, Article>();
	private List<ArticleGroup> groups = new ArrayList<ArticleGroup>();
	
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
	
	public void add(final List<Article> articles){
		for(final Article article : articles){
			this.articles.put(article.getNumber(), article);
			this.articles = new TreeMap<Integer, Article>(this.articles);
		}
	}

	public List<Integer> getArticleNumbers() {
		return new ArrayList<Integer>(articles.keySet());
	}

	public List<Article> getArticles() {
		return new ArrayList<Article>(articles.values());
	}
}
