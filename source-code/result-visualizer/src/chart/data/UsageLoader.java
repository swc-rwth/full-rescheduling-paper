package chart.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class UsageLoader {
	
	private String filePath;
	private ArrayList<Usage> usages;
	
	public UsageLoader(String filePath) {
		this.filePath = filePath;
		usages = new ArrayList<Usage>();
	}
	
	public ArrayList<Usage> getUsages(){
		return usages;
	}
	
	public void Load(){
		BufferedReader reader;
		String[] values = null;
		int lineNum = 1;
		try {
			reader = new BufferedReader(new FileReader(filePath));
			String line = reader.readLine();
			while (line != null) {
				values = line.split("\t");
				if (values.length < 3)
					throw new Exception(String.format("Invalid record of usage at line %d.", lineNum));
				// Usage info.
				String resName = values[0];
				float time = Float.parseFloat(values[1]);
				float usage = Float.parseFloat(values[2]);
				Usage u = new Usage();
				u.setResName(resName);
				u.setTime(time);
				u.setUsage(usage);
				usages.add(u);
				lineNum++;
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
