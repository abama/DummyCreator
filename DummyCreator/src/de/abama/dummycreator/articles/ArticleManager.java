package de.abama.dummycreator.articles;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

import de.abama.dummycreator.articles.utlilities.ArticleUtilities;
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

	public ListArticle getOrCreate(final ListArticle article){
		if(!articles.containsKey(article.getNumber())) articles.put(article.getNumber(), article);
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
		return new ArrayList<ListArticle>(articles.values());
	}

	public List<ListArticle> loadCsv(final File file) {
		if (file != null) {
			final CSV csv = CsvFileUtility.read(file);
			return getOrCreate(ArticleUtilities.createListArticles(csv));
		}
		return new ArrayList<ListArticle>();
	}
	
	public Collection<ListArticle> getAll() {
		return articles.values();
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
			if(match) result.add(article);
		}
		return result;
	}
	
	public List<ListArticle> searchByGroupSignature(String articleNumber){
		List<ListArticle> result = new ArrayList<ListArticle>();
		for(final ListArticle article : articles.values()){
			if(article.getGroupSignature().equals(articles.get(articleNumber).getGroupSignature())){
				result.add(article);
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
				result.add(articles.get(key));
			}
		}
		return result;
	}

	public ListArticle get(String articleNumber) {
		if(articles.containsKey(articleNumber))	return articles.get(articleNumber);
		return null;
	}
}
