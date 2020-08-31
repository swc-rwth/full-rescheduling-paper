package chart.visualizer;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;

import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;

import chart.data.TaskLoader;
import chart.data.Usage;
import chart.data.UsageLoader;
import chart.data.Resource;
import chart.data.Task;
import chart.gantt.GanttChartVisualizer;
import chart.gantt.TaskShape;
import chart.line.LineChartDataset;
import chart.line.ResTimeKey;
import chart.line.UsageLineChartVisualizer;
import chart.summary.Summarizer;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class Runner {
	
	public static void main(String[] args) throws Exception {
		
		ArgumentParser parser = ArgumentParsers.newFor("visualizer").build()
	                .defaultHelp(true)
	                .description("Visualize the result of scheduling simulation.");
	        parser.addArgument("-d", "--dir")
	        		.required(true)
	                .help("Specify directory path of the result");
	        parser.addArgument("-e", "--execution")
	        		.required(true)
	                .help("Specify filename of the result for task exectuion");
	        parser.addArgument("-u", "--usage")
		    		.required(true)
		            .help("Specify filename of the result for resource usage");
	        parser.addArgument("-m", "--mode")
	        		.required(true)
	        		.choices("gui", "cli")
            		.help("Specify filename of the result");
	        
	    Namespace ns = null;
	        try {
	            ns = parser.parseArgs(args);
	        } catch (ArgumentParserException e) {
	            parser.handleError(e);
	            System.exit(1);
	        }
		System.out.println(ns.getString("dir"));
		File file = new File(ns.getString("dir"));
		String dir;
		if (!file.exists()) {
			System.out.print("Invalid path!!!");
			return;
		}
		if (!file.isDirectory()) {
			System.out.print("Invalid path, full path must be specified!!!");
			return;
		}
		dir = file.getAbsolutePath();
		if (!dir.endsWith("/"))
			dir += "/";
		
		String mode = ns.getString("mode");
		
		//Load task from result file
		TaskLoader loader = new TaskLoader(dir + ns.getString("execution"));
		loader.Load();
		
		
		ArrayList<Resource> resources = loader.getResources();
		
		//create gantt chart
		TaskShape.SCALE = 0.0005f;
		GanttChartVisualizer ganttChart = new GanttChartVisualizer();
		for (Resource r : resources) {
			ganttChart.addResource(r);
		}
		
		JFrame window = new JFrame();
		window.setTitle("Gantt Chart");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//22 is the status bar height
		window.setSize(1440, resources.size() * TaskShape.HEIGHT + 22);
		
		window.add(ganttChart);
		
		//window.repaint();
		window.setVisible(true);
		if (mode.equalsIgnoreCase("gui")) {
			//window.setVisible(true);
		}
		
		// Save gantt chart to image file
		SVGGraphics2D g2 = new SVGGraphics2D(ganttChart.getWidth(), ganttChart.getHeight());
		ganttChart.paint(g2);
		File f = new File(dir + "gantt-chart.svg");
        try {
            SVGUtils.writeToSVG(f, g2.getSVGElement());
        } catch (IOException ex) {
            System.err.println(ex);
        }
        
        
        // Draw resource usage line graph
        UsageLoader usageLoader = new UsageLoader(dir + ns.getString("usage"));
        usageLoader.Load();
        ArrayList<Usage> usages = usageLoader.getUsages();
        UsageLineChartVisualizer usageVisualizer = new UsageLineChartVisualizer("Resource Usage");
        LineChartDataset lineChartDataset = new LineChartDataset();
        lineChartDataset.load(usages);
        if (mode.equalsIgnoreCase("gui")) {
        	usageVisualizer.render(lineChartDataset.getDataset(), resources.size());
            usageVisualizer.pack();
            usageVisualizer.setVisible(true);
        }
        
        // Save resource usage line graph to image file
        usageVisualizer.save(dir + "usage-chart.jpg", lineChartDataset.getDataset(), resources.size());
        
        // Save resource usage data
        try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(dir + "usage.csv"));
			Iterator it = lineChartDataset.getMap().entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry ePv = (Map.Entry) it.next();
				Map.Entry ePc = (Map.Entry) it.next();
				writer.write(String.format("%.0f\t%.0f\t%.0f", ((ResTimeKey)ePv.getKey()).getTime(), ePv.getValue(), ePc.getValue()));
				writer.newLine();
			}
			writer.close();
        } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
        // Summary info.
        Summarizer summarizer = new Summarizer(dir + "summary.txt");
        loader.getTasks().forEach(t -> {
        	summarizer.add(t);
        });
        if (mode == "gui") {
        	summarizer.print();
        }
        summarizer.save();
	}
}
