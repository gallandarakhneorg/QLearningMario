package mario;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mario.common.BlockType;
import mario.entity.Block;
import mario.entity.Entity;
import mario.entity.Goomba;
import mario.entity.MarioBody;
import mario.entity.World;
import mario.ui.MarioGUI;

public class Game extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			
			Scene scene = new Scene(root, 256, 240);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Q-Learning Mario"); //$NON-NLS-1$
			
			Canvas canvas = new Canvas(256, 240);
			root.getChildren().add(canvas);
			
			GraphicsContext gc = canvas.getGraphicsContext2D();
			
			World world = new World();
			
			MarioGUI gui = new MarioGUI(gc);
			world.addObserver(gui);
			gui.start();
			
	        ExecutorService executor = Executors.newFixedThreadPool(5);
			
			Scheduler scheduler = new Scheduler(world);
			world.addObserver(scheduler);
			executor.execute(scheduler);
			
			// ========== For testing purpose ================
			Entity block;
			for (int i = 0; i < 16; i++) {
				block = new Block(BlockType.GroundRock);
				block.setLocation(new Point2D(i, 14));
				world.addEntity(block);
			}
			
			MarioBody mario = new MarioBody();
			mario.setLocation(new Point2D(1, 0));
			mario.heal(1);
			world.addEntity(mario);
			
			Goomba goomba = new Goomba();
			goomba.setLocation(new Point2D(13, 1));
			world.addEntity(goomba);
			// ================================================
			
			executor.shutdown();
			
			primaryStage.show();
			
			primaryStage.setOnCloseRequest(
				new EventHandler<WindowEvent>() {
			          @Override
			          public void handle(WindowEvent we) {
			              System.out.println("Stage is closing"); //$NON-NLS-1$
			              scheduler.stop();
			          }
			    }
			);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
