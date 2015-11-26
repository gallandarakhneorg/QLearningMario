package fr.utbm.tc.qlearningmario.mario.entity;

import java.util.EventListener;

public interface WorldListener extends EventListener {
	void update(WorldEvent e);
}
