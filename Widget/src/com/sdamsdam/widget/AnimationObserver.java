package com.sdamsdam.widget;

public class AnimationObserver
{
	private static AnimationObserver instance = new AnimationObserver();
	private static int state;
	
	private AnimationObserver()
	{
		state = 0;
	}
	
	public static AnimationObserver GetInstance()
	{
		if(instance == null)
			instance = new AnimationObserver();
		
		return instance;
	}
	
	public int GetFrameNo()
	{
		return state;
	}
	
	public void SetNextFrame()
	{
		if(state == 5)		// 5 = frame's amount
			state = 0;
		else
			state++;
	}
	
	public void ResetFrame()
	{
		state = 0;
	}
}
