package at.ac.tuwien.student.e1127842.wendy;

import at.ac.tuwien.student.e1127842.wendy.service.SetupService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Main extends Application {
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	private static URL resource(String name) throws IOException {
		LOGGER.info("Attempting to access resource named " + name);
		URL url = Main.class.getClassLoader().getResource(name);

		if (url != null) {
			return url;
		}
		throw new IOException("Resource not found " + name);
	}

	public static void show(Stage stage, String name, String title, int width, int height, ApplicationContext context) {
		show(stage, name, title, width, height, context, o -> {});
	}

	public static void show(Stage stage, String name, String title, int width, int height, ApplicationContext context, Consumer<Object> intialization) {
		FXMLLoader loader = null;
		Parent parent = null;
		try {
			loader = loader(resource(name), context);
			parent = loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		intialization.accept(loader.getController());
		stage.setTitle(title);
		stage.setScene(new Scene(parent, width, height));
		stage.show();
	}

	public static void show(Stage stage, String name, String title, int width, int height, Object controller) {
		stage(stage, name, title, width, height, controller).show();
	}

	public static Stage stage(Stage stage, String name, String title, int width, int height, Object controller) {
		FXMLLoader loader = null;
		Parent parent = null;
		try {
			loader = new FXMLLoader(resource(name));
			loader.setController(controller);
			parent = loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		stage.setTitle(title);
		stage.setScene(new Scene(parent, width, height));
		return stage;
	}

	@Override
	public void start(Stage stage) throws Exception {
		ApplicationContext context = new AnnotationConfigApplicationContext("at.ac.tuwien.student.e1127842.wendy");

		SetupService s = context.getBean(SetupService.class);
		s.setup();
		LOGGER.debug("Starting!");

		show(stage, "views/Main.fxml", "Wendy's Pferdepension", 800, 500, context);
	}

	public static FXMLLoader loader(URL location, ApplicationContext context) throws IOException {
		return new FXMLLoader(location, null, new JavaFXBuilderFactory(), context::getBean);
	}

	public static void main(String[] args) {
		Application.launch(Main.class, args);
	}
}
