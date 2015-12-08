/*******************************************************************************
 * Copyright (C) 2015 BOULMIER Jérôme, CORTIER Benoît
 *
 * This software is provided 'as-is', without any express or implied
 * warranty.  In no event will the authors be held liable for any damages
 * arising from the use of this software.
 *
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 *
 * 1. The origin of this software must not be misrepresented; you must not
 *    claim that you wrote the original software. If you use this software
 *    in a product, an acknowledgment in the product documentation would be
 *    appreciated but is not required.
 * 2. Altered source versions must be plainly marked as such, and must not be
 *    misrepresented as being the original software.
 * 3. This notice may not be removed or altered from any source distribution.
 *******************************************************************************/

package fr.utbm.tc.qlearningmario.mario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.arakhne.afc.vmutil.locale.Locale;

import fr.utbm.tc.qlearningmario.mario.agent.Agent;
import fr.utbm.tc.qlearningmario.mario.agent.GoombaAgent;
import fr.utbm.tc.qlearningmario.mario.agent.MarioAgent;
import fr.utbm.tc.qlearningmario.mario.entity.Entity;
import fr.utbm.tc.qlearningmario.mario.entity.Goomba;
import fr.utbm.tc.qlearningmario.mario.entity.MarioBody;
import fr.utbm.tc.qlearningmario.mario.entity.World;
import fr.utbm.tc.qlearningmario.mario.entity.WorldEvent;
import fr.utbm.tc.qlearningmario.mario.entity.WorldListener;

/** Runnable class which handle world and agents.
 *
 * @author $Author: boulmier$
 * @author $Author: cortier$
 * @mavengroupid $GroupId$
 * @version $FullVersion$
 * @mavenartifactid $ArtifactId$
 */
public class Scheduler implements Runnable, WorldListener {
	private static final int ONE_SECOND_IN_MILLIS = 1000;

	private World world;

	private Map<Integer, Agent<?>> agents = new HashMap<>();

	private boolean running = true;

	private boolean paused = false;

	private int updatesPerSecond = Integer.parseInt(Locale.getString(Scheduler.class, "updates.per.second")); //$NON-NLS-1$

	private final Logger log = Logger.getLogger(Scheduler.class.getName());

	private final List<SchedulerListener> listeners = new ArrayList<>();

	/** Initialize a new Scheduler with the given world.
	 *
	 * @param world : the world.
	 */
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

			if (this.paused == false) {
				this.world.computePerceptions();
				updateAgents();
				this.world.update();
			}

			elapsed_millis = System.currentTimeMillis() - start_millis;

			sleep_millis = (ONE_SECOND_IN_MILLIS / this.updatesPerSecond) - elapsed_millis;
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

	/** Stop the scheduler.
	 */
	public void stop() {
		this.running = false;
	}

	/** Pause the scheduler.
	 */
	public void pause() {
		this.paused = true;
	}

	/** Unpause the scheduler.
	 */
	public void unpause() {
		this.paused = false;
	}

	/** Update all agents.
	 */
	private void updateAgents() {
		for (Entry<Integer, Agent<?>> agent : this.agents.entrySet()) {
			agent.getValue().live();
		}
	}

	@SuppressWarnings("boxing")
	@Override
	public void update(WorldEvent event) {
		if (event.getType() == WorldEvent.Type.ENTITY_ADDED) {
			Entity<?> entity = event.getEntity();
			if (entity instanceof Goomba) {
				this.log.info(Locale.getString(Scheduler.this.getClass(), "added.goomba")); //$NON-NLS-1$
				GoombaAgent agent = new GoombaAgent((Goomba) entity);
				this.agents.put(entity.getID(), agent);
				fireAgentAdded(agent);
			} else if (entity instanceof MarioBody) {
				this.log.info(Locale.getString(Scheduler.this.getClass(), "added.mario")); //$NON-NLS-1$
				MarioAgent agent = new MarioAgent((MarioBody) entity);
				this.agents.put(entity.getID(), agent);
				fireAgentAdded(agent);
			}
		} else if (event.getType() == WorldEvent.Type.ENTITY_REMOVED) {
			fireAgentRemoved(this.agents.get(event.getEntity().getID()));
			this.agents.remove(event.getEntity().getID());
		}
	}

	public MarioAgent getMarioAgent() {
		for (Agent<?> agent : this.agents.values()) {
			if (agent instanceof MarioAgent)
				return (MarioAgent) agent;
		}

		return null;
	}

	public void addSchedulerListener(SchedulerListener schedulerListener) {
		assert(schedulerListener != null);
		this.listeners.add(schedulerListener);
	}

	public void removeWorldListener(SchedulerListener schedulerListener) {
		assert(schedulerListener != null);
		this.listeners.remove(schedulerListener);
	}

	private void fireEvent(SchedulerEvent e) {
		SchedulerListener[] tab = new SchedulerListener[this.listeners.size()];
		this.listeners.toArray(tab);

		for (SchedulerListener schedulerListener : tab) {
			schedulerListener.schedulerUpdated(e);
		}
	}

	private void fireAgentAdded(Agent<?> agent) {
		SchedulerEvent e = new SchedulerEvent(this, agent, SchedulerEvent.Type.AGENT_ADDED);
		fireEvent(e);
	}

	private void fireAgentRemoved(Agent<?> agent) {
		SchedulerEvent e = new SchedulerEvent(this, agent, SchedulerEvent.Type.AGENT_REMOVED);
		fireEvent(e);
	}
}
