package at.ac.tuwien.student.e1127842.wendy.controller;


import at.ac.tuwien.student.e1127842.wendy.Constants;
import at.ac.tuwien.student.e1127842.wendy.domain.Reservation;
import at.ac.tuwien.student.e1127842.wendy.domain.ReservationDetail;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Controller zur Anzeige der Rechnung fuer eine durch ContextMenu ausgewaehlte Reservierung
 * Berechnet die Gesamtsumme fuer den Aufenthalt
 */
@Controller
public class ReceiptController {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT).withZone(Constants.TIMEZONE);
	private static final Logger LOGGER = LoggerFactory.getLogger(ReceiptController.class);

	@FXML
	private Label startLabel;

	@FXML
	private Label endLabel;

	@FXML
	private Label customerNameLabel;

	@FXML
	private Label receiptIdLabel;

	@FXML
	private Label sumLabel;
	
	@FXML
	private TableView<ReservationDetail> tableView;

	@FXML
	private TableColumn<ReservationDetail, String> boxColumn;

	@FXML
	private TableColumn<ReservationDetail, String> priceColumn;

	@FXML
	private TableColumn<ReservationDetail, String> horseNameColumn;

	private Reservation reservation;

	@FXML
	public void initialize() {
		boxColumn.setCellValueFactory(cdfs -> new ReadOnlyObjectWrapper<>(cdfs.getValue().getBox().getName()));
		priceColumn.setCellValueFactory(cdfs -> new ReadOnlyObjectWrapper<>(cdfs.getValue().getPrice().toString()));
		horseNameColumn.setCellValueFactory(cdfs -> new ReadOnlyObjectWrapper<>(cdfs.getValue().getHorseName()));

		startLabel.setText(FORMATTER.format(reservation.getStart()));
		endLabel.setText(FORMATTER.format(reservation.getEnd()));
		customerNameLabel.setText(reservation.getCustomer().getName());
		receiptIdLabel.setText(String.valueOf(reservation.getId()));

		Duration duration = Duration.between(reservation.getStart(), reservation.getEnd());

		tableView.setItems(FXCollections.observableArrayList(reservation.getDetails()));

		BigDecimal days = new BigDecimal(duration.toDays());
		Optional<BigDecimal> sum = reservation.getDetails().stream().map(rd -> rd.getPrice().multiply(days)).reduce(BigDecimal::add);

		if (!sum.isPresent()) {
			LOGGER.error("Could not calculate sum!");
			return;
		}

		sumLabel.setText(sum.get().toString());
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
}
