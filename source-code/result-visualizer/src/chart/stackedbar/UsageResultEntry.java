package chart.stackedbar;

import java.util.List;
import java.util.ArrayList;

public class UsageResultEntry {
	private String environment;
	private String algorithm;
	private List<Integer> taskCounts;
	private List<Double> usageTimes;
	
	public UsageResultEntry() {
		taskCounts = new ArrayList<Integer>();
		usageTimes = new ArrayList<Double>();
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public List<Integer> getTaskCounts() {
		return taskCounts;
	}

	public void setTaskCounts(List<Integer> taskCounts) {
		this.taskCounts = taskCounts;
	}

	public List<Double> getUsageTimes() {
		return usageTimes;
	}

	public void setUsageTimes(List<Double> usageTimes) {
		this.usageTimes = usageTimes;
	}
	
	
	
}
