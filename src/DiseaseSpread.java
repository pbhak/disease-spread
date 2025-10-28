import processing.core.PApplet;

import java.util.ArrayList;

public class DiseaseSpread extends PApplet {
    Community community;

    public void settings() {
        size(800, 800);
    }

    public void setup() {
        ArrayList<Person> people = new ArrayList<>();
        for (int i = 0; i < 50; i++) people.add(new Person(false));
        community = new Community(people);
    }

    public void draw() {
    }


    public static void main(String[] args) {
        PApplet.main("DiseaseSpread");
    }
}
