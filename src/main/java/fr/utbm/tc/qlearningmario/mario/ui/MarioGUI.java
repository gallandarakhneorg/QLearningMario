package fr.utbm.tc.qlearningmario.mario.ui;

import java.util.ArrayList;
import java.util.List;

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
	private final List<Entity> entities = new ArrayList<>();
	
	private Point2D cameraPos = Point2D.ZERO;

	public MarioGUI(GraphicsContext gc) {
		this.gc = gc;
	}

	@Override
	public void handle(long now) {
		this.gc.setFill(Color.WHITE);
		this.gc.fillRect(0, 0, 256, 240);
		
		this.gc.setFill(Color.BLACK);
		synchronized (this.entities) {
			for (Entity entity : this.entities) {
				this.gc.fillRect(
						entity.getLocation().getX() * 16 - this.cameraPos.getX(),
						entity.getLocation().getY() * 16 - this.cameraPos.getY(),
						entity.getHitbox().getWidth() * 16,
						entity.getHitbox().getHeight() * 16
						);
			}
		}
	}

	@Override
	public void update(WorldEvent e) {
		if (e.getType() == WorldEvent.Type.WORLD_UPDATE)
		{
			World w = e.getSource();
			synchronized (this.entities) {
				this.entities.clear();
				for (Entity entity : w.getEntities())
				{
					this.entities.add((Entity) entity.clone());
					if (entity instanceof MarioBody)
						this.cameraPos = new Point2D((int)(entity.getLocation().getX() * 16 - 128 + 0.5), 0);
				}
			}
		}
	}
}
