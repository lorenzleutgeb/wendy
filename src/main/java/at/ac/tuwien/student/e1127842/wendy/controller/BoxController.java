package at.ac.tuwien.student.e1127842.wendy.controller;

import at.ac.tuwien.student.e1127842.wendy.domain.Box;
import at.ac.tuwien.student.e1127842.wendy.service.BoxService;
import com.google.common.io.ByteStreams;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;

@Controller
public class BoxController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BoxController.class);

	private final BoxService boxService;

	@FXML
	private TextField nameField;

	@FXML
	private TextField priceField;

	@FXML
	private TextField sizeField;

	@FXML
	private CheckBox windowCheckBox;

	@FXML
	private Label errorName;

	@FXML
	private Label errorPrice;

	@FXML
	private Label errorPhoto;

	@FXML
	private Label errorSize;

	@FXML
	private Button photoButton;

	private Stage stage; 

	private Box box;

	@FXML
	private AnchorPane ap;

	@FXML
	private ImageView imageView;

	@Autowired
	public BoxController(BoxService boxService) {
		this.boxService = boxService;
	}

	public void initialize() throws Exception{
		LOGGER.info("initializing BoxFrameController");

		if (box == null) {
			return;
		}

		nameField.setText(box.getName());
		priceField.setText(box.getPrice().toString());
		sizeField.setText(String.valueOf(box.getSize()));
		windowCheckBox.setSelected(box.isWindow());

		if (box.getPhoto() != null && box.getPhoto().length != 0) {
			imageView.setImage(new Image(new ByteArrayInputStream(box.getPhoto())));
		}
	}

	public void setStage(Stage stage){
		this.stage = stage;
	}

	public void setBox(Box box) {
		if (box == null) {
			LOGGER.warn("Box passed is null!");
		}
		this.box = box;
	}

	private boolean validate() {
		boolean valid = true;
		if (nameField.getText().isEmpty() || nameField.getText() == null) {
			errorName.setText("Name darf nicht frei bleiben.");
			valid = false;
		} else {
			errorName.setText("");
		}
		try {
			if (priceField.getText().isEmpty() || priceField.getText() == null) {
				errorPrice.setText("Tagespreis darf nicht frei bleiben.");
				valid = false;
			} else if (Double.valueOf(priceField.getText()) <= 0) {
				errorPrice.setText("Preis darf nicht 0 oder negativ sein.");
				valid = false;
			} else {
				errorPrice.setText("");
			}
		} catch(NumberFormatException e) {
			errorPrice.setText("Ungueltige Zahl, Format: 00.00");
		}
		try {
			if (sizeField.getText().isEmpty() || sizeField.getText() == null) {
				errorSize.setText("Groesse darf nicht frei bleiben.");
				valid = false;
			} else if (Double.valueOf(sizeField.getText()) <= 0) {
				errorSize.setText("Groesse darf nicht 0 oder negativ sein.");
				valid = false;
			} else {
				errorSize.setText("");
			}
		} catch(NumberFormatException e) {
			errorSize.setText("Groesse muss eine gueltige Zahl darstellen.");
			valid = false;
		}
		return valid;
	}

	@FXML
	private void onSaveClicked() {
		if (validate()) {
			box.setName(nameField.getText().trim());
			box.setPrice(new BigDecimal(priceField.getText()));
			box.setWindow(windowCheckBox.isSelected());
			box.setSize(Double.valueOf(sizeField.getText()));
			boxService.updateBox(box);
			stage.close();
		}
	}

	@FXML
	private void onPhotoClicked() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Box Image");
		fileChooser.getExtensionFilters().addAll(
			new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
		);

		File selectedFile = fileChooser.showOpenDialog(ap.getScene().getWindow());
		if (selectedFile == null) {
			LOGGER.warn("Selected file is null!");
			return;
		}
		LOGGER.info("Selected file " + selectedFile.getAbsolutePath());

		try {
			if (box == null) {
				box = new Box();
			}
			box.setPhoto(ByteStreams.toByteArray(new FileInputStream(selectedFile)));
			imageView.setImage(new Image(new FileInputStream(selectedFile)));
		} catch (IOException e) {
			LOGGER.error("File not found! " + selectedFile.getAbsolutePath());
		}
	}

	@FXML
	private void onCancelClicked() {
		stage.close(); 
	}
}
