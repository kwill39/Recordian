package com.kylewill.controller;

import com.kylewill.controller.databaseitemcontroller.DatabaseItemModificationController;
import com.kylewill.databasemanagement.DatabaseChangeObservable;
import com.kylewill.databasemanagement.DatabaseChangeObserver;
import com.kylewill.model.Company;
import com.kylewill.model.DatabaseItem;
import com.kylewill.model.Location;
import com.kylewill.model.Supervisor;
import com.kylewill.databasemanagement.objectrelationalmap.CompanyMapper;
import com.kylewill.databasemanagement.objectrelationalmap.LocationMapper;
import com.kylewill.databasemanagement.objectrelationalmap.SupervisorMapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author  Kyle Williams
 * @since   Version 2
 */
public class MainViewController implements Initializable, DatabaseChangeObserver {
    private final String DEFAULTS_FILE_PATH = "Hour_Tracker_Files/defaults.ser";

    /**
     * Used for finding a default choicebox item
     */
    private enum DatabaseItemType{COMPANY, LOCATION, SUPERVISOR}
    /**
     * A list containing the name of each <code>Company</code> within the SQLite companies table.
     * The strings in this list make up the items within <code>companyChoicebox</code>.
     */
    private ObservableList<String> companyNames = FXCollections.observableArrayList();
    /**
     * A list containing the name of each <code>Location</code> within the SQLite locations table.
     * The strings in this list make up the items within <code>locationChoicebox</code>.
     */
    private ObservableList<String> locationNames = FXCollections.observableArrayList();
    /**
     * A list containing the display name of each <code>Supervisor</code> within the SQLite supervisors table.
     * The strings in this list make up the items within <code>supervisorChoicebox</code>.
     */
    private ObservableList<String> supervisorDisplayNames = FXCollections.observableArrayList();
    private ObservableList<String> sortedCompanyNames;
    private ObservableList<String> sortedLocationNames;
    private ObservableList<String> sortedSupervisorDisplayNames;
    @FXML private TextField hours;
    @FXML private TextArea comments;
    @FXML public ChoiceBox<String> companyChoiceBox;
    @FXML public ChoiceBox<String> locationChoiceBox;
    @FXML public ChoiceBox<String> supervisorChoiceBox;
    @FXML private Button addCompanyButton;
    @FXML private Button addLocationButton;
    @FXML private Button addSupervisorButton;
    @FXML private Button editCompanyButton;
    @FXML private Button editLocationButton;
    @FXML private Button editSupervisorButton;
    @FXML private Button deleteCompanyButton;
    @FXML private Button deleteLocationButton;
    @FXML private Button deleteSupervisorButton;
    @FXML private CheckBox locationDefaultCheckbox;
    @FXML private CheckBox companyDefaultCheckbox;
    @FXML private CheckBox supervisorDefaultCheckbox;
    @FXML private Label errorLabel;
    @FXML private Button submit;

