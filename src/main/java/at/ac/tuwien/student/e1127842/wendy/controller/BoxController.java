package at.ac.tuwien.student.e1127842.wendy.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;

import at.ac.tuwien.student.e1127842.wendy.domain.Box;
import at.ac.tuwien.student.e1127842.wendy.service.BoxService;
import com.google.common.io.ByteStreams;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

import javafx.stage.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Controller zur Anzeige des Formulars zum erstellen bzw. updaten einer Box
 *
 */
@Controller
public class BoxController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BoxController.class);

	private final BoxService boxService;

	@FXML
	private TextField name;

	@FXML
	private TextField tagespreis;

	@FXML
	private TextField groesse;

	@FXML
	private CheckBox fenster;

	@FXML
	private Label errorName;

	@FXML
	private Label errorTagespreis;

	@FXML
	private Label errorFoto;

	@FXML
	private Label errorGroesse;

	@FXML
	private Button openFoto; 

	private Stage stage; 
	private boolean okClicked; 

	private Box box;

	@FXML
	private AnchorPane ap;

	@FXML
	private ImageView imageView;

	@Autowired
	public BoxController(BoxService boxService) {
		this.boxService = boxService;
	}

	/**
	 * initialisiert den BoxFrameController
	 * @throws Exception
	 */
	public void initialize() throws Exception{
		LOGGER.info("initializing BoxFrameController");
		okClicked = false;

		if (box == null) {
			return;
		}

		name.setText(box.getName());
		tagespreis.setText(box.getPrice().toString());
		// TODO: Do something with photo.
		//foto.setText(b.getPhoto());
		groesse.setText(String.valueOf(box.getSize()));
		fenster.setSelected(box.isWindow());

		if (box.getPhoto() != null && box.getPhoto().length != 0) {
			imageView.setImage(new Image(new ByteArrayInputStream(box.getPhoto())));
		}

		/*openFoto.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent e){
				FileChooser fileChooser = new FileChooser();
				File file = fileChooser.showOpenDialog(stage);
				if(file != null){
					foto.setText(file.getAbsolutePath());
				}
			}
		});*/
	}

	public void setStage(Stage stage){
		this.stage = stage;
	}

	/**
	 * setzt die Anzeige wenn eine Box bearbeitet wird
	 * @param box
	 */
	public void setBox(Box box) {
		if (box == null) {
			LOGGER.warn("Box passed is null!");
		}
		this.box = box;

	}

	/**
	 * validiert alle Eingabefelder und setzt eventuelle Fehlermeldungen am GUI
	 * @return true, wenn alle Eingaben korrekt, false wenn nicht
	 */
	private boolean validate() {
		boolean valid = true;
		if (name.getText().isEmpty() || name.getText() == null) {
			errorName.setText("Name darf nicht frei bleiben.");
			valid = false;
		} else {
			errorName.setText("");
		}
		try {
			if (tagespreis.getText().isEmpty() || tagespreis.getText() == null) {
				errorTagespreis.setText("Tagespreis darf nicht frei bleiben.");
				valid = false;
			} else if (Double.valueOf(tagespreis.getText()) <= 0) {
				errorTagespreis.setText("Preis darf nicht 0 oder negativ sein.");
				valid = false;
			} else {
				errorTagespreis.setText("");
			}
		} catch(NumberFormatException e) {
			errorTagespreis.setText("Ungueltige Zahl, Format: 00.00");
		}
		try {
			if (groesse.getText().isEmpty() || groesse.getText() == null) {
				errorGroesse.setText("Groesse darf nicht frei bleiben.");
				valid = false;
			} else if (Double.valueOf(groesse.getText()) <= 0) {
				errorGroesse.setText("Groesse darf nicht 0 oder negativ sein.");
				valid = false;
			} else {
				errorGroesse.setText("");
			}
		} catch(NumberFormatException e) {
			errorGroesse.setText("Groesse muss eine gueltige Zahl darstellen.");
			valid = false;
		}

		/*if(foto.getText().isEmpty() || foto.getText() == null){
			errorFoto.setText("Pfad des Fotos muss angegeben werden.");
			ret = false; 
		}else{
			File image = new File(foto.getText());
			if(image.exists()){
				errorFoto.setText("");
				ret = true; 
			}else{
				errorFoto.setText("Pfad des Fotos muss gueltig sein.");
				ret = false; 
			}
		}*/
		return valid;
	}

	/**
	 * Action fuer den SpeicherButton
	 * Legt eine neue Box mit den Eingaben an bzw. updatet sie, sofern sie korrekt sind
	 */
	@FXML
	private void onSpeichernClicked() {
		LOGGER.info("speicherButtonClicked");
		if (validate()) {
			box.setName(name.getText().trim());
			box.setPrice(new BigDecimal(tagespreis.getText()));
			// TODO: Handle Photo
			//box.setPhoto(new ByteArrayInputStream(new foto.getText().trim()));
			box.setWindow(fenster.isSelected());
			box.setSize(Double.valueOf(groesse.getText()));
			boxService.updateBox(box);
			stage.close();
		}
	}

	@FXML
	private void onPhotoButtonClicked() {
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

	/**
	 * Cancelt das Anlegen einer neuen Box.
	 */
	@FXML
	private void onCancelClicked() {
		LOGGER.info("cancelButtonClicked");
		stage.close(); 
	}
}
