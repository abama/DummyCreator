package de.abama.dummycreator.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Page implements Serializable {
	
	private static final long serialVersionUID = -1333912371214942887L;

	private Catalogue catalogue;
	
	private Set<String> keywords = new HashSet<String>();
	
	private List<ArticleGroup> groups = new ArrayList<ArticleGroup>();
	
	public Page(Catalogue catalogue) {
		// TODO Auto-generated constructor stub
		this.catalogue = catalogue;
	}

	public int getNumber(){
		return this.catalogue.getPages().indexOf(this)+1;
	}
	
	public Set<String> getKeywords(){
		return keywords;
	}
	
	public void setKeywords(final String keywords){
		this.keywords = new HashSet<String>(Arrays.asList(keywords.split("[\\W]")));
	}
	
	public List<ArticleGroup> getGroups(){
		return groups;
	}
	
	public void addGroup(final ArticleGroup group){
		final ArticleGroup[] groups = {group};
		addGroups(groups);
	}
	
	public void addGroups(final ArticleGroup[] groups){
		for(ArticleGroup group : groups){
			group.setPage(this);
			this.groups.add(group);
		}
		updatePageData();
	}	
	
	public void removeGroup(final ArticleGroup group){
		
	}
	
	public void updatePageData(){
		
	}
}
