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

package fr.utbm.tc.qlearningmario.mario.agent;

import java.util.List;

import fr.utbm.tc.qlearningmario.mario.entity.Entity;
import javafx.geometry.Point2D;

/**
 *
 * @author $Author: boulmier$
 * @author $Author: cortier$
 * @mavengroupid $GroupId$
 * @version $FullVersion$
 * @mavenartifactid $ArtifactId$
 */
public interface Body {
	/** Return the perception of the body.
	 *
	 * @return the perception.
	 */
	List<Entity<?>> getPerception();

	/** Set the wanted acceleration for this loop.
	 *
	 * @param vector : a 2D vector which represent the acceleration.
	 */
	void askAcceleration(Point2D vector);

	/** Set the wanted action.
	 *
	 * @param action : an action that can be executed by the entity.
	 */
	void askAction(int action);
}
