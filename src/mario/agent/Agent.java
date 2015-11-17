package mario.agent;

import mario.entity.AgentBody;

public abstract class Agent<B extends AgentBody> {
    private B body;

    public abstract void update();
    
    public B getBody() {
        return this.body;
    }
}
