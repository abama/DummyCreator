package de.abama.dummycreator.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CatalogueGroup implements Serializable {

	private static final long serialVersionUID = -5891268528749563927L;
	
	private List<Article> articles = new ArrayList<Article>();
	
	private char index;
	private String title;
	private String description;
	
	private CataloguePage page;
	
	public void add(final Article article){
		articles.add(article);
	}	
	
	public void addAll(final Article[] articles){
		for(Article article : articles){
			add(article);
		}
		//updateGroupData();
	}
	
	public String getDescription() {
		return description;
	}
	
	public char getIndex() {
		return index;
	}
	
	public CataloguePage getPage(){
		return page;
	}	
	
	public String getTitle() {
		return title;
	}
	
	public void removeArticle(final Article article){
		Article[] articles = {article};
		removeArticles(articles);
		
	}

	public void removeArticles(final Article[] articles){
		for(Article article : articles){
			this.articles.remove(article);
		}
		//updateGroupData();
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setIndex(char index) {
		this.index = index;
	}

	public void setPage(final CataloguePage page){
		this.page = page;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/*
	public void updateGroupData(){
		if(articles.size() == 0) {
			index = null;
			title = null;
		} else {
			index = articles.get(0).getNumber();
			title = articles.get(0).getTitle();
		}
	}
	*/
	
	public int getArticlesCount(){
		return articles.size();
	}
}
