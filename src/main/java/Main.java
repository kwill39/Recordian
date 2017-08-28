import controller.MainTabPaneController;
import databasemanagement.DatabaseHelper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        // Create a new database file if one does not currently exist
        DatabaseHelper.createDatabase();

        // Creates the main view of the application
        FXMLLoader root = new FXMLLoader(getClass().getResource("/view/mainTabPane.fxml"));
        primaryStage.setTitle("Kyle's Hour Tracker");
        primaryStage.setScene(new Scene(root.load()));
        primaryStage.setMinHeight(449);
        primaryStage.setMinWidth(679);
        primaryStage.setOnCloseRequest(event -> Platform.exit());
        MainTabPaneController mainTabPaneController = root.getController();
        mainTabPaneController.setCurrentStage(primaryStage);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}