package de.abama.dummycreator.articles;

import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class MultiSelectListView<T> extends ListView<T> {
    public boolean isMultiSelect() {
        return getSelectionModel().getSelectionMode() == SelectionMode.MULTIPLE;
    }
    
    public void setMultiSelect(boolean multiSelect) {
        getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}