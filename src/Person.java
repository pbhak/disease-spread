import processing.core.PApplet;

/** An individual person */
public class Person {
    private float x, y, speed;
    private boolean infected, removed;
    private double probabilityOfSpread;

    public Person setX(float x) {
        this.x = x;
        return this;
    }

    public Person setY(float y) {
        this.y = y;
        return this;
    }

    public Person setSpeed(float speed) {
        this.speed = speed;
        return this;
    }

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
        x += (float) (Math.random() * speed);
        y += (float) (Math.random() * speed);
        window.ellipse(x, y, 5, 5);
    }
 }
