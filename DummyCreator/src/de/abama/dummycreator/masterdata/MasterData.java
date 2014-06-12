package de.abama.dummycreator.masterdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import de.abama.dummycreator.entities.Article;
import de.abama.dummycreator.entities.ArticleGroup;

public class MasterData {
	private HashMap<Integer, Article> articles = new HashMap<Integer, Article>();
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
		}
	}
}
