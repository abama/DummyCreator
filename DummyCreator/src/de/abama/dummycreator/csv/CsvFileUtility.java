package de.abama.dummycreator.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvFileUtility {

	private static String sep = "\t";

	public static CSV read(final File file) {
		
		CSV csv = new CSV();

		final List<String> lines = readLines(file);
		
		detectSeparator(lines.get(0));
				
		for(final String line : lines){
			csv.addRow(Arrays.asList(line.split(sep)));
		}

		if (csv.getRows().size() > 0) {
			csv.setHeadings(csv.getRows().get(0));
			csv.removeRow(0);
		}
				
		System.out.println("CSV - Spalten: " + csv.getHeadings());
		
		System.out.println("CSV - Zeilen: " + csv.getRows().size());

		return csv;
	}

	private static List<String> readLines(final File file) {
		
		System.out.println("Lese CSV " + file.getPath());

		final List<String> rows = new ArrayList<String>();
		
		BufferedReader bufferedReader = null;
		
		try {		
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-16"));			

			String line;
			
			while ((line = bufferedReader.readLine()) != null) {
				//System.out.println(line);
				rows.add(line);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("CSV - Zeilen gelesen: " + rows.size());

		return rows;
	}
	
	private static void detectSeparator(final String row){
		if(row.contains("\t")) sep = "\t";
		else if(row.contains(";")) sep = ";";
		else if(row.contains(",")) sep = ",";
	}
}
