package mario.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class World extends Observable {
	private List<Entity> entities = new ArrayList<>();

	public void computePerceptions() {
        for (Entity entity : this.entities) {
            if (entity instanceof AgentBody)
            {
                AgentBody agentBody = ((AgentBody) entity);

                // Compute the AgentBody's perception.
                List<Entity> perception = new ArrayList<>();
                for (Entity otherEntity : this.entities) {
                    if (entity.distance(otherEntity) < agentBody.getPerceptionDistance())
                        perception.add(entity);
                }

                agentBody.setPerception(perception);
            }
        }
	}

    public List<Entity> getEntities() {
        return this.entities;
    }

	public void update() {
	}
}
