package com.kylewill.controller;

import com.kylewill.model.Company;
import com.kylewill.objectrelationalmaps.CompanyMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DeleteCompanyController implements Initializable{
    private MainViewController mainViewController;
    private Stage stage;
    private String nameOfcompanyToDelete;
    @FXML private Button deleteButton;
    @FXML private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cancelButton.setOnMouseClicked(event -> stage.close());
        deleteButton.setOnMouseClicked(event -> deleteCompany());
    }

    void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setNameOfCompanyToDelete(String nameOfcompanyToDelete) {
        this.nameOfcompanyToDelete = nameOfcompanyToDelete;
    }

    private void deleteCompany() {
        List<Company> companies = CompanyMapper.readAll();
        for (Company someCompany : companies){
            if (someCompany.getCompanyName().equals(nameOfcompanyToDelete)){
                CompanyMapper.delete(someCompany);
            }
        }
        mainViewController.refreshCompanyNames();
        stage.close();
    }
}
