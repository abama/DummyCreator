package de.abama.dummycreator.csv;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;

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

		List<String> rows = new ArrayList<String>();
		
		try {
			rows = FileUtils.readLines(file, "UTF-16");
		} catch (IOException e) {
			System.out.println("Datei konnte nicht gelesen werden.");
			e.printStackTrace();
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
