package com.kylewill.controller;

import com.kylewill.controller.databaseitemcontroller.DatabaseItemModificationController;
import com.kylewill.model.Company;
import com.kylewill.model.Location;
import com.kylewill.model.Supervisor;
import com.kylewill.objectrelationalmap.CompanyMapper;
import com.kylewill.objectrelationalmap.LocationMapper;
import com.kylewill.objectrelationalmap.SupervisorMapper;
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
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author  Kyle Williams
 * @since   Version 2
 */
public class MainViewController implements Initializable{
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
    @FXML private Button addCompany;
    @FXML private Button addLocation;
    @FXML private Button addSupervisor;
    @FXML private Button editCompany;
    @FXML private Button editLocation;
    @FXML private Button editSupervisor;
    @FXML private Button deleteCompany;
    @FXML private Button deleteLocation;
    @FXML private Button deleteSupervisor;
    @FXML private Label errorLabel;
    @FXML private Button submit;

    public MainViewController(){
        refreshCompanyNames();
        refreshLocationNames();
        refreshSupervisorDisplayNames();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAllChoiceBoxItems();

        // Set button onClick events
        addCompany.setOnMouseClicked(event -> addCompany());
        editCompany.setOnMouseClicked(event -> editCompany());
        deleteCompany.setOnMouseClicked(event -> deleteCompany());
        addLocation.setOnMouseClicked(event -> addLocation());
        editLocation.setOnMouseClicked(event -> editLocation());
        deleteLocation.setOnMouseClicked(event -> deleteLocation());
        addSupervisor.setOnMouseClicked(event -> addSupervisor());
        editSupervisor.setOnMouseClicked(event -> editSupervisor());
        deleteSupervisor.setOnMouseClicked(event -> deleteSupervisor());
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
            choiceBox.setOnAction(event -> {
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
        companyButtons.add(editCompany);
        companyButtons.add(deleteCompany);
        disableButtonsIfChoiceboxValueIsNull.accept(companyChoiceBox, companyButtons);

        List<Button> locationButtons = new ArrayList<>();
        locationButtons.add(editLocation);
        locationButtons.add(deleteLocation);
        disableButtonsIfChoiceboxValueIsNull.accept(locationChoiceBox, locationButtons);

        List<Button> supervisorButtons = new ArrayList<>();
        supervisorButtons.add(editSupervisor);
        supervisorButtons.add(deleteSupervisor);
        disableButtonsIfChoiceboxValueIsNull.accept(supervisorChoiceBox, supervisorButtons);
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
            String logEntry = "";

            // Append the date
            LocalDateTime localDateTime = LocalDateTime.now();
            int day = localDateTime.getDayOfMonth();
            Month month = localDateTime.getMonth();
            int year = localDateTime.getYear();
            logEntry = logEntry.concat(System.lineSeparator() + System.lineSeparator()
                    + "Date: " + month.toString() + " " + day + ", " + year);

            // Append the hours
            logEntry = logEntry.concat(System.lineSeparator()
                    + "Hours: " + hours.getCharacters().toString());

            // Append the comments
            if (!comments.getText().isEmpty()) {
                logEntry = logEntry.concat(System.lineSeparator()
                        + "Comments: " + comments.getText()
                );
            }

            // Append the location
            if (locationChoiceBox.getValue() != null) {
                LocationMapper locationMapper = new LocationMapper();
                Location location = locationMapper.read(locationChoiceBox.getValue().toString());
                logEntry = logEntry.concat(System.lineSeparator()
                        + "Location: " + System.lineSeparator()
                        + location.getLocationName() + System.lineSeparator()
                        + location.getLocationAddress() + System.lineSeparator()
                        + location.getLocationCity() + ", " + location.getLocationState()
                        + " " + location.getLocationZipCode()
                );
            }

            // Append the company
            if (companyChoiceBox.getValue() != null) {
                CompanyMapper companyMapper = new CompanyMapper();
                Company company = companyMapper.read(companyChoiceBox.getValue().toString());
                logEntry = logEntry.concat(System.lineSeparator()
                        + "Company: " + company.getCompanyName());
            }

            // Append the supervisor
            if (supervisorChoiceBox.getValue() != null) {
                SupervisorMapper supervisorMapper = new SupervisorMapper();
                Supervisor supervisor = supervisorMapper.read(supervisorChoiceBox.getValue());
                logEntry = logEntry.concat(System.lineSeparator()
                        + "Supervisor: " + supervisor.getSupervisorDisplayName() + " ("
                        + supervisor.getSupervisorFirstName() + " "
                        + supervisor.getSupervisorLastName() + ")"
                );
            }

            // Try appending to the current log file or create a new one if no log file exists
            FileWriter fileWriter = new FileWriter("Hours_Worked.txt", true);
            fileWriter.append(logEntry);
            fileWriter.close();
        } catch (IOException e) {
            createErrorStage();
        }

        Platform.exit();
    }

    @FXML private void addCompany(){
        String viewPath = "/com/kylewill/view/addCompany.fxml";
        String stageTitle = "Add Company";
        createDatabaseItemModificationStage(viewPath, stageTitle);
    }

    @FXML private void editCompany(){
        if (companyChoiceBox.getValue() == null) {
            return;
        }
        String viewPath = "/com/kylewill/view/editCompany.fxml";
        String stageTitle = "Edit Company";
        createDatabaseItemModificationStage(viewPath, stageTitle);
    }

    @FXML private void deleteCompany(){
        if (companyChoiceBox.getValue() == null) {
            return;
        }
        String viewPath = "/com/kylewill/view/deleteCompany.fxml";
        String stageTitle = "Delete Company";
        createDatabaseItemModificationStage(viewPath, stageTitle);
    }

    @FXML private void addLocation(){
        String viewPath = "/com/kylewill/view/addLocation.fxml";
        String stageTitle = "Add Location";
        createDatabaseItemModificationStage(viewPath, stageTitle);
    }

    @FXML private void editLocation(){
        if (locationChoiceBox.getValue() == null) {
            return;
        }
        String viewPath = "/com/kylewill/view/editLocation.fxml";
        String stageTitle = "Edit Location";
        createDatabaseItemModificationStage(viewPath, stageTitle);
    }

    @FXML private void deleteLocation(){
        if (locationChoiceBox.getValue() == null) {
            return;
        }
        String viewPath = "/com/kylewill/view/deleteLocation.fxml";
        String stageTitle = "Delete Location";
        createDatabaseItemModificationStage(viewPath, stageTitle);
    }

    @FXML private void addSupervisor(){
        String viewPath = "/com/kylewill/view/addSupervisor.fxml";
        String stageTitle = "Add Supervisor";
        createDatabaseItemModificationStage(viewPath, stageTitle);
    }

    @FXML private void editSupervisor(){
        if (supervisorChoiceBox.getValue() == null) {
            return;
        }
        String viewPath = "/com/kylewill/view/editSupervisor.fxml";
        String stageTitle = "Edit Supervisor";
        createDatabaseItemModificationStage(viewPath, stageTitle);
    }

    @FXML private void deleteSupervisor(){
        if (supervisorChoiceBox.getValue() == null) {
            return;
        }
        String viewPath = "/com/kylewill/view/deleteSupervisor.fxml";
        String stageTitle = "Delete Supervisor";
        createDatabaseItemModificationStage(viewPath, stageTitle);
    }

    /**
     * This method should be called in order to update <code>companyNames<code/>
     * whenever the SQLite companies table is changed.
     */
    public void refreshCompanyNames(){
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
    public void refreshLocationNames(){
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
    public void refreshSupervisorDisplayNames(){
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
            errorStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
