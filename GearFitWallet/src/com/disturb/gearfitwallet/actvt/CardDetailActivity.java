package com.disturb.gearfitwallet.actvt;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.disturb.gearfitwallet.R;
import com.disturb.gearfitwallet.vo.CardVO;
import com.disturb.gearfitwallet.vo.GlobalVO;
import com.disturb.utils.BitmapManager;
import com.disturb.utils.Common;
// ī���� �������� �����ִ� ��Ƽ��Ƽ
// ī�� �߰�, ����, ���� ����
public class CardDetailActivity extends Activity {

	private GlobalVO gVo;
	private CardVO card;
	private String mode;	
	private int position;
	private Spinner spinner;
	private int categoryIndex;
	private ImageView cardImageView;
	EditText editCardName;
	EditText editCardNum1;
	EditText editCardNum2;
	EditText editCardNum3; 
	EditText editCardNum4;
	String cardNumber;
	private boolean isStart = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// �������� ����
		gVo = (GlobalVO) getApplicationContext();
		setContentView(R.layout.activity_card_detail);
		// ����Ʈ ��������
		Intent recvIntent = getIntent();
		// ���� ������ ��������
		Bundle b = recvIntent.getExtras();		
		position = b.getInt("POSITION");
		mode = b.getString("MODE");
		
		// ����������� Ȯ��
		if (Common.MODE_MODIFY.equals(mode)) {
			card = gVo.getCardList().get(position);
			categoryIndex = card.getCategory();
		}else{
			card = new CardVO();
		}
			

		cardImageView = (ImageView)findViewById(R.id.cardImageView);
		
		spinner = (Spinner) findViewById(R.id.spinner_card_category);

