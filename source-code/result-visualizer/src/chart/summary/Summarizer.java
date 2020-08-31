package chart.summary;

import java.awt.image.RescaleOp;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import chart.data.Task;

public class Summarizer {
	private String filePath;
	private double totalRunningTime = 0;
	private double workflowRunningTime = 0;
	private int taskCount = 0;
	private LinkedHashMap<String, Double> resTypeRunningTimeMap;
	private LinkedHashMap<String, Integer> resTypeTaskCountingMap;
	private TreeMap<String, Double> resRunningTimeMap;
	private TreeMap<String, Integer> resTaskCountingMap;
	
	public Summarizer() {
		resTypeRunningTimeMap = new LinkedHashMap<String, Double>();
		resTypeTaskCountingMap = new LinkedHashMap<String, Integer>();
		resRunningTimeMap = new TreeMap<String, Double>();
		resTaskCountingMap = new TreeMap<String, Integer>();
	}
	
	public Summarizer(String filePath) {
		this();
		this.filePath = filePath;
	}
	
	public void add(Task task) {
		totalRunningTime += task.getRunTime();
		String resType = task.getResource().getType();
		String resName = task.getResource().getName();
		if (!resTypeRunningTimeMap.containsKey(resType)) {
			resTypeRunningTimeMap.put(resType, 0.0);
			resTypeTaskCountingMap.put(resType, 0);
		}
		double runningTime = task.getWaitTime() + task.getRunTime();
		resTypeRunningTimeMap.put(resType, resTypeRunningTimeMap.get(resType) + runningTime);
		resTypeTaskCountingMap.put(resType, resTypeTaskCountingMap.get(resType) + 1);
		if (!resRunningTimeMap.containsKey(resName)) {
			resRunningTimeMap.put(resName, 0.0);
			resTaskCountingMap.put(resName, 0);
		}
		resRunningTimeMap.put(resName, resRunningTimeMap.get(resName) + runningTime);
		resTaskCountingMap.put(resName, resTaskCountingMap.get(resName) + 1);
		workflowRunningTime = task.getArrivalTime() + runningTime;
		taskCount++;
	}
	
	public double getTotalRunningTime() {
		return totalRunningTime;
	}
	
	public double getWorkflowRunningTime() {
		return workflowRunningTime;
	}
	
	public int getTaskCount() {
		return taskCount;
	}
	
	public void print() {
		System.out.println("*************** SUMMARY ****************");
		System.out.println(String.format("Total tasks:\t%d", getTaskCount()));
		System.out.println(String.format("Total running time:\t%,.2f", getTotalRunningTime()));
		System.out.println("Running time of resource type:");
		for (Map.Entry<String, Double> e : resTypeRunningTimeMap.entrySet()) {
			System.out.println(String.format("%s:\t%d\t%,.2f\t%.2f%%", e.getKey(), resTypeTaskCountingMap.get(e.getKey()), e.getValue(), e.getValue() / getTotalRunningTime() * 100));
		}
		System.out.println("Running time of resource:");
		for (Map.Entry<String, Double> e : resRunningTimeMap.entrySet()) {
			System.out.println(String.format("%s:\t%d\t%,.2f\t%.2f%%", e.getKey(), resTaskCountingMap.get(e.getKey()), e.getValue(), e.getValue() / getTotalRunningTime() * 100));
		}
		System.out.println();
		System.out.println(String.format("Required running time:\t%,.2f", getWorkflowRunningTime()));
		System.out.println("Active time of resource:");
		for (Map.Entry<String, Double> e : resRunningTimeMap.entrySet()) {
			System.out.println(String.format("%s:\t%,.2f\t%.2f%%", e.getKey(), e.getValue(), e.getValue() / getWorkflowRunningTime() * 100));
		}
		System.out.println("*************** END SUMMARY ****************");
	}
	
	public void save() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
			writer.write(String.format("Total tasks:\t%d", getTaskCount()));
			writer.newLine();
			writer.write(String.format("Total running time:\t%,.2f", getTotalRunningTime()));
			writer.newLine();
			writer.write("Running time of resource type:");
			writer.newLine();
			for (Map.Entry<String, Double> e : resTypeRunningTimeMap.entrySet()) {
				writer.write(String.format("%s:\t%d\t%,.2f\t%.2f%%", e.getKey(), resTypeTaskCountingMap.get(e.getKey()), e.getValue(), e.getValue() / getTotalRunningTime() * 100));
				writer.newLine();
			}
			writer.write("Running time of resource:");
			writer.newLine();
			for (Map.Entry<String, Double> e : resRunningTimeMap.entrySet()) {
				writer.write(String.format("%s:\t%d\t%,.2f\t%.2f%%", e.getKey(), resTaskCountingMap.get(e.getKey()), e.getValue(), e.getValue() / getTotalRunningTime() * 100));
				writer.newLine();
			}
			writer.newLine();
			writer.write(String.format("Required running time:\t%,.2f", getWorkflowRunningTime()));
			writer.newLine();
			writer.write("Active time of resource:");
			writer.newLine();
			for (Map.Entry<String, Double> e : resRunningTimeMap.entrySet()) {
				writer.write(String.format("%s:\t%,.2f\t%.2f%%", e.getKey(), e.getValue(), e.getValue() / getWorkflowRunningTime() * 100));
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
