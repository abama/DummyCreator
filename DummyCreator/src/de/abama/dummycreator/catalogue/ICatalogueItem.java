package de.abama.dummycreator.catalogue;

import java.io.Serializable;

public interface ICatalogueItem extends Serializable {
	
	ICatalogueItem getParent();
	
	ICatalogueItem remove();

	ICatalogueItem remove(ICatalogueItem catalogueItem);
	
}
