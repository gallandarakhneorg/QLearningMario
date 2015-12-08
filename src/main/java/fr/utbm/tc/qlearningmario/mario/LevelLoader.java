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

package fr.utbm.tc.qlearningmario.mario;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import fr.utbm.tc.qlearningmario.mario.common.BlockType;
import fr.utbm.tc.qlearningmario.mario.entity.Block;
import fr.utbm.tc.qlearningmario.mario.entity.Entity;
import fr.utbm.tc.qlearningmario.mario.entity.Goomba;
import fr.utbm.tc.qlearningmario.mario.entity.MarioBody;
import fr.utbm.tc.qlearningmario.mario.entity.Mushroom;
import fr.utbm.tc.qlearningmario.mario.entity.PrizeBlock;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

/** Contains static methods to load levels.
 *
 * @author $Author: boulmier$
 * @author $Author: cortier$
 * @mavengroupid $GroupId$
 * @version $FullVersion$
 * @mavenartifactid $ArtifactId$
 */
public class LevelLoader {

	/** Load the level from the given image.
	 *
	 * @param fileName : the image file's url.
	 * @return a list of entities.
	 * @throws IOException
	 */
	public static List<Entity<?>> loadLevelFromImage(URL fileName) throws IOException {
		List<Entity<?>> entities = new ArrayList<>();

		BufferedImage image = ImageIO.read(fileName);

		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				int rgba = image.getRGB(i, j);
				Color color = Color.rgb(
						(rgba >> 16) & 0xFF,
						(rgba >> 8) & 0xFF,
						rgba & 0xFF,
						((rgba >> 24) & 0xFF) / 255.);

				if (color.equals(Color.BLACK)) {
					Block<?> block = new Block<>(BlockType.GROUND_ROCK);
					block.setLocation(new Point2D(i, j));
					entities.add(block);
				} else if (color.equals(Color.RED)) {
					Goomba goomba = new Goomba();
					goomba.setLocation(new Point2D(i, j));
					entities.add(goomba);
				} else if (color.equals(Color.GREEN)) {
					MarioBody mario = new MarioBody();
					mario.setLocation(new Point2D(i, j));
					entities.add(mario);
				} else if (color.equals(Color.YELLOW)) {
					PrizeBlock pblock = new PrizeBlock(new Mushroom());
					pblock.setLocation(new Point2D(i, j));
					entities.add(pblock);
				}
			}
		}

		return entities;
	}
}
