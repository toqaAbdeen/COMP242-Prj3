package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import application.AVLTree;
import application.Date;
import application.Martyr;
import application.TNode;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GUI extends Application {
	HopeDone<Date> hash = new HopeDone<Date>(11);
	MinHeap<ageMartyr> heap = new MinHeap<ageMartyr>(11);
	ageMartyr martyrAge[];
//	Martyr martyrs[];

//	heap.setComparator(AgeComparator);
//	Comparator<Martyr> age = new AgeComparator();
//	MinHeap<> heap2 = new MinHeap<>(11,age);

	Alert warningAlert = new Alert(AlertType.WARNING);
	Alert CONFIRMATIONAlert = new Alert(Alert.AlertType.CONFIRMATION);
	Alert exceptionAlert = new Alert(AlertType.ERROR);
	Alert infoAlert = new Alert(AlertType.INFORMATION);
	Date date;
	Date date2;
	private ComboBox<String> districtBox = new ComboBox<>();
	private ComboBox<String> locationBox = new ComboBox<>();

	private ComboBox<String> newDistrictBox = new ComboBox<>();
	private ComboBox<String> newLocationBox = new ComboBox<>();

	private List<DistrictLocations> districtLocationsList = new ArrayList<>();

	private static final int SCENE_WIDTH = 1000;
	private static final int SCENE_HEIGHT = 600;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		FileChooser chooser = new FileChooser();

		chooser.setTitle("Open File");
		chooser.setInitialDirectory(new File("C:\\"));
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("csv File", "*.csv"));

		File file = chooser.showOpenDialog(primaryStage);
		if (file != null) {
			fromFileToAVL(file);
			BorderPane mainScreen = new BorderPane();
			Button startMainScreen = new Button("Start");
			Label title = new Label("Martyrs of Palestine");
			mainScreen.setBottom(startMainScreen);
			mainScreen.setAlignment(startMainScreen, Pos.CENTER);
			mainScreen.setTop(title);
			mainScreen.setAlignment(title, Pos.TOP_CENTER);
			mainScreen.setPadding(new Insets(100, 250, 100, 350));

			/// second screen components
			DatePicker datePicker = new DatePicker();
			DatePicker newDatePickerFS = new DatePicker();

			Button insertDate = new Button("Insert");
			Button deleteDate = new Button("Delete");
			Button updateDate = new Button("Update");
			ComboBox<String> print = new ComboBox<String>();
			print.getItems().addAll("including the empty spots.", "excluding the empty spots.");
			print.setPromptText("Print the table");
			Button nextDate = new Button("Next");
			Button prevDate = new Button("Prev");
			Button clearFS = new Button("Clear");
			Button load = new Button("Load");
			TextArea textArea = new TextArea();
			textArea.setMinWidth(200);
			textArea.setMinHeight(150);
			textArea.setMaxWidth(400);
			textArea.setMaxHeight(300);
			insertDate.setOnAction(event -> {
				if (datePicker.getValue() == null) {
					warningAlert.setTitle("Warning about insearting for the date");
					warningAlert.setContentText(
							"To insert date you must select it from date piker ,make sure to select it.");
					warningAlert.show();
				} else {
					Date date = new Date(datePicker);
					System.out.println(hash.find(date) + "");
					if (hash.find(date) != null) {
						infoAlert.setTitle("The new date already exits");
						infoAlert.setContentText(date.toString() + " is already on the list");
						infoAlert.show();
					} else {
						hash.add(date);
						infoAlert.setTitle("Insert date");
						infoAlert.setContentText(date + " has been successfully added to the list");
						infoAlert.show();
					}
				}
			});

			deleteDate.setOnAction(event -> {
				if (datePicker.getValue() == null) {
					warningAlert.setTitle("Warning about deleting for the date");
					warningAlert.setContentText(
							"To delete date you must select it from date piker ,make sure to select it.");
					warningAlert.show();
				} else {
					Date date = new Date(datePicker);
					if (hash.find(date) == null) {
						infoAlert.setTitle("The date does not exits");
						infoAlert.setContentText(date + " is not he list");
						infoAlert.show();
					} else {
						CONFIRMATIONAlert.setTitle("Confirmation Dialog");
						CONFIRMATIONAlert.setHeaderText("Are you sure?");
						CONFIRMATIONAlert.setContentText("Do you want to proceed with the action?");
						CONFIRMATIONAlert.showAndWait().ifPresent(response -> {
							if (response == ButtonType.OK) {
								System.out.println("User clicked OK");
								hash.delete(date);
								System.out.println(hash.find(date));
								infoAlert.setTitle("Delete date");
								infoAlert.setContentText(date + "  has been successfully deleted from the list");
								infoAlert.show();
							} else {
								System.out.println("User clicked Cancel or closed the dialog");
							}
						});

					}
				}

			});

			updateDate.setOnAction(event -> {
				if (datePicker.getValue() == null || newDatePickerFS.getValue() == null) {
					warningAlert.setTitle("Warning about updating for the date");
					warningAlert.setContentText(
							"To update date you must select it from date piker ,make sure to select it.");
					warningAlert.show();
				} else {
					date = new Date(datePicker);
					date2 = new Date(newDatePickerFS);
					System.out.println(hash.find(date) + "");

					if (hash.find(date) != null && hash.find(date2) == null) {
						date = (Date) hash.find(date).getData();

						if (date == date2) {
							warningAlert.setTitle("The new Date is the same as the old date");
							warningAlert.setContentText(
									"Yor are updating the same date, make sure one of them has changed");
							warningAlert.show();
						} else if (date != date2) {

							CONFIRMATIONAlert.setTitle("Confirmation Dialog");
							CONFIRMATIONAlert.setHeaderText("Are you sure?");
							CONFIRMATIONAlert.setContentText("Do you want to proceed with the action?");
							CONFIRMATIONAlert.showAndWait().ifPresent(response -> {
								if (response == ButtonType.OK) {
									System.out.println("User clicked OK");

									AVLTree<Martyr> tree = date.getTree();
									System.out.println("Date: " + date);
									System.out.println("Date tree" + tree.toString());
									for (int i = 0; i < tree.size(); i++) {
										tree.iterativeTraverseInOrderStringQueue().setDate(date2.toString());
									}
									hash.delete(date);
									date2.setTree(tree);
									hash.add(date2);
									System.out.println("New Date: " + date2);
									System.out.println("New Date tree" + tree.toString());

									infoAlert.setTitle("Update Date");
									infoAlert.setContentText("The date of " + date + "  has been updated to " + date2);
									infoAlert.show();
								} else {
									System.out.println("User clicked Cancel or closed the dialog");
								}
							});

						}

					} else {
						warningAlert.setTitle("warning about updating dates");
						warningAlert.setContentText(
								"When updating a date's record, make sure that the first date already exits and the second date does not exits before.");
						warningAlert.show();
					}
				}
			});

			print.setOnAction(event -> {
				if (print.getValue() == "including the empty spots.")
					textArea.setText(hash.printTable(true));
				else {
					textArea.setText(hash.printTable(false));
				}
			});
			nextDate.setOnAction(event -> {
				Date date = hash.test();
				textArea.setText(date + "");
				if (hash.find(date) != null) {
					date = (Date) hash.find(date).getData();
					AVLTree<Martyr> tree = date.getTree();
					String string = tree.iterativeTraverseInOrderStringQueue().getDistrict();
					String stringLoc = tree.iterativeTraverseInOrderStringQueue().getLocation();

					textArea.setText(date + "\nMartyrs’ summary:-\nTotal: " + totalMartyrs(tree.getRoot())
							+ "\nAverage age: " + averageAge(tree.getRoot()) + "\nDistrict that has the maximum: "
							+ string + ", total: " + totalMartyrsInDistrict(tree.getRoot(), string)
							+ " martyr\nLocation that has the maximum martyrs: " + stringLoc + ", total: "
							+ totalMartyrsInLocation(tree.getRoot(), stringLoc));

				}
			});

			prevDate.setOnAction(event -> {
				Date date = hash.getPreviousData();
				textArea.setText(date + "");
				if (date != null) {
					if (hash.find(date) != null) {
						date = (Date) hash.find(date).getData();
						AVLTree<Martyr> tree = date.getTree();
						String string = tree.iterativeTraverseInOrderStringQueue().getDistrict();
						String stringLoc = tree.iterativeTraverseInOrderStringQueue().getLocation();

						textArea.setText(date + "\nMartyrs’ summary:-\nTotal: " + totalMartyrs(tree.getRoot())
								+ "\nAverage age: " + averageAge(tree.getRoot()) + "\nDistrict that has the maximum: "
								+ string + ", total: " + totalMartyrsInDistrict(tree.getRoot(), string)
								+ " martyr\nLocation that has the maximum martyrs: " + stringLoc + ", total:"
								+ totalMartyrsInLocation(tree.getRoot(), stringLoc));

					}
				}
			});

			GridPane gridPane1FS = new GridPane();
			gridPane1FS.add(insertDate, 0, 1);
			gridPane1FS.add(deleteDate, 1, 1);
			gridPane1FS.add(updateDate, 2, 1);
			gridPane1FS.add(datePicker, 3, 1);
			gridPane1FS.add(newDatePickerFS, 3, 2);

			gridPane1FS.add(clearFS, 5, 1);
			gridPane1FS.add(prevDate, 6, 1);
			gridPane1FS.add(nextDate, 7, 1);
			gridPane1FS.add(print, 8, 1);
			gridPane1FS.add(load, 9, 1);

			gridPane1FS.setHgap(10);
			gridPane1FS.setVgap(10);

			clearFS.setOnAction(event -> {
				if (!textArea.getText().isEmpty()) {
					textArea.setText("");
				}
			});
			BorderPane borderPane1SS = new BorderPane();
			borderPane1SS.setLeft(gridPane1FS);
			borderPane1SS.setCenter(textArea);
			borderPane1SS.setAlignment(textArea, Pos.CENTER_LEFT);
			borderPane1SS.setPadding(new Insets(15, 15, 50, 100));
			Scene secondScene = new Scene(borderPane1SS, SCENE_WIDTH, SCENE_HEIGHT);

			//////////////

			DatePicker datePicker2 = new DatePicker();
//			datePicker.setValue(LocalDate.now());
			TextField field = new TextField();
			field.setPromptText("Name");
			Button insertMartyr = new Button("Insert");
			Button deleteMartyr = new Button("Delete");
			Button updateMartyr = new Button("Update");
			Button printTree = new Button("Print Tree");
			Button sizeAndHeight = new Button("Size - Height");
			Button printByAge = new Button("Print The Martyrs In A Table Sorted By Age");
			TextArea textArea2 = new TextArea();
			textArea2.setMinWidth(200);
			textArea2.setMinHeight(150);
			textArea2.setMaxWidth(400);
			textArea2.setMaxHeight(300);
			ComboBox<Integer> age = new ComboBox<Integer>();
			age.setPromptText("Age");
			for (int i = 0; i <= 150; i++) {
				age.getItems().addAll(i);
			}

			RadioButton maleRadioButton = new RadioButton("Male");
			RadioButton femaleRadioButton = new RadioButton("Female");
			ToggleGroup genderToggleGroup = new ToggleGroup();
			maleRadioButton.setToggleGroup(genderToggleGroup);
			femaleRadioButton.setToggleGroup(genderToggleGroup);
			Button saveButton = new Button("Save to a new file");

			districtBox.setPromptText("District");
			locationBox.setPromptText("Location");

			districtBox.setOnAction(event -> {
				String selectedDistrict = districtBox.getValue();
				if (selectedDistrict != null) {
					locationBox.getItems().clear();
					for (DistrictLocations districtLocations : districtLocationsList) {
						if (districtLocations.getDistrict().equals(selectedDistrict)) {
							locationBox.getItems().add(districtLocations.getLocation());
						}
					}
				}
			});

			Button backDateSc = new Button("Back");
			GridPane gridPane2 = new GridPane();
			gridPane2.add(field, 1, 1);
			gridPane2.add(datePicker2, 2, 1);
			gridPane2.add(age, 3, 1);
			gridPane2.add(locationBox, 4, 1);
			gridPane2.add(districtBox, 5, 1);
			gridPane2.add(maleRadioButton, 6, 1);
			gridPane2.add(femaleRadioButton, 7, 1);
			gridPane2.add(insertMartyr, 0, 2);
			gridPane2.add(deleteMartyr, 0, 3);
			gridPane2.add(updateMartyr, 0, 4);
			gridPane2.add(printTree, 0, 5);
			gridPane2.add(sizeAndHeight, 0, 6);
			gridPane2.add(printByAge, 0, 7);
			gridPane2.add(textArea2, 0, 9);
			gridPane2.add(saveButton, 0, 10);

			gridPane2.setHgap(10);
			gridPane2.setVgap(10);
			genderToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
				RadioButton selectedRadioButton = (RadioButton) newValue;
				String selectedGender = selectedRadioButton.getText();
				System.out.println("Selected Gender: " + selectedGender);
			});

			insertMartyr.setOnAction(event -> {

				if (field.getText().isEmpty()) {
					warningAlert.setTitle("Warning about insearting for the martyr");
					warningAlert.setContentText("To insert  martyr you must add information,make sure to enter it.");
					warningAlert.show();
				} else {

					String part = field.getText();
					if (datePicker2.getValue() != null) {
						if (age.getValue() != null) {
							if (!locationBox.getValue().equals("Locations")) {
								if (!districtBox.getValue().equals("Districts")) {
									if (maleRadioButton.isSelected() || femaleRadioButton.isSelected()) {
										if (part.length() > 0) {
											Martyr martyr = new Martyr();
											Date date = new Date(datePicker2);

											if (hash.find(date) != null) {
												date = (Date) hash.find(date).getData();
												martyr.setName(part);
												martyr.setDate(datePicker2.getValue().toString());
												martyr.setAge(age.getValue());
												martyr.setLocation(locationBox.getValue());
												martyr.setDistrict(districtBox.getValue());
												if (maleRadioButton.isSelected())
													martyr.setGender('M');
												else
													martyr.setGender('F');
												if (date.getTree().find(martyr) == null) {
													date.getTree().insert(martyr);
													System.out.println(
															date.getTree().iterativeTraverseLevelOrderString());
													infoAlert.setTitle("Adding martyr");
													infoAlert.setContentText("The martyr " + martyr.getName()
															+ " has been added successfully.");
													infoAlert.show();

												} else {
													infoAlert.setTitle("The new martyr already exits");
													infoAlert.setContentText(
															martyr.toString() + " is already on the list");
													infoAlert.show();
												}
											}
										}
									} else {
										warningAlert.setTitle("Warning about inserting martyr");
										warningAlert.setContentText(
												"To add martyr you must select  the gender ,make sure to select  it.");
										warningAlert.show();
									}
								} else {
									warningAlert.setTitle("Warning about inserting martyr");
									warningAlert.setContentText(
											"To add martyr you must add the distict from box,make sure to enter it.");
									warningAlert.show();
								}
							} else {
								warningAlert.setTitle("Warning about inserting martyr");
								warningAlert.setContentText(
										"To add martyr you must add the location from box,make sure to enter it.");
								warningAlert.show();

							}
						} else {
							warningAlert.setTitle("Warning about inserting martyr");
							warningAlert
									.setContentText("To add martyr you must add age from box,make sure to enter it.");
							warningAlert.show();
						}

					} else {
						warningAlert.setTitle("Warning about inserting martyr");
						warningAlert.setContentText(
								"To add martyr you must add date of martyrdom from the calender,make sure to enter it.");
						warningAlert.show();
					}
				}

			});

			deleteMartyr.setOnAction(event -> {
				if (field.getText().isEmpty()) {
					warningAlert.setTitle("Warning about insearting for the martyr");
					warningAlert.setContentText("To insert  martyr you must add information,make sure to enter it.");
					warningAlert.show();
				} else {

					String part = field.getText();
					if (datePicker2.getValue() != null) {
						if (age.getValue() != null) {
							if (!locationBox.getValue().equals("Locations")) {
								if (!districtBox.getValue().equals("Districts")) {
									if (maleRadioButton.isSelected() || femaleRadioButton.isSelected()) {
										if (part.length() > 0) {
											Martyr martyr = new Martyr();
											date = new Date(datePicker2);

											if (hash.find(date) != null) {
												date = (Date) hash.find(date).getData();
												martyr.setName(part);
												martyr.setDate(datePicker2.getValue().toString());
												martyr.setAge(age.getValue());
												martyr.setLocation(locationBox.getValue());
												martyr.setDistrict(districtBox.getValue());
												if (maleRadioButton.isSelected())
													martyr.setGender('M');
												else
													martyr.setGender('F');
												CONFIRMATIONAlert.setHeaderText("Are you sure?");
												CONFIRMATIONAlert
														.setContentText("Do you want to proceed with the action?");
												CONFIRMATIONAlert.showAndWait().ifPresent(response -> {
													if (response == ButtonType.OK) {
														System.out.println("User clicked OK");
														date.getTree().delete(martyr);
														infoAlert.setTitle("Delete Martyr");
														infoAlert.setContentText(martyr
																+ " martyr has been successfully deleted from the list");
														infoAlert.show();
													} else {
														System.out.println("User clicked Cancel or closed the dialog");
													}
												});
												System.out.println(date.getTree().iterativeTraverseLevelOrderString());
												infoAlert.setTitle("Adding martyr");
												infoAlert.setContentText("The martyr " + martyr.getName()
														+ " has been added successfully.");
												infoAlert.show();

											}
										}
									} else {
										warningAlert.setTitle("Warning about inserting martyr");
										warningAlert.setContentText(
												"To add martyr you must select  the gender ,make sure to select  it.");
										warningAlert.show();
									}
								} else {
									warningAlert.setTitle("Warning about inserting martyr");
									warningAlert.setContentText(
											"To add martyr you must add the distict from box,make sure to enter it.");
									warningAlert.show();
								}
							} else {
								warningAlert.setTitle("Warning about inserting martyr");
								warningAlert.setContentText(
										"To add martyr you must add the location from box,make sure to enter it.");
								warningAlert.show();

							}
						} else {
							warningAlert.setTitle("Warning about inserting martyr");
							warningAlert
									.setContentText("To add martyr you must add age from box,make sure to enter it.");
							warningAlert.show();
						}

					} else {
						warningAlert.setTitle("Warning about inserting martyr");
						warningAlert.setContentText(
								"To add martyr you must add date of martyrdom from the calender,make sure to enter it.");
						warningAlert.show();
					}
				}
			});

			sizeAndHeight.setOnAction(event -> {
				if (datePicker2.getValue() == null) {
					warningAlert.setTitle("Warning about showing the size and height for martyr tree");
					warningAlert.setContentText(
							"To show the size and height for martyr tree you must select date of martyrdom from the calender,make sure to select it.");
					warningAlert.show();
				} else {
					Date date = new Date(datePicker2);
					if (hash.find(date) != null) {
						date = (Date) hash.find(date).getData();
						textArea2.setText("The tree size = " + date.getTree().size() + ".\nThe tree height = "
								+ date.getTree().height() + ".");
					} else {
						warningAlert.setTitle("Warning about showing the size and height for martyr tree");
						warningAlert.setContentText(
								"To show the size and height for martyr tree you must select date of martyrdom from the calender that is in the list.");
						warningAlert.show();
					}
				}
			});

			printByAge.setOnAction(event -> {

				heap.sort(martyrAge);
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < martyrAge.length; i++) {
					if (martyrAge[i] != null) { // Skip null values
						sb.append(martyrAge[i].toString()).append("");
					}
				}
				textArea2.setText(sb.toString());
			});

			printTree.setOnAction(event -> {
				if (datePicker2.getValue() == null) {
					warningAlert.setTitle("Warning about printing martyrs for martyr tree");
					warningAlert.setContentText(
							"To print the martyrs for martyr tree you must select date of martyrdom from the calender,make sure to select it.");
					warningAlert.show();
				} else {
					Date date = new Date(datePicker2);
					if (hash.find(date) != null) {
						date = (Date) hash.find(date).getData();
						textArea2.setText("The tree :\n" + date.getTree().iterativeTraverseLevelOrderString());
					} else {
						warningAlert.setTitle("Warning about showing the size and height for martyr tree");
						warningAlert.setContentText(
								"To show the size and height for martyr tree you must select date of martyrdom from the calender that is in the list.");
						warningAlert.show();
					}
				}
			});

			////// button to update

			TextField newName = new TextField();
			newName.setPromptText("New Name");
			DatePicker newDatePicker = new DatePicker();
			ComboBox<Integer> newAge = new ComboBox<Integer>();
			newAge.setPromptText("New Age");
			for (int i = 0; i <= 150; i++) {
				newAge.getItems().addAll(i);
			}
			newDistrictBox.setPromptText("New District");
			newLocationBox.setPromptText("Location");
			newDistrictBox.setOnAction(event -> {
				String selectedDistrict = newDistrictBox.getValue();
				if (selectedDistrict != null) {
					newLocationBox.getItems().clear();
					for (DistrictLocations districtLocations : districtLocationsList) {
						if (districtLocations.getDistrict().equals(selectedDistrict)) {
							newLocationBox.getItems().add(districtLocations.getLocation());
						}
					}
				}
			});

			RadioButton newMaleRadioButton = new RadioButton("Male");
			RadioButton newFemaleRadioButton = new RadioButton("Female");
			ToggleGroup newGenderToggleGroup = new ToggleGroup();
			newMaleRadioButton.setToggleGroup(newGenderToggleGroup);
			newFemaleRadioButton.setToggleGroup(newGenderToggleGroup);

			newGenderToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
				RadioButton selectedRadioButton = (RadioButton) newValue;
				String selectedGender = selectedRadioButton.getText();
				System.out.println("Selected Gender: " + selectedGender);
			});

			updateMartyr.setOnAction(event -> {
				if (field.getText().isEmpty() || newName.getText().isEmpty()) {
					warningAlert.setTitle("Warning about searching for the martyr");
					warningAlert.setContentText(
							"To search for a martyr by part of his name you m ust add information,make sure to enter it.");
					warningAlert.show();
				} else {

					String part = field.getText();
					String part2 = newName.getText();

					if (datePicker2.getValue() != null && newDatePicker.getValue() != null) {
						if (age.getValue() != null && newAge.getValue() != null) {
							if (locationBox.getValue() != null && newLocationBox.getValue() != null) {
								if (districtBox.getValue() != null && newDistrictBox.getValue() != null) {
									if ((maleRadioButton.isSelected() || femaleRadioButton.isSelected())
											&& (newMaleRadioButton.isSelected() || newFemaleRadioButton.isSelected())) {
										if (part.length() > 0 && part2.length() > 0) {

											Martyr martyr1 = new Martyr();
											Martyr newMartyr = new Martyr();

											date = new Date(datePicker2);
											date2 = new Date(newDatePicker);

											if (hash.find(date) != null && hash.find(date2) != null) {
												date = (Date) hash.find(date).getData();
												date2 = (Date) hash.find(date2).getData();
												martyr1.setName(part);
												newMartyr.setName(part2);
												martyr1.setDate(datePicker2.getValue().toString());
												newMartyr.setDate(newDatePicker.getValue().toString());
												martyr1.setAge(age.getValue());
												newMartyr.setAge(newAge.getValue());
												martyr1.setLocation(locationBox.getValue());
												newMartyr.setLocation(newLocationBox.getValue());
												martyr1.setDistrict(districtBox.getValue());
												newMartyr.setDistrict(newDistrictBox.getValue());
												if (maleRadioButton.isSelected())
													martyr1.setGender('M');
												else
													martyr1.setGender('F');
												if (newMaleRadioButton.isSelected())
													newMartyr.setGender('M');
												else
													newMartyr.setGender('F');
												if (date.getTree().find(martyr1) != null
														&& date2.getTree().find(newMartyr) == null) {

													CONFIRMATIONAlert.setHeaderText("Are you sure?");
													CONFIRMATIONAlert
															.setContentText("Do you want to proceed with the action?");
													CONFIRMATIONAlert.showAndWait().ifPresent(response -> {
														if (response == ButtonType.OK) {
															System.out.println("User clicked OK");
															if (date == date2)
																date.getTree().update(martyr1, newMartyr);
															else if (date != date2) {
																date.getTree().delete(martyr1);
																date2.getTree().insert(newMartyr);
															}
															infoAlert.setTitle("Update Martyr");
															infoAlert.setContentText(martyr1
																	+ " martyr has been successfully updating to "
																	+ newMartyr + " in the list");
															infoAlert.show();
														} else {
															System.out.println(
																	"User clicked Cancel or closed the dialog");
														}
													});
												} else {
													warningAlert.setTitle("warning about updating martyrs");
													warningAlert.setContentText(
															"When updating a marty's record, make sure that there is no previous record for the same new record.");
													warningAlert.show();
												}
											}

										}
									} else {
										warningAlert.setTitle("Warning about updating martyr");
										warningAlert.setContentText(
												"To update martyr you must add the gender from box,make sure to enter it.");
										warningAlert.show();
									}

								} else {
									warningAlert.setTitle("Warning about updating martyr");
									warningAlert.setContentText(
											"To update martyr you must add the distict from box,make sure to enter it.");
									warningAlert.show();
								}
							} else {
								warningAlert.setTitle("Warning about updating martyr");
								warningAlert.setContentText(
										"To update martyr you must add the location from box,make sure to enter it.");
								warningAlert.show();
							}
						} else {
							warningAlert.setTitle("Warning about update martyr");
							warningAlert.setContentText(
									"To update martyr you must add age from box,make sure to enter it.");
							warningAlert.show();
						}
					} else {
						warningAlert.setTitle("Warning about updating martyr");
						warningAlert.setContentText(
								"To update martyr you must add date of martyrdom from the calender,make sure to enter it.");
						warningAlert.show();
					}
				}

			});

