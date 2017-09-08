package controller;

import com.jfoenix.controls.*;
import controller.databaseitemcontroller.DatabaseItemModificationController;
import databasemanagement.DatabaseChangeObservable;
import databasemanagement.DatabaseChangeObserver;
import databasemanagement.LogFileHelper;
import databasemanagement.objectrelationalmap.CompanyMapper;
import databasemanagement.objectrelationalmap.LocationMapper;
import databasemanagement.objectrelationalmap.LogEntryMapper;
import databasemanagement.objectrelationalmap.SupervisorMapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author  Kyle Williams
 * @since   Version 2
 */
public class NewLogEntryTabController implements Initializable, DatabaseChangeObserver {
    private Stage currentStage;
    private MainTabPaneController parentTabPaneController;
    private final String DEFAULTS_FILE_PATH = "Hour_Tracker_Files/defaults.ser";

    /**
     * Used for finding a default combo box item
     */
    private enum DatabaseItemType{COMPANY, LOCATION, SUPERVISOR}
    /**
     * A list containing the name of each <code>Company</code> within the SQLite companies table.
     * The strings in this list make up the items within <code>companyComboBox</code>.
     */
    private ObservableList<String> companyNames = FXCollections.observableArrayList();
    /**
     * A list containing the name of each <code>Location</code> within the SQLite locations table.
     * The strings in this list make up the items within <code>locationComboBox</code>.
     */
    private ObservableList<String> locationNames = FXCollections.observableArrayList();
    /**
     * A list containing the display name of each <code>Supervisor</code> within the SQLite supervisors table.
     * The strings in this list make up the items within <code>supervisorComboBox</code>.
     */
    private ObservableList<String> supervisorDisplayNames = FXCollections.observableArrayList();
    private ObservableList<String> sortedCompanyNames;
    private ObservableList<String> sortedLocationNames;
    private ObservableList<String> sortedSupervisorDisplayNames;
    @FXML private StackPane tabRootPane;
    @FXML private TextField hours;
    @FXML private JFXDatePicker theDatePicker;
    @FXML private TextArea comments;
    @FXML public ComboBox<String> companyComboBox;
    @FXML public ComboBox<String> locationComboBox;
    @FXML public ComboBox<String> supervisorComboBox;
    @FXML private JFXButton addCompanyButton;
    @FXML private JFXButton addLocationButton;
    @FXML private JFXButton addSupervisorButton;
    @FXML private JFXButton editCompanyButton;
    @FXML private JFXButton editLocationButton;
    @FXML private JFXButton editSupervisorButton;
    @FXML private JFXButton deleteCompanyButton;
    @FXML private JFXButton deleteLocationButton;
    @FXML private JFXButton deleteSupervisorButton;
    @FXML private JFXCheckBox locationDefaultCheckbox;
    @FXML private JFXCheckBox companyDefaultCheckbox;
    @FXML private JFXCheckBox supervisorDefaultCheckbox;
    @FXML private Label errorLabel;
    @FXML private JFXButton submitButton;

