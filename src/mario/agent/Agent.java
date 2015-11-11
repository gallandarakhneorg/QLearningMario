package mario.agent;

import mario.entity.AgentBody;

public interface Agent {
    public void update();
    
    public AgentBody getBody();
}
