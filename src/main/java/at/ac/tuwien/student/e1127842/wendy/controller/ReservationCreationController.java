package at.ac.tuwien.student.e1127842.wendy.controller;

import at.ac.tuwien.student.e1127842.wendy.Constants;
import at.ac.tuwien.student.e1127842.wendy.domain.Box;
import at.ac.tuwien.student.e1127842.wendy.domain.Customer;
import at.ac.tuwien.student.e1127842.wendy.domain.Reservation;
import at.ac.tuwien.student.e1127842.wendy.domain.ReservationDetail;
import at.ac.tuwien.student.e1127842.wendy.service.BoxService;
import at.ac.tuwien.student.e1127842.wendy.service.ReservationService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.time.LocalDate;

@Controller
public class ReservationCreationController {
	@FXML
	private Stage stage;

	@FXML
	private TableView<Box> availableBoxesTable;

	@FXML
	private DatePicker startPicker;

	@FXML
	private DatePicker endPicker;

	@FXML
	private TextField customerNameField;

	@FXML
	private TableView<ReservationDetail> tableViewChosen;

	@FXML
	private TableColumn<ReservationDetail, String> chosenBoxColumn;

	@FXML
	private TableColumn<ReservationDetail, String> chosenPriceColumn;

	@FXML
	private TableColumn<ReservationDetail, String> chosenHorseNameColumn;

	private final ReservationService reservationService;
	private final BoxService boxService;

	private Reservation reservation;

	private static final Logger log = LoggerFactory.getLogger(ReservationCreationController.class);

	@Autowired
	public ReservationCreationController(ReservationService reservationService, BoxService boxService) {
		this.reservationService = reservationService;
		this.boxService = boxService;
	}

	public void setStage(Stage stage){
		this.stage = stage; 
	}

	/**
	 * initalisiert den Controller und fuegt bei den Datumsfeldern einen Listener hinzu
	 * @throws Exception
	 */
	@FXML
	public void initialize() throws Exception{
		startPicker.setValue(LocalDate.now().minusMonths(2));
		endPicker.setValue(LocalDate.now().plusMonths(2));

		chosenBoxColumn.setCellValueFactory(cdfs -> new ReadOnlyObjectWrapper<>(cdfs.getValue().getBox().getName()));
		chosenPriceColumn.setCellValueFactory(cdfs -> new ReadOnlyObjectWrapper<>(cdfs.getValue().getPrice().toString()));
		chosenHorseNameColumn.setCellValueFactory(cdfs -> new ReadOnlyObjectWrapper<>(cdfs.getValue().getHorseName()));

		refreshTable();
	}

	/**
	 * setzt beide tabellen zurueck
	 */
	private void refreshTable() {
		log.info("refreshing available and chosen table");

		Instant start = startPicker.getValue().atStartOfDay(Constants.TIMEZONE.getRules().getOffset(Instant.now())).toInstant();
		Instant end = endPicker.getValue().atStartOfDay(Constants.TIMEZONE.getRules().getOffset(Instant.now())).toInstant();

		availableBoxesTable.setItems(FXCollections.observableArrayList(reservationService.getFreeBoxes(start, end)));
	}

	/**
	 * fuegt die ausgewaehlte Box der availableTable zur chosenTable hinzu
	 */
	@FXML 
	private void add() {
		final Stage dialog = new Stage(); 
		dialog.setTitle("Enter Horse Name");
		TextField horseNameField = new TextField();
		Button submitButton = new Button("Submit");
		submitButton.setDefaultButton(true);
		submitButton.setOnAction(e -> dialog.close());
		horseNameField.setMinHeight(TextField.USE_PREF_SIZE);

		final VBox layout = new VBox(50);
		layout.setAlignment(Pos.CENTER_RIGHT);
		layout.getChildren().setAll(horseNameField, submitButton);
		dialog.setScene(new Scene(layout));
		dialog.showAndWait();

		Box box = availableBoxesTable.getSelectionModel().getSelectedItem();
		availableBoxesTable.getItems().remove(box);
		tableViewChosen.getItems().add(new ReservationDetail(box, reservation, horseNameField.getText()));
	}

	/**
	 * speichert eine neue Reservierung sofern, die Eingaben gueltig sind
	 */
	@FXML
	private void onSave() {
		log.info("speicherButtonClicked");

		Instant start = startPicker.getValue().atStartOfDay(Constants.TIMEZONE.getRules().getOffset(Instant.now())).toInstant();
		Instant end = endPicker.getValue().atStartOfDay(Constants.TIMEZONE.getRules().getOffset(Instant.now())).toInstant();

		// TODO: Clean up.
		Customer customer;
		if (customerNameField.getText().contains(" ")) {
			String[] customerNames = customerNameField.getText().split(" ");
			customer = new Customer(customerNames[0], customerNames[1]);
		} else {
			customer = new Customer(customerNameField.getText(), "");
		}


		reservationService.create(customer, start, end, tableViewChosen.getItems());
		stage.close();
	}

	/**
	 * cancelt das Anlegen einer neuen Reservierung
	 */
	@FXML
	private void onCancelClicked() {
		log.info("cancelButtonClicked");
		stage.close(); 
	}
}