    public MainViewController(){
        refreshCompanyNames();
        refreshLocationNames();
        refreshSupervisorDisplayNames();
        DatabaseChangeObservable.registerObserver(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAllChoiceBoxItems();

        // Set button onClick events to open associated JavaFX stages
        addCompanyButton.setOnMouseClicked(event -> createDatabaseItemModificationStage(
                "/com/kylewill/view/addCompany.fxml", "Add Company"));
        editCompanyButton.setOnMouseClicked(event -> createDatabaseItemModificationStage(
                "/com/kylewill/view/editCompany.fxml", "Edit Company"));
        deleteCompanyButton.setOnMouseClicked(event -> createDatabaseItemModificationStage(
                "/com/kylewill/view/deleteCompany.fxml", "Delete Company"));
        addLocationButton.setOnMouseClicked(event -> createDatabaseItemModificationStage(
                "/com/kylewill/view/addLocation.fxml", "Add Location"));
        editLocationButton.setOnMouseClicked(event -> createDatabaseItemModificationStage(
                "/com/kylewill/view/editLocation.fxml", "Edit Location"));
        deleteLocationButton.setOnMouseClicked(event -> createDatabaseItemModificationStage(
                "/com/kylewill/view/deleteLocation.fxml", "Delete Location"));
        addSupervisorButton.setOnMouseClicked(event -> createDatabaseItemModificationStage(
                "/com/kylewill/view/addSupervisor.fxml", "Add Supervisor"));
        editSupervisorButton.setOnMouseClicked(event -> createDatabaseItemModificationStage(
                "/com/kylewill/view/editSupervisor.fxml", "Edit Supervisor"));
        deleteSupervisorButton.setOnMouseClicked(event -> createDatabaseItemModificationStage(
                "/com/kylewill/view/deleteSupervisor.fxml", "Delete Supervisor"));

        companyDefaultCheckbox.setOnAction(e -> {
            String companyName = companyChoiceBox.getValue();
            CompanyMapper companyMapper = new CompanyMapper();
            Integer companyID  = companyMapper.read(companyName).getCompanyID();
            defaultCheckboxClicked(companyDefaultCheckbox, companyID, DatabaseItemType.COMPANY);
        });

        locationDefaultCheckbox.setOnAction(e -> {
            String locationName = locationChoiceBox.getValue();
            LocationMapper locationMapper = new LocationMapper();
            Integer locationID = locationMapper.read(locationName).getLocationID();
            defaultCheckboxClicked(locationDefaultCheckbox, locationID, DatabaseItemType.LOCATION);
        });

        supervisorDefaultCheckbox.setOnAction(e -> {
            String supervisorDisplayName = supervisorChoiceBox.getValue();
            SupervisorMapper supervisorMapper = new SupervisorMapper();
            Integer supervisorID = supervisorMapper.read(supervisorDisplayName).getSupervisorID();
            defaultCheckboxClicked(supervisorDefaultCheckbox, supervisorID, DatabaseItemType.SUPERVISOR);
        });

        submit.setOnMouseClicked(event -> submit());

        /* If a choicebox start outs out with a null value,
         * then disable its Edit/Delete buttons. Also, set the action listener
         * of the choicebox to enable the buttons when the choicebox
         * does not have a null value and disable the buttons when the
         * choicebox does have a null value.
         */
        BiConsumer<ChoiceBox<String>, List<Button>> disableButtonsIfChoiceboxValueIsNull = (choiceBox, buttons) -> {
            if (choiceBox.getValue() == null) {
                for (Button button : buttons) {
                    button.setDisable(true);
                }
            }
            choiceBox.addEventHandler(ActionEvent.ACTION, event -> {
                if (choiceBox.getValue() == null) {
                    for (Button button : buttons) {
                        button.setDisable(true);
                    }
                } else {
                    for (Button button : buttons) {
                        button.setDisable(false);
                    }
                }
            });
        };

        List<Button> companyButtons = new ArrayList<>();
        companyButtons.add(editCompanyButton);
        companyButtons.add(deleteCompanyButton);
        disableButtonsIfChoiceboxValueIsNull.accept(companyChoiceBox, companyButtons);

        List<Button> locationButtons = new ArrayList<>();
        locationButtons.add(editLocationButton);
        locationButtons.add(deleteLocationButton);
        disableButtonsIfChoiceboxValueIsNull.accept(locationChoiceBox, locationButtons);

        List<Button> supervisorButtons = new ArrayList<>();
        supervisorButtons.add(editSupervisorButton);
        supervisorButtons.add(deleteSupervisorButton);
        disableButtonsIfChoiceboxValueIsNull.accept(supervisorChoiceBox, supervisorButtons);

        companyChoiceBox.addEventHandler(ActionEvent.ACTION, e -> {
            Integer defaultCompanyID = getDefaultChoiceboxItemID(DatabaseItemType.COMPANY);
            if (defaultCompanyID != null && companyChoiceBox.getValue() != null) {
                CompanyMapper companyMapper = new CompanyMapper();
                Company defaultCompany = companyMapper.read(defaultCompanyID);
                if (companyChoiceBox.getValue().equals(defaultCompany.getCompanyName())) {
                    companyDefaultCheckbox.setSelected(true);
                } else {
                    companyDefaultCheckbox.setSelected(false);
                }
            }
        });

        locationChoiceBox.addEventHandler(ActionEvent.ACTION, e-> {
            Integer defaultLocationID = getDefaultChoiceboxItemID(DatabaseItemType.LOCATION);
            if (defaultLocationID != null && locationChoiceBox.getValue() != null) {
                LocationMapper locationMapper = new LocationMapper();
                Location defaultLocation = locationMapper.read(defaultLocationID);
                locationChoiceBox.getValue();
                if (locationChoiceBox.getValue().equals(defaultLocation.getLocationName())) {
                    locationDefaultCheckbox.setSelected(true);
                } else {
                    locationDefaultCheckbox.setSelected(false);
                }
            }
        });

        supervisorChoiceBox.addEventHandler(ActionEvent.ACTION, e-> {
            Integer defaultSupervisorID = getDefaultChoiceboxItemID(DatabaseItemType.SUPERVISOR);
            if (defaultSupervisorID != null && supervisorChoiceBox.getValue() != null) {
                SupervisorMapper supervisorMapper = new SupervisorMapper();
                Supervisor defaultSupervisor = supervisorMapper.read(defaultSupervisorID);
                if (supervisorChoiceBox.getValue().equals(defaultSupervisor.getSupervisorDisplayName())) {
                    supervisorDefaultCheckbox.setSelected(true);
                } else {
                    supervisorDefaultCheckbox.setSelected(false);
                }
            }
        });

        fillChoiceboxesWithDefaultChoices();
    }

    /**
     * Submits a work iteration, to the Hours_Worked log file, with all the
     * information given by the user pertaining to the work iteration
     */
    @FXML private void submit(){
        if (hours.getText().isEmpty()) {
            errorLabel.setVisible(true);
            return;
        }
        try {

            // The string that will be written to the log file
            StringBuilder logEntry = new StringBuilder();
            String newLine = System.lineSeparator();

            // Append the date
            LocalDateTime localDateTime = LocalDateTime.now();
            int day = localDateTime.getDayOfMonth();
            Month month = localDateTime.getMonth();
            int year = localDateTime.getYear();
            logEntry.append(newLine).append(newLine)
                    .append("Date: ").append(month).append(" ")
                    .append(day).append(", ").append(year);

            // Append the hours
            // TODO: find out what hours.toString() returns
            logEntry.append(newLine).append("Hours: ").append(hours.getCharacters());

            // Append the comments
            if (!comments.getText().isEmpty()) {
                logEntry.append(newLine).append("Comments: ").append(comments.getText());
            }

            // Append the location
            if (locationChoiceBox.getValue() != null) {
                LocationMapper locationMapper = new LocationMapper();
                Location location = locationMapper.read(locationChoiceBox.getValue().toString());
                logEntry.append(newLine).append("Location:").append(newLine)
                        .append(location.getLocationName()).append(newLine)
                        .append(location.getLocationAddress()).append(newLine)
                        .append(location.getLocationCity()).append(", ")
                        .append(location.getLocationState()).append(" ")
                        .append(location.getLocationZipCode());
            }

            // Append the company
            if (companyChoiceBox.getValue() != null) {
                CompanyMapper companyMapper = new CompanyMapper();
                Company company = companyMapper.read(companyChoiceBox.getValue().toString());
                logEntry.append(newLine).append("Company: ").append(company.getCompanyName());
            }

            // Append the supervisor
            if (supervisorChoiceBox.getValue() != null) {
                SupervisorMapper supervisorMapper = new SupervisorMapper();
                Supervisor supervisor = supervisorMapper.read(supervisorChoiceBox.getValue());
                logEntry.append(newLine).append("Supervisor: ")
                        .append(supervisor.getSupervisorDisplayName()).append(" (")
                        .append(supervisor.getSupervisorFirstName()).append(" ")
                        .append(supervisor.getSupervisorLastName()).append(")");
            }

            // Try appending to the current log file or create a new one if no log file exists
            FileWriter fileWriter = new FileWriter("Hours_Worked.txt", true);
            fileWriter.append(logEntry.toString());
            fileWriter.close();
        } catch (IOException e) {
            createErrorStage();
        }

        Platform.exit();
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
     * Sets the items for all choiceboxes in mainView
     */
    private void setAllChoiceBoxItems(){

        Consumer<ChoiceBox<String>> disableChoiceboxIfEmpty = (choiceBox) -> {
            if (choiceBox.getItems().isEmpty()) {
                choiceBox.setDisable(true);
            } else {choiceBox.setDisable(false);}
        };

        // Establish process for populating a choicebox
        BiConsumer<ChoiceBox<String>, ObservableList<String>> setChoiceBoxItems = (choiceBox, observableList) -> {
            observableList.addListener((ListChangeListener<String>) c -> {
                choiceBox.setItems(observableList);
                disableChoiceboxIfEmpty.accept(choiceBox);
            });
            choiceBox.setItems(observableList);
            disableChoiceboxIfEmpty.accept(choiceBox);
        };

        // Set company choicebox items
        sortedCompanyNames = new SortedList<>(companyNames, String.CASE_INSENSITIVE_ORDER);
        setChoiceBoxItems.accept(companyChoiceBox, sortedCompanyNames);

        // Set location choicebox items
        sortedLocationNames = new SortedList<>(locationNames, String.CASE_INSENSITIVE_ORDER);
        setChoiceBoxItems.accept(locationChoiceBox, sortedLocationNames);

        // Set supervisor choicebox items
        sortedSupervisorDisplayNames = new SortedList<>(supervisorDisplayNames, String.CASE_INSENSITIVE_ORDER);
        setChoiceBoxItems.accept(supervisorChoiceBox, sortedSupervisorDisplayNames);
    }

    private void createDatabaseItemModificationStage(String viewPath, String stageTitle) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
            Stage newStage = new Stage();
            newStage.setTitle(stageTitle);
            newStage.setScene(new Scene(loader.load()));
            DatabaseItemModificationController databaseItemModificationController = loader.getController();
            databaseItemModificationController.setMainViewController(this);
            databaseItemModificationController.setStage(newStage);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.show();
        } catch (IOException e) {
            createErrorStage();
        }
    }

