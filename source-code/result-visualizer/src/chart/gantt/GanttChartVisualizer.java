package chart.gantt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JComponent;
import chart.data.Resource;
import chart.data.Task;
import chart.gantt.TaskShape;

public class GanttChartVisualizer extends JComponent {
	
	public static int leftMargin = 50;
	public static int topMargin = 60;
	
	public static final ArrayList<Color> COLORS = new ArrayList<Color>(Arrays.asList(
				hex2Rgb("#228408"),
				hex2Rgb("#cee0e5"),
				hex2Rgb("#addd6c"),
				hex2Rgb("#f4af09"),
				hex2Rgb("#a6a5cb"),
				hex2Rgb("#3b72c5"),
				hex2Rgb("#690101"),
				hex2Rgb("#6caa15"),
				hex2Rgb("#7a07e2"),
				hex2Rgb("#96b7e8"),
				hex2Rgb("#dae0cb"),
				hex2Rgb("#65a0a4")
			));
	
	private ArrayList<Resource> resources;
	
	public static Color hex2Rgb(String colorStr) {
	    return new Color(
	            Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
	            Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
	            Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
	}
	
	public GanttChartVisualizer() {
		this.resources = new ArrayList<Resource>();
	}
	
	public void addResource(Resource resource) {
		resources.add(resource);
	}
	
	@Override
	public void paintComponent(Graphics gr) {
		Graphics2D g2d = (Graphics2D) gr;
		for (Resource res : resources) {
			for (Task t : res.getTasks()) {
				TaskShape taskShape = new TaskShape(t);
				//number of distinct colors
				int numColor = GanttChartVisualizer.COLORS.size();
				//ensure similar indices have differentiable colors
				int m = t.getId() % numColor;
				g2d.setColor(GanttChartVisualizer.COLORS.get(m));
				g2d.fill(taskShape);
			}
		}
		g2d.setColor(Color.GRAY);
		g2d.drawLine(leftMargin, topMargin-3, 1350, topMargin-3);
		g2d.setColor(Color.BLACK);
		Font font = new Font("Latin Modern Roman", Font.PLAIN, 12);
		g2d.setFont(font);
		for (Resource res : resources) {
			int y = res.getIndex() * TaskShape.HEIGHT + TaskShape.HEIGHT + topMargin;
			g2d.drawString(res.getName(), 5, y);
		}
		
		  
		AffineTransform affineTransform = new AffineTransform();
		font = new Font("Latin Modern Roman", Font.PLAIN, 18);
		affineTransform.rotate(Math.toRadians(-90), 0, 0);
		Font rotatedFont = font.deriveFont(affineTransform);
		for (int i = 0; i <= 2550000; i += 86400) {
			int x = (int) Math.floor(i * TaskShape.SCALE);
			g2d.drawLine(x + leftMargin, topMargin-3, x + leftMargin, topMargin-2);
			g2d.setFont(rotatedFont);
			g2d.drawString(String.format("%,d", i/86400), x + leftMargin + 5, topMargin-8);
		}
		
	}

}
