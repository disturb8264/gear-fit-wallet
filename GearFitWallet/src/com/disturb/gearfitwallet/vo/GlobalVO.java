package com.disturb.gearfitwallet.vo;

import java.util.ArrayList;

import android.app.Application;

public class GlobalVO extends Application {
	private ArrayList<CardVO> cardList;
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	@Override
	public void onTerminate() {		
		super.onTerminate();
	}

	public ArrayList<CardVO> getCardList() {
		return cardList;
	}

	public void setCardList(ArrayList<CardVO> cardList) {
		this.cardList = cardList;
	}
}
