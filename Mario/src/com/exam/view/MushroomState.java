package com.exam.view;

import android.graphics.*;
import android.media.*;
import android.media.MediaPlayer.OnSeekCompleteListener;

import com.exam.*;


class MushroomState implements ICoinBlockViewState{
	Sprite sp = MediaAssets.getInstance().getSprite(R.drawable.brick_disabled);
	MediaPlayer snd = MediaAssets.getInstance().getSoundPlayer(R.raw.smb_powerup_appears);
	private int animStage = 0;
	private int[] heightModifier = { 12, 8, 4, 2 };

	public MushroomState(CoinBlockView viewContext) {
		viewContext.addAnimatable(new MushroomAnimation(viewContext.getDensity()));
		snd.seekTo(0);
		snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
			public void onSeekComplete(MediaPlayer mp) {
				snd.start();
			}
		});
	}

	public void Draw(CoinBlockView viewContext, Bitmap canvas) {
		// Draw the brick at bottom
		SpriteHelper.DrawSprite(canvas, sp, 0, SpriteHelper.DrawPosition.BottomCenter, 0,
						- (int)(heightModifier[animStage] * viewContext.getDensity()));
		animStage++;
		if (animStage >= heightModifier.length) {
			viewContext.setState(new DisabledState(viewContext));
		}
	}

	public void OnClick(CoinBlockView viewContext) {
	}

	public boolean NeedRedraw() {
		return true;
	}
}