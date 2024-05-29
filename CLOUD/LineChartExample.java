import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class LineChartExample extends JFrame {

   private static final long serialVersionUID = 1L;

   public LineChartExample(String applicationTitle, String chartTitle) {
        super(applicationTitle);

        // based on the dataset we create the chart
        JFreeChart pieChart = ChartFactory.createXYLineChart(chartTitle, "Category", "Score", createDataset(),PlotOrientation.VERTICAL, true, true, false);

        // Adding chart into a chart panel
        ChartPanel chartPanel = new ChartPanel(pieChart);
      
        // settind default size
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
      
        // add to contentPane
        setContentPane(chartPanel);
    }
  
   private XYDataset createDataset() {
     
      final XYSeries firefox = new XYSeries("A");
      firefox.add(0, 0);
      firefox.add(2, 1);
      //firefox.add(3.0, 4.0);
     
      final XYSeries chrome = new XYSeries("B");
      chrome.add(0,0);
      chrome.add(4, 2);
     //chrome.add(3.0, 5.0);
    
     /*
      final XYSeries iexplorer = new XYSeries("C");
      iexplorer.add(3.0, 4.0);
      iexplorer.add(4.0, 5.0);
      iexplorer.add(5.0, 4.0);
     */
     
      final XYSeriesCollection dataset = new XYSeriesCollection();
      dataset.addSeries(firefox);
      dataset.addSeries(chrome);
     // dataset.addSeries(iexplorer);
     
      return dataset;
     
  }

   public static void main(String[] args) {
      LineChartExample chart = new LineChartExample("SAG", "Scenario Attack Graph");
      chart.pack();
      chart.setVisible(true);
   }
}