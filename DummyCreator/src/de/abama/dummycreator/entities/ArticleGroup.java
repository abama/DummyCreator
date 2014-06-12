package de.abama.dummycreator.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ArticleGroup implements Serializable {

	private static final long serialVersionUID = -5891268528749563927L;
	
	private List<Article> articles = new ArrayList<Article>();
	
	private Integer number;
	private String title;
	private String description;
	
	private Page page;
	
	public void addArticle(final Article article){
		Article[] articles = {article};
		addArticles(articles);
	}	
	
	public void addArticles(final Article[] articles){
		for(Article article : articles){
			article.setGroup(this);
			this.articles.add(article);
		}
		updateGroupData();
	}
	
	public String getDescription() {
		return description;
	}
	
	public char getIndex(){
		int offset = page.getGroups().indexOf(this);
		char index = (char)(Character.getNumericValue('A')+offset); 
		return index;
	}
	
	public Integer getNumber() {
		return number;
	}
	
	public Page getPage(){
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
		updateGroupData();
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public void setPage(final Page page){
		this.page = page;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void updateGroupData(){
		if(articles.size() == 0) {
			number = null;
			title = null;
		} else {
			number = articles.get(0).getNumber();
			title = articles.get(0).getTitle();
		}
	}
}
