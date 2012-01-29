package com.aramgutang.games.snakesaver.utils;

import com.aramgutang.games.snakesaver.sprites.CurvedGirder;

import android.graphics.PointF;

public 	class Segment {
	public PointF start;
	public PointF end;
	public PointF control;
	public PointF vector;
	public Boolean visible = true;
	public CurvedGirder curve = null;

	public Segment(PointF start, PointF end) {
		this.start = start;
		this.control = start;
		this.end = end;
		this.vector = new PointF(end.x - start.x, end.y - start.y);
	}

	public Segment(PointF start, PointF middle, PointF end) {
		this.start = start;
		this.control = new PointF(0.5f*start.x + 0.5f*end.x - 2f*middle.x, 0.5f*start.y + 0.5f*end.y - 2f*middle.y);
		this.end = end;
		//this.vector = new PointF(this.end.x - this.control.x, this.end.y - this.control.y);
		this.vector = new PointF(end.x - start.x, end.y - start.y);
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
		// See: http://stackoverflow.com/questions/573084/how-to-calculate-bounce-angle
		this.end.x = this.start.x + time * 0.99f * this.vector.x;
		this.end.y = this.start.y + time * 0.99f * this.vector.y;
		float normalx = intersector.vector.y;
		float normaly = intersector.vector.x;
		// Pick the right one out of the two possible normals
		if((Math.abs(this.end.x + normalx - this.start.x)
				+ Math.abs(this.end.y - normaly - this.start.y)) 
				<= (Math.abs(this.end.x - normalx - this.start.x) 
						+ Math.abs(this.end.y + normaly - this.start.y)))
			normaly *= -1f;
		else
			normalx *= -1f;
		float multiplier = -2 *
				((this.vector.x * normalx + this.vector.y * normaly) 
						/ (normalx * normalx + normaly * normaly));
		this.vector.x = this.vector.x + multiplier * normalx;
		this.vector.y = this.vector.y + multiplier * normaly;
	}
}