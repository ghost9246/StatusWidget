package com.sdamsdam.widget;

import android.util.Log;

public class AnimationThread extends Thread
{
	private AnimationObserver ob = null;
	private static boolean isThreadRunning;
	
	public AnimationThread()
	{
		ob = AnimationObserver.GetInstance();
		Log.v("sdamsdam", "AnimationThread::AnimationThread");
	}
	
	public void SetState(boolean state)
	{
		isThreadRunning = state;
		Log.v("sdamsdam", "AnimationThread::SetState");
	}
	
	@Override
	public void run()
	{
		while(isThreadRunning)
		{
			try
			{
				ob.SetNextFrame();
				Thread.sleep(500);
			}
			catch(InterruptedException e)
			{
				isThreadRunning = false;
				Log.v("TAG","Error occured, so stop thread");
			}
		}
	}
}
