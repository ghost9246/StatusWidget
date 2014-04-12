package com.sdamsdam.widget;

import android.util.Log;

public class AnimationObserver
{
	private static AnimationObserver instance = new AnimationObserver();
	private static int state;
	
	private AnimationObserver()
	{
		state = 0;
		Log.v("sdamsdam", "AnimationObserver::AnimationObserver");
	}
	
	public static AnimationObserver GetInstance()
	{
		if(instance == null)
			instance = new AnimationObserver();
		
		Log.v("sdamsdam", "AnimationObserver::GetInstance");
		
		return instance;
	}
	
	public int GetFrameNo()
	{
		return state;
	}
	
	public void SetNextFrame()
	{
		if(state == 2)
			state = 0;
		else
			state++;
	}
}
