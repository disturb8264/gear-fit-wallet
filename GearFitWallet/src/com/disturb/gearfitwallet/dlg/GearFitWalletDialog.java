package com.disturb.gearfitwallet.dlg;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.disturb.gearfitwallet.vo.CardVO;
import com.disturb.gearfitwallet.vo.GlobalVO;
import com.samsung.android.sdk.cup.ScupDialog;
import com.samsung.android.sdk.cup.ScupLabel;
import com.samsung.android.sdk.cup.ScupThumbnailListBox;
import com.samsung.android.sdk.cup.ScupThumbnailListBox.ClickListener;

public class GearFitWalletDialog extends ScupDialog {
	// 423 x 128	
	private Context mContext = null;
	private ScupThumbnailListBox thumbnailListBox=null;
	private ScupLabel label =null;

	private ArrayList<CardVO> cardList;
	
	public GearFitWalletDialog(Context context) {
		super(context);
		mContext = context;
	}
	
	@Override
	protected void onCreate() {		
		super.onCreate();
		
		GlobalVO gVo = (GlobalVO)(mContext.getApplicationContext());
		cardList = gVo.getCardList();
		
		setBackEnabled(false);
		setBackgroundColor(Color.WHITE);		
		this.setWidgetAlignment(ScupDialog.WIDGET_ALIGN_VERTICAL_CENTER);
		Log.e("DISTURB", "before show list");
		showList();
	}
	
	private void showList(){
		thumbnailListBox = new ScupThumbnailListBox(this);
		thumbnailListBox.setBackgroundColor(Color.WHITE);
		Log.e("DISTURB", "cardlist size: "+cardList.size());
		for(int i=0;i<cardList.size();i++){			
			thumbnailListBox.addItem(i, cardList.get(i).getCardImageBitmap());
		}
		thumbnailListBox.setHeight(ScupThumbnailListBox.FILL_DIALOG);		
		thumbnailListBox.setClickListener(thumnailClickListener);		
		thumbnailListBox.show();
		this.update();	
	}
	
	private ScupThumbnailListBox.ClickListener thumnailClickListener = new ClickListener() {
		
		@Override
		public void onClick(ScupThumbnailListBox list, int id) {
			thumbnailListBox.destroy();			
			showLabel(cardList.get(id).getCardNumber(),cardList.get(id).getBarCodeBitmap());			
		}
	};
	
	private void showLabel(String barCode, Bitmap bitmap){
		setBackEnabled(false);		
		label = new ScupLabel(this);
		label.setWidth(ScupLabel.FILL_DIALOG);
		label.setHeight(ScupLabel.FILL_DIALOG);		
		label.setBackgroundImage(bitmap);				
		label.setAlignment(ScupLabel.ALIGN_BOTTOM);
		label.setTextColor(Color.BLACK);
		String text = barCode.substring(0, 4)+" "
							+barCode.substring(4, 8)+" "
							+barCode.substring(8, 12)+" "
							+barCode.substring(12, 16);
		label.setText(text);		
		label.setTapListener(labelTapListener);		
		label.show();				
		this.update();
	}	
	
	private ScupLabel.TapListener labelTapListener = new ScupLabel.TapListener() {		
		@Override
		public void onSingleTap(ScupLabel arg0, float arg1, float arg2) {
			vibrate(VIBRATION_TYPE_SHORT);
			label.destroy();
			showList();							
		}
	};
}
