import processing.core.PApplet;

public class TextDisplay extends PApplet {
    private final Community community;

    public TextDisplay(Community community) {
        this.community = community;
    }

    public void settings() {
        size(400, 300);
    }

    public void draw() {
        background(255);
        fill(0);
        textAlign(LEFT, TOP);
        textSize(20);
        text("Healthy: " + community.getHealthy(), 20, 20);
        text("Infected: " + community.getInfected(), 20, 50);
        text("Removed: " + community.getRemoved(), 20, 80);
    }
}
