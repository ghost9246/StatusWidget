package com.sdamsdam.widget;

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

public class MainLayout extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainlayout);
	}

	public void mOnClick(View v){

		switch(v.getId()){    	

		case R.id.setbutton:    		
			Intent intent = new Intent(this, Setting.class);
			startActivity(intent); 		
			break;
		}
	}
}