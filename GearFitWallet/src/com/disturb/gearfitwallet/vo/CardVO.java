package com.disturb.gearfitwallet.vo;

import com.disturb.utils.BitmapManager;

import android.graphics.Bitmap;

public class CardVO {
	// 기본 데이터
	private int category;
	private String name;
	private String cardNumber;
	// 비트맵
	private Bitmap cardImageBitmap;
	private Bitmap barCodeBitmap;
	// 데이터 입력시 필요 데이터
	private String cardImageFileName;
	private String barCodeBitmapFileName;
	private boolean isUserCardImage= false;
	
	public CardVO() {
		super();
	}
		
	public CardVO(int category) {
		super();
		this.category = category;
	}
	
	public CardVO(int category, String name, String cardNumber) {
		super();
		this.category = category;
		this.name = name;
		this.cardNumber = cardNumber;
	}
	
	public CardVO(int category, String name, String cardNumber, String cardImageFileName) {
		super();
		this.category = category;
		this.name = name;
		this.cardNumber = cardNumber;
		this.cardImageFileName= cardImageFileName;
		BitmapManager.createCardImageBitmapFromFile( cardImageFileName);
	}
	
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCardImageFileName() {
		return cardImageFileName;
	}
	public void setCardImageFileName(String cardImageFileName) {
		this.cardImageFileName = cardImageFileName;
	}
	public String getBarCodeBitmapFileName() {
		return barCodeBitmapFileName;
	}
	public void setBarCodeBitmapFileName(String barCodeBitmapFileName) {
		this.barCodeBitmapFileName = barCodeBitmapFileName;
	}
	public boolean isUserCardImage() {
		return isUserCardImage;
	}
	public void setUserCardImage(boolean isUserCardImage) {
		this.isUserCardImage = isUserCardImage;
	}
	public Bitmap getCardImageBitmap() {
		return cardImageBitmap;
	}
	public void setCardImageBitmap(Bitmap cardImageBitmap) {
		this.cardImageBitmap = cardImageBitmap;
	}
	public Bitmap getBarCodeBitmap() {
		return barCodeBitmap;
	}
	public void setBarCodeBitmap(Bitmap barCodeBitmap) {
		this.barCodeBitmap = barCodeBitmap;
	}
}
