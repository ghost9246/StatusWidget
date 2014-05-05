package com.sdamsdam.widget;


import java.io.*;

import android.annotation.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;




public class Setting extends Activity { 
	
	
	
	private static final String TAG = "Setting_TAG";

	private static final boolean DEVELOPER_MODE = true;
	
	
	//Mesuring Time
	static long count = 0;
	static TextView time;	
	ThreadTime thread;	
	static long second = 0;
	
	
	//프레퍼런스 값들
	TextPref mPref;			
	String stNum1;
	String stNum2;
	
	int spTag1;
	int spTag2;
	int spTag3;
	
	
	Boolean checked[] = new Boolean[20];
	
	

	
	//스피너 변수들
	ArrayAdapter<CharSequence> adspin1;
	ArrayAdapter<CharSequence> adspin2;
	ArrayAdapter<CharSequence> adspin3;
	boolean mInitSpinner;
		
	
	
	
	
	
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public void onCreate(Bundle savedInstanceState) {

		if (DEVELOPER_MODE) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
			.detectDiskReads()
			.detectDiskWrites()
			.detectNetwork()
			.penaltyLog()
			.build());
		}		
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.settingpage);             
        Log.d(TAG, "setting view");
        
      
        
        
        time = (TextView)findViewById(R.id.time);
        
        Log.d(TAG, "setting view");
        
            
        thread = new ThreadTime(mHandler);
        thread.start();
        thread.onStart();
        
        
               
        
        Log.d(TAG, "time01");

        
        
        
        
        
        
        
        
        
        //프레퍼런스 읽어오기        
        
        
        
        
        File saveDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "SsdamSsdam"); // dir : 생성하고자 하는 경로
        if(!saveDir.exists()) saveDir.mkdirs();
        
        
        
        
        try {
			mPref = new TextPref("mnt/sdcard/SsdamSsdam/textpref.pref");
		} catch (Exception e) { 
			e.printStackTrace();
		}       
        mPref.Ready();    
        
        
        TextView Num1;
    	TextView Num2;
    	
    	
        Num1 = (TextView)findViewById(R.id.input01);		
        stNum1 = mPref.ReadString("stNum1","0");
        Num1.setText(stNum1);
        		
      
     
              
        spTag1 = mPref.ReadInt("Tag1", 0);
        spTag2 = mPref.ReadInt("Tag2", 0);
        spTag3 = mPref.ReadInt("Tag3", 0);
        
        Log.d(TAG,"spTag3");
        
        checked[0] = mPref.ReadBoolean("checked0", false);
        checked[1] = mPref.ReadBoolean("checked1", false);
        checked[2] = mPref.ReadBoolean("checked2", false);
        checked[3] = mPref.ReadBoolean("checked3", false);
        checked[4] = mPref.ReadBoolean("checked4", false);
        checked[5] = mPref.ReadBoolean("checked5", false);
        checked[6] = mPref.ReadBoolean("checked6", false);
        checked[7] = mPref.ReadBoolean("checked7", false);
        checked[8] = mPref.ReadBoolean("checked8", false);
        checked[9] = mPref.ReadBoolean("checked9", false);
        checked[10] = mPref.ReadBoolean("checked10", false);
        checked[11] = mPref.ReadBoolean("checked11", false);
        checked[12] = mPref.ReadBoolean("checked12", false);
        checked[13] = mPref.ReadBoolean("checked13", false);
       
        Log.d(TAG,"checked[11]");

		mPref.EndReady();
		
		
		
		
		
		//체크박스 값에 따라 체크해주기
		
		if(checked[0]){
			CheckBox bo = (CheckBox)findViewById(R.id.kind1);			
			bo.setChecked(true);
		}
		if(checked[1]){
			CheckBox bo = (CheckBox)findViewById(R.id.kind2);
			bo.setChecked(true);
		}
		if(checked[2]){
			CheckBox bo = (CheckBox)findViewById(R.id.kind3);
			bo.setChecked(true);
		}
		if(checked[3]){
			CheckBox bo = (CheckBox)findViewById(R.id.kind4);
			bo.setChecked(true);
		}
		if(checked[4]){
			CheckBox bo = (CheckBox)findViewById(R.id.kind5);
			bo.setChecked(true);
		}
		if(checked[5]){
			CheckBox bo = (CheckBox)findViewById(R.id.kind6);
			bo.setChecked(true);
		}
		if(checked[6]){
			CheckBox bo = (CheckBox)findViewById(R.id.for1);
			bo.setChecked(true);
		}
		if(checked[7]){
			CheckBox bo = (CheckBox)findViewById(R.id.for2);
			bo.setChecked(true);
		}
		if(checked[8]){
			CheckBox bo = (CheckBox)findViewById(R.id.for3);
			bo.setChecked(true);
		}
		if(checked[9]){
			CheckBox bo = (CheckBox)findViewById(R.id.dry1);
			bo.setChecked(true);
		}
		if(checked[10]){
			CheckBox bo = (CheckBox)findViewById(R.id.dry2);
			bo.setChecked(true);
		}
		if(checked[11]){
			CheckBox bo = (CheckBox)findViewById(R.id.dry3);
			bo.setChecked(true);
		}
		if(checked[12]){
			CheckBox bo = (CheckBox)findViewById(R.id.dry4);
			bo.setChecked(true);
		}
		if(checked[13]){
			CheckBox bo = (CheckBox)findViewById(R.id.dry5);
			bo.setChecked(true);
		}
		
		
		
		
		
		
		
		
		
		
		Spinner spin1 = (Spinner)findViewById(R.id.myspinner1);
		spin1.setPrompt("안녕스피너");

		adspin1 = ArrayAdapter.createFromResource(this, R.array.nation, 
				android.R.layout.simple_spinner_item);
		adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin1.setAdapter(adspin1);

		spin1.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, 
					int position, long id) {
				 //�ʱ�ȭ���� ���� ���ܽ�
				if (mInitSpinner == false) {
					mInitSpinner = true;
					return;
				}
				
				Toast.makeText(Setting.this,adspin1.getItem(position) + "를 선택했지 난.",
						Toast.LENGTH_SHORT).show();
				//프레퍼런스에 기록하기
				spTag1 = position;
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		//스피너 초기값지정
		spin1.setSelection(spTag1);
		
		
		
		Spinner spin2 = (Spinner)findViewById(R.id.myspinner2);
		spin2.setPrompt("안녕스피너");

		adspin2 = ArrayAdapter.createFromResource(this, R.array.vintage, 
				android.R.layout.simple_spinner_item);
		adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin2.setAdapter(adspin2);

		spin2.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, 
					int position, long id) {
				 //�ʱ�ȭ���� ���� ���ܽ�
				if (mInitSpinner == false) {
					mInitSpinner = true;
					return;
				}
				//프레퍼런스에 기록하기
				spTag2 = position;
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		//스피너 초기값지정
		spin2.setSelection(spTag2);
		
		
		
		Spinner spin3 = (Spinner)findViewById(R.id.myspinner3);
		spin3.setPrompt("안녕스피너");

		adspin3 = ArrayAdapter.createFromResource(this, R.array.vintage, 
				android.R.layout.simple_spinner_item);
		adspin3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin3.setAdapter(adspin3);

		spin3.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, 
					int position, long id) {
				 //�ʱ�ȭ���� ���� ���ܽ�
				if (mInitSpinner == false) {
					mInitSpinner = true;
					return;
				}
				//프레퍼런스에 기록하기
				spTag3 = position;
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		//스피너 초기값지정
		spin3.setSelection(spTag3);
		
		}
	
	
	
	
	
	
	
	
	

    static Handler mHandler = new Handler(){
    	
		public void handleMessage(Message msg){
			Log.v("StopWatch", "Handler" + count);
			count ++;
			second = getSecond(count);
			time.setText( second + "초 " + count%10 );
		}		
		
	};
	
	public static long getSecond(long milli){
		long secondValue = 0;
		secondValue = milli / 10;
		return secondValue;
	}
    
    
    
    
    class ThreadTime extends Thread{
		Handler mHandler;
		boolean sns = false; //Thread를 통제하기 위한 boolean 값
		public void run(){
			while(true){
				if(sns){
					Log.v("StopWatch", "ThreadTime");
					mHandler.sendEmptyMessage(0);
					try{
						Thread.sleep(100);
					}catch(InterruptedException e){
					}
				}
			}
		}
		
		//생성자
		public ThreadTime(Handler handler){
			mHandler = handler;
		}
		
		public void onStart(){
			sns = true;
		}
		
		public void onStop(){
			sns = false;
		}		
		
	}
        
        
    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void onPause() {
		super.onPause();

		
		
		

		/* 
		mPref.BulkWriteReady(1000);
		mPref.BulkWrite("Name", Name);
		mPref.BulkWrite("StNum", Integer.toString(StNum));
		mPref.CommitWrite();
		//*/
	}
	
	
	
	
	
	public void mOnClick(View v){
		Log.d(TAG,"monclick");
		
    	switch(v.getId()){    	
    		
    	case R.id.ok:    		
    		Intent intent = new Intent();    		
    		setResult(RESULT_OK,intent)	;
    		Log.d(TAG,"setOK");
    		
    		mPref.Ready();
    		
    		TextView txtname1 = (TextView)findViewById(R.id.input01);
    		stNum1 = txtname1.getText().toString();
    		
    		mPref.WriteString("stNum1", stNum1);
    		mPref.WriteString("stNum2", stNum2);
    		
    		mPref.WriteInt("Tag1", spTag1);
    		mPref.WriteInt("Tag2", spTag2);
    		mPref.WriteInt("Tag3", spTag3);
    		
    		mPref.WriteBoolean("checked0", checked[0]);
    		mPref.WriteBoolean("checked1", checked[1]);
    		mPref.WriteBoolean("checked2", checked[2]);
    		mPref.WriteBoolean("checked3", checked[3]);
    		mPref.WriteBoolean("checked4", checked[4]);
    		mPref.WriteBoolean("checked5", checked[5]);
    		mPref.WriteBoolean("checked6", checked[6]);
    		mPref.WriteBoolean("checked7", checked[7]);
    		mPref.WriteBoolean("checked8", checked[8]);
    		mPref.WriteBoolean("checked9", checked[9]);
    		mPref.WriteBoolean("checked10", checked[10]);
    		mPref.WriteBoolean("checked11", checked[11]);
    		mPref.WriteBoolean("checked12", checked[12]);
    		mPref.WriteBoolean("checked13", checked[13]);
    			
    		
    		mPref.CommitWrite();
    		
    		
    		
    		
    		finish();
    		break;
    		
    	case R.id.cancled:
    		setResult(RESULT_CANCELED);
    		finish();
    		break;
    		
			
			
		case R.id.reset3:
			Spinner spin2 = (Spinner)findViewById(R.id.myspinner2);
			spin2.setSelection(0);
			Spinner spin3 = (Spinner)findViewById(R.id.myspinner3);
			spin3.setSelection(0);
			break;
			
			
		case R.id.kind1:
			checked[0] = !checked[0];
			break;
		case R.id.kind2:
			checked[1] = !checked[1];
			break;
		case R.id.kind3:
			checked[2] = !checked[2];
			break;
		case R.id.kind4:
			checked[3] = !checked[3];
			break;
		case R.id.kind5:
			checked[4] = !checked[4];
			break;
		case R.id.kind6:
			checked[5] = !checked[5];
			break;
		case R.id.for1:
			checked[6] = !checked[6];
			break;
		case R.id.for2:
			checked[7] = !checked[7];
			break;
		case R.id.for3:
			checked[8] = !checked[8];
			break;
		case R.id.dry1:
			checked[9] = !checked[9];
			break;
		case R.id.dry2:
			checked[10] = !checked[10];
			Log.d(TAG,"checked[10]!");
			break;
		case R.id.dry3:
			checked[11] = !checked[11];
			break;
		case R.id.dry4:
			checked[12] = !checked[12];
			break;
		case R.id.dry5:
			checked[13] = !checked[13];			
			break;
			
		}
    		
    		
    		
    		
    }	
	
	
		
		
		

}
