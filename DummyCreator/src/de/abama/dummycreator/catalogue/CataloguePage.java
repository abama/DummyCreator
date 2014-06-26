package de.abama.dummycreator.catalogue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.scene.image.Image;

public class CataloguePage implements ICatalogueItem {
	
	private static final String INDEX = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	private static final long serialVersionUID = -1333912371214942887L;

	private Catalogue catalogue;
	
	private final List<CatalogueGroup> groups = new ArrayList<CatalogueGroup>();
	
	private Set<String> keywords = new HashSet<String>();

	public CatalogueGroup add(final CatalogueGroup group){
		group.setPage(this);
		groups.add(group);
		return group;
	}
	
	@Override
	public void add(ICatalogueItem item) {
		try {
			final CatalogueGroup group = new CatalogueGroup((CatalogueGroup) item);
			add(group);
		}
		catch(final ClassCastException e){
			System.out.println("Objekt kann nicht eingefügt werden");
		}
	}
	
	@Override
	public void addAll(List<ICatalogueItem> selection) {
		System.out.println(this + " - " + selection.size() + " Elemente hinzufügen");
		for(final ICatalogueItem item : selection){
			System.out.println(this + " - Füge hinzu: " + item);
			this.add(item);
		}
	}
	
	public int getArticlesCount(){
		int articleCount = 0;
		for(CatalogueGroup group : groups){
			articleCount+=group.getArticlesCount();
		}
		return articleCount;
	}
	
	public CatalogueGroup getGroup(final char groupIndex){
		return groups.get(INDEX.indexOf(groupIndex));
	}	

	public List<CatalogueGroup> getGroups(){
		return groups;
	}
	
	public int getGroupsCount() {
		return groups.size();
	}

	public Image getImage(boolean loadImage) {
		if(groups.size()>0)	return groups.get(0).getImage(loadImage);
		return null;
	}

	public Set<String> getKeywords(){
		return keywords;
	}

	public int getNumber(){
		return catalogue.getPages().indexOf(this)+catalogue.getFirstPageNumber();
	}
	
	public CatalogueGroup getOrCreateGroup(char index) {
		while(groups.size()-1<INDEX.indexOf(index)){
			final CatalogueGroup group = add(new CatalogueGroup(this));
			System.out.println("Erstelle Gruppe " + group.getIndex());
		}
		return getGroup(index);
	}
	
	public CatalogueGroup getOrCreateGroup(String groupSignature) {
		System.out.println("Suche nach Gruppensignatur " + groupSignature);
		final List<String> signatures = new ArrayList<String>();
		for(final CatalogueGroup group : groups){
			signatures.add(group.getGroupSignature());
			System.out.println(group.getGroupSignature());
		}
		if(signatures.contains(groupSignature)) 
			return groups.get(signatures.indexOf(groupSignature));
		
		final CatalogueGroup group = new CatalogueGroup(this);
		this.add(group);
		System.out.println("Erstelle Gruppe " + group.getIndex());
		return group; 
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
	
	public void setCatalogue(Catalogue catalogue) {
		this.catalogue = catalogue;
	}

	public void setKeywords(final String keywords){
		this.keywords = new HashSet<String>(Arrays.asList(keywords.split("[\\W]")));
	}

	public String toString(){
		return "Seite " + getNumber();
	}
}
