package de.abama.dummycreator.articles;

import java.io.Serializable;

import de.abama.dummycreator.config.Configuration;
import de.abama.dummycreator.constants.SU;
import javafx.scene.image.Image;

public class Article implements Serializable{
	
	private static final long serialVersionUID = 4904155239691627018L;

	protected String number = "Artikelnummer";
	
	protected String title = "Titel";
	protected String description1 = "Beschreibung1";
	protected String description2 = "Beschreibung2";
	protected String description3 = "Beschreibung3";
	
	protected int pageNumber;
	protected char groupIndex;
	
	protected float singlePrice;
	protected float suPrice;
	
	protected SU su = SU.PIECE;

	protected Image image = null;
	
	
	// Generischer Konstruktor
	public Article(){
		super();
	}
	
	// Konstruktor mit Artikelnummer
	public Article(final String number){
		this();
		this.number = number;
	}
	
	public int compareTo(Article other) {
		if(this.pageNumber>other.pageNumber) return 1;
		if(this.pageNumber<other.pageNumber) return -1;
		if(this.groupIndex>other.groupIndex) return 1;
		if(this.groupIndex<other.groupIndex) return -1;	
		return this.description1.compareTo(other.description1);
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
	
	public char getGroupIndex() {
		return groupIndex;
	}
	
	public String getGroupSignature(){
		return title+description1;
	}
	
	public Image getImage(boolean load) {
		
		if (image == null && load) loadImage();
		return image;
	}
	
	public String getNumber(){
		return number;
	}
	
	public int getPageNumber() {
		return pageNumber;
	}

	public float getSinglePrice() {
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

	public void loadImage(){
		this.image = new Image(Configuration.getInstance().imageBasePath+this.getNumber()+".png", true);
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

	public void setGroupIndex(String group) throws Exception {
		if(group.matches("[a-zA-Z]")) this.groupIndex = group.toUpperCase().charAt(0);
		else throw new Exception();
	}

	public void setImage(Image image) {
		this.image = image; 
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public void setPageNumber(String page) throws Exception {
		this.pageNumber = Integer.parseInt(page);
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
	
	@Override
	public boolean equals(final Object other){
		return(getNumber().equals(((Article)other).getNumber()));
	}
}
