import processing.core.PApplet;

import java.util.ArrayList;

/** A community of individuals */
public class Community {
    private final ArrayList<Person> people;
    private long startTime;
    private boolean movementStopped;

    public Community(ArrayList<Person> people) {
        this.people = people;
        movementStopped = false;
    }

    public long getStartTime() { return startTime; }

    public void drawMembers(PApplet window) {
        if (startTime == 0L) startTime = System.currentTimeMillis();
        if (getNumMembers() == getRemoved()) stopAllMovement();

        for (int i = 0; i < people.size(); i++) {
            Person person = people.get(i);
            for (int j = i + 1; j < people.size(); j++) {
                Person otherPerson = people.get(j);
                if (person.isCollidingWith(otherPerson)) {
                    if (person.infected() && otherPerson.healthy()) {
                        otherPerson.setInfected(true);
                        otherPerson.incrementSpreadCount();
                    }
                    if (otherPerson.infected() && person.healthy()) {
                        person.setInfected(true);
                        person.incrementSpreadCount();
                    }

                    person.correctOverlap(otherPerson);

                    person.reverseTrajectory();
                    otherPerson.reverseTrajectory();
                }
            }
            person.draw(window);
        }
    }

    public int getHealthy() {
        int healthy = 0;
        for (Person person : people) if (person.healthy()) healthy++;
        return healthy;
    }

    public int getInfected() {
        int infected = 0;
        for (Person person : people) if (person.infected()) infected++;
        return infected;
    }

    public int getRemoved() {
        int removed = 0;
        for (Person person : people) if (person.removed()) removed++;
        return removed;
    }

    public int getQuarantined() {
        int quarantined = 0;
        for (Person person : people) if (person.quarantined()) quarantined++;
        return quarantined;
    }

    public int getNumMembers() {
        return people.size();
    }

    public void stopAllMovement() {
        movementStopped = true;
        for (Person person : people) person.stopAllMovement();
    }

    public void restartMovement() {
        movementStopped = false;
        for (Person person : people) person.startMovement();
    }

    public void toggleMovement() {
        if (movementStopped) restartMovement();
        else stopAllMovement();
    }

    public void changeSpeedBy(float changeFactor) {
        for (Person person : people) person.setSpeed(person.getSpeed() + changeFactor);
    }

    public void changeProbabilityOfSpreadBy(double changeFactor) {
        for (Person person : people)
            person.setProbabilityOfSpread(person.getProbabilityOfSpread() + changeFactor);
    }

    public double getR0() {
        return Person.getR0(people);
    }

    public float getSpeed() { return people.get(0).getSpeed(); }

    public double getProbabilityOfInfection() { return people.get(0).getProbabilityOfSpread(); }

    public boolean isMovementStopped() {
        return movementStopped;
    }
}
