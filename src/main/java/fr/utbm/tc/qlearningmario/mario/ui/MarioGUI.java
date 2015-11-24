package fr.utbm.tc.qlearningmario.mario.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import fr.utbm.tc.qlearningmario.mario.entity.Entity;
import fr.utbm.tc.qlearningmario.mario.entity.MarioBody;
import fr.utbm.tc.qlearningmario.mario.entity.World;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// FIXME: Observer is too old => no generic. Replace by EventListener or ad-hoc interface.
public class MarioGUI extends AnimationTimer implements Observer {
	// FIXME: final
	private final GraphicsContext gc;
	// FIXME: final
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
	public void update(Observable o, Object arg) {
		// FIXME: cast => must be remove with introduction of generics, when possible
		World world = ((World) o);
		
		synchronized (this.entities) {
			this.entities.clear();
			for (Entity entity : world.getEntities())
			{
				this.entities.add(entity.clone());
				if (entity instanceof MarioBody)
					this.cameraPos = new Point2D((int)(entity.getLocation().getX() * 16 - 128 + 0.5), 0);
			}
		}
	}
}
