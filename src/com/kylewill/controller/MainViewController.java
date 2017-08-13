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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.ResourceBundle;

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
    @FXML private TextArea comments;
    @FXML private Button submit;

    public MainViewController(){
        refreshCompanyNames();
        refreshLocationNames();
        refreshSupervisorDisplayNames();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setChoiceBoxItems();
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
    }

    /**
     * Submits a work iteration, to the Hours_Worked log file, with all the
     * information given by the user pertaining to the work iteration
     */
    @FXML private void submit(){
        try {
            // TODO: Add error dialogs for issues - such as the hours field being blank
            // TODO: maybe an entire function dedicated to field checking --- ex.  checkFields()

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

            // Append the company
            if (companyChoiceBox.getValue() != null) {
                CompanyMapper companyMapper = new CompanyMapper();
                Company company = companyMapper.read(companyChoiceBox.getValue().toString());
                logEntry = logEntry.concat(System.lineSeparator()
                        + "Company: " + company.getCompanyName());
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

            // Append the comments
            if (!comments.getText().isEmpty()) {
                logEntry = logEntry.concat(System.lineSeparator()
                        + "Comments: " + comments.getText()
                );
            }

            // Try appending to the current log file or create a new one if no log file exists
            FileWriter fileWriter = new FileWriter("Hours_Worked.txt", true);
            fileWriter.append(logEntry);
            fileWriter.close();
        } catch (IOException e) {
            //TODO: Handle exception
            System.err.println(e);
            System.exit(1);
        }

        Platform.exit();
    }

    @FXML private void addCompany(){
        try {
            String viewPath = "/com/kylewill/view/addCompany.fxml";
            String stageTitle = "Add Company";
            createDatabaseItemModificationStage(viewPath, stageTitle);
        }catch (IOException e){
            //TODO: Handle exception
            System.err.println(e);
        }
    }

    @FXML private void editCompany(){
        if (companyChoiceBox.getValue() == null) {
            return;
        }
        try {
            String viewPath = "/com/kylewill/view/editCompany.fxml";
            String stageTitle = "Edit Company";
            createDatabaseItemModificationStage(viewPath, stageTitle);
        } catch (IOException e){
            //TODO: Handle Exception
            System.err.println(e);
        }
    }

    @FXML private void deleteCompany(){
        if (companyChoiceBox.getValue() == null) {
            return;
        }
        try {
            String viewPath = "/com/kylewill/view/deleteCompany.fxml";
            String stageTitle = "Delete Company";
            createDatabaseItemModificationStage(viewPath, stageTitle);
        }catch (IOException e){
            //TODO: Handle exception
            System.err.println(e);
        }
    }

    @FXML private void addLocation(){
        try {
            String viewPath = "/com/kylewill/view/addLocation.fxml";
            String stageTitle = "Add Location";
            createDatabaseItemModificationStage(viewPath, stageTitle);
        }catch (IOException e){
            //TODO: Handle exception
            System.err.println(e);
        }
    }

    @FXML private void editLocation(){
        if (locationChoiceBox.getValue() == null) {
            return;
        }
        try {
            String viewPath = "/com/kylewill/view/editLocation.fxml";
            String stageTitle = "Edit Location";
            createDatabaseItemModificationStage(viewPath, stageTitle);
        } catch (IOException e){
            //TODO: Handle Exception
            System.err.println(e);
        }
    }

    @FXML private void deleteLocation(){
        if (locationChoiceBox.getValue() == null) {
            return;
        }
        try {
            String viewPath = "/com/kylewill/view/deleteLocation.fxml";
            String stageTitle = "Delete Location";
            createDatabaseItemModificationStage(viewPath, stageTitle);
        }catch (IOException e){
            //TODO: Handle exception
            System.err.println(e);
        }
    }

    @FXML private void addSupervisor(){
        try {
            String viewPath = "/com/kylewill/view/addSupervisor.fxml";
            String stageTitle = "Add Supervisor";
            createDatabaseItemModificationStage(viewPath, stageTitle);
        }catch (IOException e){
            //TODO: Handle exception
            System.err.println(e);
        }
    }

    @FXML private void editSupervisor(){
        if (supervisorChoiceBox.getValue() == null) {
            return;
        }
        try {
            String viewPath = "/com/kylewill/view/editSupervisor.fxml";
            String stageTitle = "Edit Supervisor";
            createDatabaseItemModificationStage(viewPath, stageTitle);
        }catch (IOException e){
            //TODO: Handle exception
            System.err.println(e);
        }
    }

    @FXML private void deleteSupervisor(){
        if (supervisorChoiceBox.getValue() == null) {
            return;
        }
        try {
            String viewPath = "/com/kylewill/view/deleteSupervisor.fxml";
            String stageTitle = "DeleteSupervisor";
            createDatabaseItemModificationStage(viewPath, stageTitle);
        }catch (IOException e){
            //TODO: Handle exception
            System.err.println(e);
        }
    }

    /**
     * Sets the items for all choiceboxes in mainView
     */
    private void setChoiceBoxItems(){
        // Set company choicebox items
        sortedCompanyNames = new SortedList<>(companyNames, String.CASE_INSENSITIVE_ORDER);
        sortedCompanyNames.addListener((ListChangeListener<String>) c ->
                companyChoiceBox.setItems(sortedCompanyNames));
        companyChoiceBox.setItems(sortedCompanyNames);

        // Set location choicebox items
        sortedLocationNames = new SortedList<>(locationNames, String.CASE_INSENSITIVE_ORDER);
        sortedLocationNames.addListener((ListChangeListener<String>) c ->
                locationChoiceBox.setItems(sortedLocationNames));
        locationChoiceBox.setItems(sortedLocationNames);

        // Set supervisor choicebox items
        sortedSupervisorDisplayNames = new SortedList<>(supervisorDisplayNames, String.CASE_INSENSITIVE_ORDER);
        sortedSupervisorDisplayNames.addListener((ListChangeListener<String>) c ->
                supervisorChoiceBox.setItems(sortedSupervisorDisplayNames));
        supervisorChoiceBox.setItems(sortedSupervisorDisplayNames);
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

    private void createDatabaseItemModificationStage(String viewPath, String stageTitle) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
        Stage newStage = new Stage();
        newStage.setTitle(stageTitle);
        newStage.setScene(new Scene(loader.load()));
        DatabaseItemModificationController databaseItemModificationController = loader.getController();
        databaseItemModificationController.setMainViewController(this);
        databaseItemModificationController.setStage(newStage);
        newStage.show();
    }
}
