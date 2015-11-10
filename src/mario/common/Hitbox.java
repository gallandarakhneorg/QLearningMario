package mario.common;

public class Hitbox {
    private double x;
    private double y;
    private double height;
    private double width;
    
    public static final Hitbox nullHitbox = new Hitbox(0, 0, 0, 0);

    public Hitbox(double x, double y, double height, double width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getHeight() {
        return this.height;
    }
    
    public double getWidth() {
        return this.width;
    }
    
    public boolean contains(double x1, double y1) {
        return this.x < x1
                && this.x + this.width > x1
                && this.y < y1
                && this.y + this.height > y1;
    }

    public boolean intersect(Hitbox hitbox) {
        return this.x < hitbox.getX() + hitbox.getWidth()
                && this.x + this.width > hitbox.getX()
                && this.y + this.height > hitbox.getY()
                && this.y < hitbox.getY() + hitbox.getHeight();
    }
}
