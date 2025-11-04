import processing.core.PApplet;

import java.util.ArrayList;

public class DiseaseSpread extends PApplet {
    Community community, community2;
    TextDisplay textDisplay; // secondary text display window
    DataGraph dataGraph; // data plot window
    SecondaryWindow secondaryWindow;

    public void settings() {
        size(400, 400);
    }

    public void setup() {
        ArrayList<Person> people = new ArrayList<>();
        for (int x = 30; x < height; x += 80) {
            for (int y = 30; y < width; y += 80)
                people.add(new Person().setX(x).setY(y));
        }
        community = new Community(people);
        community2 = new Community(people);

        textDisplay = new TextDisplay(community, this);
        PApplet.runSketch(new String[]{"TextDisplay"}, textDisplay);

        surface.setLocation(610, 15);

        dataGraph = new DataGraph(community).spawn(
                580,
                555,
                "Change over time",
                "Time (ms)",
                "Count"
        );

        secondaryWindow = new SecondaryWindow(community2, 400, 400);
        PApplet.runSketch(new String[]{"SecondaryWindow"}, secondaryWindow);
    }

    public void draw() {
        background(255);
        community.drawMembers(this);
        if (!community.isMovementStopped()) dataGraph.redraw();
    }


    public static void main(String[] args) {
        PApplet.main("DiseaseSpread");
    }
}
