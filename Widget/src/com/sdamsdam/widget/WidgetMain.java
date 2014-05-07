package com.sdamsdam.widget;

import android.content.Context;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

public class WidgetMain extends AppWidgetProvider
{
	// constant variables
	private static final String TAG = "sdamsdam";

	// object variables
	private Context context;	
	private static AnimationThread aniThread = null;
	private static AnimationObserver aniOb = null;
	private _ThreadHandler mMainHandler = null;

	// other variables
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
	private static GestureDetector mGestureDetector; 
	private int nowBattery;

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
		Log.i(TAG, "========== ============= onUpdate() =======================");

		this.context = context;

		super.onUpdate(context, appWidgetManager, appWidgetIds);

		for(int i=0; i<appWidgetIds.length; i++)
		{
			String output = "";
			int appWidgetId = appWidgetIds[i];
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

			// Show thread animation
			if(isThreadCreated)
			{
				switch(aniOb.GetFrameNo())
				{
				case 0:
					views.setImageViewResource(R.id.imageView1, R.drawable.rammus);
					break;

				case 1:
					views.setImageViewResource(R.id.imageView1, R.drawable.rammus1);
					break;

				case 2:
					views.setImageViewResource(R.id.imageView1, R.drawable.rammus2);
					break;

				case 3:
					views.setImageViewResource(R.id.imageView1, R.drawable.rammus3);
					break;

				case 4:
					views.setImageViewResource(R.id.imageView1, R.drawable.rammus4);
					aniThread.SetState(false);
					// aniThread.stop();
					break;
				}
				Log.v(TAG, "Frame #" + Integer.toString(aniOb.GetFrameNo()));
				appWidgetManager.updateAppWidget(appWidgetId, views);
			}

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

		// Set additional intent filter (Headset & battery)
		context.getApplicationContext().registerReceiver(this, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		context.getApplicationContext().registerReceiver(this, new IntentFilter(Intent.ACTION_HEADSET_PLUG));

		// Create & run thread
		aniThread = new AnimationThread();
		aniOb = AnimationObserver.GetInstance();
		mMainHandler = new _ThreadHandler();
		aniThread.SetThreadHandler(mMainHandler);
		isThreadCreated = true;
		
		mGestureDetector = new GestureDetector(context, new LearnGestureListener());
		mGestureDetector.onTouchEvent(event);  

		// Set event intent
//		Intent eventIntent = new Intent(Const.ACTION_EVENT);
		/*
		Intent moveIntent = new Intent(Const.ACTION_MOVE);
		Intent downIntent = new Intent(Const.ACTION_DOWN);
		Intent upIntent = new Intent(Const.ACTION_UP);
		 */

//		PendingIntent eventPIntent = PendingIntent.getBroadcast(context, 0, eventIntent, 0);
		/*
		PendingIntent movePIntent = PendingIntent.getBroadcast(context, 0, moveIntent, 0);
		PendingIntent downPIntent = PendingIntent.getBroadcast(context, 0, downIntent, 0);
		PendingIntent upPIntent = PendingIntent.getBroadcast(context, 0, upIntent, 0);
		 */

		// Set intent's event
//		views.setOnClickPendingIntent(R.id.imageView1, eventPIntent);

		/*
		views.setOnClickPendingIntent(R.id.imageView1, movePIntent);
		views.setOnClickPendingIntent(R.id.imageView1, downPIntent);
		views.setOnClicaadsfkPendingIntent(R.id.imageView1, upPIntent);
		 */

		for(int appWidgetId : appWidgetIds)
			appWidgetManager.updateAppWidget(appWidgetId, views);

		context.sendBroadcast(new Intent(Const.ACTION_SILENT));
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
		/*
		else if(Const.ACTION_SILENT.equals(action))
		{
			Check_SMSRead(context);
			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			initUI(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}
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

			Log.v(TAG, "Get ready for thread");
			
			if(aniThread == null)
			{
				Log.v(TAG, "damn");
				aniThread = new AnimationThread();
			}
			
			aniThread.SetState(true);
			aniThread.start();

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}
		*/
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

	public boolean onTouchEvent(MotionEvent event) {  
        return mGestureDetector.onTouchEvent(event);  
    }
	
	class _ThreadHandler extends Handler
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			context.sendBroadcast(new Intent(Const.ACTION_SILENT));
		}
	};
	
	class LearnGestureListener extends GestureDetector.SimpleOnGestureListener {  
        @Override  
        public boolean onSingleTapUp(MotionEvent ev) {  
            Log.d(TAG,"onSingleTapUp");  
            return true;  
        }  
        @Override  
        public void onShowPress(MotionEvent ev) {  
            Log.d(TAG,"onShowPress");
        }  
        @Override  
        public void onLongPress(MotionEvent ev) {  
            Log.d(TAG,"onLongPress");  
        }  
        @Override  
        public boolean onScroll(MotionEvent e1, MotionEvent e2,  
                float distanceX, float distanceY) {  
            Log.d(TAG,"onScroll");  
            return true;  
        }  
        @Override  
        public boolean onDown(MotionEvent ev) {  
            Log.d(TAG,"onDownd");  
            return true;  
        }  
        @Override  
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,  
                float velocityY) {  
            Log.d(TAG,"onFling");  
            return true;  
        }  
        public boolean onDoubleTap(MotionEvent event){  
            Log.d(TAG,"onDoubleTap");  
            return true;  
        }  
    }  
}