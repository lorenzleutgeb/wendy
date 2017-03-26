package at.ac.tuwien.student.e1127842.wendy.controller;

import at.ac.tuwien.student.e1127842.wendy.domain.Box;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;

@Controller
public class ImageController {
	@FXML
	private ImageView imageView;

	private Box box;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

	@FXML
	public void initialize() {
		if (box == null) {
			LOGGER.error("Missing box to show image of.");
		}
		imageView.setImage(new Image(new ByteArrayInputStream(box.getPhoto())));
	}

	public void setBox(Box box) {
		this.box = box;
	}
}
