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
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class LevelLoader {
	// FIXME: A filename is not a string (you are not a "f**g C developer"); use File, URL (best is URL) 
	public static List<Entity> loadLevelFromPng(URL fileName) throws IOException {
		List<Entity> entities = new ArrayList<>();
		
		// Create image object.
//		Image image = new Image(fileName.toString()); //$NON-NLS-1$
//		PixelReader preader = image.getPixelReader();
		
		// FIXME: Why cast to int? => Invalid API usage. See below for ImageIO use.
//		int width = ((int) image.getWidth());
//		int height = ((int) image.getHeight());
		
		BufferedImage image = ImageIO.read(fileName);
		
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				int rgba = image.getRGB(i, j);
				Color color = new Color(
						((rgba >> 16) & 0xFF) / 255.,
						((rgba >> 8) & 0xFF) / 255.,
						(rgba & 0xFF) / 255.,
						((rgba >> 24) & 0xFF) / 255.);
				
				if (color.equals(Color.BLACK)) {
					Block block = new Block(BlockType.GroundRock);
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
