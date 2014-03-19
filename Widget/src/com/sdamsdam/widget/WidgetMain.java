package com.sdamsdam.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
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
			int appWidgetId = appWidgetIds[i];
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
			
			views.setTextViewText(R.id.textView1, "");
			appWidgetManager.updateAppWidget(appWidgetId, views);

			if(isBatteryLow == true)
			{
				views.setTextViewText(R.id.textView1, "Battery Low");
				appWidgetManager.updateAppWidget(appWidgetId, views);
			}
			
			if(isSMSNotRead == true)
			{
				Log.v(TAG, "Moonja Watshong");
				views.setTextViewText(R.id.textView1, "SMS Received");
				appWidgetManager.updateAppWidget(appWidgetId, views);
			}
			
			if(isWifiConnected == true)
			{
				Log.v(TAG, "Wifi Connected");
				views.setTextViewText(R.id.textView1, "WiFi");
				appWidgetManager.updateAppWidget(appWidgetId, views);
			}

			if(isPlaneMode == true)
			{
				views.setTextViewText(R.id.textView1, "I'm on a plane");
				appWidgetManager.updateAppWidget(appWidgetId, views);
			}

			if(isHeadset == true)
			{
				// not working yet
				Log.v(TAG, "Headset True");
				views.setTextViewText(R.id.textView1, "Ear breaking");
				appWidgetManager.updateAppWidget(appWidgetId, views);
			}
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
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{ 
		super.onReceive(context, intent);

		String action = intent.getAction();
		Log.v(TAG, "onReceive() action = " + action);
		
		Log.d("HeadSetPlugInTest", "state: " + intent.getIntExtra("state", -1));

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
		else if(Const.BATTERY_LOW.equals(action))
		{
			Log.v(TAG, "Battery Low");
			isBatteryLow = true;
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
		else if(Intent.ACTION_HEADSET_PLUG.equals(action))
		{
			isHeadset = !isHeadset;
			Log.v(TAG, "Headset Mode: " + isHeadset);
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
