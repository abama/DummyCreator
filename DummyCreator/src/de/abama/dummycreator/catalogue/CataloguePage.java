package de.abama.dummycreator.catalogue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.scene.image.Image;

public class CataloguePage implements Serializable {
	
	private static final long serialVersionUID = -1333912371214942887L;

	private Catalogue catalogue;
	
	private Set<String> keywords = new HashSet<String>();
	
	private final List<ArticleGroup> groups = new ArrayList<ArticleGroup>();

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
	
	public List<ArticleGroup> getGroups(){
		return groups;
	}
	
	public ArticleGroup addGroup(final ArticleGroup group){
		final ArticleGroup[] groups = {group};
		addGroups(groups);
		return group;
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

	public void setCatalogue(Catalogue catalogue) {
		this.catalogue = catalogue;
	}
	
	public int getArticlesCount(){
		int articleCount = 0;
		for(ArticleGroup group : groups){
			articleCount+=group.getArticlesCount();
		}
		return articleCount;
	}

	public ArticleGroup getOrCreateGroup(char index) {		
		for(ArticleGroup group : groups){
			if(group.getIndex()==index) return group;
		}
		final ArticleGroup group = new ArticleGroup();
		addGroup(group);

		return group;
	}

	public int getGroupsCount() {
		return groups.size();
	}

	public Image getImage(boolean loadImage) {
		if(groups.size()>0)	return groups.get(0).getImage(loadImage);
		return null;
	}
}
