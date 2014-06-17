package de.abama.dummycreator.entities;

import de.abama.dummycreator.config.Configuration;
import de.abama.dummycreator.constants.SU;
import javafx.scene.image.Image;

public class Article {
	
	protected String number = "Artikelnummer";
	
	protected String title = "Titel";
	
	protected String description1 = "Beschreibung1";
	protected String description2 = "Beschreibung2";
	protected String description3 = "Beschreibung3";
	
	protected Image image = null;
	
	protected SU su = SU.PIECE;
	
	protected float singlePrice;
	protected float suPrice;
	
	// Generischer Konstruktor
	public Article(){
		super();
	}
	
	// Konstruktor mit Artikelnummer
	public Article(final String number){
		this();
		this.number = number;
	}
	
	public String getDescription(){
		return description1 + description2;
	}

	public String getDescription1() {
		return description1;
	}

	public String getDescription2() {
		return description2;
	}

	public String getDescription3() {
		return description3;
	}
	
	public Image getImage(boolean load) {
		
		if (image == null && load) loadImage();
		return image;
	}

	public String getNumber(){
		return number;
	}
	
	public float getPrice() {
		return singlePrice;
	}
	
	public SU getSu() {
		return su;
	}
	
	public float getSuPrice() {
		return suPrice;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getGroupSignature(){
		return title+description1;
	}
	
	public void setDescription1(String description1) {
		this.description1 = description1;
	}

	public void setDescription2(String description2) {
		this.description2 = description2;
	}

	public void setDescription3(String description3) {
		this.description3 = description3;
	}
	
	public void loadImage(){
		this.image = new Image(Configuration.getInstance().imageBasePath+this.getNumber()+".png", true);
	}

	public void setImage(Image image) {
		this.image = image; 
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public void setSinglePrice(float price) {
		this.singlePrice = price;
	}

	public void setSu(SU su) {
		this.su = su;
	}

	public void setSuPrice(float suPrice) {
		this.suPrice = suPrice;
	}

	public void setTitle(final String title){
		this.title = title;
	}
}
