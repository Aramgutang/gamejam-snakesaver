package com.aramgutang.games.snakesaver.sprites;

import java.util.Queue;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

public class FadingTrail extends Path {
	private Paint paint = new Paint();

	public FadingTrail(Queue<PointF> trail) {
		this.paint.setColor(Color.rgb(110, 232, 9));
		this.paint.setStyle(Paint.Style.STROKE);
		this.paint.setStrokeCap(Paint.Cap.ROUND);
		this.paint.setStrokeWidth(50f);
		this.paint.setStrokeJoin(Paint.Join.ROUND);
		this.paint.setAlpha(255);
		this.reset();
		this.moveTo(trail.peek().x, trail.peek().y);
		for(PointF point : trail)
			this.lineTo(point.x, point.y);
	}

	public Boolean faded() {
		return this.paint.getAlpha() <= 0;
	}

	public void draw(Canvas canvas) {
		this.paint.setAlpha(Math.max(this.paint.getAlpha()-20, 0));
		canvas.drawPath(this, this.paint);
	}
}
