package de.abama.dummycreator;

import java.io.File;

import de.abama.dummycreator.entities.Catalogue;

public class CatalogueManager {
	
	private Catalogue catalogue;
		
	public Catalogue newFile() {
		catalogue = new Catalogue();
		return catalogue;
	}
	
	public Catalogue openFile(final File file) {
		return catalogue;
	}
}
