package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class Controller {
    @FXML private TextField hours;
    @FXML private Button submit;


    @FXML private void submitHours(ActionEvent actionEvent){
        String hoursWorked = hours.getCharacters().toString();
        Path path = FileSystems.getDefault().getPath("", "Hours_Worked");
        try {
            File newFile = Files.createFile(path,)
        }
    }
}
