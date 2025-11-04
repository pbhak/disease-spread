import processing.core.PApplet;

public class SecondaryWindow extends PApplet {
    Community community;
    int width, height;

    public SecondaryWindow(Community community, int width, int height) {
        this.community = community;
        this.width = width;
        this.height = height;
    }

    public void settings() {
        size(width, height);
    }

    public void setup() {
    }

    public void draw() {
        background(255);
        community.drawMembers(this);
    }
}
