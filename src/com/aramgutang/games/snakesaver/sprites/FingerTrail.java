package com.aramgutang.games.snakesaver.sprites;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

public class FingerTrail extends Path {
	private Paint paint = new Paint();
	public ConcurrentLinkedQueue<PointF> trail = new ConcurrentLinkedQueue<PointF>();
	public PointF last_point = null;
	static public float STRAIGHT_THRESHOLD = 0.25f;
	
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
	
	static float standard_deviation(LinkedList<Float> list, float sum) {
		float diffsum = 0;
		float mean = sum / list.size();
		for(float item : list)
			diffsum += Math.pow(item - mean, 2f);
		return (float) Math.sqrt(1f/(list.size()-1) * diffsum);
	}
	
	public Boolean is_straight() {
		LinkedList<Float> x_vectors = new LinkedList<Float>();
		LinkedList<Float> y_vectors = new LinkedList<Float>();
		float lastx = this.trail.peek().x;
		float lasty = this.trail.peek().y;
		float diffx, diffy, length, sumx = 0, sumy = 0;
		for(PointF point : this.trail) {
			diffx = point.x - lastx;
			diffy = point.y - lasty;
			if(Math.abs(diffx) + Math.abs(diffy) > 20f) {
				lastx = point.x;
				lasty = point.y;
				length = (float) Math.sqrt(Math.pow(diffx, 2f) + Math.pow(diffy, 2f));
				diffx /= length;
				diffy /= length;
				x_vectors.add(diffx);
				y_vectors.add(diffy);
				sumx += diffx;
				sumy += diffy;
			}
		}
		return standard_deviation(x_vectors, sumx) <= STRAIGHT_THRESHOLD
				&& standard_deviation(y_vectors, sumy) <= STRAIGHT_THRESHOLD;
	}
}
