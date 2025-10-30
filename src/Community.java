import processing.core.PApplet;

import java.util.ArrayList;

/** A community of individuals */
public class Community {
    ArrayList<Person> people;

    public Community(ArrayList<Person> people) {
        this.people = people;
    }

    public void drawMembers(PApplet window) {
        for (int i = 0; i < people.size(); i++) {
            Person person = people.get(i);
            for (int j = i + 1; j < people.size(); j++) {
                Person otherPerson = people.get(j);
                if (person.isCollidingWith(otherPerson)) {
                    if (person.infected() && otherPerson.healthy()) otherPerson.setInfected(true);
                    if (otherPerson.infected() && person.healthy()) person.setInfected(true);
                    person.reverseTrajectory();
                    otherPerson.reverseTrajectory();
                }
            }
            person.draw(window);
        }
    }
}
