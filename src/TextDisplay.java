import processing.core.PApplet;

public class TextDisplay extends PApplet {
    private final Community community;
    long startTime;
    long experimentTime;
    long experimentFrameCount;

    public TextDisplay(Community community) {
        this.community = community;
    }

    public void settings() {
        size(400, 210);
    }

    public void setup() {
        surface.setLocation(25, 15);
        startTime = System.currentTimeMillis();
    }

    public void draw() {
        background(255);
        fill(0);
        textAlign(LEFT, TOP);
        textSize(20);
        if (community.getRemoved() == community.getNumMembers())
            text("All members have been removed", 20, 20);
        else {
            // this prevents the values from incrementing after all members are removed
            experimentTime = (System.currentTimeMillis() - startTime) / 1000;
            experimentFrameCount = frameCount;

            text("Healthy: " + community.getHealthy(), 20, 20);
            text("Infected: " + community.getInfected(), 20, 50);
            text("Removed: " + community.getRemoved(), 20, 80);
        }
        text("Total members: " + community.getNumMembers(), 20, 110);
        text("Time elapsed: " + experimentTime + "s", 20, 140);
        text("Frame: " + experimentFrameCount + " (" + Math.round(frameRate) + "fps)", 20, 170);
    }
}
