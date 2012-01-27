package com.aramgutang.games.snakesaver;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SnakesaverView extends SurfaceView implements SurfaceHolder.Callback {
	public SnakesaverThread thread;

	public SnakesaverView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setSystemUiVisibility(SYSTEM_UI_FLAG_LOW_PROFILE);
		
		SurfaceHolder holder = this.getHolder();
		holder.addCallback(this);
		
		this.thread = new SnakesaverThread(holder, context, new Handler());
		
		this.setFocusable(true);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.thread.running = true;
		this.thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
        // We have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
        boolean retry = true;
        thread.running = false;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {}
        }
	}
	
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
//        if (!hasWindowFocus)
//        	thread.pause();
    }
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		//thread.setSurfaceSize(width, height);
	}
}
