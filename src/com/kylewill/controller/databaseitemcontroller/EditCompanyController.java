package com.kylewill.controller.databaseitemcontroller;

import com.kylewill.controller.MainViewController;
import com.kylewill.model.Company;
import com.kylewill.databasemanagement.objectrelationalmap.CompanyMapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author  Kyle Williams
 * @since   Version 2
 */
public class EditCompanyController extends DatabaseItemModificationController implements Initializable {
    private CompanyMapper companyMapper = new CompanyMapper();
    private Company companyToEdit;
    private String oldNameOfCompanyToEdit;
    @FXML private TextField companyName;
    @FXML private Label errorLabel;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveButton.setOnMouseClicked(event -> updateCompany());
        cancelButton.setOnMouseClicked(event -> stage.close());
    }

    @Override
    protected void onMainViewControllerSet() {
        oldNameOfCompanyToEdit = mainViewController.companyChoiceBox.getValue().toString();
        companyToEdit = companyMapper.read(oldNameOfCompanyToEdit);
        companyName.setText(companyToEdit.getCompanyName());
    }

    private void updateCompany() {
        if (companyName.getText().isEmpty()) {
            errorLabel.setText("Please fill in all fields");
            errorLabel.setVisible(true);
            return;
        }
        List<Company> companies = companyMapper.readAll();
        for (Company company : companies) {
            if (!company.getCompanyName().equals(oldNameOfCompanyToEdit)
                    && company.getCompanyName().equals(companyName.getText())){
                errorLabel.setText("Company name already exists");
                errorLabel.setVisible(true);
                return;
            }
        }
        companyToEdit.setCompanyName(companyName.getText());
        companyMapper.update(companyToEdit);
        stage.close();
    }
}
