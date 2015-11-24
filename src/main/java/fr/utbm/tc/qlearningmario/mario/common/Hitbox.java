package fr.utbm.tc.qlearningmario.mario.common;

public class Hitbox {
    public static final Hitbox nullHitbox = new Hitbox(0, 0);
	
    private double height;
    private double width;

    public Hitbox(double width, double height) {
        this.height = height;
        this.width = width;
    }
    
    public double getHeight() {
        return this.height;
    }
    
    public double getWidth() {
        return this.width;
    }
}
