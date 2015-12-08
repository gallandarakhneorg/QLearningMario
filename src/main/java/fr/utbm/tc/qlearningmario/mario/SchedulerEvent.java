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

import java.util.EventObject;

import fr.utbm.tc.qlearningmario.mario.agent.Agent;

public class SchedulerEvent extends EventObject {

	public enum Type {
		AGENT_ADDED,
		AGENT_REMOVED
	}

	private static final long serialVersionUID = -8396490884864538128L;

	private final Type eventType;

	private Agent<?> agent;

	public SchedulerEvent(Scheduler source, Type eventType) {
		super(source);
		this.eventType = eventType;
	}

	public SchedulerEvent(Scheduler source, Agent<?> agent, Type eventType) {
		this(source, eventType);
		this.agent = agent;
	}

	@Override
	public Scheduler getSource() {
		return (Scheduler) super.getSource();
	}

	public Agent<?> getAgent() {
		return this.agent;
	}

	public Type getType() {
		return this.eventType;
	}
}
