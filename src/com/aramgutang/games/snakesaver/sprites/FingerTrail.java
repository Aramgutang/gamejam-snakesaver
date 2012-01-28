package com.aramgutang.games.snakesaver.sprites;

import java.util.concurrent.ConcurrentLinkedQueue;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

public class FingerTrail extends Path {
	private Paint paint = new Paint();
	public ConcurrentLinkedQueue<PointF> trail = new ConcurrentLinkedQueue<PointF>();
	
	public FingerTrail() {
		this.paint.setColor(Color.rgb(110, 232, 9));
		this.paint.setStyle(Paint.Style.STROKE);
		this.paint.setStrokeCap(Paint.Cap.ROUND);
		this.paint.setStrokeWidth(50f);
		this.paint.setStrokeJoin(Paint.Join.ROUND);
	}
	
	public void add(PointF point) {
		this.trail.add(point);
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
}
