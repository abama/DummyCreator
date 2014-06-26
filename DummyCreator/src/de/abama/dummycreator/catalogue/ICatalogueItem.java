package de.abama.dummycreator.catalogue;

import java.io.Serializable;
import java.util.List;

public interface ICatalogueItem extends Serializable {
	
	ICatalogueItem getParent();
	
	ICatalogueItem remove();

	ICatalogueItem remove(ICatalogueItem catalogueItem);
	
	void add(ICatalogueItem selection);

	void addAll(List<ICatalogueItem> selection);
	
}
