package com.aramgutang.games.snakesaver;

import android.content.Context;
import android.graphics.PointF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SnakesaverView extends SurfaceView implements SurfaceHolder.Callback {
	private int active_pointer_id = -1;
	
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
	public boolean onTouchEvent(MotionEvent event) {
		int pointer_index;
	    switch (event.getAction() & MotionEvent.ACTION_MASK) {
	    case MotionEvent.ACTION_DOWN:
	        this.thread.touch_trail.addLast(new PointF(event.getX(), event.getY()));
	        this.active_pointer_id = event.getPointerId(0);
	        break;
	    case MotionEvent.ACTION_MOVE:
	        pointer_index = event.findPointerIndex(this.active_pointer_id);
	        this.thread.touch_trail.addLast(new PointF(event.getX(pointer_index), event.getY(pointer_index)));
	        break;
	    case MotionEvent.ACTION_UP:
	        this.thread.make_girder();
	    case MotionEvent.ACTION_CANCEL:
	        this.active_pointer_id = -1;
	        this.thread.clear_trail();
	        break;
	    case MotionEvent.ACTION_POINTER_UP:
	        // Extract the index of the pointer that left the touch sensor
	    	pointer_index = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) 
	                >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
	        if (event.getPointerId(pointer_index) == this.active_pointer_id) {
	            // This was our active pointer going up.
	        	this.thread.make_girder();
		        this.active_pointer_id = -1;
		        this.thread.clear_trail();
	        }
	    }
	    return true;
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
