package mario;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import mario.common.BlockType;
import mario.entity.Block;
import mario.entity.Entity;
import mario.entity.Goomba;
import mario.entity.MarioBody;

public class LevelLoader {
	public static List<Entity> loadLevelFromPng(String fileName) {
		List<Entity> entities = new ArrayList<>();
		
		// Create image object.
		Image image = new Image("file:" + fileName); //$NON-NLS-1$
		PixelReader preader = image.getPixelReader();
		
		int width = ((int) image.getWidth());
		int height = ((int) image.getHeight());
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Color color = preader.getColor(i, j);
				
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
				}
			}
		}
		
		return entities;
	}
}
