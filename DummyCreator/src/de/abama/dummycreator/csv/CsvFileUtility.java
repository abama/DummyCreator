package de.abama.dummycreator.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvFileUtility {

	private static String sep = "\t";

	public static CSV read(final File file) {

		CSV csv = new CSV();

		final List<List<String>> rows = readFile(file);

		if (rows.size() > 0) {
			csv.setHeadings(rows.get(0));
			rows.remove(0);
		}

		return csv;
	}

	private static List<List<String>> readFile(final File file) {

		BufferedReader br = null;

		final List<List<String>> rows = new ArrayList<List<String>>();

		try {

			br = new BufferedReader(new FileReader(file));

			String line;

			// List<String> row = new ArrayList<String>();

			while ((line = br.readLine()) != null) {

				rows.add(Arrays.asList(line.split(sep)));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");

		return rows;
	}
}