//saveButton.setOnAction(event -> {
//	saveToFile(hash.toString(false), generateCSVFilename());
//});

			saveButton.setOnAction(e -> {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save File As");
				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

				File file2 = fileChooser.showSaveDialog(null);
				if (file2 != null) {
					System.out.println("Saved file: " + file2.getName());
				}
			}); // Closing brace for setOnAction

			gridPane2.add(newName, 1, 2);
			gridPane2.add(newDatePicker, 2, 2);
			gridPane2.add(newAge, 3, 2);
			gridPane2.add(newLocationBox, 4, 2);
			gridPane2.add(newDistrictBox, 5, 2);
			gridPane2.add(newMaleRadioButton, 6, 2);
			gridPane2.add(newFemaleRadioButton, 7, 2);
			backDateSc.setOnAction(event -> {
				primaryStage.setScene(secondScene);
			});
			BorderPane borderPane = new BorderPane();
			borderPane.setLeft(gridPane2);
			borderPane.setBottom(backDateSc);
			borderPane.setAlignment(backDateSc, Pos.CENTER);
			;

			borderPane.setPadding(new Insets(15, 15, 50, 100));

			Scene thirdScene = new Scene(borderPane, SCENE_WIDTH, SCENE_HEIGHT);

			startMainScreen.setOnAction(event -> {
				primaryStage.setScene(secondScene);
			});
			Button next2S = new Button("Next");
			Button back2S = new Button("Back");
			next2S.setOnAction(event -> {
				primaryStage.setScene(thirdScene);
			});

			HBox box = new HBox(10);
			box.getChildren().addAll(back2S, next2S);
			box.setAlignment(Pos.CENTER);
			borderPane1SS.setBottom(box);
			borderPane1SS.setAlignment(box, Pos.BOTTOM_CENTER);
			startMainScreen.setOnAction(event -> {
				primaryStage.setScene(secondScene);
			});

			load.setOnAction(event -> {
				if (datePicker.getValue() == null) {
					warningAlert.setTitle("Warning about loading  date’s AVL into martyrs screen");
					warningAlert.setContentText(
							"To load  date’s AVL into martyrs screen you must select it from date piker ,make sure to select it.");
					warningAlert.show();
				} else {
					Date date = new Date(datePicker);

					if (hash.find(date) != null) {
						primaryStage.setScene(thirdScene);
						date = (Date) hash.find(date).getData();
						textArea2.setText("The tree :\n" + date.getTree().iterativeTraverseLevelOrderString());
					} else {
						infoAlert.setTitle("The  date does not exits");
						infoAlert.setContentText(date + " is not on the list");
						infoAlert.show();
					}
				}
			});

			Scene firstScene = new Scene(mainScreen, SCENE_WIDTH, SCENE_HEIGHT);
			primaryStage.setScene(firstScene);
			back2S.setOnAction(event -> {
				primaryStage.setScene(firstScene);
			});
			primaryStage.setTitle("PALESTINE");
			primaryStage.show();
		}
	}

	private void fromFileToAVL(File file) {
		String name = null, location = null, district = null;
		int age = 0;
		char gender = 0;
		String dateString = null;
		DatePicker datePicker = new DatePicker();
		int count = 0;
		List<ageMartyr> martyrAgeList = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] arr = line.trim().split(",");
				if (arr.length == 6) {
					try {
						name = arr[0].trim();
						dateString = arr[1];
						DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("M/d/yyyy");
						LocalDate localDate = LocalDate.parse(dateString, inputFormat);
						DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						String formattedDate = localDate.format(outputFormat);
						datePicker.setValue(LocalDate.parse(formattedDate));
						age = Integer.parseInt(arr[2].trim());
						location = arr[3].trim();
						district = arr[4].trim();
						gender = arr[5].trim().charAt(0);
						ageMartyr ageMartyr = new ageMartyr(name, datePicker.getValue().toString(), age, location,
								district, gender);
						heap.add(ageMartyr);

						martyrAgeList.add(ageMartyr);

					} catch (DateTimeParseException e) {
						continue;
					} catch (Exception e) {
						continue;
					}

					Martyr martyr = new Martyr(name, datePicker.getValue().toString(), age, location, district, gender);
					Date dateObj = new Date(datePicker);

					if (hash.find(dateObj) == null) {
						hash.add(dateObj);
					} else {
						dateObj = (Date) hash.find(dateObj).getData();
					}

					dateObj.getTree().insert(martyr);

					if (!districtBox.getItems().contains(district)) {
						districtBox.getItems().add(district);
					}
					if (!newDistrictBox.getItems().contains(district)) {
						newDistrictBox.getItems().add(district);
					}

					boolean districtFound = false;
					for (DistrictLocations districtLocations : districtLocationsList) {
						if (districtLocations.getDistrict().equals(district)
								&& districtLocations.getLocation().equals(location)) {
							districtFound = true;
							break;
						}
					}
					if (!districtFound) {
						districtLocationsList.add(new DistrictLocations(district, location));
					}

					count++;
				}
			}
		} catch (Exception e) {
			exceptionAlertMethod(e);
		}

		martyrAge = martyrAgeList.toArray(new ageMartyr[0]);

		System.out.println(heap.getSize() + " size");
		System.out.println(martyrAge.length + " len");
		for (ageMartyr martyr : martyrAge) {
			System.out.println(martyr);
		}
	}

	public void exceptionAlertMethod(Exception e) {
		exceptionAlert.setTitle("Exception Dialog");
		exceptionAlert.setHeaderText("Look, an Exception Dialog");
		exceptionAlert.setContentText(e.getMessage());

		Label labelException = new Label("The exception stacktrace was:");
		TextArea textArea = new TextArea(e.toString());
		BorderPane borderPaneException = new BorderPane();
		borderPaneException.setTop(labelException);
		borderPaneException.setCenter(textArea);

		exceptionAlert.getDialogPane().setExpandableContent(borderPaneException);
		exceptionAlert.show();
	}

	private int totalMartyrs(TNode<Martyr> root) {
		if (root == null) {
			return 0;
		}
		int leftCount = totalMartyrs(root.getLeft());
		int rightCount = totalMartyrs(root.getRight());
		int currentCount = 1;
		return leftCount + rightCount + currentCount;
	}

	private double averageAge(TNode<Martyr> root) {
		if (root == null) {
			return 0.0;
		}
		int leftAgeSum = totalAge(root.getLeft());
		int rightAgeSum = totalAge(root.getRight());
		int totalMartyrsCount = totalMartyrs(root);
		double totalAge = leftAgeSum + rightAgeSum + root.getData().getAge();

		return totalMartyrsCount > 0 ? (double) totalAge / totalMartyrsCount : 0.0;
	}

	private int totalAge(TNode<Martyr> root) {
		if (root == null) {
			return 0;
		}
		return totalAge(root.getLeft()) + totalAge(root.getRight()) + root.getData().getAge();
	}

	private int totalMartyrsInDistrict(TNode<Martyr> root, String district) {
		if (root == null) {
			return 0;
		}
		int leftCount = totalMartyrsInDistrict(root.getLeft(), district);
		int rightCount = totalMartyrsInDistrict(root.getRight(), district);
		int currentCount = root.getData().getDistrict().equals(district) ? 1 : 0;
		return leftCount + rightCount + currentCount;
	}

	private int totalMartyrsInLocation(TNode<Martyr> root, String location) {
		if (root == null) {
			return 0;
		}
		int leftCount = totalMartyrsInLocation(root.getLeft(), location);
		int rightCount = totalMartyrsInLocation(root.getRight(), location);
		int currentCount = root.getData().getLocation().equals(location) ? 1 : 0;
		return leftCount + rightCount + currentCount;
	}

