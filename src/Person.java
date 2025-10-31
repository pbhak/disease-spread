import processing.core.PApplet;

/** An individual person */
public class Person {
    private float x, y, speed, size;
    private int daysSinceInfected;
    private boolean infected, removed;
    private double probabilityOfSpread;

    private float xTrajectory, yTrajectory;

    public Person setX(float x) {
        this.x = x;
        return this;
    }

    public Person setY(float y) {
        this.y = y;
        return this;
    }

    public float getSpeed() {
        return speed;
    }

    public float getRadius() {
        return size / 2;
    }

    public Person setSpeed(float speed) {
        this.speed = speed;
        return this;
    }


    public Person setSize(float size) {
        this.size = size;
        return this;
    }

    public boolean infected() {
        return infected;
    }

    public boolean healthy() { return !infected && !removed; }

    public boolean removed() { return removed; }

    public int getDaysSinceInfected() { return daysSinceInfected; }

    public Person setInfected(boolean infected) {
        this.infected = infected;
        return this;
    }

    public Person setRemoved(boolean removed) {
        // by the rules of an SIR model, individuals are removed when they either
        // recover or die from the infection
        this.removed = removed;
        return this;
    }

    public Person setProbabilityOfSpread(double probabilityOfSpread) {
        this.probabilityOfSpread = probabilityOfSpread;
        return this;
    }

    public void draw (PApplet window) {
        if (xTrajectory == 0.0f || yTrajectory == 0.0f) {
            // the particle is not currently moving
            xTrajectory = (float) (Math.random() * (2 * speed) - speed);
            yTrajectory = (float) (Math.random() * (2 * speed) - speed);
        }

        if (x < getRadius() || x >= window.width - getRadius()) xTrajectory *= -1;
        else if (y < getRadius() || y >= window.height - getRadius()) yTrajectory *= -1;

        x += xTrajectory;
        y += yTrajectory;

        if (daysSinceInfected > 250) {
            infected = false;
            removed = true;
        }

        if (infected) {
            window.fill(169, 104, 48);
            daysSinceInfected++;
        } else if (removed) {
            daysSinceInfected = 0;
            window.fill(190, 142, 189);
        } else {
            window.fill(178, 197, 201);
        }

        window.noStroke();
        window.ellipse(x, y, size, size);
    }

    public double getDistanceFrom(Person other) {
        return Math.sqrt((other.x - x) * (other.x - x) + (other.y - y) * (other.y - y));
    }

    public boolean isCollidingWith(Person other) {
        return getDistanceFrom(other) < getRadius() + other.getRadius();
    }

    public void reverseTrajectory() {
        xTrajectory *= -1;
        yTrajectory *= -1;
    }

    public void correctOverlap(Person other) {
        float distance = (float) getDistanceFrom(other);

        // get overlap between the two particles
        float overlap = getRadius() + other.getRadius() - distance;

        float unitDx = (other.x - x) / distance;
        float unitDy = (other.y - y) / distance;

        x -= unitDx * (overlap / 2);
        y -= unitDy * (overlap / 2);

        other.x += unitDx * (overlap / 2);
        other.y += unitDy * (overlap / 2);
    }
 }
