import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.markers.SeriesMarkers;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DataGraph {
    private final Community community;
    // x-axis represents time passed
    private final ArrayList<Long> healthyX, infectedX, removedX, quarantinedX, r0X;
    // y-axis represents # of people with that attribute
    private final ArrayList<Integer> healthyY, infectedY, removedY, quarantinedY;
    private final ArrayList<Double> r0Y;
    // chart objects
    private XYChart chart;
    private SwingWrapper<XYChart> swingWrapper;

    public DataGraph(Community community) {
        this.community = community;

        healthyX = new ArrayList<>();
        infectedX = new ArrayList<>();
        removedX = new ArrayList<>();
        quarantinedX = new ArrayList<>();
        healthyY = new ArrayList<>();
        infectedY = new ArrayList<>();
        removedY = new ArrayList<>();
        quarantinedY = new ArrayList<>();
        r0X = new ArrayList<>();
        r0Y = new ArrayList<>();
    }

    private void addTimeValuesToX() {
        long timeSecs = (System.currentTimeMillis() - community.getStartTime());
        healthyX.add(timeSecs);
        infectedX.add(timeSecs);
        removedX.add(timeSecs);
        quarantinedX.add(timeSecs);
        r0X.add(timeSecs);
    }

    private void addValuesToY(int healthy, int infected, int removed, int quarantined, double r0) {
        healthyY.add(healthy);
        infectedY.add(infected);
        removedY.add(removed);
        quarantinedY.add(quarantined);
        r0Y.add(r0);
    }

    /* Spawns initial graph window */
    public DataGraph spawn(int width, int height, String title, String xTitle, String yTitle) {
        chart = new XYChartBuilder()
                .width(width).height(height)
                .title(title)
                .xAxisTitle(xTitle)
                .yAxisTitle(yTitle)
                .build();
        // remove chart legend, change bg color, change line colors
        chart.getStyler()
                .setLegendVisible(false)
                .setChartBackgroundColor(Color.white)
                .setSeriesColors(new Color[]{
                        new Color(48, 97, 108),
                        new Color(245, 103, 85),
                        new Color(68, 68, 68),
                        new Color(255, 255, 68),
                        new Color(0, 0, 0),
                });

        // add chart data
        healthyX.add(0L);
        infectedX.add(0L);
        removedX.add(0L);
        quarantinedX.add(0L);
        r0X.add(0L);

        healthyY.add(community.getHealthy());
        infectedY.add(community.getInfected());
        removedY.add(community.getRemoved());
        quarantinedY.add(community.getQuarantined());
        r0Y.add(community.getR0());

        chart.addSeries("Amt. Healthy", healthyX, healthyY).setMarker(SeriesMarkers.NONE);
        chart.addSeries("Amt. Infected", infectedX, infectedY).setMarker(SeriesMarkers.NONE);
        chart.addSeries("Amt. Removed", removedX, removedY).setMarker(SeriesMarkers.NONE);
        chart.addSeries("Amt. Quarantined", quarantinedX, quarantinedY).setMarker(SeriesMarkers.NONE);
        chart.addSeries("R0", r0X, r0Y).setMarker(SeriesMarkers.NONE);

        // display chart
        swingWrapper = new SwingWrapper<>(chart);
        swingWrapper.displayChart().setLocation(25, 260);
        return this;
    }

    /* Redraws graph */
    public void redraw() {
        addTimeValuesToX();
        addValuesToY(community.getHealthy(), community.getInfected(), community.getRemoved(), community.getQuarantined(), community.getR0() * 100   );

        chart.updateXYSeries("Amt. Healthy", healthyX, healthyY, null);
        chart.updateXYSeries("Amt. Infected", infectedX, infectedY, null);
        chart.updateXYSeries("Amt. Removed", removedX, removedY, null);
        chart.updateXYSeries("Amt. Quarantined", quarantinedX, quarantinedY, null);
        chart.updateXYSeries("R0", r0X, r0Y, null);

        swingWrapper.repaintChart();
    }

    public void close() {
        swingWrapper.displayChart().dispose();
    }

    public JFrame getFrame() { return this.getFrame(); }
}