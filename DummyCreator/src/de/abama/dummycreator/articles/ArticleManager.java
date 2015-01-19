package de.abama.dummycreator.articles;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

import de.abama.dummycreator.articles.utlilities.ArticleUtilities;
import de.abama.dummycreator.config.Configuration;
import de.abama.dummycreator.csv.CSV;
import de.abama.dummycreator.csv.CsvFileUtility;

public class ArticleManager {
	
	private static ArticleManager instance = null;
	
	private ArticleManager(){ super(); }
	
	public static ArticleManager getInstance(){
		if(instance==null) instance = new ArticleManager();
		return instance;
	}
	
	private TreeMap<String, ListArticle> articles = new TreeMap<String, ListArticle>();
	
	public List<ListArticle> getOrCreate(final List<ListArticle> list){
		final List<ListArticle> addedArticles = new ArrayList<ListArticle>();
		for(final ListArticle article : list){
			addedArticles.add(getOrCreate(article));
		}
		return addedArticles;
	}

	public ListArticle getOrCreate(ListArticle article){
		ListArticle outputArticle;
		if(!articles.containsKey(article.getNumber())) {
			outputArticle = article;
			articles.put(article.getNumber(), article);
		} else {
			outputArticle = articles.get(article.getNumber());
			outputArticle.setPageNumber(article.getPageNumber());
			outputArticle.setGroupIndex(article.getGroupIndex());
		}
		
		return articles.get(article.getNumber());
	}
	
	public void clear(){
		articles.clear();
	}
	
	public int getArticleCount(){
		return articles.size();
	}

	public List<String> getArticleNumbers() {
		return new ArrayList<String>(articles.keySet());
	}

	public List<ListArticle> getArticles() {		
		List<ListArticle> result = new ArrayList<ListArticle>();
		for(final String key : articles.keySet()){
			final ListArticle article = articles.get(key);
			//TODO Achtung hier werden alle Artikel weggefiltert, die mal in den Katalog gezogen wurden
			if(article.getOccurencies() == 0) result.add(article);
		}
		return result;
		
		
	}

	public List<ListArticle> loadCsv(final File file) {
		if (file != null) {
			final CSV csv = CsvFileUtility.read(file);
			return getOrCreate(ArticleUtilities.createListArticles(csv));
		}
		return new ArrayList<ListArticle>();
	}
	
	public Collection<ListArticle> getAll() {
		List<ListArticle> result = new ArrayList<ListArticle>();
		for(final String key : articles.keySet()){
			final ListArticle article = articles.get(key);

			if(Configuration.getInstance().showOnlyUnusedArticles) {
				if(article.getOccurencies() == 0) result.add(article);
				//System.out.println("Suche nicht verwendete Artikel");
			}
				
			else {
				//System.out.println("Suche alle Artikel");
				result.add(article);
			}
		}
		return result;
	}
	
	public List<ListArticle> getByDescription(String text){
		
		List<ListArticle> result = new ArrayList<ListArticle>();
		final String[] searchTerms = text.toLowerCase().split(" +|, *|; *");
		for(final ListArticle article : articles.values()){
			final String articleString = article.getFullString().toLowerCase();
			boolean match = true;
			for(final String searchTerm : searchTerms){
				if(!articleString.contains(searchTerm)) {
					match = false;
					break;
				}
			}

			if(Configuration.getInstance().showOnlyUnusedArticles)
				if(match && article.getOccurencies() == 0) result.add(article);
			else
				result.add(article);
		}
		return result;
	}
	
	public List<ListArticle> searchByGroupSignature(String articleNumber){
		List<ListArticle> result = new ArrayList<ListArticle>();
		for(final ListArticle article : articles.values()){
			if(article.getGroupSignature().equals(articles.get(articleNumber).getGroupSignature())){
				if(Configuration.getInstance().showOnlyUnusedArticles)
					if(article.getOccurencies() == 0) result.add(article);
				else result.add(article);
			}
		}
		return result;
	}

	public List<ListArticle> searchByKeywords(String text) {
		// TODO searchByKeywords
		return new ArrayList<ListArticle>();
	}
	
	public List<ListArticle> searchByNumber(String number){
		List<ListArticle> result = new ArrayList<ListArticle>();
		for(final String key : articles.keySet()){
			if(key.contains(number)){
				final ListArticle article = articles.get(key);
				result.add(article);
			}
		}
		return result;
	}

	public ListArticle get(String articleNumber) {
		if(articles.containsKey(articleNumber))	return articles.get(articleNumber);
		return null;
	}
}
