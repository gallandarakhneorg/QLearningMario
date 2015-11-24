package fr.utbm.tc.qlearningmario.mario;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import org.arakhne.afc.vmutil.locale.Locale;

import fr.utbm.tc.qlearningmario.mario.agent.Agent;
import fr.utbm.tc.qlearningmario.mario.agent.GoombaAgent;
import fr.utbm.tc.qlearningmario.mario.agent.MarioAgent;
import fr.utbm.tc.qlearningmario.mario.entity.Goomba;
import fr.utbm.tc.qlearningmario.mario.entity.MarioBody;
import fr.utbm.tc.qlearningmario.mario.entity.World;

public class Scheduler implements Runnable, Observer {
    private World world;
    private List<Agent<?>> agents = new ArrayList<>();
    private boolean running = true;
    private int updatesPerSecond = 60;
    
    private final Logger log = Logger.getLogger(Scheduler.class.getName());
    
    public Scheduler(World world) {
        this.world = world;
    }

    @Override
	public void run() {
    	this.log.info(Locale.getString(Scheduler.this.getClass(), "scheduler.started")); //$NON-NLS-1$
    	
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
					this.log.severe(e.toString());
				}
            }
        }

        this.log.info(Locale.getString(Scheduler.this.getClass(), "scheduler.ended")); //$NON-NLS-1$
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
			this.log.info(Locale.getString(Scheduler.this.getClass(), "added.goomba")); //$NON-NLS-1$
			GoombaAgent agent = new GoombaAgent((Goomba) arg);
			this.agents.add(agent);
		} else if (arg instanceof MarioBody) {
			this.log.info(Locale.getString(Scheduler.this.getClass(), "added.mario")); //$NON-NLS-1$
			MarioAgent agent = new MarioAgent((MarioBody) arg);
			this.agents.add(agent);
		}
	}
}
