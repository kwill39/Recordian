package com.kylewill.controller;

import com.kylewill.model.Company;
import com.kylewill.objectrelationalmaps.CompanyMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCompanyController implements Initializable{
    private MainViewController mainViewController;
    private Stage stage;
    @FXML private TextField newCompanyName;
    @FXML private Button addButton;
    @FXML private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addButton.setOnMouseClicked(event -> addCompany());
        cancelButton.setOnMouseClicked(event -> stage.close());
    }

    void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }

    private void addCompany() {
        Company newCompany = new Company(newCompanyName.getText());
        CompanyMapper.create(newCompany);
        mainViewController.refreshCompanyNames();
        stage.close();
    }
}
