package chart.line;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import chart.data.Usage;

import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.TableXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class LineChartDataset {
	
	private DefaultTableXYDataset dataset;
	private Map<ResTimeKey, Double> map;
	private double maxValue = 0.0;
	
	public LineChartDataset() {
		dataset = new DefaultTableXYDataset();
		map = new TreeMap<ResTimeKey, Double>();
	}
	
	public void load(ArrayList<Usage> usages) {
		for (int i = 0; i < usages.size(); i++) {
			ResTimeKey key = new ResTimeKey(usages.get(i).getResName().substring(0, 2), usages.get(i).getTime());
			if (!map.containsKey(key)) {
				map.put(key, 0.0d);
			}
			double newValue = map.get(key) + usages.get(i).getUsage() / 100.0d;
			if (newValue > maxValue) {
				maxValue = newValue;
			}
			map.put(key, newValue);
		}
		LinkedHashMap<String, XYSeries> series = new LinkedHashMap<String, XYSeries>();
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			String res = ((ResTimeKey)e.getKey()).getRes();
			if (!series.containsKey(res)) {
				XYSeries s = new XYSeries(res, true, false);
				series.put(res, s);
			}
			series.get(res).add(((ResTimeKey)e.getKey()).getTime(), (double)e.getValue());
		}
		series.values().forEach(s -> {
			dataset.addSeries(s);
		});
	}
	
	public TableXYDataset getDataset() {
		return dataset;
	}
	
	public Map<ResTimeKey, Double> getMap(){
		return map;
	}
	
	public double getMaxValue() {
		return maxValue;
	}
}
