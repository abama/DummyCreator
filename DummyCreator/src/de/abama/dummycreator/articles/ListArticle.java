package de.abama.dummycreator.articles;


public class ListArticle extends Article implements Comparable<ListArticle> {

	private static final long serialVersionUID = 3668915187324896182L;
	
	private int page;
	private char group;
	
	public void setPage(String page) throws Exception {
		this.page = Integer.parseInt(page);
	}
	
	public void setGroup(String group) throws Exception {
		if(group.matches("[a-zA-Z]")) this.group = group.toUpperCase().charAt(0);
		else throw new Exception();
	}

	public int compareTo(ListArticle other) {
		if(this.page>other.page) return 1;
		if(this.page<other.page) return -1;
		if(this.group>other.group) return 1;
		if(this.group<other.group) return -1;	
		return this.description1.compareTo(other.description1);
	}

	public int getPageNumber(){
		return page;
	}

	public char getGroupIndex(){
		return group;
	}
}
