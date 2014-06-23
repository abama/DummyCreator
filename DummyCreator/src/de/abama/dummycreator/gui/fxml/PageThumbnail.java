package de.abama.dummycreator.gui.fxml;

import java.io.IOException;

import de.abama.dummycreator.catalogue.CataloguePage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PageThumbnail extends VBox{
	
	@FXML
    private ImageView image;

	@FXML
    private TextField label;

	@FXML
    private Label number;
	
	@FXML
    private ImageView linke_seite;	

	@FXML
    private ImageView rechte_seite;		
	
	private CataloguePage page;


	@FXML
	private Pane selection;
	
    public PageThumbnail(final CataloguePage page) {
    	
    	this.setPage(page);
    	
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PageThumbnail.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        	
        	this.setImage(page.getImage(true));
        	this.number.setText(String.valueOf(page.getNumber()));
        	
    		this.linke_seite.setVisible(page.getNumber()%2==0);
    		this.rechte_seite.setVisible(page.getNumber()%2!=0);
    		
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
	}

    private void setImage(Image image) {
		this.image.setImage(page.getImage(true));
	}

	public CataloguePage getPage() {
		return page;
	}

    public void setPage(CataloguePage page) {
		this.page = page;
	}
    
    public String getNumber(){
    	return String.valueOf(page.getNumber());
    }
}

