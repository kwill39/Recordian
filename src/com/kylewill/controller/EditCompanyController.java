package com.kylewill.controller;

import com.kylewill.model.Company;
import com.kylewill.objectrelationalmaps.CompanyMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditCompanyController implements Initializable {
    private String nameOfCompanyToEdit;
    private MainViewController mainViewController;
    private Stage stage;
    private @FXML Button saveButton;
    private @FXML Button cancelButton;
    private @FXML TextField companyName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveButton.setOnMouseClicked(event -> updateCompany());
        cancelButton.setOnMouseClicked(event -> stage.close());
    }

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setNameOfCompanyToEdit(String nameOfCompanyToEdit) {
        this.nameOfCompanyToEdit = nameOfCompanyToEdit;
        // TODO: Find a better way to set the text of companyName - currently setting it outside the initialize method
        companyName.setText(nameOfCompanyToEdit);
    }

    private void updateCompany() {
        List<Company> companies = CompanyMapper.readAll();
        for (Company someCompany : companies) {
            if (someCompany.getCompanyName().equals(nameOfCompanyToEdit)){
                someCompany.setCompanyName(companyName.getText());
                CompanyMapper.update(someCompany);
            }
        }
        mainViewController.refreshCompanyNames();
        stage.close();
    }
}
