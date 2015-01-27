package com.disturb.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.disturb.BarCode.BarCode128;
import com.disturb.gearfitwallet.R;

public class BitmapManager {
	
	public static final int BAR_CODE_BITMAP_WIDTH = 423;
	public static final int BAR_CODE_BITMAP_HEIGHT = 128;	
	public static final int CARD_BITMAP_WIDTH = 200;
	public static final int CARD_BITMAP_HEIGHT = 120;
	
	// �Է¹��� ���� (barCode)�� ���� ���ڵ念��(bitmap)�� ������ ����
	public static Bitmap createBarCodeBitmapFromBarCodeString(String barCode){
		Bitmap bitmap =  Bitmap.createBitmap(BAR_CODE_BITMAP_WIDTH, BAR_CODE_BITMAP_HEIGHT, Bitmap.Config.ARGB_8888);
		int [] barBinCode = BarCode128.generateBinaryBarCode128FromNumString(barCode);
		getBarCodeBitmap(bitmap, barBinCode);		
		return bitmap;
	}
	
	// ���� ���ڵ� �迭�� ��Ʈ�� ���� �׸��� �Լ�
	private static void getBarCodeBitmap(Bitmap bitmap, int[] bar){
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int factor = width/bar.length;
		int startPosition = (width-(bar.length*factor))/2;
		
		for(int x=0;x<width;x++){			
			for(int y=0;y<height;y++){
				bitmap.setPixel(x, y, Color.WHITE);		
			}			
		}
		
		for(int i=0;i<bar.length;i++){
			if(bar[i]==0){
				int x = (i*factor)+startPosition;
				for(int y=0;y<height-30;y++){
					for(int f=0;f<factor;f++){
						bitmap.setPixel(x+f, y, Color.BLACK);							
					}							
				}		
			}
		}		
	}
	
	public static Bitmap createCardImageBitmapFromResource(Context context, int category){
		int resId = getResIdByCardCategory(category);
		return Bitmap.createScaledBitmap(
				BitmapFactory.decodeResource(context.getResources(),resId), 
				CARD_BITMAP_WIDTH, 
				CARD_BITMAP_HEIGHT, 
				false);		
	}
	
	public static Bitmap createCardImageBitmapFromFile(String filePath){	
		if(filePath == null){
			return null;
		}
		return Bitmap.createScaledBitmap(
				BitmapFactory.decodeFile(filePath),  
				CARD_BITMAP_WIDTH, 
				CARD_BITMAP_HEIGHT, 
				false);		
	}
	
	public static void saveBitmapToFile(Bitmap bitmap, String filePath){
	        File fileCacheItem = new File(filePath);	        
	        OutputStream out = null;
	 
	        try {
	            fileCacheItem.createNewFile();
	            out = new FileOutputStream(fileCacheItem);
	 
	            bitmap.compress(CompressFormat.JPEG, 100, out);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                out.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	}
	
	
	private static int getResIdByCardCategory(int category){
		int resId = R.drawable.card1;
		switch(category){
		case 0:		// ��Ÿ
			resId = R.drawable.card0;
			break;
		case 1:		// ��������Ʈ
			resId = R.drawable.card1;
			break;
		case 2:		// SKT
			resId = R.drawable.card2;
			break;
		case 3:		// CJ ONE
			resId = R.drawable.card3;
			break;
		default:
			break;
				
		}
		return resId;
	}
}
