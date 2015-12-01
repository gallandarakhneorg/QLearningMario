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

package fr.utbm.tc.qlearningmario.mario.ui;

import java.util.ArrayList;
import java.util.List;

import fr.utbm.tc.qlearningmario.mario.Game;
import fr.utbm.tc.qlearningmario.mario.entity.Entity;
import fr.utbm.tc.qlearningmario.mario.entity.MarioBody;
import fr.utbm.tc.qlearningmario.mario.entity.World;
import fr.utbm.tc.qlearningmario.mario.entity.WorldEvent;
import fr.utbm.tc.qlearningmario.mario.entity.WorldListener;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MarioGUI extends AnimationTimer implements WorldListener {
	private final GraphicsContext gc;
	private final List<Entity<?>> entities = new ArrayList<>();

	private Point2D cameraPos = Point2D.ZERO;

	public MarioGUI(GraphicsContext gc) {
		this.gc = gc;
	}

	@Override
	public void handle(long now) {
		this.gc.setFill(Color.WHITE);
		this.gc.fillRect(0, 0, Game.SCENE_WIDTH, Game.SCENE_HEIGHT);

		this.gc.setFill(Color.BLACK);
		synchronized (this.entities) {
			for (Entity<?> entity : this.entities) {
				this.gc.fillRect(
						entity.getLocation().getX() * Game.SCALE - this.cameraPos.getX(),
						entity.getLocation().getY() * Game.SCALE - this.cameraPos.getY(),
						entity.getHitbox().getWidth() * Game.SCALE,
						entity.getHitbox().getHeight() * Game.SCALE);
			}
		}
	}

	@Override
	public void update(WorldEvent e) {
		if (e.getType() == WorldEvent.Type.WORLD_UPDATE) {
			World w = e.getSource();
			synchronized (this.entities) {
				this.entities.clear();
				for (Entity<?> entity : w.getEntities()) {
					this.entities.add((Entity<?>) entity.clone());
					if (entity instanceof MarioBody) {
						this.cameraPos = new Point2D((int)(entity.getLocation().getX() * Game.SCALE - Game.SCENE_WIDTH/2 + 0.5), 0);
					}

				}
			}
		}
	}
}
