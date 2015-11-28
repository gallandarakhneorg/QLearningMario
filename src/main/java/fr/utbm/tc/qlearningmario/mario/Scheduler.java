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

import java.util.HashMap;
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
import fr.utbm.tc.qlearningmario.mario.entity.WorldEvent.Type;
import fr.utbm.tc.qlearningmario.mario.entity.WorldListener;

public class Scheduler implements Runnable, WorldListener {
	private World world;
	private Map<Integer, Agent<?>> agents = new HashMap<>();
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
		for (Entry<Integer, Agent<?>> agent : this.agents.entrySet()) {
			agent.getValue().live();
		}
	}

	@SuppressWarnings("boxing")
	@Override
	public void update(WorldEvent e) {
		if (e.getType() == Type.ENTITY_ADDED) {
			Entity<?> entity = e.getEntity();
			if (entity instanceof Goomba) {
				this.log.info(Locale.getString(Scheduler.this.getClass(), "added.goomba")); //$NON-NLS-1$
				GoombaAgent agent = new GoombaAgent((Goomba) entity);
				this.agents.put(entity.getID(), agent);
			} else if (entity instanceof MarioBody) {
				this.log.info(Locale.getString(Scheduler.this.getClass(), "added.mario")); //$NON-NLS-1$
				MarioAgent agent = new MarioAgent((MarioBody) entity);
				this.agents.put(entity.getID(), agent);
			}
		} else if (e.getType() == Type.ENTITY_REMOVED) {
			this.agents.remove(e.getEntity().getID());
		}
	}
}
