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

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Main extends Application {
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	private URL resource(String name) throws IOException {
		URL url = getClass().getClassLoader().getResource(name);
		if (url != null) {
			return url;
		}
		throw new IOException("Resource not found " + name);
	}

	private void show(Stage stage, String name, String title, int width, int height, ApplicationContext context) throws IOException {
		Parent parent = load(resource(name), context);
		stage.setTitle(title);
		stage.setScene(new Scene(parent, width, height));
		stage.show();
	}

	@Override
	public void start(Stage stage) throws Exception {
		ApplicationContext context = new AnnotationConfigApplicationContext("at.ac.tuwien.student.e1127842.wendy");

		SetupService s = context.getBean(SetupService.class);
		s.setup();
		LOGGER.debug("Starting!");

		show(stage, "views/Main.fxml", "Wendy's Pferdepension", 300, 275, context);
	}

	public Parent load(URL location, ApplicationContext context) throws IOException {
		return FXMLLoader.load(location, null, new JavaFXBuilderFactory(), context::getBean);
	}

	public static void main(String[] args) {
		Application.launch(Main.class, args);
	}
}
