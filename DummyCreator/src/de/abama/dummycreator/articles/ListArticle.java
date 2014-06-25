package de.abama.dummycreator.articles;


public class ListArticle extends Article implements Comparable<ListArticle> {

	private static final long serialVersionUID = 3668915187324896182L;
	
	private int pageNumber;
	private char groupIndex;
	
	public void setPage(String page) throws Exception {
		this.pageNumber = Integer.parseInt(page);
	}
	
	public void setGroup(String group) throws Exception {
		if(group.matches("[a-zA-Z]")) this.groupIndex = group.toUpperCase().charAt(0);
		else throw new Exception();
	}

	public int compareTo(ListArticle other) {
		if(this.getPageNumber()>other.getPageNumber()) return 1;
		if(this.getPageNumber()<other.getPageNumber()) return -1;
		if(this.getGroupIndex()>other.getGroupIndex()) return 1;
		if(this.getGroupIndex()<other.getGroupIndex()) return -1;	
		return this.description1.compareTo(other.description1);
	}

	public int getPageNumber(){
		return pageNumber;
	}

	public char getGroupIndex(){
		return groupIndex;
	}
}
