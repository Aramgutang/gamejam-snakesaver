package com.aramgutang.games.snakesaver.sprites;

import java.util.LinkedList;
import java.util.Queue;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import com.aramgutang.games.snakesaver.utils.Segment;

public class CurvedGirder extends Path {
	private Paint paint = new Paint();
	private LinkedList<Segment> curve = new LinkedList<Segment>();

	public CurvedGirder(LinkedList<Segment> curve) {
		this.paint.setColor(Color.rgb(8, 200, 8));
		this.paint.setStyle(Paint.Style.STROKE);
		this.paint.setStrokeCap(Paint.Cap.ROUND);
		this.paint.setStrokeWidth(25f);
		this.curve = curve;
	}

	public void draw(Canvas canvas) {
		this.reset();
		this.moveTo(curve.getFirst().start.x, curve.getFirst().start.y);
		for(Segment segment : this.curve)
			this.quadTo(segment.control.x, segment.control.y, segment.end.x, segment.end.y);
		canvas.drawPath(this, this.paint);
	}
}
