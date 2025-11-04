import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.awt.*;
import java.util.ArrayList;

public class DataGraph {
    private final Community community;
    // x-axis represents time passed
    private final ArrayList<Long> healthyX, infectedX, removedX, quarantinedX;
    // y-axis represents # of people with that attribute
    private final ArrayList<Integer> healthyY, infectedY, removedY, quarantinedY;
    // chart objects
    XYChart chart;
    SwingWrapper<XYChart> swingWrapper;

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
    }

    private void addTimeValuesToX() {
        long timeSecs = (System.currentTimeMillis() - community.getStartTime());
        healthyX.add(timeSecs);
        infectedX.add(timeSecs);
        removedX.add(timeSecs);
        quarantinedX.add(timeSecs);
    }

    private void addValuesToY(int healthy, int infected, int removed, int quarantined) {
        healthyY.add(healthy);
        infectedY.add(infected);
        removedY.add(removed);
        quarantinedY.add(quarantined);
    }

    /* Spawns initial graph window */
    public DataGraph spawn(int width, int height, String title, String xTitle, String yTitle) {
        chart = new XYChartBuilder()
                .width(width).height(height)
                .title(title)
                .xAxisTitle(xTitle)
                .yAxisTitle(yTitle)
                .build();
        // remove chart legend and change bg color
        chart.getStyler()
                .setLegendVisible(false)
                .setChartBackgroundColor(Color.white)
                .setSeriesColors(new Color[]{
                        new Color(178, 197, 201),
                        new Color(169, 104, 48),
                        new Color(190, 142, 189),
                        new Color(255, 255, 68)
                });

        // add chart data
        healthyX.add(0L);
        infectedX.add(0L);
        removedX.add(0L);
        quarantinedX.add(0L);

        healthyY.add(community.getHealthy());
        infectedY.add(community.getInfected());
        removedY.add(community.getRemoved());
        quarantinedY.add(community.getQuarantined());

        chart.addSeries("Amt. Healthy", healthyX, healthyY).setMarker(SeriesMarkers.NONE);
        chart.addSeries("Amt. Infected", infectedX, infectedY).setMarker(SeriesMarkers.NONE);
        chart.addSeries("Amt. Removed", removedX, removedY).setMarker(SeriesMarkers.NONE);
        chart.addSeries("Amt. Quarantined", quarantinedX, quarantinedY).setMarker(SeriesMarkers.NONE);

        // display chart
        swingWrapper = new SwingWrapper<>(chart);
        swingWrapper.displayChart().setLocation(25, 260);

        return this;
    }

    /* Redraws graph */
    public void redraw() {
        addTimeValuesToX();
        addValuesToY(community.getHealthy(), community.getInfected(), community.getRemoved(), community.getQuarantined());

        chart.updateXYSeries("Amt. Healthy", healthyX, healthyY, null);
        chart.updateXYSeries("Amt. Infected", infectedX, infectedY, null);
        chart.updateXYSeries("Amt. Removed", removedX, removedY, null);
        chart.updateXYSeries("Amt. Quarantined", quarantinedX, quarantinedY, null);

        swingWrapper.repaintChart();
    }
}