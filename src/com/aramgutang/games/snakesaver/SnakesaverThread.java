package com.aramgutang.games.snakesaver;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.SurfaceHolder;

import com.aramgutang.games.snakesaver.sprites.Snake;

public class SnakesaverThread extends Thread {
	public Boolean running = false;
	
	public SurfaceHolder surface_holder;
	public Context context;
	public Handler handler;
	
	private Snake snake = new Snake();
	
	public SnakesaverThread(SurfaceHolder surface_holder, Context context, Handler handler) {
		this.surface_holder = surface_holder;
		this.context = context;
		this.handler = handler;
	}
	
    @Override
    public void run() {
        while(this.running) {
            Canvas canvas = null;
            try {
                canvas = this.surface_holder.lockCanvas(null);
                this.draw(canvas);
            } finally {
                if (canvas != null)
                    this.surface_holder.unlockCanvasAndPost(canvas);
            }
        }
    }
    
    private void draw(Canvas canvas) {
    	canvas.drawRGB(76, 202, 237);
    	this.snake.draw(canvas);
    }
}
