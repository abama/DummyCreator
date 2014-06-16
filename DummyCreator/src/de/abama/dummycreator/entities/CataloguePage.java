package de.abama.dummycreator.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CataloguePage implements Serializable {
	
	private static final long serialVersionUID = -1333912371214942887L;

	private Catalogue catalogue;
	
	private Set<String> keywords = new HashSet<String>();
	
	private List<CatalogueGroup> groups = new ArrayList<CatalogueGroup>();
	
	public int getNumber(){
		//System.out.println(catalogue);
		//System.out.println(catalogue.getPages().indexOf(this));
		//System.out.println(catalogue.getFirstPage());
		return catalogue.getPages().indexOf(this)+catalogue.getFirstPageNumber();
	}
	
	public Set<String> getKeywords(){
		return keywords;
	}
	
	public void setKeywords(final String keywords){
		this.keywords = new HashSet<String>(Arrays.asList(keywords.split("[\\W]")));
	}
	
	public List<CatalogueGroup> getGroups(){
		return groups;
	}
	
	public void addGroup(final CatalogueGroup group){
		final CatalogueGroup[] groups = {group};
		addGroups(groups);
	}
	
	public void addGroups(final CatalogueGroup[] groups){
		for(CatalogueGroup group : groups){
			group.setPage(this);
			this.groups.add(group);
		}
		updatePageData();
	}	
	
	public void removeGroup(final CatalogueGroup group){
		
	}
	
	public void updatePageData(){
		
	}

	public void setCatalogue(Catalogue catalogue) {
		this.catalogue = catalogue;
	}
	
	public int getArticlesCount(){
		int articleCount = 0;
		for(CatalogueGroup group : groups){
			articleCount+=group.getArticlesCount();
		}
		return articleCount;
	}

	public CatalogueGroup getOrCreateGroup(char index) {		
		for(CatalogueGroup group : groups){
			if(group.getIndex()==index) return group;
		}
		final CatalogueGroup group = new CatalogueGroup();
		group.setIndex(index);
		addGroup(group);

		return group;
	}

	public int getGroupsCount() {
		return groups.size();
	}
}
