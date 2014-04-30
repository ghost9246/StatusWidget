package com.sdamsdam.widget;

import android.annotation.*;
import android.app.*;
import android.appwidget.*;
import android.bluetooth.*;
import android.content.*;
import android.database.*;
import android.graphics.*;
import android.media.*;
import android.net.*;
import android.net.wifi.*;
import android.os.*;
import android.provider.*;
import android.util.*;
import android.widget.*;

public class WidgetMain extends AppWidgetProvider
{
	private static final String TAG = "sdamsdam";
	private Context context;
	private AudioManager manager;
	
	private AnimationThread aniThread = null;
	private static AnimationObserver aniOb = null;

	private static boolean isDown = false;
	private static boolean isSMSNotRead = false;
	private static boolean isBatteryLow = false;
	private static boolean isHeadset = false;
	private static boolean isPlaneMode = false;
	private static boolean isWifiConnected = false;
	private static boolean isBluetoothActivated = false;
	private static boolean isPowerConnected = false;
	private static boolean isUsbAttached = false;
	private static boolean isThreadCreated = false;
	private int nowBattery;
	
	
	

	@Override
	public void onEnabled(Context context)
	{
		Log.i(TAG, "======================= onEnabled() =======================");
		super.onEnabled(context);
	}

	
	
	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds)
	{
		
		
		this.context = context;

		
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
					R.layout.gridview_appwidget);
			
			
				// GridView  making the intent indicating the service that you want to bind the data to the
				Intent intent = new Intent(context, GridViewAppWidgetRemoteViewsService.class);
				intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
				Uri intentUri = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME));
				intent.setData(intentUri);

				// GridView と GridViewAppWidgetRemoteViewsService を結びつける
				remoteViews.setRemoteAdapter(appWidgetIds[0],R.id.messages_gridview, intent);
 
				// messages_gridview にデータがなかった時表示するビューを empty_textview に設定
				
				remoteViews.setEmptyView(R.id.messages_gridview, R.id.empty_textview);
				
				Log.v(TAG, "action3");

			
				// GridView의 각 아이템 클릭할때의 인텐트 정하기 
				
				Intent templateIntent1 = new Intent(Const.ACTION_EVENT);
				//Intent templateIntent2 = new Intent(context, HelloActivity.class);
				
						
				PendingIntent template1 = 
						PendingIntent.getBroadcast(context, 0, templateIntent1, PendingIntent.FLAG_UPDATE_CURRENT);
				
				/*
				PendingIntent template2 = PendingIntent.getActivity(context, 0,
						templateIntent2, PendingIntent.FLAG_UPDATE_CURRENT);
					    */
					
				remoteViews.setPendingIntentTemplate(R.id.messages_gridview, template1);
				
		

			appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
			

			/*
				for(int i=0; i<appWidgetIds.length; i++)
				{
					String output = "";
					int appWidgetId = appWidgetIds[i];
					// Show device's state
					if(isBatteryLow == true)
						output = output.concat("Hungry ");

					if(isSMSNotRead == true)
						output = output.concat("SMS ");

					if(isWifiConnected == true)
						output = output.concat("WiFi ");

					if(isPlaneMode == true)
						output = output.concat("Plane ");

					if(isHeadset == true)
						output = output.concat("Headset ");

					if(isBluetoothActivated == true)
						output = output.concat("Bluetooth ");

					if(isPowerConnected == true)
						output = output.concat("Charging ");

					if(isUsbAttached == true)
						output = output.concat("PC ");

					
					
				}
				*/
					
				/*
				
				// Show thread animation
			
				if(isThreadCreated == true)
				{
					switch(aniOb.GetFrameNo())
					{
					case 0:
						remoteViews.setImageViewResource(R.layout.gridview_appwidget, R.id.Linear1);
						break;
						
					case 1:
						remoteViews.setImageViewResource(R.layout.gridview_appwidget, R.id.Linear1);
						break;
					}
					Log.v(TAG, "Frame #" + Integer.toString(aniOb.GetFrameNo()));
					appWidgetManager.updateAppWidget(appWidgetId, views);
				}
					
					*/
	
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds)
	{
		Log.i(TAG, "======================= onDeleted() =======================");
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onDisabled(Context context)
	{
		Log.i(TAG, "======================= onDisabled() =======================");
		super.onDisabled(context);
	}

	
	
	public void initUI(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
	{
		this.context = context;
		ImageView iv = new ImageView(context);

		Log.i(TAG, "======================= initUI() =======================");
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.gridview_appwidget);
		
		// Set additional intent filter (Headset & battery)
		context.getApplicationContext().registerReceiver(this, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		context.getApplicationContext().registerReceiver(this, new IntentFilter(Intent.ACTION_HEADSET_PLUG));
		
		// Create & run thread
		aniThread = new AnimationThread();
		aniOb = AnimationObserver.GetInstance();
		aniThread.SetState(true);
		aniThread.start();
		isThreadCreated = true;

		// Set event intent
		Intent eventIntent = new Intent(Const.ACTION_EVENT);
	
		PendingIntent eventPIntent = PendingIntent.getBroadcast(context, 0, eventIntent, 0);
	

		
		// Set intent's event
		//views.setOnClickPendingIntent(R.id.Linear1, eventPIntent);

	

		for(int appWidgetId : appWidgetIds)
			appWidgetManager.updateAppWidget(appWidgetId, views);

		context.sendBroadcast(new Intent(Const.ACTION_EVENT));
		Log.v(TAG, Integer.toString(nowBattery));
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
		super.onReceive(context, intent);
		String action = intent.getAction();

		Log.v(TAG, "action: " + action);

		// Default Receiver
		if(AppWidgetManager.ACTION_APPWIDGET_ENABLED.equals(action)) {}
		else if(AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action))
		{
			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			initUI(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}
		else if(AppWidgetManager.ACTION_APPWIDGET_DELETED.equals(action)) {}
		else if(AppWidgetManager.ACTION_APPWIDGET_DISABLED.equals(action)) {}

		// Custom Recevier
		else if(Const.SMS_RECEIVED.equals(action))
		{
			Log.v(TAG, "SMS Received");

			isSMSNotRead = true;
			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}
		else if(Const.BATTERY_CHANGED.equals(action))
		{
			int bLevel = intent.getIntExtra("level", 0);
			Log.v(TAG, "Battery level changed: " + bLevel);

			if(bLevel < 20)
				isBatteryLow = true;
			else
				isBatteryLow = false;

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}
		else if(Const.WIFI_CONNCHANGE.equals(action))
		{
			Log.v(TAG, "Wifi Connect state changed");
			NetworkInfo netInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
			isWifiConnected = netInfo.isConnected();

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}
		else if(Const.PLANE_MODE.equals(action))
		{
			if(Settings.System.getInt(context.getContentResolver(),Settings.System.AIRPLANE_MODE_ON, 0) == 1)
				isPlaneMode = true;
			else
				isPlaneMode = false;

			Log.v(TAG, "isPlaneMode: " + isPlaneMode);

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}
		else if(Const.BLUETOOTH_CHANGE.equals(action))
		{
			Log.v(TAG, "Bluetooth");
			BluetoothAdapter mBTAdapter = BluetoothAdapter.getDefaultAdapter();
			isBluetoothActivated = mBTAdapter.isEnabled();

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}
		else if(Const.POWER_CONNECT.equals(action))
		{
			Log.v(TAG, "Power Connected");
			isPowerConnected = true;

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}
		else if(Const.POWER_DISCONNECT.equals(action))
		{
			Log.v(TAG, "Power Disconnected");
			isPowerConnected = false;

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}
		else if(Const.HEADSET_MODE.equals(action))
		{
			Log.v(TAG, "Entering headset");
			
			if(intent.getIntExtra("state", -1) == 1)
                isHeadset = true;
                
			else
                isHeadset = false;

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}
		// not working
		else if(Const.USB_ATTACH.equals(action))
		{
			Log.v(TAG, "USB Attached");
			isUsbAttached = true;

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}
		else if(Const.USB_DETACH.equals(action))
		{
			Log.v(TAG, "USB Detached");
			isUsbAttached = false;

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}

		
		else if(Const.ACTION_EVENT.equals(action))
		{
			int textcode = (int)(Math.random()*5);
			String text = null;
			
			String extraName = intent.getStringExtra("EXTRA_NAME");
			

			switch(textcode)
			{
			case 0:
				text = "?";
				break;

			case 1:
				text = "....";
				break;

			case 2:
				text = "...";
				break;

			case 3:
				text = "!!!";
				break;

			case 4:
				text = "...!!";
				break;
			}

			Toast.makeText(context, extraName, Toast.LENGTH_SHORT).show();

			Check_SMSRead(context);

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));

			// solution A: check SMS/WiFi when screen on
			// solution B: touch Rammus(temporary solution for user who DON'T turn off screen)
		}
	}

	public void Check_SMSRead(Context context)
	{
		Uri allMessage = Uri.parse("content://sms");
		ContentResolver cr = context.getContentResolver();

		Cursor c = cr.query(allMessage, 
				new String[] { "_id", "thread_id", "address", "person", "date", "body",
				"protocol","read","status", "type","reply_path_present",
				"subject","service_center", "locked","error_code", "seen"},
				null, null, 
				"date DESC");

		if (c != null)
			c.moveToFirst();

		long read = c.getLong(7);

		if(read == 1)
			isSMSNotRead = false;
		else
			isSMSNotRead = true;
	}
}
