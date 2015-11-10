package mario.entity;

import java.util.List;

import javafx.geometry.Point2D;

public interface AgentBody {
	public List<Entity> getPerception();
	
	public void move(Point2D vector);
	
	public void act(int action);
}
