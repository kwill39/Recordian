package com.kylewill.controller.databaseitemcontroller;

import com.kylewill.model.Company;
import com.kylewill.databasemanagement.objectrelationalmap.CompanyMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author  Kyle Williams
 * @since   Version 2
 */
public class AddCompanyController extends DatabaseItemModificationController implements Initializable{
    private CompanyMapper companyMapper = new CompanyMapper();
    @FXML private TextField newCompanyName;
    @FXML private Label errorLabel;
    @FXML private Button addButton;
    @FXML private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addButton.setOnMouseClicked(event -> addCompany());
        cancelButton.setOnMouseClicked(event -> stage.close());
    }

    private void addCompany() {
        if (newCompanyName.getText().isEmpty()) {
            errorLabel.setVisible(true);
        } else {
            Company newCompany = new Company(newCompanyName.getText());
            companyMapper.create(newCompany);
            stage.close();
        }
    }
}
