package com.aramgutang.games.snakesaver;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SnakesaverView extends SurfaceView implements SurfaceHolder.Callback {
	public SnakesaverThread thread;

	public SnakesaverView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setSystemUiVisibility(SYSTEM_UI_FLAG_LOW_PROFILE);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}

}
