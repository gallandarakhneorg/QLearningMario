package fr.utbm.tc.qlearningmario.mario.agent;

import java.util.List;

import fr.utbm.tc.qlearningmario.mario.entity.Entity;
import javafx.geometry.Point2D;

public interface Body {
	List<Entity<?>> getPerception();

	void askAcceleration(Point2D vector);

	void askAction(int action);
}
