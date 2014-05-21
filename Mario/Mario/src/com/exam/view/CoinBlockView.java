package com.exam.view;

import java.util.*;

import android.app.*;
import android.appwidget.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

import com.exam.*;

public class CoinBlockView {
	public static String INTENT_ON_CLICK_FORMAT = "com.gueei.mario.coinBlock.id.%d.click";
	private static final int REFRESH_RATE = 40;
	private int cheight;
	private volatile Set<IAnimatable> Children;
	private int cwidth;
	private float density;

	private long lastRedrawMillis = 0;
	private int mWidgetId;
	private ICoinBlockViewState state;

	public CoinBlockView(Context context, int widgetId) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metrics);

		density = metrics.density;
		cwidth = (int) (72 * metrics.density);
		cheight = cwidth;

		Children = new HashSet<IAnimatable>();
		mWidgetId = widgetId;
		setState(new NormalState());
	}

	public synchronized void addAnimatable(IAnimatable child)
	{
		Children.add(child);
	}
	
	public synchronized void removeAnimatable(IAnimatable child)
	{
		Children.remove(child);
	}

	public void createCoin() {
		Children.add(new CoinAnimation(density));
	}

	public Context getContext() {
		return (CoinBlockWidgetApp.getApplication());
	}

	public float getDensity() {
		return density;
	}
	
	public int getmWidgetId() {
		return mWidgetId;
	}

	public void OnClick() {
		state.OnClick(this);
	}

	public void Redraw(Context context) {
		RemoteViews rviews = new RemoteViews(context.getPackageName(), R.layout.coin_block_widget);
		Bitmap canvas = Bitmap.createBitmap(cwidth, cheight, Bitmap.Config.ARGB_8888);

		IAnimatable[] child = new IAnimatable[Children.size()];
		Children.toArray(child);
		for (int i = 0; i < child.length; i++) {
			child[i].Draw(canvas);
			if (child[i].AnimationFinished())
				Children.remove(child[i]);
		}

		state.Draw(this,canvas);
		rviews.setImageViewBitmap(R.id.block, canvas);
		updateClickIntent(rviews);
		AppWidgetManager.getInstance(context).updateAppWidget(mWidgetId, rviews);

		lastRedrawMillis = SystemClock.uptimeMillis();

		if (state.NeedRedraw() || Children.size() > 0)
			scheduleRedraw();
	}

	private void scheduleRedraw() {
		long nextRedraw = lastRedrawMillis + REFRESH_RATE;
		nextRedraw = nextRedraw > SystemClock.uptimeMillis() ? nextRedraw :
			SystemClock.uptimeMillis() + REFRESH_RATE;
		scheduleRedrawAt(nextRedraw);
	}

	private void scheduleRedrawAt(long timeMillis) {
		(new Handler()).postAtTime(new Runnable() {
			public void run() {
				Redraw(CoinBlockWidgetApp.getApplication());
			}
		}, timeMillis);
	}

	public void setState(ICoinBlockViewState newState) {
		state = newState;
		scheduleRedraw();
	}
	
	public ICoinBlockViewState getState(){
		return state;
	}

	private void updateClickIntent(RemoteViews rviews)
	{
		Intent intent = new Intent(String.format(INTENT_ON_CLICK_FORMAT, mWidgetId));
		intent.setClass(getContext(), coinBlockWidgetProvider.class);
		intent.putExtra("widgetId", mWidgetId);
		PendingIntent pi = PendingIntent.getBroadcast(getContext(), 0, intent,
						PendingIntent.FLAG_UPDATE_CURRENT);
		rviews.setOnClickPendingIntent(R.id.widget, pi);
	}
}
