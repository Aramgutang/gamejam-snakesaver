package com.aramgutang.games.snakesaver;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.os.Handler;
import android.view.SurfaceHolder;

import com.aramgutang.games.snakesaver.sprites.FadingTrail;
import com.aramgutang.games.snakesaver.sprites.FingerTrail;
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
	private LinkedList<FadingTrail> fading_trails = new LinkedList<FadingTrail>();
	public FingerTrail touch_trail = new FingerTrail();
	
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
    	PointF last_point = null;
    	for(PointF point : this.touch_trail.trail)
    		last_point = point;
    	this.girders.add(new Segment(this.touch_trail.trail.peek(), last_point));
    	this.fading_trails.add(new FadingTrail(this.touch_trail.trail));
    }
    
    public void clear_trail() {
    	this.touch_trail.trail.clear();
    }
    
    private void draw(Canvas canvas) {
    	canvas.drawRGB(76, 202, 237);
    	this.snake.draw(canvas);
    	for(Segment girder : this.girders)
    		if(girder.visible)
    			this.master_girder.draw(girder, canvas);
    	this.touch_trail.draw(canvas);
    	for(FadingTrail trail : this.fading_trails) {
    		if(trail.faded())
    			this.fading_trails.remove(trail);
    		else
    			trail.draw(canvas);
    	}
    }
}
