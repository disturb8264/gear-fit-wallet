package com.disturb.gearfitwallet.actvt;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.disturb.gearfitwallet.R;
import com.disturb.gearfitwallet.dlg.GearFitWalletDialog;
import com.disturb.gearfitwallet.vo.CardVO;
import com.disturb.gearfitwallet.vo.GlobalVO;
import com.disturb.utils.BitmapManager;
import com.disturb.utils.Common;
import com.disturb.utils.DataManager;
import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.cup.Scup;

public class MainActivity extends ActionBarActivity {
	private GearFitWalletDialog mGearFitWalletDialog= null;
	private GlobalVO gVo=null;
	private ArrayList<CardVO> cardList;
	ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		// 레이아웃 설정
		setContentView(R.layout.activity_main);
		// 전역변수 설정
		gVo = (GlobalVO) getApplicationContext();
//		Intent intent = new Intent();
//		startIntent.setClass(this, "com.samsung.android.sdk.cup.ScupService");
//		intent.setPackage("com.samsung.android.sdk.cup");
		
//		this.bindService(intent, this, Context.BIND_AUTO_CREATE);

		// 기어핏 초기화
		Scup scup = new Scup();
		try {
			scup.initialize(this);
		} catch (SsdkUnsupportedException e) {
			if (e.getType() == SsdkUnsupportedException.VENDOR_NOT_SUPPORTED) {
				// Vendor is not Samsung.
			}
		}
		
		// xml 파일로부터 데이터 읽기
		cardList = DataManager.importCardListFromXML(this);
		DataManager.exportCardListToXML(this,cardList);
		gVo.setCardList(cardList);
		
		
		// 리스트뷰 용 데이터리스트 설정 
		adapter = new ArrayAdapter<String>(this,	android.R.layout.simple_list_item_1);
		ListView mListView = (ListView) findViewById(R.id.listCard);
		mListView.setAdapter(adapter);
		setAdapterData();

		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {				
				String mode = "modify";
				if(position == adapter.getCount()-1){					
					mode = "create";
				}
				Intent detailIntent = new Intent(getApplicationContext(), CardDetailActivity.class);
				Bundle b = new Bundle();
				detailIntent.putExtra("bundle", b);
				detailIntent.putExtra("POSITION", position);				
				detailIntent.putExtra("MODE", mode);
				startActivityForResult(detailIntent, Common.REQUEST_CODE_CARD_DETAIL);
			}
		});
		Log.e("DISTURB", "aAA");
		mGearFitWalletDialog = new GearFitWalletDialog(getApplicationContext());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == Common.REQUEST_CODE_CARD_DETAIL){
			if(resultCode == Activity.RESULT_OK){
				int resultMode = data.getIntExtra("RESULT_MODE", RESULT_OK);
				if(resultMode == Common.RESULT_MODE_ADD){
					
					int category = data.getIntExtra(Common.DATA_CATEGORY, -1);
					String cardName = data.getStringExtra(Common.DATA_CARD_NAME);
					String cardNumber = data.getStringExtra(Common.DATA_CARD_NUMBER);
					String cardImageFileName = data.getStringExtra(Common.DATA_CARD_IMAGE_FILE_NAME);		
					
					CardVO card = new CardVO(category, cardName, cardNumber);
					card.setCategory(category);
					card.setName(cardName);
					card.setCardNumber(cardNumber);
					card.setBarCodeBitmap(BitmapManager.createBarCodeBitmapFromBarCodeString(cardNumber));
					if(cardImageFileName != null){
						card.setCardImageFileName(cardImageFileName);
						card.setCardImageBitmap(BitmapManager.createCardImageBitmapFromFile(cardImageFileName));
					}else{
						card.setCardImageBitmap(BitmapManager.createCardImageBitmapFromResource(getApplicationContext(), category));
					}
					cardList.add(card);					
					adapter.clear();
					setAdapterData();
				}else if(resultMode == Common.RESULT_MODE_MODIFY){
					int position = data.getIntExtra("POSITION", -1);				
					if(position>-1){
						CardVO card = cardList.get(position);
						int category = data.getIntExtra(Common.DATA_CATEGORY, -1);
						String cardName = data.getStringExtra(Common.DATA_CARD_NAME);
						String cardNumber = data.getStringExtra(Common.DATA_CARD_NUMBER);
						String cardImageFileName = data.getStringExtra(Common.DATA_CARD_IMAGE_FILE_NAME);
						card.setCategory(category);
						card.setName(cardName);
						card.setCardNumber(cardNumber);
						card.setBarCodeBitmap(BitmapManager.createBarCodeBitmapFromBarCodeString(cardNumber));
						if(cardImageFileName != null){
							card.setCardImageFileName(cardImageFileName);
							card.setCardImageBitmap(BitmapManager.createCardImageBitmapFromFile(cardImageFileName));
						}else{
							card.setCardImageBitmap(BitmapManager.createCardImageBitmapFromResource(getApplicationContext(), category));
						}
							
						adapter.clear();
						setAdapterData();
					}
				}else if(resultMode == Common.RESULT_MODE_CANCEL){
					// 아무것도 하지 않음
					return;
				}else if(resultMode == Common.RESULT_MODE_DELETE){
					int position = data.getIntExtra("POSITION", -1);
					if (position > -1) {
						// 확인 질문용 다이알로그 추가할 것
						cardList.remove(position);
						adapter.clear();
						setAdapterData();

					}
				}
				/*
				 *  xml저장
				 */
				DataManager.exportCardListToXML(this, cardList);
				
			}
			mGearFitWalletDialog.finish();
			mGearFitWalletDialog = new GearFitWalletDialog(getApplicationContext());
		}		
	}
	
	private void setAdapterData(){
		for(CardVO card:cardList){
			adapter.add(card.getName());
		}
		
		adapter.add(getString(R.string.add));

	}
}
