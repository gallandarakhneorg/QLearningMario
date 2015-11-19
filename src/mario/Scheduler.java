package mario;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import mario.agent.Agent;
import mario.agent.GoombaAgent;
import mario.agent.MarioAgent;
import mario.entity.Goomba;
import mario.entity.MarioBody;
import mario.entity.World;

public class Scheduler implements Runnable, Observer {
    private World world;
    private List<Agent<?>> agents = new ArrayList<>();
    private boolean running = true;
    private int updatesPerSecond = 60;
    
    public Scheduler(World world) {
        this.world = world;
    }

    @Override
	public void run() {
    	System.out.println("Scheduler started."); //$NON-NLS-1$
    	
    	long start_millis;
    	long elapsed_millis;
    	long sleep_millis;
    	
    	this.running = true;
        while (this.running) {
        	start_millis = System.currentTimeMillis();
        	
            this.world.computePerceptions();
            updateAgents();
            this.world.update();
            
            elapsed_millis = System.currentTimeMillis() - start_millis;
            
            sleep_millis = (1000 / this.updatesPerSecond) - elapsed_millis;
            if (sleep_millis > 0) {
	            try {
            		Thread.sleep(sleep_millis);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
        }
        
        System.out.println("Scheduler ended."); //$NON-NLS-1$
    }
    
    public void stop() {
    	this.running = false;
    }
    
    private void updateAgents() {
        for (Agent<?> agent : this.agents) {
            agent.update();
        }
    }

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Goomba) {
			System.out.println("Added GoombaAgent."); //$NON-NLS-1$
			GoombaAgent agent = new GoombaAgent((Goomba) arg);
			this.agents.add(agent);
		} else if (arg instanceof MarioBody) {
			System.out.println("Added MarioAgent."); //$NON-NLS-1$
			MarioAgent agent = new MarioAgent((MarioBody) arg);
			this.agents.add(agent);
		}
	}
}
