package com.aramgutang.games.snakesaver;

import java.util.LinkedList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.os.Handler;
import android.view.SurfaceHolder;

import com.aramgutang.games.snakesaver.sprites.Girder;
import com.aramgutang.games.snakesaver.sprites.Snake;
import com.aramgutang.games.snakesaver.utils.Segment;

public class SnakesaverThread extends Thread {
	public Boolean running = false;
	
	public SurfaceHolder surface_holder;
	public Context context;
	public Handler handler;
	
	private Snake snake = new Snake();
	private Girder master_girder = new Girder();
	private LinkedList<Segment> girders = new LinkedList<Segment>();
	public LinkedList<PointF> touch_trail = new LinkedList<PointF>();
	
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
                synchronized(this.surface_holder) {                	
                	if(this.girders.size() == 0)
                		this.initialise_girders(canvas);
                	this.update_state();
                	this.draw(canvas);
                }
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
		this.girders.add(new Segment(new PointF(0,0), new PointF(screen_width, 0)));
		this.girders.add(new Segment(new PointF(0,0), new PointF(0, screen_height)));
		this.girders.add(new Segment(new PointF(screen_width, 0), new PointF(screen_width, screen_height)));
		this.girders.add(new Segment(new PointF(0, screen_height), new PointF(screen_width, screen_height)));
		for(Segment girder : this.girders)
			girder.visible = false;
	}
    
    private void update_state() {
    	Segment next_segment = this.snake.next_segment();
    	Segment intersector = null;
    	float nearest_intersection = 0;
    	float intersection_time = -1f;
    	for(Segment girder : this.girders) {
    		intersection_time = next_segment.intersects(girder);
    		if(intersection_time >= 0 && (intersector == null || intersection_time < nearest_intersection)) {
    			intersector = girder;
    			nearest_intersection = intersection_time;
    		}
    	}
    	if(intersector != null)
    		next_segment.bounce(intersector, nearest_intersection);
    	this.snake.push_segment(next_segment);
    }
    
    public void make_girder() {
    	this.girders.add(new Segment(this.touch_trail.getFirst(), this.touch_trail.getLast()));
    }
    
    public void clear_trail() {
    	this.touch_trail.clear();
    }
    
    private void draw(Canvas canvas) {
    	canvas.drawRGB(76, 202, 237);
    	this.snake.draw(canvas);
    	for(Segment girder : this.girders)
    		if(girder.visible)
    			this.master_girder.draw(girder, canvas);
    }
}
