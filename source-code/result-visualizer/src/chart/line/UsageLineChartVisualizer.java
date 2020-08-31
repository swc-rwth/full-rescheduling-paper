package chart.line;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.StandardTickUnitSource;
import org.jfree.chart.axis.TickUnits;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.Layer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtils;
import org.jfree.data.xy.TableXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

public class UsageLineChartVisualizer extends ApplicationFrame {
	
	
	public UsageLineChartVisualizer(String applicationTitle) {
		super(applicationTitle);
	}

	public void render(TableXYDataset dataset, double upperBound) {
		JFreeChart chart = createChart(dataset, upperBound);
        ChartPanel chartPanel = new ChartPanel( chart );
        chartPanel.setMaximumDrawWidth(4000);
        chartPanel.setPreferredSize( new java.awt.Dimension( 1440 , 480 ) );
        setContentPane( chartPanel );
	}

	private JFreeChart createChart(TableXYDataset dataset, double upperBound) {
		JFreeChart chart = ChartFactory.createStackedXYAreaChart(
	            "Resource Usage",  // title
	            "Time (s)",             // x-axis label
	            "Number of used nodes",   // y-axis label
	            dataset);

	        chart.setBackgroundPaint(Color.WHITE);
	        chart.getTitle().setFont(new Font("Latin Modern Roman", Font.PLAIN, 16));
	        
	        LegendTitle legend = chart.getLegend();
	        legend.setItemFont(new Font("Latin Modern Roman", Font.PLAIN, 30));

	        XYPlot plot = (XYPlot) chart.getPlot();
	        plot.setForegroundAlpha(0.8f);
	        plot.setBackgroundPaint(Color.white);
	        plot.setDomainGridlinePaint(Color.DARK_GRAY);
	        plot.setRangeGridlinePaint(Color.DARK_GRAY);
	        
	        XYItemRenderer renderer = plot.getRenderer();
	        renderer.setSeriesPaint(0, new Color(46, 70, 106));
	        renderer.setSeriesPaint(1, new Color(135, 182, 255));
	        
	        NumberAxis axis = (NumberAxis) plot.getDomainAxis();
	        axis.setLowerMargin(0.0);
	        axis.setUpperMargin(0.01);
	        axis.setVerticalTickLabels(true);
	        axis.setTickLabelFont(new Font("Latin Modern Roman", Font.PLAIN, 18));
	        axis.setLabelFont(new Font("Latin Modern Roman", Font.PLAIN, 20));
	        //axis.setStandardTickUnits(new StandardTickUnitSource());
	        //axis.setAutoRangeMinimumSize(0.01);
	        axis.setTickUnit(new NumberTickUnit(86400));
	        
	        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	        //rangeAxis.setAutoRange(true);
	        //rangeAxis.setRange(0, 45);
	        //rangeAxis.setUpperMargin(0.1);
	        rangeAxis.setUpperBound(upperBound);
	        rangeAxis.setTickUnit(new NumberTickUnit(5));
	        rangeAxis.setTickLabelFont(new Font("Latin Modern Roman", Font.PLAIN, 18));
	        rangeAxis.setLabelFont(new Font("Latin Modern Roman", Font.PLAIN, 20));
	        
	        //ValueMarker max = new ValueMarker(45);
	        //max.setPaint(Color.orange);
	        //max.setLabel("Highest Value");
	        //max.setLabelTextAnchor(TextAnchor.CENTER_LEFT);
	        //plot.addRangeMarker(max,Layer.BACKGROUND);
	        
		return chart;
	}
	
	public void save(String filePath, TableXYDataset dataset, double upperBound) throws IOException { 
	    JFreeChart chart = createChart(dataset, upperBound);
		File file = new File(filePath);
		int width = 1440;
		int height = 480;
		if (isVisible()) {
			width = getWidth();
			height = getHeight();
		}
		ChartUtils.saveChartAsJPEG(file, chart, width, height);
		
		// Save to svg file
//        SVGGraphics2D svgGenerator = new SVGGraphics2D(width, height);
//        chart.draw(svgGenerator,new Rectangle(width,height));
//        Writer out = new OutputStreamWriter(new    FileOutputStream(filePath + ".svg"), "UTF-8");
//        out.write(svgGenerator.getSVGDocument());
//        out.close();
	}
}
