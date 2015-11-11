package mario.common;

public class Hitbox {
    public static final Hitbox nullHitbox = new Hitbox(0, 0);
	
    private double height;
    private double width;

    public Hitbox(double height, double width) {
        this.height = height;
        this.width = width;
    }
    
    public double getHeight() {
        return this.height;
    }
    
    public void setHeight(double h) {
    	this.height = h;
    }
    
    public double getWidth() {
        return this.width;
    }
    
    public void setWidth(double w) {
    	this.width = w;
    }
}
