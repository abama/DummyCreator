package de.abama.dummycreator.csv;

import java.util.ArrayList;
import java.util.List;

public class CSV {
	
	private List<String> headings;
	
	private List<List<String>> rows = new ArrayList<List<String>>();
	
	private String keyName;
	
	private int keyIndex;
	
	public int getLength(){
		return rows.size();
	}
	
	public List<String> getRow(final int index){
		return rows.get(index);
	}
	
	public List<String> getHeadings(){
		return headings;
	}
	
	public String getField(int column, int row){
		if(rows.size()<row) return null;
		if(rows.get(row).size()<column) return null;
		return rows.get(row).get(column);
	}
	
	public String getField(String heading, int row){
		if(headings.contains(heading)) {
			return getField(headings.indexOf(heading),row);
		}
		return null;
	}
	
	public List<String> getKeys(final int column){
		
		final List<String> keys = new ArrayList<String>();
				
		for(final List<String> row : rows){
			keys.add(row.get(column));
		}
		
		return keys;
	}
	
	public List<String> getKeys(final String heading){
		if(headings.contains(heading)){
			return getKeys(headings.indexOf(heading));
		}
		return null;
	}
	
	public List<List<String>> getRows(){
		return rows;
	}

	public void setHeadings(List<String> headings) {
		this.headings = headings;		
	}
}
