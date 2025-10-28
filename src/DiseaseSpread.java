import processing.core.PApplet;

import java.util.ArrayList;

public class DiseaseSpread extends PApplet {
    Community community;

    public void settings() {
        size(800, 800);
    }

    public void setup() {
        ArrayList<Person> people = new ArrayList<>();
        for (int i = 0; i < 50; i++) people.add(new Person().setX(100).setY(100).setSpeed(5));
        community = new Community(people);
    }

    public void draw() {
        for (Person person : community.people) person.draw(this);
        // where i left off: currently, the values that x and y are incremented by are both positive.
        // this means the particle will always travel diagonally bottom-right.
    }


    public static void main(String[] args) {
        PApplet.main("DiseaseSpread");
    }
}
