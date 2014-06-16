package de.abama.dummycreator.csv;

import java.util.ArrayList;
import java.util.List;

public class CSV {
	
	private List<String> headings;
	
	private List<List<String>> rows = new ArrayList<List<String>>();
	
	public int getLength(){
		return rows.size();
	}
	
	public List<String> getRow(final int index){
		return rows.get(index);
	}
	
	public List<String> getHeadings(){
		return headings;
	}
	
	public String getField(int row, int column){
		if(rows.size()<row) return null;
		if(rows.get(row).size()<column) return null;
		return rows.get(row).get(column);
	}
	
	public String getField(int row, String heading){
		if(headings.contains(heading)) {
			final String value = getField(row, headings.indexOf(heading));
			return value;
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
	
	public void addRow(final List<String> row){
		rows.add(row);
	}

	public void removeRow(int i) {
		if(rows.size()>= i){
			rows.remove(i);	
		}		
	}

	public int getRowCount() {
		return rows.size();
	}
}
