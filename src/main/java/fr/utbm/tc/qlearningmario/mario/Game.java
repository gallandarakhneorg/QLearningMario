package fr.utbm.tc.qlearningmario.mario;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import fr.utbm.tc.qlearningmario.mario.entity.Entity;
import fr.utbm.tc.qlearningmario.mario.entity.World;
import fr.utbm.tc.qlearningmario.mario.ui.MarioGUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.arakhne.afc.vmutil.Resources;
import org.arakhne.afc.vmutil.locale.Locale;

public class Game extends Application {
	
	private final Logger log = Logger.getLogger(Game.class.getName());
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			
			Scene scene = new Scene(root, 256, 240);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle(Locale.getString(getClass(), "frame.title")); //$NON-NLS-1$
			
			Canvas canvas = new Canvas(256, 240);
			root.getChildren().add(canvas);
			
			GraphicsContext gc = canvas.getGraphicsContext2D();
			
			World world = new World();
			
			MarioGUI gui = new MarioGUI(gc);
			world.addWorldListener(gui);
			gui.start();
			
	        ExecutorService executor = Executors.newFixedThreadPool(5);
			
			Scheduler scheduler = new Scheduler(world);
			world.addWorldListener(scheduler);
			
			// Loading a level.
			URL resource = Resources.getResource(getClass(), "fr/utbm/tc/qlearningmario/levels/levelA.png"); //$NON-NLS-1$
			assert (resource != null);
			for (Entity<?> entity : LevelLoader.loadLevelFromImage(resource)) {
				world.addEntity(entity);
			}
			
			// Run the scheduler.
			executor.execute(scheduler);
			executor.shutdown();
			
			primaryStage.show();

			primaryStage.setOnCloseRequest(
					(WindowEvent we) -> {
						this.log.info(Locale.getString(Game.this.getClass(), "closing.stage")); //$NON-NLS-1$
						scheduler.stop();
					});
		} catch(Exception e) {
			this.log.severe(e.toString());
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
