package at.ac.tuwien.student.e1127842.wendy.controller;

import at.ac.tuwien.student.e1127842.wendy.Constants;
import at.ac.tuwien.student.e1127842.wendy.domain.Reservation;
import at.ac.tuwien.student.e1127842.wendy.domain.ReservationDetail;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ReservationReadController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReservationReadController.class);

	@FXML
	private TableView<ReservationDetail> tableView;

	@FXML
	private ObservableList<TableModel> data;

	private Reservation reservation;

	@FXML
	private TableColumn<ReservationDetail, String> boxColumn;

	@FXML
	private DatePicker startPicker;

	@FXML
	private DatePicker endPicker;

	@FXML
	private TextField customerField;

	@FXML
	public void initialize() {
		boxColumn.setCellValueFactory(cdfs -> new ReadOnlyObjectWrapper<>(cdfs.getValue().getBox().getName()));

		// TODO: Makes pickers grey out, which is not nice.
		startPicker.setDisable(true);
		endPicker.setDisable(true);

		startPicker.setValue(LocalDateTime.ofInstant(reservation.getStart(), Constants.TIMEZONE).toLocalDate());
		endPicker.setValue(LocalDateTime.ofInstant(reservation.getEnd(), Constants.TIMEZONE).toLocalDate());

		customerField.setText(reservation.getCustomer().getName());

		tableView.setItems(FXCollections.observableArrayList(reservation.getDetails()));
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;

		if (this.reservation == null) {
			LOGGER.warn("Received null reservation.");
		}
	}
}
