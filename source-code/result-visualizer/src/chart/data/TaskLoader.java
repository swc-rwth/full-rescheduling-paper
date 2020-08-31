package chart.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TaskLoader {
	
	private String filePath;
	private ArrayList<Resource> resources;
	private ArrayList<Task> tasks;
	
	public TaskLoader(String filePath) {
		this.filePath = filePath;
		resources = new ArrayList<Resource>();
		tasks = new ArrayList<Task>();
	}
	
	public void Load(){
		BufferedReader reader;
		String[] values = null;
		int lineNum = 2;
		try {
			reader = new BufferedReader(new FileReader(filePath));
			String line = reader.readLine();
			//Skip first line (column headers)
			if (line != null)
				line = reader.readLine();
			while (line != null) {
				values = line.split("\t");
				if (values.length < 5)
					throw new Exception(String.format("Invalid record at line %d.", lineNum));
				//Task info.
				int id = Integer.parseInt(values[2]);
				float arrivalTime = Float.parseFloat(values[3]);
				float waitTime = Float.parseFloat(values[4]);
				float runTime = Float.parseFloat(values[5]);
				Task task = new Task();
				task.setId(id);
				task.setArrivalTime(arrivalTime);
				task.setWaitTime(waitTime);
				task.setRunTime(runTime);
				//Resource info.
				int resId = Integer.parseInt(values[0]);
				String resName = values[1];
				Resource resource = null;
				for (Resource r : resources) {
					if (r.getId() == resId) {
						resource = r;
					}
				}
				if (resource == null) {
					//Add resource to appropriate position in the list based on the order of index
					int ind = resources.size();
					for (Resource r : resources) {
						if (r.getId() > resId) {
							ind = resources.indexOf(r);
							for (int i = ind; i < resources.size(); i++) {
								resources.get(i).setIndex(i+1);
							}
							break;
						}
					}
					resource = new Resource(ind, resId, resName);
					resources.add(ind, resource);
				}
				resource.addTask(task);
				tasks.add(task);
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Resource> getResources(){
		return resources;
	}
	
	public ArrayList<Task> getTasks(){
		return tasks;
	}
}
