package at.ac.tuwien.student.e1127842.wendy.controller;

import at.ac.tuwien.student.e1127842.wendy.Constants;
import at.ac.tuwien.student.e1127842.wendy.Main;
import at.ac.tuwien.student.e1127842.wendy.domain.Reservation;
import at.ac.tuwien.student.e1127842.wendy.domain.ReservationDetail;
import at.ac.tuwien.student.e1127842.wendy.service.ReservationService;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Controller
public class ReservationTabController {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT).withZone(Constants.TIMEZONE);
	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationTabController.class);

	private final ReservationService reservationService;
	private final ApplicationContext applicationContext;

	@FXML
	private TableView<Reservation> reservationTableView;

	@FXML
	private ObservableList<Reservation> dataRes;

	@FXML
	private TableColumn<Reservation, String> startColumn;

	@FXML
	private TableColumn<Reservation, String> endColumn;

	@FXML
	private TableColumn<Reservation, String> customerColumn;

	@FXML
	private TableColumn<Reservation, String> detailColumn;

	@FXML
	private DatePicker startPicker;

	@FXML
	private DatePicker endPicker;

	@FXML
	private TextField customerFilter;

	public ReservationTabController(ReservationService reservationService, ApplicationContext applicationContext) {
		this.reservationService = reservationService;
		this.applicationContext = applicationContext;
	}

	@FXML
	public void initialize() throws Exception {
		LOGGER.info("starting to initialize MainFrameController");

		LOGGER.info("filled Box Table");

		startPicker.setValue(LocalDate.now().minusMonths(2));
		endPicker.setValue(LocalDate.now().plusMonths(2));

		Callback<TableColumn.CellDataFeatures<Reservation, String>, ObservableValue<String>> dateFormat = cdfs -> new ReadOnlyObjectWrapper<>(cdfs.getValue().getCustomer().getName());

		customerColumn.setCellValueFactory(cdfs -> new ReadOnlyObjectWrapper<>(cdfs.getValue().getCustomer().getName()));
		startColumn.setCellValueFactory(cdfs -> new ReadOnlyObjectWrapper<>(FORMATTER.format(cdfs.getValue().getStart())));
		endColumn.setCellValueFactory(cdfs -> new ReadOnlyObjectWrapper<>(FORMATTER.format(cdfs.getValue().getEnd())));
		detailColumn.setCellValueFactory(cdfs -> new ReadOnlyObjectWrapper<>(cdfs.getValue().getDetails().stream().map(ReservationDetail::toString).collect(Collectors.joining(", "))));

		//restable
		dataRes = FXCollections.observableArrayList(reservationService.findAll());

		reservationTableView.setItems(dataRes);
		LOGGER.info("filled Reservierung Table");

		LOGGER.info("initializing Context Menu for Reservierungen");
		reservationTableView.setRowFactory(
			table -> {
				final TableRow<Reservation> row = new TableRow<>();
				final ContextMenu rowMenu = new ContextMenu();
				MenuItem showItem = new MenuItem("Show");
				showItem.setOnAction(event -> {
					ReservationReadController controller = new ReservationReadController();
					controller.setReservation(row.getItem());
					Main.stage(new Stage(), "views/ReservationRead.fxml", "Reservation " + row.getItem().getId(), 600, 400, controller).showAndWait();
				});
				MenuItem billItem = new MenuItem("Show Receipt");
				billItem.setOnAction(event -> {
					ReceiptController rfc = applicationContext.getBean(ReceiptController.class);
					rfc.setReservation(row.getItem());
					Main.show(new Stage(), "views/Receipt.fxml", "Receipt for " + row.getId(), 600, 400, rfc);
				});
				MenuItem deleteItem = new MenuItem("Delete");
				deleteItem.setOnAction(event -> {
					table.getItems().remove(row.getItem());
					reservationService.delete(row.getItem());
				});

				rowMenu.getItems().addAll(showItem, billItem, deleteItem);

				// only display context menu for non-null items:
				row.contextMenuProperty().bind(
						Bindings.when(Bindings.isNotNull(row.itemProperty()))
						.then(rowMenu)
						.otherwise((ContextMenu)null));
				return row;
			});

		LOGGER.info("initialized MainFrameController");
	}

	private void filter() {
		Instant start = startPicker.getValue().atStartOfDay(Constants.TIMEZONE.getRules().getOffset(Instant.now())).toInstant();
		Instant end = endPicker.getValue().atStartOfDay(Constants.TIMEZONE.getRules().getOffset(Instant.now())).toInstant();

		String customer = customerFilter.getText();

		reservationTableView.setItems(FXCollections.observableArrayList(reservationService.find(customer, start, end)));
	}

	@FXML
	private void onFilter() {
		filter();
	}

	private void openReservationDialog(Reservation reservation) {
		String title = reservation == null ? "New Reservation" : "Edit Reservation";
		Stage stage = new Stage();
		ReservationReadController controller = applicationContext.getBean(ReservationReadController.class);
		controller.setReservation(reservation);
		//controller.setStage(stage);
		Stage x = Main.stage(stage, "views/ReservationRead.fxml", title, 624, 559, controller);
		x.showAndWait();
		filter();
	}

	/**
	 * Action fuer eine neue Reservierung.
	 * oeffnet das Frame mit dem Formular fuer eine neue Reservierung
	 */
	@FXML
	private void onNewReservationClicked() {
		Stage stage = new Stage();
		ReservationCreationController controller = applicationContext.getBean(ReservationCreationController.class);
		controller.setStage(stage);
		Main.stage(stage, "views/ReservationCreation.fxml", "New Reservation", 700, 400, controller).showAndWait();
		filter();
	}
}