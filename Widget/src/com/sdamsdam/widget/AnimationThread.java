package com.sdamsdam.widget;

import com.sdamsdam.widget.WidgetMain._ThreadHandler;

import android.os.Message;
import android.util.Log;

public class AnimationThread extends Thread
{
	private AnimationObserver ob = null;
	private static boolean isThreadRunning;
	private _ThreadHandler handler;

	public AnimationThread()
	{
		ob = AnimationObserver.GetInstance();
		Log.v("sdamsdam", "AnimationThread::AnimationThread");
	}

	public void SetState(boolean state)
	{
		Log.v("sdamsdam", "Entering SetState");
		ob.ResetFrame();
		
		Log.v("sdamsdam", "Reset Frame");
		
		isThreadRunning = state;
		
		Log.v("sdamsdam", "AnimationThread::SetState");
	}

	public void SetThreadHandler(_ThreadHandler handler)
	{
		this.handler = handler;
	}

	@Override
	public void run()
	{
		while(isThreadRunning)
		{
			try
			{
				ob.SetNextFrame();		// Set next frame

				// Set & send message to handler
				Message msg = handler.obtainMessage();
				msg.what = 0;
				handler.sendMessage(msg);

				Thread.sleep(100);		// frame delay (ms)
			}
			catch(InterruptedException e)
			{
				isThreadRunning = false;
				Log.v("TAG","Error occured, so stop thread");
			}
		}
	}
}