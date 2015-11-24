package fr.utbm.tc.qlearningmario.mario.entity;

import java.util.List;

import javafx.geometry.Point2D;

// FIXME: Provide two interfaces: for the agent side, one for the simulator side.
public interface AgentBody {
	// FIXME: only in agent side
	public List<Entity> getPerception();
	
	// FIXME: only in simulator side
	public void setPerception(List<Entity> perception);
	
	// FIXME: only in agent side
	public double getPerceptionDistance();
	
	// FIXME: only in agent side
	public void askAcceleration(Point2D vector);
	
	// FIXME: only in agent side
	public void askAction(int action);

	// FIXME: only in simulator side
	public Point2D getWantedAcceleration();

	// FIXME: only in simulator side
	public int getWantedAction();
}
