package com.kylewill.controller.databaseitemcontroller;

import com.kylewill.model.Company;
import com.kylewill.objectrelationalmap.CompanyMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCompanyController extends DatabaseItemController implements Initializable{
    private CompanyMapper companyMapper = new CompanyMapper();
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
        companyMapper.create(newCompany);
        mainViewController.refreshCompanyNames();
        stage.close();
    }
}
