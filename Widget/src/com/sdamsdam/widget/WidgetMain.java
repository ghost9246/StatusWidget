package com.sdamsdam.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

public class WidgetMain extends AppWidgetProvider
{
	private static final String TAG = "sdamsdam";
	private Context context;

	private static boolean isDown = false;
	
	private static boolean isSMSNotRead = false;
	private static boolean isBatteryLow = false;
	private static boolean isHeadset = false;
	private static boolean isPlaneMode = false;
	private static boolean isWifiConnected = false;
	private static boolean isBluetoothActivated = false;
	private static boolean isPowerConnected = false;
	private static boolean isUsbAttached = false;

	@Override
	public void onEnabled(Context context)
	{
		Log.i(TAG, "======================= onEnabled() =======================");
		super.onEnabled(context);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds)
	{
		Log.i(TAG, "======================= onUpdate() =======================");

		this.context = context;

		super.onUpdate(context, appWidgetManager, appWidgetIds);

		for(int i=0; i<appWidgetIds.length; i++)
		{
			String output = "";
			int appWidgetId = appWidgetIds[i];
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
			
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
			
			Log.v(TAG, "Supposed output: "+output);
			views.setTextViewText(R.id.textView1, output);
			
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
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
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

		Intent eventIntent = new Intent(Const.ACTION_EVENT);
		/*
		Intent moveIntent = new Intent(Const.ACTION_MOVE);
		Intent downIntent = new Intent(Const.ACTION_DOWN);
		Intent upIntent = new Intent(Const.ACTION_UP);
		 */

		PendingIntent eventPIntent = PendingIntent.getBroadcast(context, 0, eventIntent, 0);
		/*
		PendingIntent movePIntent = PendingIntent.getBroadcast(context, 0, moveIntent, 0);
		PendingIntent downPIntent = PendingIntent.getBroadcast(context, 0, downIntent, 0);
		PendingIntent upPIntent = PendingIntent.getBroadcast(context, 0, upIntent, 0);
		 */

		// Set intent's event
		views.setOnClickPendingIntent(R.id.imageView1, eventPIntent);

		/*
		views.setOnClickPendingIntent(R.id.imageView1, movePIntent);
		views.setOnClickPendingIntent(R.id.imageView1, downPIntent);
		views.setOnClickPendingIntent(R.id.imageView1, upPIntent);
		 */

		for(int appWidgetId : appWidgetIds)
			appWidgetManager.updateAppWidget(appWidgetId, views);
		
		// sendBroadcast(new Intent(Const.ACTION_EVENT));
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
		super.onReceive(context, intent);
		String action = intent.getAction();

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
		// SMS Broadcast
		else if (Const.SMS_RECEIVED.equals(action))
		{
			Log.v(TAG, "SMS Received");

			isSMSNotRead = true;
			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}
		else if(Const.BATTERY_CHANGED.equals(action))
		{			
			int bLevel = intent.getIntExtra("level", 0);
			int chargeState = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
			
			Log.v(TAG, "Battery level changed: " + bLevel);
			
			if(bLevel < 20)
				isBatteryLow = true;
			else
				isBatteryLow = false;

		    switch (chargeState)
		    {
		        case BatteryManager.BATTERY_STATUS_CHARGING:
		        case BatteryManager.BATTERY_STATUS_FULL:
		            Log.v(TAG, "charging");
		            break;
		        default:
		        	Log.v(TAG, "Not charging");
		    }
			
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
		else if(Const.HEADSET_MODE.equals(action))
		{
			isHeadset = true;
			Log.v(TAG, "Headset Mode: " + isHeadset);
			
			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}
		else if(action.equals(Intent.ACTION_HEADSET_PLUG))
		{
			isHeadset = true;
			Log.v(TAG, "Headset Mode2: " + isHeadset);
			
			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}

		// Action
		/*
		else if(Const.ACTION_DOWN.equals(action))
		{
			Toast.makeText(context, "DOWN", Toast.LENGTH_SHORT).show();
			isDown = true;
		}
		else if(Const.ACTION_UP.equals(action))
		{
			if(isDown)
				Toast.makeText(context, "UPUP", Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(context, "UPDOWN", Toast.LENGTH_SHORT).show();
		}
		else if(Const.ACTION_MOVE.equals(action))
		{
			Toast.makeText(context, "MOVE", Toast.LENGTH_SHORT).show();
		}
		*/		
		else if(Const.ACTION_EVENT.equals(action))
		{
			int textcode = (int)(Math.random()*5);
			String text = null;

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

			Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
			
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
