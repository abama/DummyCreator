package de.abama.dummycreator.test;

import javafx.scene.control.ListView;

public class MultiSelectListView<T> extends ListView<T> {

	public void setFocused() {
		this.setFocused(true);
	}
}
