package mario.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import mario.entity.Entity;
import mario.entity.World;

public class MarioGUI extends AnimationTimer implements Observer {
	private GraphicsContext gc;
	private List<Entity> entities = new ArrayList<>();

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
				this.gc.fillRect(entity.getLocation().getX() * 16, entity.getLocation().getY() * 16, entity.getHitbox().getWidth() * 16, entity.getHitbox().getHeight() * 16);
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		World world = ((World) o);
		
		synchronized (this.entities) {
			this.entities.clear();
			for (Entity entity : world.getEntities())
				this.entities.add(entity.clone());
		}
	}
}