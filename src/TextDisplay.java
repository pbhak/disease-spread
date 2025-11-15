import processing.core.PApplet;

public class TextDisplay extends PApplet {
    private final Community community;
    private PApplet parent;
    private long startTime;
    private long experimentTime;
    private long experimentFrameCount;

    public TextDisplay(Community community, PApplet parent) {
        this.community = community;
        this.parent = parent;
    }

    public void settings() {
        size(580, 210);
    }

    public void setup() {
        surface.setLocation(25, 15);
        startTime = System.currentTimeMillis();
    }

    public void draw() {
        background(255);
        fill(0);
        textAlign(LEFT, TOP);
        textSize(15);
        if (community.getRemoved() == community.getNumMembers())
            text("All members have been removed", 20, 20);
        else {
            // this prevents the values from incrementing after all members are removed
            experimentTime = (System.currentTimeMillis() - startTime) / 1000;
            experimentFrameCount = parent.frameCount;

            text("Healthy: " + community.getHealthy(), 20, 20);
            text("Speed: " + community.getSpeed() + " (<a, d> to adjust)", 180, 20);
            text("Chance of random infection: " +
                    community.getProbabilityOfInfection() * 100 +
                    "% (<j, l> to adjust)", 180, 45);
            text("<space> to pause/play simulation", 180, 70);
            text("R0: " + community.getR0(), 180, 120);

            text("Infected: " + community.getInfected(), 20, 45);
            text("Removed: " + community.getRemoved(), 20, 70);
            text("Quarantined: " + community.getQuarantined(), 20, 95);
        }
        text("Total members: " + community.getNumMembers(), 20, 120);
        text("Time elapsed: " + experimentTime + "s", 20, 145);
        text("Frame: " + experimentFrameCount + " (" + Math.round(parent.frameRate) + "fps)", 20, 170);
    }
}
