package de.abama.dummycreator.catalogue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.scene.image.Image;

public class CataloguePage implements ICatalogueItem {
	
	private static final long serialVersionUID = -1333912371214942887L;
	
	private static final String INDEX = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private Catalogue catalogue;
	
	private Set<String> keywords = new HashSet<String>();
	
	private final List<CatalogueGroup> groups = new ArrayList<CatalogueGroup>();

	public int getNumber(){
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
	
	public CatalogueGroup addGroup(final CatalogueGroup group){
		group.setPage(this);
		groups.add(group);
		return group;
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
		while(groups.size()-1<INDEX.indexOf(index)){
			final CatalogueGroup group = addGroup(new CatalogueGroup());
			System.out.println("Erstelle Gruppe " + group.getIndex());
		}
		return getGroup(index);
	}

	public int getGroupsCount() {
		return groups.size();
	}

	public Image getImage(boolean loadImage) {
		if(groups.size()>0)	return groups.get(0).getImage(loadImage);
		return null;
	}
	
	public CatalogueGroup getGroup(final char groupIndex){
		return groups.get(INDEX.indexOf(groupIndex));
	}

	@Override
	public ICatalogueItem getParent() {
		return catalogue;
	}

	@Override
	public ICatalogueItem remove() {
		return this.getParent().remove(this);		
	}

	@Override
	public ICatalogueItem remove(ICatalogueItem catalogueItem) {
		try {
			System.out.println("Lösche " + catalogueItem);
			final int index = groups.indexOf(catalogueItem);
			groups.remove(index);
			return groups.get(index);
		}
		catch(final Exception e){
			return null;
		}
	}
	
	public String toString(){
		return "Seite " + getNumber();
	}
}
