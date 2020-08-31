package chart.line;

import java.util.Comparator;

public class ResTimeKey implements Comparable<ResTimeKey> {
	private String res;
	private double time;
	
	public ResTimeKey(String res, double time) {
		this.res = res;
		this.time = time;
	}
	public String getRes() {
		return res;
	}
	public void setRes(String res) {
		this.res = res;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	
	
	@Override
	public int compareTo(ResTimeKey o) {
		if (getRes() == o.getRes() && getTime() == o.getTime()) {
			return 0;
		}
		return Comparator.comparing(ResTimeKey::getTime)
				.thenComparing(ResTimeKey::getRes, Comparator.reverseOrder())
				.compare(this, o);
	}
}
