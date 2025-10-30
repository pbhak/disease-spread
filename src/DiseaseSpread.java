import processing.core.PApplet;

import java.util.ArrayList;

public class DiseaseSpread extends PApplet {
    Community community;

    public void settings() {
        size(800, 800);
    }

    public void setup() {
        ArrayList<Person> people = new ArrayList<>();
        people.add(new Person().setX(400).setY(400).setSpeed(2).setSize(25).setInfected(true));
        for (int x = 30; x < height; x += 80) {
            for (int y = 30; y < width; y += 80) {
                people.add(new Person().setX(x).setY(y).setSpeed(1).setSize(25));
            }
        }
        community = new Community(people);
    }

    public void draw() {
        background(255);
        community.drawMembers(this);
    }


    public static void main(String[] args) {
        PApplet.main("DiseaseSpread");
    }
}
