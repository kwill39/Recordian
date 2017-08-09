package com.kylewill.controller;

import com.kylewill.model.Company;
import com.kylewill.objectrelationalmap.CompanyMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCompanyController extends DatabaseItemController implements Initializable{
    @FXML private TextField newCompanyName;
    @FXML private Button addButton;
    @FXML private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addButton.setOnMouseClicked(event -> addCompany());
        cancelButton.setOnMouseClicked(event -> stage.close());
    }

    private void addCompany() {
        Company newCompany = new Company(newCompanyName.getText());
        CompanyMapper.create(newCompany);
        mainViewController.refreshCompanyNames();
        stage.close();
    }
}
