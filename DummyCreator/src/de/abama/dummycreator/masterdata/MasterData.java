package de.abama.dummycreator.masterdata;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import de.abama.dummycreator.entities.Article;
import de.abama.dummycreator.entities.CatalogueGroup;
import de.abama.dummycreator.entities.ListArticle;

public class MasterData {
	private TreeMap<String, Article> articles = new TreeMap<String, Article>();
	private List<CatalogueGroup> groups = new ArrayList<CatalogueGroup>();
	
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
		for(final Article article : list){
			this.articles.put(article.getNumber(), article);
			this.articles = new TreeMap<String, Article>(this.articles);
		}
		System.out.println("Artikel: " + list.size());
	}

	public List<String> getArticleNumbers() {
		return new ArrayList<String>(articles.keySet());
	}

	public List<Article> getArticles() {
		return new ArrayList<Article>(articles.values());
	}
}
