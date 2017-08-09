package com.kylewill.controller.databaseitemcontroller;

import com.kylewill.model.Company;
import com.kylewill.objectrelationalmap.CompanyMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditCompanyController extends DatabaseItemController implements Initializable {
    private Company companyToEdit;
    private @FXML Button saveButton;
    private @FXML Button cancelButton;
    private @FXML TextField companyName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveButton.setOnMouseClicked(event -> updateCompany());
        cancelButton.setOnMouseClicked(event -> stage.close());
    }

    @Override
    protected void onMainViewControllerSet() {
        String nameOfCompanyToEdit = mainViewController.companyChoiceBox.getValue().toString();
        List<Company> companies = CompanyMapper.readAll();
        for (Company someCompany : companies) {
            if (someCompany.getCompanyName().equals(nameOfCompanyToEdit)){
                companyToEdit = someCompany;
            }
        }
        companyName.setText(companyToEdit.getCompanyName());
    }

    private void updateCompany() {
        companyToEdit.setCompanyName(companyName.getText());
        CompanyMapper.update(companyToEdit);
        mainViewController.refreshCompanyNames();
        stage.close();
    }
}
