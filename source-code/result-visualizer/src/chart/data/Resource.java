package chart.data;

import java.util.ArrayList;

public class Resource {
	private ArrayList<Task> tasks;
	
	private int index;
	private int id;
	private String name;
	
	public Resource(int index, int id, String name) {
		this.index = index;
		this.id = id;
		this.name = name;
		this.tasks = new ArrayList<Task>();
	}
	
	public int getIndex() {
		return index;
	}



	public void setIndex(int index) {
		this.index = index;
	}



	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return getName().substring(0, 2);
	}
	
	public ArrayList<Task> getTasks(){
		return tasks;
	}
	
	public void addTask(Task task) {
		task.setResource(this);
		this.tasks.add(task);
	}
	
}
