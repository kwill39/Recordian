package com.kylewill.controller;

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

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController implements Initializable{
    private ObservableList<String> companyNames = FXCollections.observableArrayList();
    private ObservableList<String> locationNames = FXCollections.observableArrayList();
    private ObservableList<String> supervisorDisplayNames = FXCollections.observableArrayList();
    private ObservableList<String> sortedCompanyNames;
    private ObservableList<String> sortedLocationNames;
    private ObservableList<String> sortedSupervisorDisplayNames;
    @FXML private TextField hours;
    @FXML private ChoiceBox<String> companyChoiceBox;
    @FXML private Button addCompany;
    @FXML private Button editCompany;
    @FXML private Button deleteCompany;
    @FXML private ChoiceBox<String> locationChoiceBox;
    @FXML private Button addLocation;
    @FXML private Button editLocation;
    @FXML private Button deleteLocation;
    @FXML private ChoiceBox<String> supervisorChoiceBox;
    @FXML private Button addSupervisor;
    @FXML private Button editSupervisor;
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
        //editSupervisor.setOnMouseClicked(event -> editSupervisor());
        deleteSupervisor.setOnMouseClicked(event -> deleteSupervisor());
        submit.setOnMouseClicked(event -> submitHours());
    }

    @FXML private void submitHours(){
        try {
            //TODO: Add error dialogs for Exceptions
            // Try appending to the current log file or create a new one if there is no log file
            FileWriter fileWriter = new FileWriter("Hours_Worked.txt", true);
            LocalDateTime localDateTime = LocalDateTime.now();
            int day = localDateTime.getDayOfMonth();
            Month month = localDateTime.getMonth();
            int year = localDateTime.getYear();
            String logEntry = System.lineSeparator() + System.lineSeparator()
                    + month.toString() + " "
                    + day + ", "
                    + year + System.lineSeparator()
                    + "Hours: " + hours.getCharacters().toString() + System.lineSeparator()
                    + "Location: " + locationChoiceBox.getValue() + System.lineSeparator()
                    + "Company: " + companyChoiceBox.getValue() + System.lineSeparator()
                    + "Supervisor: " + supervisorChoiceBox.getValue() + System.lineSeparator()
                    + "Comments: " + comments.getText();
            fileWriter.append(logEntry);
            fileWriter.close();
        } catch (IOException exception) {
            System.err.println("Caught IOException: " + exception.getMessage());
            System.exit(1);
        }

        Platform.exit();
    }

    @FXML private void addCompany(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kylewill/view/addCompany.fxml"));
            Stage addCompanyStage = new Stage();
            addCompanyStage.setTitle("Add Company");
            addCompanyStage.setScene(new Scene(loader.load()));
            AddCompanyController addCompanyController = loader.getController();
            addCompanyController.setMainViewController(this);
            addCompanyController.setStage(addCompanyStage);
            addCompanyStage.show();
        }catch (IOException e){
            //TODO: Handle exception
            System.err.println(e);
        }
    }

    @FXML private void editCompany(){
        String companyName = companyChoiceBox.getValue();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kylewill/view/editCompany.fxml"));
            Stage editCompanyStage = new Stage();
            editCompanyStage.setTitle("Edit Company");
            editCompanyStage.setScene(new Scene(loader.load()));
            EditCompanyController editCompanyController = loader.getController();
            editCompanyController.setMainViewController(this);
            editCompanyController.setStage(editCompanyStage);
            editCompanyController.setNameOfCompanyToEdit(companyName);
            editCompanyStage.show();
        } catch (IOException e){
            //TODO: Handle Exception
            System.err.println(e);
        }
    }

    @FXML private void deleteCompany(){
        String companyName = companyChoiceBox.getValue();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kylewill/view/deleteCompany.fxml"));
            Stage deleteCompanyStage = new Stage();
            deleteCompanyStage.setTitle("Delete Company");
            deleteCompanyStage.setScene(new Scene(loader.load()));
            DeleteCompanyController deleteCompanyController = loader.getController();
            deleteCompanyController.setMainViewController(this);
            deleteCompanyController.setStage(deleteCompanyStage);
            deleteCompanyController.setNameOfCompanyToDelete(companyName);
            deleteCompanyStage.show();
        }catch (IOException e){
            //TODO: Handle exception
            System.err.println(e);
        }
    }

    @FXML private void addLocation(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kylewill/view/addLocation.fxml"));
            Stage addLocationStage = new Stage();
            addLocationStage.setTitle("Add Location");
            addLocationStage.setScene(new Scene(loader.load()));
            AddLocationController addLocationController = loader.getController();
            addLocationController.setMainViewController(this);
            addLocationController.setStage(addLocationStage);
            addLocationStage.show();
        }catch (IOException e){
            //TODO: Handle exception
            System.err.println(e);
        }
    }

    @FXML private void editLocation(){
        String locationName = locationChoiceBox.getValue();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kylewill/view/editLocation.fxml"));
            Stage editLocationStage = new Stage();
            editLocationStage.setTitle("Edit Location");
            editLocationStage.setScene(new Scene(loader.load()));
            EditLocationController editLocationController = loader.getController();
            editLocationController.setMainViewController(this);
            editLocationController.setStage(editLocationStage);
            editLocationController.setNameOfLocationToEdit(locationName);
            editLocationStage.show();
        } catch (IOException e){
            //TODO: Handle Exception
            System.err.println(e);
        }
    }

    @FXML private void deleteLocation(){
        String locationName = locationChoiceBox.getValue();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kylewill/view/deleteLocation.fxml"));
            Stage deleteLocationStage = new Stage();
            deleteLocationStage.setTitle("Delete Location");
            deleteLocationStage.setScene(new Scene(loader.load()));
            DeleteLocationController deleteLocationController = loader.getController();
            deleteLocationController.setMainViewController(this);
            deleteLocationController.setStage(deleteLocationStage);
            deleteLocationController.setNameOfLocationToDelete(locationName);
            deleteLocationStage.show();
        }catch (IOException e){
            //TODO: Handle exception
            System.err.println(e);
        }
    }

    @FXML private void addSupervisor(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kylewill/view/addSupervisor.fxml"));
            Stage addSupervisorStage = new Stage();
            addSupervisorStage.setTitle("Add Supervisor");
            addSupervisorStage.setScene(new Scene(loader.load()));
            AddSupervisorController addSupervisorController = loader.getController();
            addSupervisorController.setMainViewController(this);
            addSupervisorController.setStage(addSupervisorStage);
            addSupervisorStage.show();
        }catch (IOException e){
            //TODO: Handle exception
            System.err.println(e);
        }
    }

    /*@FXML private void editSupervisor(){
        // Updates a supervisor record inside the db
    }*/

    @FXML private void deleteSupervisor(){
        String supervisorDisplayName = companyChoiceBox.getValue();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/kylewill/view/deleteSupervisor.fxml"));
            Stage deleteSupervisorStage = new Stage();
            deleteSupervisorStage.setTitle("Delete Supervisor");
            deleteSupervisorStage.setScene(new Scene(loader.load()));
            DeleteSupervisorController deleteSupervisorController = loader.getController();
            deleteSupervisorController.setMainViewController(this);
            deleteSupervisorController.setStage(deleteSupervisorStage);
            deleteSupervisorController.setDisplayNameOfSupervisorToDelete(supervisorDisplayName);
            deleteSupervisorStage.show();
        }catch (IOException e){
            //TODO: Handle exception
            System.err.println(e);
        }
    }

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
                locationChoiceBox.setItems(sortedSupervisorDisplayNames));
        supervisorChoiceBox.setItems(sortedSupervisorDisplayNames);
    }

    public void refreshCompanyNames(){
        companyNames.clear();
        List<Company> companies = CompanyMapper.readAll();
        for (Company someCompany : companies){
            companyNames.add(someCompany.getCompanyName());
        }
    }

    public void refreshLocationNames(){
        locationNames.clear();
        List<Location> locations = LocationMapper.readAll();
        for (Location someLocation : locations) {
            locationNames.add(someLocation.getLocationName());
        }
    }

    public void refreshSupervisorDisplayNames(){
        supervisorDisplayNames.clear();
        List<Supervisor> supervisors = SupervisorMapper.readAll();
        for (Supervisor someSupervisor : supervisors) {
            supervisorDisplayNames.add(someSupervisor.getSupervisorDisplayName());
        }
    }

    /*private void createModifyDatabaseItemStage(String choiceBoxItem, String stageTitle, String viewPath, Datab) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
        Stage newStage = new Stage();
        newStage.setTitle(stageTitle);
        newStage.setScene(new Scene(loader.load()));
        DeleteSupervisorController deleteSupervisorController = loader.getController();
        deleteSupervisorController.setMainViewController(this);
        deleteSupervisorController.setStage(newStage);
        newStage.show();
    }*/
}
