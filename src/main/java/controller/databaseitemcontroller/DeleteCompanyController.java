package controller.databaseitemcontroller;

import model.Company;
import databasemanagement.objectrelationalmap.CompanyMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author  Kyle Williams
 * @since   Version 2
 */
public class DeleteCompanyController extends DatabaseItemModificationController implements Initializable{
    private CompanyMapper companyMapper = new CompanyMapper();
    private Company companyToDelete;
    @FXML private Label confirmationLabel;
    @FXML private Button deleteButton;
    @FXML private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cancelButton.setOnMouseClicked(event -> stage.close());
        deleteButton.setOnMouseClicked(event -> deleteCompany());
    }

    @Override
    protected void onMainViewControllerSet() {
        String nameOfcompanyToDelete = mainViewController.companyChoiceBox.getValue().toString();
        companyToDelete = companyMapper.read(nameOfcompanyToDelete);
        confirmationLabel.setText("Are you sure you want to delete " + companyToDelete.getCompanyName() + "?");
    }

    private void deleteCompany() {
        companyMapper.delete(companyToDelete);
        stage.close();
    }
}