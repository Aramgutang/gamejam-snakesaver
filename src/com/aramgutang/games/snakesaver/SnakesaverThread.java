package com.aramgutang.games.snakesaver;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.os.Handler;
import android.view.SurfaceHolder;

import com.aramgutang.games.snakesaver.sprites.Snake;
import com.aramgutang.games.snakesaver.utils.Segment;

public class SnakesaverThread extends Thread {
	public Boolean running = false;
	
	public SurfaceHolder surface_holder;
	public Context context;
	public Handler handler;
	
	private Snake snake = new Snake();
	private Segment[] girders = null;
	
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
                if(this.girders == null)
                	this.initialise_girders(canvas);
                this.update_state();
                this.draw(canvas);
            } finally {
                if (canvas != null)
                    this.surface_holder.unlockCanvasAndPost(canvas);
            }
        }
    }

	private void initialise_girders(Canvas canvas) {
		// Initialise screen edges
		int screen_height = canvas.getHeight();
		int screen_width = canvas.getWidth();
		this.girders = new Segment[4];
		this.girders[0] = new Segment(new PointF(0,0), new PointF(screen_width, 0));
		this.girders[1] = new Segment(new PointF(0,0), new PointF(0, screen_height));
		this.girders[2] = new Segment(new PointF(screen_width, 0), new PointF(screen_width, screen_height));
		this.girders[3] = new Segment(new PointF(0, screen_height), new PointF(screen_width, screen_height));
	}
    
    private void update_state() {
    	Segment next_segment = this.snake.next_segment();
    	Segment intersector = null;
    	float nearest_intersection = 0;
    	float intersection_time = -1f;
    	for(Segment girder : this.girders) {
    		intersection_time = next_segment.intersects(girder);
    		if(intersection_time > 0 && (intersector == null || intersection_time < nearest_intersection)) {
    			intersector = girder;
    			nearest_intersection = intersection_time;
    		}
    	}
    	if(intersector != null)
    		next_segment.bounce(intersector, intersection_time);
    	this.snake.push_segment(next_segment);
    	
    }
    
    private void draw(Canvas canvas) {
    	canvas.drawRGB(76, 202, 237);
    	this.snake.draw(canvas);
    }
}
