package de.abama.dummycreator.csv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSV {
	
	private String br = "\r\n";
	
	private List<String> headings;
	
	private List<List<String>> rows = new ArrayList<List<String>>();
	
	private String sep = "\t";
	
	public void addRow(final List<String> row){
		rows.add(row);
	}

	public String getBr() {
		return br;
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
		// TODO Besser null?
		return "";
	}

	public List<String> getHeadings(){
		return headings;
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
	
	public List<String> getRow(final int index){
		return rows.get(index);
	}
	
	public int getRowCount() {
		return rows.size();
	}
	
	public List<List<String>> getRows(){
		return rows;
	}
	
	public String getSep() {
		return sep;
	}
	
	public void removeRow(int i) {
		if(rows.size()>= i){
			rows.remove(i);	
		}		
	}

	public String serialize() {
		return serialize(sep,br);
	}
	
	public String serialize(final String sep, final String br) {
		StringBuilder content = new StringBuilder();
		for(final String field : getHeadings()){
			content.append(field+sep);
		}
		content.append(br);
		for(final List<String> row : getRows()){
			for(final String field : row){
				content.append(field+sep);
			}
			content.append(br);
		}
		return content.toString();
	}
	
	public void setBr(String br) {
		this.br = br;
	}

	public void setHeadings(List<String> headings) {
		this.headings = headings;
	}

	public void setHeadings(final String headings){
		setHeadings(new ArrayList<String>(Arrays.asList(headings.split(", *"))));
	}

	public void setSep(String sep) {
		this.sep = sep;
	}

	public void addRows(final List<List<String>> rows) {
		this.rows.addAll(rows); 
	}
}
