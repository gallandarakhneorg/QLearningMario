package fr.utbm.tc.qlearningmario.mario.agent;

import fr.utbm.tc.qlearningmario.mario.entity.AgentBody;

public abstract class Agent<B extends AgentBody> {
    private final B body;

    public Agent(B body) {
    	assert (body != null);
        this.body = body;
    }
    
    public abstract void live();
    
    protected final B getBody() {
        return this.body;
    }
}
