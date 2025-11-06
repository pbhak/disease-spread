import processing.core.PApplet;

import java.util.ArrayList;

public class DiseaseSpread extends PApplet {
    Community community;
    TextDisplay textDisplay; // secondary text display window
    DataGraph dataGraph; // data plot window

    public void settings() {
        size(800, 800);
    }

    public void setup() {
        ArrayList<Person> people = new ArrayList<>();
        for (int x = 30; x < height; x += 80) {
            for (int y = 30; y < width; y += 80)
                people.add(new Person().setX(x).setY(y));
        }
        community = new Community(people);

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
    }

    public void draw() {
        background(255);
        community.drawMembers(this);
        if (!community.isMovementStopped()) dataGraph.redraw();
    }

    public void keyPressed() {
        if (keyCode == ESC) exit();
        else if (key == 'a' || key == 'A') community.changeSpeedBy(-0.25f);
        else if (key == 'd' || key == 'D') community.changeSpeedBy(0.25f);
        else if (key == 'j' || key == 'J') community.changeProbabilityOfSpreadBy(-0.01);
        else if (key == 'l' || key == 'L') community.changeProbabilityOfSpreadBy(0.01);
    }


    public static void main(String[] args) {
        PApplet.main("DiseaseSpread");
    }
}
