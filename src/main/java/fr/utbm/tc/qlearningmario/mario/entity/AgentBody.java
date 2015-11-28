package fr.utbm.tc.qlearningmario.mario.entity;

import java.util.List;

import javafx.geometry.Point2D;

public interface AgentBody {
	void setPerception(List<Entity<?>> perception);

	double getPerceptionDistance();

	Point2D getWantedAcceleration();

	int getWantedAction();
}