//	private void findMaxMartyrDistrict(TNode<Martyr> root, HashMap<String, Integer> districtCounts) {
//		  if (root == null) {
//		    return;
//		  }
//		  String currentDistrict = root.getData().getDistrict();
//		  int currentCount = districtCounts.getOrDefault(currentDistrict, 0);
//		  districtCounts.put(currentDistrict, currentCount + 1);
//		  findMaxMartyrDistrict(root.getLeft(), districtCounts);
//		  findMaxMartyrDistrict(root.getRight(), districtCounts);
//		}

}

class DistrictLocations {
	private String district;
	private String location;

	public DistrictLocations(String district, String location) {
		this.district = district;
		this.location = location;
	}

	public String getDistrict() {
		return district;
	}

	public String getLocation() {
		return location;
	}

}

class AgeComparator implements Comparator<Martyr> {

	@Override
	public int compare(Martyr o1, Martyr o2) {
		return o1.getAge() - o2.getAge();
	}
}

class ageMartyr implements Comparable<ageMartyr> {
	private String name;
	private String date;
	private int age;
	private String location;
	private String district;
	private char gender;

	public ageMartyr() {
	}

	public ageMartyr(String name) {
		super();
		this.name = name;
	}

	public ageMartyr(String name, String date, int age, String location, String district, char gender) {
		super();
		setName(name);
		setAge(age);
		setDate(date);
		setDistrict(district);
		setLocation(location);
		setGender(gender);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		if (age >= 0)
			this.age = age;
		else
			System.out.println(getName() + " Age must be >= 0");
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		if (Character.toUpperCase(gender) == 'M' || Character.toUpperCase(gender) == 'F')
			this.gender = gender;
		else
			System.out.println(getName() + " Gender must be F/M");
	}

	@Override
	public int hashCode() {
		return date.hashCode();

	}

	@Override
	public int compareTo(ageMartyr o) {
		return (Integer.compare(this.age, o.age));
	}

	@Override
	public String toString() {
		return "" + name + ", " + date + "," + age + "," + location + ", " + district + ", " + gender + "\n";
	}

}