package de.abama.dummycreator.entities;

public class ListArticle extends Article implements Comparable<ListArticle> {
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
		return 0;
	}

	public int getPage(){
		return page;
	}

	public char getGroup(){
		return group;
	}
}
