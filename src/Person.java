import processing.core.PApplet;
import java.math.BigDecimal;
import java.math.RoundingMode;

/** An individual person */
public class Person {
    private float size = 25;
    private float speed;
    private double probabilityOfSpread = 0.05;
    public double probabilityOfQuarantine = 0.2;

    private float x, y;
    private int daysSinceInfected;
    private boolean infected, quarantined, removed, movementStopped;

    private float xTrajectory, yTrajectory;

    public Person() { speed = 2; }

    public void setSpeed(float speed) {
        this.speed = speed;
        recalculateTrajectory();
    }

    public void setProbabilityOfSpread(double probabilityOfSpread) {
        if (probabilityOfSpread < 0) {
            this.probabilityOfSpread = 0;
            return;
        } else if (probabilityOfSpread > 1) {
            this.probabilityOfSpread = 1;
            return;
        }
        this.probabilityOfSpread = roundToTwo(probabilityOfSpread);
    }

    public Person setX(float x) {
        this.x = x;
        return this;
    }

    public Person setY(float y) {
        this.y = y;
        return this;
    }

    public float getRadius() {
        return size / 2;
    }

    public float getSpeed() { return speed; }

    public double getProbabilityOfSpread() { return probabilityOfSpread; }

    public boolean infected() {
        return infected;
    }

    public boolean healthy() { return !infected && !removed && !quarantined; }

    public boolean removed() { return removed; }

    public boolean quarantined() { return quarantined; }

    public void setInfected(boolean infected) {
        if (infected && Math.random() < probabilityOfQuarantine) {
            quarantined = true;
            return;
        }
        this.infected = infected;
    }

    public void draw (PApplet window) {
        // 0.02% chance of becoming infected per frame
        if (Math.random() < (probabilityOfSpread / window.frameRate) && healthy()) infected = true;

        if (xTrajectory == 0.0f || yTrajectory == 0.0f) recalculateTrajectory();

        if (x < getRadius() || x >= window.width - getRadius()) xTrajectory *= -1;
        else if (y < getRadius() || y >= window.height - getRadius()) yTrajectory *= -1;

        if (!movementStopped) {
            x += xTrajectory;
            y += yTrajectory;
        }

        if (daysSinceInfected > 250 && Math.random() < 0.05) {
            infected = false;
            quarantined = false;
            removed = true;
        }

        if (infected) {
//            window.fill(169, 104, 48);
            window.fill(245, 103, 85);
            daysSinceInfected++;
        } else if (removed) {
            daysSinceInfected = 0;
//            window.fill(190, 142, 189);
            window.fill(68, 68, 68);
        } else if (quarantined) {
            window.fill(255, 255, 68);
            daysSinceInfected += 2; // recover at double speed
        } else {
//            window.fill(178, 197, 201);
            window.fill(48, 97, 108);
        }

        window.noStroke();
        if (!quarantined) window.ellipse(x, y, size, size);
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

    public void stopAllMovement() {
        movementStopped = false;
    }

    void recalculateTrajectory() {
        xTrajectory = (float) (Math.random() * (2 * speed) - speed);
        yTrajectory = (float) (Math.random() * (2 * speed) - speed);
    }

    double roundToTwo(double value) {
        // ugly rounding code i found on stack overflow
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
 }
