package com.sdamsdam.widget;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class StopWatch extends Activity {
	
	TextView mainTxt;
	static TextView time;
	ThreadTime thread;
	static int checkHandler = 0;
	static long count = 0;
	
	private static final String TAG = "time_TAG";
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        time = (TextView)findViewById(R.id.time);
        
        Log.d(TAG, "setting view");
        
            
        thread = new ThreadTime(mHandler);
        thread.start();
        thread.onStart();
        
         
    
    }
    
    static Handler mHandler = new Handler(){
    	
		public void handleMessage(Message msg){
			Log.v("StopWatch", "Handler" + count);
			count ++;
			long second = getSecond(count);
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
	
}




