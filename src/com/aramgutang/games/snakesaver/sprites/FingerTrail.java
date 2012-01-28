package com.aramgutang.games.snakesaver.sprites;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Log;

public class FingerTrail extends Path {
	private Paint paint = new Paint();
	public ConcurrentLinkedQueue<PointF> trail = new ConcurrentLinkedQueue<PointF>();
	public PointF last_point = null;
	static public float STRAIGHT_THRESHOLD = 100f;
	
	public FingerTrail() {
		this.paint.setColor(Color.rgb(110, 232, 9));
		this.paint.setStyle(Paint.Style.STROKE);
		this.paint.setStrokeCap(Paint.Cap.ROUND);
		this.paint.setStrokeWidth(50f);
		this.paint.setStrokeJoin(Paint.Join.ROUND);
	}
	
	public void add(PointF point) {
		this.trail.add(point);
		this.last_point = point;
	}
	
	public void draw(Canvas canvas) {
		if(this.trail.size() > 0) {
			this.reset();
			this.moveTo(trail.peek().x, trail.peek().y);
			for(PointF point : trail)
				this.lineTo(point.x, point.y);
			canvas.drawPath(this, this.paint);
		}
	}
	
	public Boolean is_straight() {
		float lastx = this.trail.peek().x;
		float lasty = this.trail.peek().y;
		float maxx = 0, maxy = 0, minx = 0, miny = 0, diffx, diffy;
		Boolean first = true;
		for(PointF point : this.trail) {
			diffx = point.x - lastx;
			diffy = point.y - lasty;
			if(Math.abs(diffx) + Math.abs(diffy) > 20f) {
				lastx = point.x;
				lasty = point.y;
				if(first) {
					maxx = maxy = diffx;
					maxy = miny = diffy;
					first = false;
				} else {
					maxx = Math.max(diffx, maxx);
					maxy = Math.max(diffy, maxy);
					minx = Math.min(diffx, minx);
					miny = Math.min(diffy, miny);
				}
			}
		}
		return maxx - minx + maxy - miny <= STRAIGHT_THRESHOLD;
	}
}