    private void createErrorStage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kylewill/view/error.fxml"));
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

    private void createErrorStage(String customErrorMessage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kylewill/view/error.fxml"));
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

    // TODO: add javadoc
    private Integer getDefaultChoiceboxItemID(DatabaseItemType databaseItemType) {
        HashMap<DatabaseItemType, Integer> defaults = readFromDefaultsFile();
        return defaults.get(databaseItemType);
    }

    // TODO: add javadoc
    private void setDefaultChoiceboxItemID(DatabaseItemType databaseItemType, Integer databaseItemID) {
        HashMap<DatabaseItemType, Integer> defaultsHashMap = readFromDefaultsFile();
        defaultsHashMap.put(databaseItemType, databaseItemID);
        writeToDefaultsFile(defaultsHashMap);
    }

    // TODO: add javadoc
    private void fillChoiceboxesWithDefaultChoices() {
        Integer defaultCompanyID = getDefaultChoiceboxItemID(DatabaseItemType.COMPANY);
        Integer defaultLocationID = getDefaultChoiceboxItemID(DatabaseItemType.LOCATION);
        Integer defaultSupervisorID = getDefaultChoiceboxItemID(DatabaseItemType.SUPERVISOR);

        if (defaultCompanyID != null) {
            CompanyMapper companyMapper = new CompanyMapper();
            Company defaultCompany = companyMapper.read(defaultCompanyID);
            companyChoiceBox.setValue(defaultCompany.getCompanyName());
        }
        if (defaultLocationID != null) {
            LocationMapper locationMapper = new LocationMapper();
            Location defaultLocation = locationMapper.read(defaultLocationID);
            locationChoiceBox.setValue(defaultLocation.getLocationName());
        }
        if(defaultSupervisorID != null){
            SupervisorMapper supervisorMapper = new SupervisorMapper();
            Supervisor defaultSupervisor = supervisorMapper.read(defaultSupervisorID);
            supervisorChoiceBox.setValue(defaultSupervisor.getSupervisorDisplayName());
        }
    }

    // TODO: add javadoc
    private void defaultCheckboxClicked(CheckBox clickedCheckbox,
                                        Integer databaseItemID, DatabaseItemType databaseItemType){
        // If a defaults file does not exist, we'll need to create a new one.
        File defaultsFile = new File(DEFAULTS_FILE_PATH);
        if (!defaultsFile.exists()){
            writeNewDefaultsFile();
        }

        // If the user has unchecked the default checkbox
        // then they're only trying to remove the current default
        // for that choicebox - not set a new one. Thus, the new
        // value for that choicebox default should be null.
        if (!clickedCheckbox.isSelected()) {
            databaseItemID = null;
        }
        setDefaultChoiceboxItemID(databaseItemType, databaseItemID);
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
     * Reads in the defaults for each DatabaseItem choicebox
     *
     * @return either a <code>HashMap&lt;DatabaseItemType, Integer&gt;</code> representing the default
     * choices for each DatabaseItem choicebox or null if either a defaults file does not exist or
     * no <code>HashMap&lt;DatabaseItemType, Integer&gt;</code> was found in the preexisting defaults file
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
     * Writes the defaults for the DatabaseItem choiceboxes to the defaults file
     *
     * @param defaultsHashMap a <code>HashMap&lt;DatabaseItemType, Integer&gt;</code>
     * representing the default choices for each DatabaseItem choicebox
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

    @Override
    public void databaseItemWasCreated(DatabaseItem databaseItem) {
        if (databaseItem instanceof Company) {
            refreshCompanyNames();
            Company newCompany = (Company) databaseItem;
            companyChoiceBox.setValue(newCompany.getCompanyName());
        } else if (databaseItem instanceof Location) {
            refreshLocationNames();
            Location newLocation = (Location) databaseItem;
            locationChoiceBox.setValue(newLocation.getLocationName());
        } else if (databaseItem instanceof Supervisor) {
            refreshSupervisorDisplayNames();
            Supervisor newSupervisor = (Supervisor) databaseItem;
            supervisorChoiceBox.setValue(newSupervisor.getSupervisorDisplayName());
        }
    }

    @Override
    public void databaseItemWasUpdated(DatabaseItem databaseItem) {
        if (databaseItem instanceof Company) {
            refreshCompanyNames();
        } else if (databaseItem instanceof Location) {
            refreshLocationNames();
        } else if (databaseItem instanceof Supervisor) {
            refreshSupervisorDisplayNames();
        }
    }

    @Override
    public void databaseItemWasDeleted(DatabaseItem databaseItem) {
        if (databaseItem instanceof Company) {
            refreshCompanyNames();
            // If the deleted DatabaseItem was a default for its
            // choicebox, then we need to remove it from the defaults.
            Company oldCompany = (Company) databaseItem;
            Integer defaultCompanyID = getDefaultChoiceboxItemID(DatabaseItemType.COMPANY);
            if (defaultCompanyID.equals(oldCompany.getCompanyID())) {
                setDefaultChoiceboxItemID(DatabaseItemType.COMPANY, null);
                companyDefaultCheckbox.setSelected(false);
            }
        } else if (databaseItem instanceof Location) {
            refreshLocationNames();
            // If the deleted DatabaseItem was a default for its
            // choicebox, then we need to remove it from the defaults.
            Location oldLocation = (Location) databaseItem;
            Integer defaultLocationID = getDefaultChoiceboxItemID(DatabaseItemType.LOCATION);
            if (defaultLocationID.equals(oldLocation.getLocationID())) {
                setDefaultChoiceboxItemID(DatabaseItemType.LOCATION, null);
                locationDefaultCheckbox.setSelected(false);
            }
        } else if (databaseItem instanceof Supervisor) {
            refreshSupervisorDisplayNames();
            // If the deleted DatabaseItem was a default for its
            // choicebox, then we need to remove it from the defaults.
            Supervisor oldSupervisor = (Supervisor) databaseItem;
            Integer defaultSupervisorId = getDefaultChoiceboxItemID(DatabaseItemType.SUPERVISOR);
            if (defaultSupervisorId.equals(oldSupervisor.getSupervisorID())) {
                setDefaultChoiceboxItemID(DatabaseItemType.SUPERVISOR, null);
                supervisorDefaultCheckbox.setSelected(false);
            }
        }
    }
}
