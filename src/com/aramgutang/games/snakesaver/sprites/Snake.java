package com.aramgutang.games.snakesaver.sprites;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

public class Snake extends Path {
	private Paint paint = new Paint();
	
	private Point[] segments = {new Point(0, 0), new Point(500, 500)};
	private int length = (int)Math.round(Math.sqrt(Math.pow(50.0, 2.0) * 2.0));
	
	public Snake() {
		this.paint.setColor(Color.rgb(245, 245, 47));
		this.paint.setStyle(Paint.Style.STROKE);
		this.paint.setStrokeCap(Paint.Cap.ROUND);
		this.paint.setStrokeWidth(10.0f);
	}
	
	public void draw(Canvas canvas) {
		this.moveTo(this.segments[0].x, this.segments[0].y);
		for(Point point : this.segments) {
			this.lineTo(point.x, point.y);
		}
		canvas.drawPath(this, this.paint);
	}
}
