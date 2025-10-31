import processing.core.PApplet;

import java.util.ArrayList;

public class DiseaseSpread extends PApplet {
    Community community;
    TextDisplay textDisplay; // secondary text display window

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

        textDisplay = new TextDisplay(community);
        PApplet.runSketch(new String[]{"TextDisplay"}, textDisplay);

        surface.setLocation(450, 15);
    }

    public void draw() {
        background(255);
        community.drawMembers(this);
    }


    public static void main(String[] args) {
        PApplet.main("DiseaseSpread");
    }
}
