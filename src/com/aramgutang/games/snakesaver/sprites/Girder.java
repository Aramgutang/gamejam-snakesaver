package com.aramgutang.games.snakesaver.sprites;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.aramgutang.games.snakesaver.utils.Segment;

public class Girder extends Path {
	private Paint paint = new Paint();
	
	public Girder() {
		this.paint.setColor(Color.rgb(8, 200, 8));
		this.paint.setStyle(Paint.Style.STROKE);
		this.paint.setStrokeCap(Paint.Cap.ROUND);
		this.paint.setStrokeWidth(25f);
	}
	
	public void draw(Segment segment, Canvas canvas) {
		this.reset();
		this.moveTo(segment.end.x, segment.end.y);
		this.lineTo(segment.start.x, segment.start.y);
		canvas.drawPath(this, this.paint);
	}
}
