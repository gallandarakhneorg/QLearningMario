package mario;

import java.util.ArrayList;
import java.util.List;

import mario.agent.Agent;
import mario.entity.World;

public class Scheduler {
    private World world;
    private List<Agent> agents = new ArrayList<>();
    
    public void run() {
        while (true) {
            this.world.computePerceptions();
            updateAgents();
            this.world.update();
        }
    }
    
    private void updateAgents() {
        for (Agent agent : this.agents) {
            agent.update();
        }
    }
}
