package com.sdamsdam.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MyCustomWidget extends AppWidgetProvider
{
	private static final String TAG = "sdamsdam";
	private Context context;
	
	private static boolean isDown = false;
	private static boolean isSMSReceived = false;

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
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.mycustomwidget);
			
			Log.v(TAG, "SMS Received = " + isSMSReceived);  
			if(isSMSReceived == true)
				views.setTextViewText(R.id.textView1, "문자 왔숑");
			
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
		ImageView iv = new ImageView(context);
		
		Log.i(TAG, "======================= initUI() =======================");
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.mycustomwidget);

		Intent eventIntent = new Intent(Const.ACTION_EVENT);
		Intent moveIntent = new Intent(Const.ACTION_MOVE);
		Intent downIntent = new Intent(Const.ACTION_DOWN);
		Intent upIntent = new Intent(Const.ACTION_UP);
		
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
		Log.d(TAG, "onReceive() action = " + action);

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
			Log.d(TAG, "SMS Received");
			// Widget update didn't work well
			isSMSReceived = true;
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
		}
	}
}
