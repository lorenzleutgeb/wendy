package at.ac.tuwien.student.e1127842.wendy.controller;

import at.ac.tuwien.student.e1127842.wendy.domain.Box;
import at.ac.tuwien.student.e1127842.wendy.service.BoxService;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BoxDeletionConfirmationController {
	private static final Logger log = LoggerFactory.getLogger(BoxDeletionConfirmationController.class);

	private final BoxService boxService;

	private Box box;

	@FXML
	private GridPane root;

	@Autowired
	public BoxDeletionConfirmationController(BoxService boxService) {
		this.boxService = boxService;
	}

	public void setBox(Box box) {
		this.box = box;
	}

	@FXML
	private void onConfirm() {
		log.info("User confirmed deletion of box " + box.getId());
		boxService.delete(box);
		close();
	}

	@FXML
	private void onCancel() {
		log.info("User cancelled deletion of box " + box.getId());
		close();
	}

	private void close() {
		((Stage)root.getScene().getWindow()).close();
	}
}
