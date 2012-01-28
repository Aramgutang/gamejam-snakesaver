package com.aramgutang.games.snakesaver.utils;

import android.graphics.PointF;

public 	class Segment {
	public PointF start;
	public PointF end;
	public PointF control;
	public PointF vector;
	
	public Segment(PointF start, PointF end) {
		this.start = start;
		this.control = start;
		this.end = end;
		this.vector = new PointF(end.x - start.x, end.y - start.y);
	}
	
	public Segment(PointF start, PointF middle, PointF end) {
		// TODO: Curve segments
	}
	
	public Segment next() {
		return new Segment(end, new PointF(end.x + vector.x, end.y + vector.y));
	}
}