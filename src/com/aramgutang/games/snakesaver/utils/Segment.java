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

	public float intersects(Segment segment) {
		// See: http://stackoverflow.com/questions/563198/how-do-you-detect-where-two-line-segments-intersect
		float qpx = segment.start.x - this.start.x;
		float qpy = segment.start.y - this.start.y;
		float rxs = this.vector.x * segment.vector.y - this.vector.y * segment.vector.x;
		float t = (qpx * segment.vector.y - qpy * segment.vector.x) / rxs;
		float u = (qpx * this.vector.y - qpy * this.vector.x) / rxs;
		if((t >= 0) && (t <= 1f) && (u >= 0) && (u <= 1f))
			return t;
		return -1f;
	}

	public void bounce(Segment intersector, float time) {
		this.end.x = this.start.x + time * this.vector.x;
		this.end.y = this.start.y + time * this.vector.y;
		float normalx = intersector.vector.y;
		float normaly = -intersector.vector.x;
		float multiplier = -2 * ((this.vector.x * normalx + this.vector.y * normaly) / (normalx * normalx + normaly * normaly));
		this.vector.x = this.vector.x + multiplier * normalx;
		this.vector.y = this.vector.y + multiplier * normaly;
	}
}