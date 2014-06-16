package de.abama.dummycreator;

import java.io.File;
import java.util.Collections;
import java.util.List;

import de.abama.dummycreator.csv.CSV;
import de.abama.dummycreator.csv.CsvFileUtility;
import de.abama.dummycreator.entities.Article;
import de.abama.dummycreator.entities.CatalogueGroup;
import de.abama.dummycreator.entities.Catalogue;
import de.abama.dummycreator.entities.CataloguePage;
import de.abama.dummycreator.entities.ListArticle;
import de.abama.dummycreator.utlilities.ArticleUtilities;

public class CatalogueManager {
	
	private Catalogue catalogue = new Catalogue();
		
	public Catalogue newFile() {
		catalogue = new Catalogue();
		return catalogue;
	}
	
	public Catalogue openFile(final File file) {
		final CSV csv = CsvFileUtility.read(file);
		
		final List<ListArticle> articles = ArticleUtilities.createListArticles(csv);
		Collections.sort(articles);
		
		for(final ListArticle article : articles){
			final CataloguePage page = catalogue.getOrCreatePage(article.getPage());
			//System.out.println("Page: " + page);
			final CatalogueGroup group = page.getOrCreateGroup(article.getGroup());
			//System.out.println("Group: " + group);
			group.add((Article) article);
		}

		return catalogue;
	}
	
	public void save(){
		
	}
	
	
}
