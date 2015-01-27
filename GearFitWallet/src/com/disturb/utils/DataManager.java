package com.disturb.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

import com.disturb.gearfitwallet.vo.CardVO;

public class DataManager {
	public static ArrayList<CardVO> importCardListFromXML(Context context) {		
		// 변수 선언
		ArrayList<CardVO> cardList = new ArrayList<CardVO>();
		CardVO cardVo= null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		Document doc = null;
		Element root = null;
		// xml 파일 읽기
		try {
			File fileRoot = context.getDir("cardData", Activity.MODE_PRIVATE);
			String path = fileRoot.getAbsolutePath();
			File file = new File(path+"/cardlist.xml");
			if(!file.exists()){				
				return cardList;
//				DataManager.exportCardListToXML(context, cardList);
			}
			db = dbf.newDocumentBuilder();
			doc = db.parse(new FileInputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// xml 파싱 후 할당
		root = doc.getDocumentElement();
		NodeList sub = root.getChildNodes();
		for(int i=0;i<sub.getLength();i++){
			Node subNode = sub.item(i);
			if(subNode.getNodeType() == Node.ELEMENT_NODE){
				int categoty = Integer.parseInt(subNode.getAttributes().getNamedItem("Category").getNodeValue());
				String name = (String)subNode.getAttributes().getNamedItem("Name").getNodeValue();
				String cardNumber = (String)subNode.getAttributes().getNamedItem("CardNumber").getNodeValue();
				String cardImageFileName = null;
				if(subNode.getAttributes().getNamedItem("CardImageFileName") != null){
					cardImageFileName =(String)subNode.getAttributes().getNamedItem("CardImageFileName").getNodeValue();
				}
				cardVo =new CardVO(categoty, name, cardNumber);		
				cardVo.setBarCodeBitmap(BitmapManager.createBarCodeBitmapFromBarCodeString(cardVo.getCardNumber()));
				cardVo.setCardImageFileName(cardImageFileName);
				if(cardImageFileName != null){
					cardVo.setCardImageBitmap(BitmapManager.createCardImageBitmapFromFile(cardImageFileName));
				}else{				
					cardVo.setCardImageBitmap(BitmapManager.createCardImageBitmapFromResource(context, cardVo.getCategory()));
				}
				cardList.add(cardVo);				
			}
		}
		
		return cardList;
	}
	
	public static void exportCardListToXML(Context context, ArrayList<CardVO> cardList){		
		byte[] cardData = getCardListData(cardList);
		File fileRoot = context.getDir("cardData", Activity.MODE_PRIVATE);
		String path = fileRoot.getAbsolutePath();
		File file = new File(path+"/cardlist.xml");
		try {			
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(cardData);			
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private static byte[] getCardListData(ArrayList<CardVO> cardList) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n");
		sb.append("<CardList>\n");
		for(CardVO card:cardList){
			sb.append("<Card ");
			sb.append("Category=\""+card.getCategory()+"\" ");
			sb.append("Name=\""+card.getName()+"\" ");
			sb.append("CardNumber=\""+card.getCardNumber()+"\" ");
			if(card.getCardImageFileName() != null){
				sb.append("CardImageFileName=\""+card.getCardImageFileName()+"\" ");
			}
			sb.append("/>\n");			
		}
		sb.append("</CardList>");
		
		return sb.toString().getBytes();
	}
	
	public static void exportDataToZip(Context context){
//		File fileRoot = context.getDir("cardData", Activity.MODE_PRIVATE);
//		String inPath = fileRoot.getAbsolutePath();				
//		String outPath = Environment.DIRECTORY_DOWNLOADS;
	}
}
