package com.kylewill.controller.databaseitemcontroller;

import com.kylewill.controller.MainViewController;
import com.kylewill.model.Company;
import com.kylewill.objectrelationalmap.CompanyMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditCompanyController extends DatabaseItemController implements Initializable {
    private String nameOfCompanyToEdit;
    private @FXML Button saveButton;
    private @FXML Button cancelButton;
    private @FXML TextField companyName;

    public EditCompanyController() {
        nameOfCompanyToEdit = mainViewController.companyChoiceBox.getValue().toString();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveButton.setOnMouseClicked(event -> updateCompany());
        cancelButton.setOnMouseClicked(event -> stage.close());
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
