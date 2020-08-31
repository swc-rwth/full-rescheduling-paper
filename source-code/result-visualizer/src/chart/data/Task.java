package chart.data;

public class Task {
	private int id;
	private float arrivalTime;
	private float waitTime;
	private float runTime;
	
	private Resource resource;
	
	public Task() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(float arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public float getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(float waitTime) {
		this.waitTime = waitTime;
	}

	public float getRunTime() {
		return runTime;
	}

	public void setRunTime(float runTime) {
		this.runTime = runTime;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
}
