import controller.MainTabPaneController;
import databasemanagement.DatabaseHelper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author  Kyle Williams
 * @since   Version 1
 */
public class Main extends Application {

    private DatabaseHelper databaseHelper = new DatabaseHelper();

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Create a new database file if one does not currently exist
        databaseHelper.createDatabase();
        Stage mainView = createMainViewOfRecordian(primaryStage);
        mainView.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private Stage createMainViewOfRecordian(Stage primaryStage) throws Exception {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/view/mainTabPane.fxml"));
        primaryStage.setTitle("Recordian");
        primaryStage.setScene(new Scene(root.load()));
        primaryStage.setOnCloseRequest(event -> Platform.exit());
        MainTabPaneController mainTabPaneController = root.getController();
        mainTabPaneController.setCurrentStage(primaryStage);
        return primaryStage;
    }
}