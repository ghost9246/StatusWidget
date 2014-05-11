package com.exam.view;

public class NormalStateFactory {
	public NormalState Create(CoinBlockView viewContext){
		// Get the theme from the view context
		return new NormalState();
	}
}
