package mario.entity;

import java.util.List;

import javafx.geometry.Point2D;

public interface AgentBody {
	public List<Entity> getPerception();
	
	public void setPerception(List<Entity> perception);
	
	public double getPerceptionDistance();
	
	public void askAcceleration(Point2D vector);
	
	public void askAction(int action);

	public Point2D getWantedAcceleration();

	public int getWantedAction();
}
