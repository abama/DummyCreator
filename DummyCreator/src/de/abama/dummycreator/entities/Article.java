package de.abama.dummycreator.entities;

import java.io.Serializable;

public class Article implements Serializable {
	
	private static final long serialVersionUID = 4624798489378228851L;
	
	private int number;
	private String title;
	
	private String description1;
	private String description2;
	private String description3;
	
	private ArticleGroup group;

	public Article(final int number){
		this.number = number;
	}

	public String getDescription1() {
		return description1;
	}

	public String getDescription2() {
		return description2;
	}

	public String getDescription3() {
		return description3;
	}

	public ArticleGroup getGroup(){
		return group;
	}

	public int getNumber(){
		return number;
	}

	public Page getPage(){
		return group.getPage();
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setDescription1(String description1) {
		this.description1 = description1;
	}
	
	public void setDescription2(String description2) {
		this.description2 = description2;
	}
	
	public void setDescription3(String description3) {
		this.description3 = description3;
	}
	
	public void setGroup(final ArticleGroup group){
		this.group = group;
	}
	
	public void setNumber(final int number) {
		this.number = number;
	}
	
	public void setTitle(final String title){
		this.title = title;
	}
}
