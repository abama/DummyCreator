package de.abama.dummycreator.entities;

import java.io.Serializable;

import javafx.scene.image.Image;

public class Article {
		
	private String description1 = "Beschreibung1";
	private String description2 = "Beschreibung2";
	private String description3 = "Beschreibung3";
	
	private Image image;
	
	private String number = "Artikelnummer";

	private float price;
	
	private String title = "Titel";
	
	public Article(){
		
	}

	public Article(final String number){
		this.number = number;
	}
	
	public String getDescription(){
		return description1 + description2 + description3;
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

	public Image getImage() {
		return image;
	}

	public String getNumber(){
		return number;
	}
	
	public float getPrice() {
		return price;
	}
	
	public String getTitle() {
		return title;
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

	public void setImage(Image image) {
		this.image = image; 
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setTitle(final String title){
		this.title = title;
	}
}
