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

	private CatalogueChapter chapter;
	
	private Image image = null;

	public CatalogueGroup add(final CatalogueGroup group){
		group.setPage(this);
		groups.add(group);
		return group;
	}
	
	@Override
	public void add(ICatalogueItem item) {
		System.out.println(this + " - Füge hinzu: " + item);
		try {
			final CatalogueGroup group = (CatalogueGroup) item;
			final CatalogueGroup copy = new CatalogueGroup((CatalogueGroup) group);
			add(copy);
		}
		catch(final Exception e){
			System.out.println("Objekt kann nicht eingefügt werden");
		}
		try {
			final CatalogueArticle article = (CatalogueArticle) item;
			final CatalogueArticle copy = new CatalogueArticle(article);
			final CatalogueGroup group = getOrCreateGroup(article.getGroupSignature());
			group.add(copy);
		}
		catch(final Exception e){
			System.out.println("Objekt kann nicht eingefügt werden");
		}
	}
	
	@Override
	public void addAll(List<ICatalogueItem> selection) {
		System.out.println(this + " - " + selection.size() + " Element(e) hinzufügen");
		for(final ICatalogueItem item : selection){
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
		if(image!=null) return image;
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

	public CatalogueChapter getChapter() {
		return chapter;
	}

	public List<List<String>> serialize(){
		final List<List<String>> rows = new ArrayList<List<String>>();
		for(final CatalogueGroup group : getGroups()){
			rows.addAll(group.serialize());
		}
		return rows;
	}
}
