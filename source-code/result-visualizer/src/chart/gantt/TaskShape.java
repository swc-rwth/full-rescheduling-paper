package chart.gantt;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

import chart.data.Task;

import java.awt.Rectangle;

public class TaskShape extends Rectangle {
	
	public static float SCALE = 0.1f;

	public static final int HEIGHT = 10;
	
	private Task task;
	
	public TaskShape(Task task) {
		this.task = task;
		this.x = (int) Math.floor((task.getArrivalTime() + task.getWaitTime()) * TaskShape.SCALE) + GanttChartVisualizer.leftMargin;
		this.y = HEIGHT * task.getResource().getIndex() + GanttChartVisualizer.topMargin;
		this.height = HEIGHT;
		this.width = (int) Math.floor(task.getRunTime() * TaskShape.SCALE);
	}

}
