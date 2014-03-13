package com.sdamsdam.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

public class WidgetMain extends AppWidgetProvider
{
	private static final String TAG = "sdamsdam";
	private Context context;

	private static boolean isDown = false;
	private static boolean isSMSReceived = false;
	private static boolean isBatteryLow = false;
	private static boolean isPlaneMode = false;
	private static boolean isHeadset = false;

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
			// this area may be trouble...
			int appWidgetId = appWidgetIds[i];
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

			if(isBatteryLow == true)
				views.setTextViewText(R.id.textView1, "애미야 배고프다 밥 갖고 온나");
			else if(isBatteryLow == false)
				views.setTextViewText(R.id.textView1, "");

			if(isSMSReceived == true)
			{
				Log.v(TAG, "Moonja Watshong");
				views.setTextViewText(R.id.textView1, "문자 왔숑");
				appWidgetManager.updateAppWidget(appWidgetId, views);
			}

			if(isPlaneMode == true)
				views.setTextViewText(R.id.textView1, "비행기 타고 있다요");
			else if(isPlaneMode == false)
				views.setTextViewText(R.id.textView1, "");

			if(isHeadset == true)
			{
				// not working yet
				Log.v(TAG, "Headset True");
				views.setTextViewText(R.id.textView1, "Dodoomchit");
			}	
			else if(isHeadset == false)
				views.setTextViewText(R.id.textView1, "");			
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
		else if(Const.SMS_RECEIVED.equals(action))
		{
			isSMSReceived = true;
			Log.v(TAG, "SMS Received : " + isSMSReceived);
		}
		else if(Const.BATTERY_LOW.equals(action))
		{
			Log.v(TAG, "Battery Low");
			isBatteryLow = true;
		}
		else if(Const.BATTERY_OK.equals(action))
		{
			Log.v(TAG, "Battery Okay");
			isBatteryLow = false;
		}
		else if(Const.PLANE_MODE.equals(action))
		{
			isPlaneMode = !isPlaneMode;
			Log.v(TAG, "Change Airplane Mode: " + isPlaneMode);
		}
		else if(Const.HEADSET_MODE.equals(action))
		{
			isHeadset = !isHeadset;
			Log.v(TAG, "Change Headset Mode: " + isHeadset);
		}

		// Action
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
		else if(Const.ACTION_EVENT.equals(action))
		{
			int textcode = (int)(Math.random()*5);
			String text = null;

			switch(textcode)
			{
			case 0:
				text = "뀨?";
				break;

			case 1:
				text = "....";
				break;

			case 2:
				text = "뀨잉...";
				break;

			case 3:
				text = "뀨웃! 뀨우웃!";
				break;

			case 4:
				text = "뀨르르르릉....";
				break;
			}

			Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
			Check_SMSRead(context);
		}

		AppWidgetManager manager = AppWidgetManager.getInstance(context);
		this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
	}

	public void Check_SMSRead(Context context)
	{
		Log.v(TAG, "Entering trouble zone");
		
		Uri allMessage = Uri.parse("content://sms");
		ContentResolver cr = context.getContentResolver();
		
		Cursor c = cr.query(allMessage, 
				new String[] { "_id", "thread_id", "address", "person", "date", "body",
				"protocol","read","status", "type","reply_path_present",
				"subject","service_center", "locked","error_code", "seen"},
				null, null, 
				"date DESC");

		int read = c.getInt(7);			// trouble code. OutOfIndex??
		
		Log.v(TAG, "success");
	}
}