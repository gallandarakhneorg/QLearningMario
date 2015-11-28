package fr.utbm.tc.qlearningmario.mario.agent;

import javafx.geometry.Point2D;

public abstract class Agent<B extends Body> {
    private final B body;

    public Agent(B body) {
    	assert (body != null);
        this.body = body;
    }
    
    public void live() {
    	this.body.askAcceleration(Point2D.ZERO);
    }
    
    protected final B getBody() {
        return this.body;
    }
}
