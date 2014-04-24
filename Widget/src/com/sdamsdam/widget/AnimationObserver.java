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
		if(state == 2)
			state = 0;
		else
			state++;
	}
}