		ArrayAdapter<?> card_category_adapter = ArrayAdapter.createFromResource(this, R.array.card_category,
						android.R.layout.simple_spinner_item);
		card_category_adapter	.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(card_category_adapter);
		spinner.setSelected(false);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,	int position, long id) {
				if(isStart){
					isStart = false;
					if(card.getCardImageFileName() != null)
						return;
				}
//				String a[] = getResources().getStringArray(	R.array.card_category);		
				categoryIndex = position+1;			
				
				int drawId = R.drawable.card0;
				if(categoryIndex==Common.CARD_CATEGORY_HAPPYPOINT){
//				if(a[categoryIndex].equals("��������Ʈ")){
					drawId = R.drawable.card1;		
				}else if(categoryIndex==Common.CARD_CATEGORY_SKT){
//				}else if(a[categoryIndex].equals("SKT")){
					drawId = R.drawable.card2;		
				}else if(categoryIndex==Common.CARD_CATEGORY_CJONE){
//				}else if(a[categoryIndex].equals("CJ ONE")){
					drawId = R.drawable.card3;		
				}else{
					categoryIndex=0;
					drawId = R.drawable.card0;
				}
				
				Bitmap bitmap =Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), drawId), 400, 246, false);
				cardImageView.setImageBitmap(bitmap);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		/*
		 * �̹��� ���� ��ư
		 */
		final Button btnChangeImage =  (Button) findViewById(R.id.btnChangeCardImage);
		btnChangeImage.setOnClickListener(onBtnChangeImageClickListener);
		
		// ��尡 �߰��� ���
		if ("create".equals(mode)) {
			// ���� ��ư ������
			Button btnDelete = (Button)findViewById(R.id.btnDelete);
			btnDelete.setVisibility(View.GONE);
		} else {
			int p = card.getCategory();
			spinner.setSelection(p-1);
			if(card.getCardImageFileName() != null){
				if(card.getCardImageBitmap() != null){					
					cardImageView.setImageBitmap(Bitmap.createScaledBitmap(card.getCardImageBitmap(), 400, 246, false));
				}else{
					cardImageView.setImageBitmap(Bitmap.createScaledBitmap(BitmapManager.createCardImageBitmapFromFile(card.getCardImageFileName()), 400, 246, false));										
				}
			}
		}
		/*
		 * ī���̸� �Է�
		 */
		// ī���̸� �Է��� ���� ����Ʈ�ؽ�Ʈ
		editCardName = (EditText) findViewById(R.id.editCardName);
		/*
		 * ī���ȣ �Է�
		 */
		// ī���ȣ �Է��� ���� ����Ʈ�ؽ�Ʈ
		editCardNum1 = (EditText) findViewById(R.id.editCardNum1);
		editCardNum2 = (EditText) findViewById(R.id.editCardNum2);
		editCardNum3 = (EditText) findViewById(R.id.editCardNum3);
		editCardNum4 = (EditText) findViewById(R.id.editCardNum4);
		InputFilter[] filter = new InputFilter[1];
		filter[0] = new InputFilter.LengthFilter(4);
		editCardNum1.setFilters(filter);
		editCardNum2.setFilters(filter);
		editCardNum3.setFilters(filter);
		editCardNum4.setFilters(filter);

		TextWatcher textWatcher = new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() == 4) {
					if (editCardNum1.isFocused())
						editCardNum2.requestFocus();
					else if (editCardNum2.isFocused())
						editCardNum3.requestFocus();
					else if (editCardNum3.isFocused())
						editCardNum4.requestFocus();
					// else if(editCardNum2.isFocused())

				}
			}
		};

		editCardNum1.addTextChangedListener(textWatcher);
		editCardNum2.addTextChangedListener(textWatcher);
		editCardNum3.addTextChangedListener(textWatcher);
		editCardNum4.addTextChangedListener(textWatcher);
		if(Common.MODE_MODIFY.equals(mode)){
			editCardName.setText(card.getName());
			if(card.getCardNumber() != null && card.getCardNumber().length() == 16){
				editCardNum1.setText(card.getCardNumber().substring(0, 4));
				editCardNum2.setText(card.getCardNumber().substring(4, 8));
				editCardNum3.setText(card.getCardNumber().substring(8, 12));
				editCardNum4.setText(card.getCardNumber().substring(12, 16));
			}
		}
		
		/*
		 * �ϴܹ�ư ����
		 */
		final Button btnOK =  (Button) findViewById(R.id.btnOK);
		final Button btnCancel =  (Button) findViewById(R.id.btnCancel);
		final Button btnDelete =  (Button) findViewById(R.id.btnDelete);
		btnOK.setOnClickListener(onBtnClickListener);
		btnCancel.setOnClickListener(onBtnClickListener);
		btnDelete.setOnClickListener(onBtnClickListener);
		if(Common.MODE_CREATE.equals(mode)){
			btnOK.setText("Add");
		}
	}
	
	View.OnClickListener onBtnChangeImageClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent galeryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);			
			galeryIntent.setType("image/*");
			List<ResolveInfo> list = getPackageManager().queryIntentActivities(galeryIntent, 0);
			int size = list.size();
			if(size == 0){
				Toast.makeText(getApplicationContext(), "can not find image select app", Toast.LENGTH_SHORT).show();
			}else{
				ResolveInfo res = list.get(0);
				galeryIntent.setClassName(res.activityInfo.packageName, res.activityInfo.name);
			}
			startActivityForResult(Intent.createChooser(galeryIntent, "ī�� �̹��� ����"), Common.REQUEST_CODE_PICK_FROM_ALBUM);
		}
		
	};
	
	View.OnClickListener onBtnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent resultIntent = new Intent();
			resultIntent.putExtra("POSITION", position);
			if(v.getId() == R.id.btnOK){

				card.setCategory(categoryIndex);
				card.setName(editCardName.getText().toString());
				card.setCardNumber(getCardNumber());

				resultIntent.putExtra(Common.DATA_CATEGORY, categoryIndex);
				resultIntent.putExtra(Common.DATA_CARD_NAME, editCardName.getText().toString());
				resultIntent.putExtra(Common.DATA_CARD_NUMBER, getCardNumber());
				resultIntent.putExtra(Common.DATA_CARD_IMAGE_FILE_NAME, card.getCardImageFileName());
				if (Common.MODE_MODIFY.equals(mode)) {
					resultIntent.putExtra("RESULT_MODE", Common.RESULT_MODE_MODIFY);					
				}else{
					resultIntent.putExtra("RESULT_MODE", Common.RESULT_MODE_ADD);					
				}				
			}else if(v.getId() == R.id.btnCancel){
				resultIntent.putExtra("RESULT_MODE", Common.RESULT_MODE_CANCEL);
			}else if(v.getId() == R.id.btnDelete){				
				resultIntent.putExtra("RESULT_MODE", Common.RESULT_MODE_DELETE);				
			}			
			setResult(Activity.RESULT_OK,resultIntent);
			finish();
		}
	};
	
	

	@Override
	public void onBackPressed() {
		Intent resultIntent = new Intent();
		resultIntent.putExtra("RESULT_MODE", Common.RESULT_MODE_CANCEL);
		setResult(0,resultIntent);
		finish();
	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode != RESULT_OK){
			return;
		}
		// 200*123
		switch(requestCode){
		case Common.REQUEST_CODE_PICK_FROM_ALBUM:
			Uri mImageCaptureUri = data.getData();			
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(mImageCaptureUri, "image/*");
			intent.putExtra("outputX", 200);
			intent.putExtra("outputY", 123);
			intent.putExtra("aspectX", 2);
			intent.putExtra("aspectY", 1);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);
			startActivityForResult(intent, Common.REQUEST_CODE_CROP_FROM_CAMERA);
			break;
		case Common.REQUEST_CODE_CROP_FROM_CAMERA:
			final Bundle extras = data.getExtras();
//			String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/temp/"+
//			System.currentTimeMillis()+".jpg";
			if(extras != null){
				Bitmap bitmap =extras.getParcelable("data");	// crop�� bitmap 
				Bitmap photo = Bitmap.createScaledBitmap(bitmap, 400, 246, false);
				cardImageView.setImageBitmap(photo);
				// VO ���� ���� �߰� �� ��.
				File fileRoot = getDir("cardData", Activity.MODE_PRIVATE);
				String path = fileRoot.getAbsolutePath();
				String filePath = path+ "/ci"+getCardNumber()+".jpg";
				BitmapManager.saveBitmapToFile(bitmap, filePath);
				// ������ ����
				card.setCardImageFileName(filePath);
				card.setCardImageBitmap(bitmap);
			}
			break;
		}
	}
	
	private String getCardNumber(){
		return editCardNum1.getText().toString()
				+editCardNum2.getText().toString()
				+editCardNum3.getText().toString()
				+editCardNum4.getText().toString();
	}
}
