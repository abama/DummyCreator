package de.abama.dummycreator.masterdata;

import java.util.ArrayList;
import java.util.List;

import de.abama.dummycreator.entities.Article;
import de.abama.dummycreator.entities.ArticleGroup;

public class MasterData {
	private List<Article> articles = new ArrayList<Article>();
	private List<ArticleGroup> groups = new ArrayList<ArticleGroup>();
	
	public int getArticleCount(){
		return articles.size();
	}
	
	public int getGroupsCount(){
		return groups.size();
	}
	
	public void addArticles(final List<Article> articles){
		this.articles.addAll(articles);
	}
	
	public void clear(){
		articles.clear();
		groups.clear();
	}
}