    public NewLogEntryTabController(){
        refreshCompanyNames();
        refreshLocationNames();
        refreshSupervisorDisplayNames();
        DatabaseChangeObservable.registerObserver(this);
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public void setParentTabPaneController(MainTabPaneController parentTabPaneController) {
        this.parentTabPaneController = parentTabPaneController;
    }

    @Override
    public void initialize(URL locationURL, ResourceBundle resources) {

        // Give focus to hours
        Platform.runLater(() -> hours.requestFocus());

        hours.setOnKeyReleased(keyReleased -> {
            if (keyReleased.getCode() == KeyCode.ENTER) {
                submit();
            }
        });

        // Datepicker defaults to today
        theDatePicker.setValue(LocalDate.now());

        setAllComboBoxItems();

        // Set button onClick events to open associated JavaFX stages
        addCompanyButton.setOnMouseClicked(event -> createDatabaseItemModificationStage(
                "/view/addCompany.fxml", "Add Company"));
        editCompanyButton.setOnMouseClicked(event -> createDatabaseItemModificationStage(
                "/view/editCompany.fxml", "Edit Company"));
        deleteCompanyButton.setOnMouseClicked(event -> {
            CompanyMapper companyMapper = new CompanyMapper();
            Company company = companyMapper.read(companyComboBox.getValue());
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            dialogLayout.setHeading(new Text("Delete Company"));
            Text body = new Text("Are you sure you want to delete " + company.getCompanyName() + "?");
            dialogLayout.setBody(body);

            JFXDialog dialog = new JFXDialog(tabRootPane, dialogLayout, JFXDialog.DialogTransition.CENTER);

            JFXButton deleteButton = new JFXButton("DELETE");
            deleteButton.setOnMouseClicked(deleteEvent -> {
                companyMapper.delete(company);
                dialog.close();
            });
            // Light blue text fill
            deleteButton.setTextFill(Color.valueOf("#6c93e4"));
            deleteButton.getStyleClass().add("dialogButton");

            JFXButton cancelButton = new JFXButton("CANCEL");
            cancelButton.setOnMouseClicked(cancelEvent -> {
                dialog.close();
            });
            cancelButton.getStyleClass().add("dialogButton");

            dialogLayout.setActions(deleteButton, cancelButton);

            dialog.show();
        });
        addLocationButton.setOnMouseClicked(event -> createDatabaseItemModificationStage(
                "/view/addLocation.fxml", "Add Location"));
        editLocationButton.setOnMouseClicked(event -> createDatabaseItemModificationStage(
                "/view/editLocation.fxml", "Edit Location"));
        deleteLocationButton.setOnMouseClicked(event -> {
            LocationMapper locationMapper = new LocationMapper();
            Location location = locationMapper.read(locationComboBox.getValue());
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            dialogLayout.setHeading(new Text("Delete Location"));
            Text body = new Text("Are you sure you want to delete " + location.getLocationName() + "?");
            dialogLayout.setBody(body);

            JFXDialog dialog = new JFXDialog(tabRootPane, dialogLayout, JFXDialog.DialogTransition.CENTER);

            JFXButton deleteButton = new JFXButton("DELETE");
            deleteButton.setOnMouseClicked(deleteEvent -> {
                locationMapper.delete(location);
                dialog.close();
            });
            // Light blue text fill
            deleteButton.setTextFill(Color.valueOf("#6c93e4"));
            deleteButton.getStyleClass().add("dialogButton");

            JFXButton cancelButton = new JFXButton("CANCEL");
            cancelButton.setOnMouseClicked(cancelEvent -> {
                dialog.close();
            });
            cancelButton.getStyleClass().add("dialogButton");

            dialogLayout.setActions(deleteButton, cancelButton);

            dialog.show();
        });
        addSupervisorButton.setOnMouseClicked(event -> createDatabaseItemModificationStage(
                "/view/addSupervisor.fxml", "Add Supervisor"));
        editSupervisorButton.setOnMouseClicked(event -> createDatabaseItemModificationStage(
                "/view/editSupervisor.fxml", "Edit Supervisor"));
        deleteSupervisorButton.setOnMouseClicked(event -> {
            SupervisorMapper supervisorMapper = new SupervisorMapper();
            Supervisor supervisor = supervisorMapper.read(supervisorComboBox.getValue());
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            dialogLayout.setHeading(new Text("Delete Supervisor"));
            Text body = new Text("Are you sure you want to delete " + supervisor.getSupervisorDisplayName() + "?");
            dialogLayout.setBody(body);

            JFXDialog dialog = new JFXDialog(tabRootPane, dialogLayout, JFXDialog.DialogTransition.CENTER);

            JFXButton deleteButton = new JFXButton("DELETE");
            deleteButton.setOnMouseClicked(deleteEvent -> {
                supervisorMapper.delete(supervisor);
                dialog.close();
            });
            // Light blue text fill
            deleteButton.setTextFill(Color.valueOf("#6c93e4"));
            deleteButton.getStyleClass().add("dialogButton");

            JFXButton cancelButton = new JFXButton("CANCEL");
            cancelButton.setOnMouseClicked(cancelEvent -> {
                dialog.close();
            });
            cancelButton.getStyleClass().add("dialogButton");

            dialogLayout.setActions(deleteButton, cancelButton);

            dialog.show();
        });

        // Sets/Removes the company default
        companyDefaultCheckbox.setOnAction(e -> {
            String companyName = companyComboBox.getValue();
            CompanyMapper companyMapper = new CompanyMapper();
            Integer companyID  = companyMapper.read(companyName).getCompanyID();
            defaultCheckboxClicked(companyDefaultCheckbox, companyID, DatabaseItemType.COMPANY);
        });

        // Sets/Removes the location default
        locationDefaultCheckbox.setOnAction(e -> {
            String locationName = locationComboBox.getValue();
            LocationMapper locationMapper = new LocationMapper();
            Integer locationID = locationMapper.read(locationName).getLocationID();
            defaultCheckboxClicked(locationDefaultCheckbox, locationID, DatabaseItemType.LOCATION);
        });

        // Sets/Removes the supervisor default
        supervisorDefaultCheckbox.setOnAction(e -> {
            String supervisorDisplayName = supervisorComboBox.getValue();
            SupervisorMapper supervisorMapper = new SupervisorMapper();
            Integer supervisorID = supervisorMapper.read(supervisorDisplayName).getSupervisorID();
            defaultCheckboxClicked(supervisorDefaultCheckbox, supervisorID, DatabaseItemType.SUPERVISOR);
        });

        submitButton.setOnMouseClicked(event -> submit());



        /* If a combo box start outs out with a null value,
         * then disable its Edit/Delete buttons and default checkbox.
         * Also, set the action listener of the combo box to enable/re-enable
         * the buttons/default checkbox when the combo box does not have a null value
         * and disable the buttons/default checkbox when the combo box does have a null value.
         */

        editCompanyButton.setDisable(companyComboBox.getValue() == null);
        deleteCompanyButton.setDisable(companyComboBox.getValue() == null);
        companyDefaultCheckbox.setDisable(companyComboBox.getValue() == null);
        companyComboBox.valueProperty().addListener(event -> {
            editCompanyButton.setDisable(companyComboBox.getValue() == null);
            deleteCompanyButton.setDisable(companyComboBox.getValue() == null);
            companyDefaultCheckbox.setDisable(companyComboBox.getValue() == null);
        });

        editLocationButton.setDisable(locationComboBox.getValue() == null);
        deleteLocationButton.setDisable(locationComboBox.getValue() == null);
        locationDefaultCheckbox.setDisable(locationComboBox.getValue() == null);
        locationComboBox.valueProperty().addListener(event -> {
            editLocationButton.setDisable(locationComboBox.getValue() == null);
            deleteLocationButton.setDisable(locationComboBox.getValue() == null);
            locationDefaultCheckbox.setDisable(locationComboBox.getValue() == null);
        });

        editSupervisorButton.setDisable(supervisorComboBox.getValue() == null);
        deleteSupervisorButton.setDisable(supervisorComboBox.getValue() == null);
        supervisorDefaultCheckbox.setDisable(supervisorComboBox.getValue() == null);
        supervisorComboBox.valueProperty().addListener(event -> {
            editSupervisorButton.setDisable(supervisorComboBox.getValue() == null);
            deleteSupervisorButton.setDisable(supervisorComboBox.getValue() == null);
            supervisorDefaultCheckbox.setDisable(supervisorComboBox.getValue() == null);
        });



        /* If a combo box's item is displaying the
         * default choice for that combo box, then set the
         * corresponding default checkbox to checked.
         * Otherwise, remove the check mark.
         */

        companyComboBox.valueProperty().addListener(event -> {
            Integer defaultCompanyID = getDefaultComboBoxItemID(DatabaseItemType.COMPANY);
            if (defaultCompanyID != null && companyComboBox.getValue() != null) {
                CompanyMapper companyMapper = new CompanyMapper();
                Company defaultCompany = companyMapper.read(defaultCompanyID);
                if (companyComboBox.getValue().equals(defaultCompany.getCompanyName())) {
                    companyDefaultCheckbox.setSelected(true);
                } else {
                    companyDefaultCheckbox.setSelected(false);
                }
            }
        });

        locationComboBox.valueProperty().addListener(event -> {
            Integer defaultLocationID = getDefaultComboBoxItemID(DatabaseItemType.LOCATION);
            if (defaultLocationID != null && locationComboBox.getValue() != null) {
                LocationMapper locationMapper = new LocationMapper();
                Location defaultLocation = locationMapper.read(defaultLocationID);
                locationComboBox.getValue();
                if (locationComboBox.getValue().equals(defaultLocation.getLocationName())) {
                    locationDefaultCheckbox.setSelected(true);
                } else {
                    locationDefaultCheckbox.setSelected(false);
                }
            }
        });

        supervisorComboBox.valueProperty().addListener(event -> {
            Integer defaultSupervisorID = getDefaultComboBoxItemID(DatabaseItemType.SUPERVISOR);
            if (defaultSupervisorID != null && supervisorComboBox.getValue() != null) {
                SupervisorMapper supervisorMapper = new SupervisorMapper();
                Supervisor defaultSupervisor = supervisorMapper.read(defaultSupervisorID);
                if (supervisorComboBox.getValue().equals(defaultSupervisor.getSupervisorDisplayName())) {
                    supervisorDefaultCheckbox.setSelected(true);
                } else {
                    supervisorDefaultCheckbox.setSelected(false);
                }
            }
        });

        fillComboBoxesWithDefaultChoices();
    }

    /**
     * Submits a work iteration, to the log file, with all the
     * information given by the user pertaining to the work iteration
     */
    @FXML private void submit(){
        if (hours.getText().isEmpty() || theDatePicker.getValue() == null) {
            errorLabel.setVisible(true);
            return;
        }

        // The string that will be written to the log file
        StringBuilder logEntry = new StringBuilder();
        String newLine = System.lineSeparator();

        // Append the date
        LocalDate localDate = theDatePicker.getValue();
        logEntry.append("Date: ").append(localDate.getMonth())
                .append(" ")
                .append(localDate.getDayOfMonth())
                .append(", ")
                .append(localDate.getYear());

        // Append the hours
        logEntry.append(newLine).append("Hours: ").append(hours.getCharacters());

        // Append the comments
        if (!comments.getText().isEmpty()) {
            logEntry.append(newLine).append("Comments: ").append(comments.getText());
        }

        // Append the location
        if (locationComboBox.getValue() != null) {
            LocationMapper locationMapper = new LocationMapper();
            Location location = locationMapper.read(locationComboBox.getValue().toString());
            logEntry.append(newLine).append("Location:").append(newLine)
                    .append(location.getLocationName()).append(newLine)
                    .append(location.getLocationAddress()).append(newLine)
                    .append(location.getLocationCity()).append(", ")
                    .append(location.getLocationState()).append(" ")
                    .append(location.getLocationZipCode());
        }

        // Append the company
        if (companyComboBox.getValue() != null) {
            CompanyMapper companyMapper = new CompanyMapper();
            Company company = companyMapper.read(companyComboBox.getValue().toString());
            logEntry.append(newLine).append("Company: ").append(company.getCompanyName());
        }

        // Append the supervisor
        if (supervisorComboBox.getValue() != null) {
            SupervisorMapper supervisorMapper = new SupervisorMapper();
            Supervisor supervisor = supervisorMapper.read(supervisorComboBox.getValue());
            logEntry.append(newLine).append("Supervisor: ")
                    .append(supervisor.getSupervisorDisplayName()).append(" (")
                    .append(supervisor.getSupervisorFirstName()).append(" ")
                    .append(supervisor.getSupervisorLastName()).append(")");
        }

        /* If the log file exists, append two new lines
         * to the new entry so it can be easily distinguished
         * from the previous entry when the user reads the log file.
         */
        if (LogFileHelper.logFileExists()) {
            logEntry.append(newLine).append(newLine);
        }

        // Append the current log file text so that the new
        // entry will appear on the top of any previous entries
        logEntry.append(LogFileHelper.getLogFileText());

        // Update the log file to reflect the new entry
        LogFileHelper.setLogFileText(logEntry.toString());

        LogEntry newLogEntry = new LogEntry(localDate.toString(), hours.getText());
        newLogEntry.setLogEntryComments(comments.getText());
        if (locationComboBox.getValue() != null) {
            LocationMapper locationMapper = new LocationMapper();
            Location theLocation = locationMapper.read(locationComboBox.getValue());
            newLogEntry.setLogEntryLocationName(theLocation.getLocationName());
            newLogEntry.setLogEntryLocationAddress(theLocation.getLocationAddress());
            newLogEntry.setLogEntryLocationCity(theLocation.getLocationCity());
            newLogEntry.setLogEntryLocationState(theLocation.getLocationState());
            newLogEntry.setLogEntryLocationZipCode(theLocation.getLocationZipCode());
        }
        if (companyComboBox.getValue() != null) {
            CompanyMapper companyMapper = new CompanyMapper();
            Company theCompany = companyMapper.read(companyComboBox.getValue());
            newLogEntry.setLogEntryCompanyName(theCompany.getCompanyName());
        }
        if (supervisorComboBox.getValue() != null) {
            SupervisorMapper supervisorMapper = new SupervisorMapper();
            Supervisor theSupervisor = supervisorMapper.read(supervisorComboBox.getValue());
            newLogEntry.setLogEntrySupervisorFirstName(theSupervisor.getSupervisorFirstName());
            newLogEntry.setLogEntrySupervisorLastName(theSupervisor.getSupervisorLastName());
            newLogEntry.setLogEntrySupervisorDisplayName(theSupervisor.getSupervisorDisplayName());
        }
        LogEntryMapper logEntryMapper = new LogEntryMapper();
        logEntryMapper.create(newLogEntry);

        parentTabPaneController.logEntryWasSubmitted();
    }

    /**
     * This method should be called in order to update <code>companyNames<code/>
     * whenever the SQLite companies table is changed.
     */
    private void refreshCompanyNames(){
        CompanyMapper companyMapper = new CompanyMapper();
        companyNames.clear();
        List<Company> companies = companyMapper.readAll();
        for (Company someCompany : companies){
            companyNames.add(someCompany.getCompanyName());
        }
    }

    /**
     * This method should be called in order to update <code>locationNames<code/>
     * whenever the SQLite locations table is changed.
     */
    private void refreshLocationNames(){
        LocationMapper locationMapper = new LocationMapper();
        locationNames.clear();
        List<Location> locations = locationMapper.readAll();
        for (Location someLocation : locations) {
            locationNames.add(someLocation.getLocationName());
        }
    }

    /**
     * This method should be called in order to update <code>supervisorDisplayNames<code/>
     * whenever the SQLite supervisors table is changed.
     */
    private void refreshSupervisorDisplayNames(){
        SupervisorMapper supervisorMapper = new SupervisorMapper();
        supervisorDisplayNames.clear();
        List<Supervisor> supervisors = supervisorMapper.readAll();
        for (Supervisor someSupervisor : supervisors) {
            supervisorDisplayNames.add(someSupervisor.getSupervisorDisplayName());
        }
    }

    /**
     * Sets the items for all combo boxes in newLogEntryTab view
     */
    private void setAllComboBoxItems(){

        Consumer<ComboBox<String>> disableComboBoxIfEmpty = (comboBox) -> {
            if (comboBox.getItems().isEmpty()) {
                comboBox.setDisable(true);
            } else {comboBox.setDisable(false);}
        };

        // Establish process for populating a combo box
        BiConsumer<ComboBox<String>, ObservableList<String>> setComboBoxItems = (comboBox, observableList) -> {
            observableList.addListener((ListChangeListener<String>) c -> {
                comboBox.setItems(observableList);
                disableComboBoxIfEmpty.accept(comboBox);
            });
            comboBox.setItems(observableList);
            disableComboBoxIfEmpty.accept(comboBox);
        };

        // Set company combo box items
        sortedCompanyNames = new SortedList<>(companyNames, String.CASE_INSENSITIVE_ORDER);
        setComboBoxItems.accept(companyComboBox, sortedCompanyNames);

        // Set location combo box items
        sortedLocationNames = new SortedList<>(locationNames, String.CASE_INSENSITIVE_ORDER);
        setComboBoxItems.accept(locationComboBox, sortedLocationNames);

        // Set supervisor combo box items
        sortedSupervisorDisplayNames = new SortedList<>(supervisorDisplayNames, String.CASE_INSENSITIVE_ORDER);
        setComboBoxItems.accept(supervisorComboBox, sortedSupervisorDisplayNames);
    }

    /**
     * Creates a new JavaFX stage for adding/editing/deleting a <code>DatabaseItem</code>
     *
     * @param viewPath      the path to the xml file representing the view
     * @param stageTitle    the title to be shown at the top of the stage
     */
    private void createDatabaseItemModificationStage(String viewPath, String stageTitle) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
            Stage newStage = new Stage();
            newStage.setTitle(stageTitle);
            newStage.setScene(new Scene(loader.load()));
            DatabaseItemModificationController databaseItemModificationController = loader.getController();
            databaseItemModificationController.setNewLogEntryTabController(this);
            databaseItemModificationController.setStage(newStage);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();
        } catch (IOException e) {
            createErrorStage();
            e.printStackTrace();
        }
    }

    /**
     * Creates error stage with default error message
     */
    private void createErrorStage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/error.fxml"));
            Stage errorStage = new Stage();
            errorStage.setTitle("Error");
            errorStage.setScene(new Scene(loader.load()));
            ErrorController errorController = loader.getController();
            errorController.setStage(errorStage);
            errorStage.initModality(Modality.APPLICATION_MODAL);
            errorStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates error stage with a custom message
     *
     * @param customErrorMessage the error message to be shown to the user
     */
    private void createErrorStage(String customErrorMessage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/error.fxml"));
            Stage errorStage = new Stage();
            errorStage.setTitle("Error");
            errorStage.setScene(new Scene(loader.load()));
            ErrorController errorController = loader.getController();
            errorController.setCustomErrorMessage(customErrorMessage);
            errorController.setStage(errorStage);
            errorStage.initModality(Modality.APPLICATION_MODAL);
            errorStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the ID of the <code>DatabaseItem</code> that is the
     * default choice for the combo box that represents
     * DatabaseItems of type <code>databaseItemType</code>
     *
     * @param databaseItemType the type of DatabaseItem
     * @return a DatabaseItem ID
     */
    private Integer getDefaultComboBoxItemID(DatabaseItemType databaseItemType) {
        HashMap<DatabaseItemType, Integer> defaults = readFromDefaultsFile();
        return defaults.get(databaseItemType);
    }

    /**
     * Sets the default choice for the combo box that represents
     * DatabaseItems of type <code>DatabaseItemType</code> to
     * the DatabaseItem represented by <code>databaseItemID</code>
     *
     * @param databaseItemType  the type of DatabaseItem
     * @param databaseItemID    a DatabaseItem ID
     */
    private void setDefaultComboBoxItemID(DatabaseItemType databaseItemType, Integer databaseItemID) {
        HashMap<DatabaseItemType, Integer> defaultsHashMap = readFromDefaultsFile();
        defaultsHashMap.put(databaseItemType, databaseItemID);
        writeToDefaultsFile(defaultsHashMap);
    }

    /**
     * Loads any default choices into their
     * corresponding combo boxes.
     */
    private void fillComboBoxesWithDefaultChoices() {
        Integer defaultCompanyID = getDefaultComboBoxItemID(DatabaseItemType.COMPANY);
        Integer defaultLocationID = getDefaultComboBoxItemID(DatabaseItemType.LOCATION);
        Integer defaultSupervisorID = getDefaultComboBoxItemID(DatabaseItemType.SUPERVISOR);

        if (defaultCompanyID != null) {
            CompanyMapper companyMapper = new CompanyMapper();
            Company defaultCompany = companyMapper.read(defaultCompanyID);
            companyComboBox.setValue(defaultCompany.getCompanyName());
        }
        if (defaultLocationID != null) {
            LocationMapper locationMapper = new LocationMapper();
            Location defaultLocation = locationMapper.read(defaultLocationID);
            locationComboBox.setValue(defaultLocation.getLocationName());
        }
        if(defaultSupervisorID != null){
            SupervisorMapper supervisorMapper = new SupervisorMapper();
            Supervisor defaultSupervisor = supervisorMapper.read(defaultSupervisorID);
            supervisorComboBox.setValue(defaultSupervisor.getSupervisorDisplayName());
        }
    }

    /**
     * Sets/Removes the default choice of the combo box
     * associated with the clicked default checkbox.
     * <p>
     * If the checkbox check mark was removed by the user,
     * then the user is trying to remove the default.
     * Otherwise, the user is trying to set the default.
     *
     * @param clickedCheckbox   the checkbox that was clicked by the user
     * @param databaseItemID    the ID of the DatabaseItem in the combo box
     *                          associated with <code>clickedCheckbox</code> at
     *                          the time when the user clicked <code>clickedCheckbox</code>
     * @param databaseItemType  the type of DatabaseItem represented by <code>databaseItemID</code>
     */
    private void defaultCheckboxClicked(CheckBox clickedCheckbox,
                                        Integer databaseItemID, DatabaseItemType databaseItemType){
        // If a defaults file does not exist, we'll need to create a new one.
        File defaultsFile = new File(DEFAULTS_FILE_PATH);
        if (!defaultsFile.exists()){
            writeNewDefaultsFile();
        }

        // If the user has unchecked the default checkbox
        // then they're only trying to remove the current default
        // for that combo box - not set a new one. Thus, the new
        // value for that combo box default should be null.
        if (!clickedCheckbox.isSelected()) {
            databaseItemID = null;
        }
        setDefaultComboBoxItemID(databaseItemType, databaseItemID);
    }

    /**
     * Creates a new defaults file with a fresh
     * defaults HashMap stored inside the file
     */
    private void writeNewDefaultsFile() {
        HashMap<DatabaseItemType, Integer> defaultsHashMap = new HashMap<>();
        defaultsHashMap.put(DatabaseItemType.COMPANY, null);
        defaultsHashMap.put(DatabaseItemType.LOCATION, null);
        defaultsHashMap.put(DatabaseItemType.SUPERVISOR, null);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DEFAULTS_FILE_PATH))) {
            out.writeObject(defaultsHashMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads in the defaults for each DatabaseItem combo box
     *
     * @return either a <code>HashMap&lt;DatabaseItemType, Integer&gt;</code> representing the default
     * choices for each DatabaseItem combo box or null if no <code>HashMap&lt;DatabaseItemType, Integer&gt;</code>
     * was found in the defaults file
     */
    private HashMap<DatabaseItemType, Integer> readFromDefaultsFile() {
        File defaultsFile = new File(DEFAULTS_FILE_PATH);
        // If a defaults file does not exist, we'll need to create a new one.
        if (!defaultsFile.exists()) {
            writeNewDefaultsFile();
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(DEFAULTS_FILE_PATH))) {
            return (HashMap<DatabaseItemType, Integer>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Writes the defaults for the DatabaseItem combo boxes to the defaults file
     *
     * @param defaultsHashMap a <code>HashMap&lt;DatabaseItemType, Integer&gt;</code>
     * representing the default choices for each DatabaseItem combo box
     */
    private void writeToDefaultsFile(HashMap<DatabaseItemType, Integer> defaultsHashMap) {
        File defaultsFile = new File(DEFAULTS_FILE_PATH);
        // If a defaults file does not exist, we'll need to create a new one.
        if (!defaultsFile.exists()) {
            writeNewDefaultsFile();
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(defaultsFile))) {
            out.writeObject(defaultsHashMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method that is called each time a
     * <code>DatabaseItem</code> is created in the database
     *
     * @param databaseItem the <code>DatabaseItem</code> that
     *                     was just created in the database
     */
    @Override
    public void databaseItemWasCreated(DatabaseItem databaseItem) {
        if (databaseItem instanceof Company) {
            refreshCompanyNames();
            Company newCompany = (Company) databaseItem;
            companyComboBox.setValue(newCompany.getCompanyName());
        } else if (databaseItem instanceof Location) {
            refreshLocationNames();
            Location newLocation = (Location) databaseItem;
            locationComboBox.setValue(newLocation.getLocationName());
        } else if (databaseItem instanceof Supervisor) {
            refreshSupervisorDisplayNames();
            Supervisor newSupervisor = (Supervisor) databaseItem;
            supervisorComboBox.setValue(newSupervisor.getSupervisorDisplayName());
        }
    }

    /**
     * The method that is called each time a
     * <code>DatabaseItem</code> is updated in the database
     *
     * @param databaseItem the <code>DatabaseItem</code> that
     *                     was just updated in the database
     */
    @Override
    public void databaseItemWasUpdated(DatabaseItem databaseItem) {
        if (databaseItem instanceof Company) {
            refreshCompanyNames();
            Company updatedCompany = (Company) databaseItem;
            companyComboBox.setValue(updatedCompany.getCompanyName());
        } else if (databaseItem instanceof Location) {
            refreshLocationNames();
            Location updatedLocation = (Location) databaseItem;
            locationComboBox.setValue(updatedLocation.getLocationName());
        } else if (databaseItem instanceof Supervisor) {
            refreshSupervisorDisplayNames();
            Supervisor updatedSupervisor = (Supervisor) databaseItem;
            supervisorComboBox.setValue(updatedSupervisor.getSupervisorDisplayName());
        }
    }

    /**
     * The method that is called each time a
     * <code>DatabaseItem</code> is deleted from the database
     *
     * @param databaseItem the <code>DatabaseItem</code> that
     *                     was just deleted from the database
     */
    @Override
    public void databaseItemWasDeleted(DatabaseItem databaseItem) {
        if (databaseItem instanceof Company) {
            refreshCompanyNames();
            // If the deleted DatabaseItem was a default for its
            // combo box, then we need to remove it from the defaults.
            Company oldCompany = (Company) databaseItem;
            Integer defaultCompanyID = getDefaultComboBoxItemID(DatabaseItemType.COMPANY);
            if ((defaultCompanyID != null) && (defaultCompanyID == oldCompany.getCompanyID())) {
                setDefaultComboBoxItemID(DatabaseItemType.COMPANY, null);
                companyDefaultCheckbox.setSelected(false);
            }
        } else if (databaseItem instanceof Location) {
            refreshLocationNames();
            // If the deleted DatabaseItem was a default for its
            // combo box, then we need to remove it from the defaults.
            Location oldLocation = (Location) databaseItem;
            Integer defaultLocationID = getDefaultComboBoxItemID(DatabaseItemType.LOCATION);
            if ((defaultLocationID != null) && (defaultLocationID == oldLocation.getLocationID())) {
                setDefaultComboBoxItemID(DatabaseItemType.LOCATION, null);
                locationDefaultCheckbox.setSelected(false);
            }
        } else if (databaseItem instanceof Supervisor) {
            refreshSupervisorDisplayNames();
            // If the deleted DatabaseItem was a default for its
            // combo box, then we need to remove it from the defaults.
            Supervisor oldSupervisor = (Supervisor) databaseItem;
            Integer defaultSupervisorId = getDefaultComboBoxItemID(DatabaseItemType.SUPERVISOR);
            if ((defaultSupervisorId != null) && (defaultSupervisorId == oldSupervisor.getSupervisorID())) {
                setDefaultComboBoxItemID(DatabaseItemType.SUPERVISOR, null);
                supervisorDefaultCheckbox.setSelected(false);
            }
        }
    }
}