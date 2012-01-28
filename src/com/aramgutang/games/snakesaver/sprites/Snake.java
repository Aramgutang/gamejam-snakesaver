package com.aramgutang.games.snakesaver.sprites;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import com.aramgutang.games.snakesaver.utils.Segment;


public class Snake extends Path {
	private Paint paint = new Paint();
	
	private Segment[] segments = new Segment[1];
	private int len = 10;
	
	public Snake() {
		this.paint.setColor(Color.rgb(245, 245, 47));
		this.paint.setStyle(Paint.Style.STROKE);
		this.paint.setStrokeCap(Paint.Cap.ROUND);
		this.paint.setStrokeWidth(10.0f);
		
		this.segments[0] = new Segment(new PointF(0,0), new PointF(1f,1f));
	}
	
	public void draw(Canvas canvas) {
		this.moveTo(this.segments[0].end.x, this.segments[0].end.y);
		for(Segment segment : this.segments) {
			this.quadTo(segment.start.x, segment.start.y, segment.control.x, segment.control.y);
		}
		canvas.drawPath(this, this.paint);
		this.advance();
	}
	
	public void advance() {
		int segment_count = Math.min(this.segments.length, this.len);
		Segment[] new_segments = new Segment[segment_count];
		new_segments[0] = this.segments[0].next();
		for(int i=1; i < segment_count; i++)
			new_segments[i] = this.segments[i-1];
		this.segments = new_segments;
	}
}
