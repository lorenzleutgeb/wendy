package at.ac.tuwien.student.e1127842.wendy.controller;

import at.ac.tuwien.student.e1127842.wendy.Main;
import at.ac.tuwien.student.e1127842.wendy.control.BooleanCell;
import at.ac.tuwien.student.e1127842.wendy.domain.Box;
import at.ac.tuwien.student.e1127842.wendy.domain.Reservation;
import at.ac.tuwien.student.e1127842.wendy.service.BoxService;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class BoxTabController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BoxTabController.class);

	private final BoxService boxService;
	private final ApplicationContext applicationContext;

	@FXML
	private TableView<Box> boxesTableView;

	@FXML
	private TableColumn<Box, Boolean> windowColumn;

	@FXML
	private Slider filterPriceMin;

	@FXML
	private Slider filterPriceMax;

	@FXML
	private TextField filterPriceMinBox;

	@FXML
	private TextField filterPriceMaxBox;

	@FXML
	private ChoiceBox<String> filterWindow;

	@FXML
	private TextField filterName;

	public BoxTabController(BoxService boxService, ApplicationContext applicationContext) {
		this.boxService = boxService;
		this.applicationContext = applicationContext;
	}

	@FXML
	public void initialize() throws Exception{
		LOGGER.info("starting to initialize MainFrameController");

		filterPriceMax.valueProperty().addListener((observable, oldValue, newValue) -> {
			maxSizeSliderChanged();
		});

		filterPriceMin.valueProperty().addListener((observable, oldValue, newValue) -> {
			minSizeSliderChanged();
		});

		ObservableList<TableColumn<Box, ?>> columns = boxesTableView.getColumns();
		columns.get(0).setCellValueFactory(new PropertyValueFactory<>("name"));

		windowColumn.setCellValueFactory(new PropertyValueFactory<>("window"));
		windowColumn.setCellFactory(p -> new BooleanCell());

		boxesTableView.setItems(FXCollections.observableArrayList(boxService.getBoxen()));
		LOGGER.info("filled Box Table");

		LOGGER.info("initializing Context Menu for Boxen");
		boxesTableView.setRowFactory(
			table -> {
				final TableRow<Box> row = new TableRow<>();
				final ContextMenu rowMenu = new ContextMenu();

				final MenuItem editItem = new MenuItem("Edit");
				editItem.setOnAction(event -> {
					openBoxDialog(row.getItem());
				});

				final MenuItem imageItem = new MenuItem("Show Image");
				imageItem.setOnAction(event -> {
					ImageController controller = applicationContext.getBean(ImageController.class);
					controller.setBox(row.getItem());
					Main.stage(new Stage(), "views/BoxImage.fxml", "Image of Box: " + row.getItem().getName(), 500, 500, controller).showAndWait();
				});

				final MenuItem removeItem = new MenuItem("Delete");
				removeItem.setOnAction(event -> {
					BoxDeletionConfirmationController controller = applicationContext.getBean(BoxDeletionConfirmationController.class);
					controller.setBox(row.getItem());
					Main.stage(new Stage(), "views/BoxDeletionConfirmation.fxml", "Delete?", 470, 150, controller).showAndWait();
				});
				rowMenu.getItems().addAll(editItem, imageItem, removeItem);

				// only display context menu for non-null items:
				row.contextMenuProperty().bind(
					Bindings.when(Bindings.isNotNull(row.itemProperty()))
						.then(rowMenu)
						.otherwise((ContextMenu)null));

				return row;
			});
	}

	@FXML
	private void onClickNewBox() {
		openBoxDialog(null);
	}

	@FXML
	private void minPriceTextChanged() {
		filterPriceMin.setValue(Double.valueOf(filterPriceMinBox.getText()));
		filter();
	}

	@FXML
	private void maxPriceTextChanged() {
		filterPriceMax.setValue(Double.valueOf(filterPriceMaxBox.getText()));
		filter();
	}

	@FXML
	private void onFilter() {
		filter();
	}

	@FXML
	private void minSizeSliderChanged() {
		filterPriceMinBox.setText(String.valueOf(filterPriceMin.getValue()));
		filter();
	}

	@FXML
	private void maxSizeSliderChanged() {
		filterPriceMaxBox.setText(String.valueOf(filterPriceMax.getValue()));
		filter();
	}

	private void filter() {
		BigDecimal minSize = new BigDecimal(filterPriceMin.getValue());
		BigDecimal maxSize = new BigDecimal(filterPriceMax.getValue());

		String name = filterName.getText();

		List<Box> boxes;

		// TODO: Eliminate magic constants (Optional<Boolean>)
		if (!filterWindow.getValue().equals("n/a")) {
			boxes = boxService.findAll(minSize, maxSize, name, filterWindow.getValue().equals("yes"));
		} else {
			boxes = boxService.findAll(minSize, maxSize, name);
		}

		boxesTableView.setItems(FXCollections.observableArrayList(boxes));
	}

	private void openBoxDialog(Box box) {
		String title = box == null ? "New Box" : "Edit Box";

		Stage stage = new Stage();
		BoxController controller = applicationContext.getBean(BoxController.class);
		controller.setBox(box);
		controller.setStage(stage);
		Main.stage(stage, "views/Box.fxml", title, 424, 559, controller).showAndWait();
	}
}
