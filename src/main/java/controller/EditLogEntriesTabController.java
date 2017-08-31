package controller;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import databasemanagement.LogFileHelper;
import databasemanagement.objectrelationalmap.LogEntryMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.LogEntry;
import org.apache.commons.text.WordUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.BiFunction;

/**
 * @author  Kyle Williams
 * @since   Version 3
 */
public class EditLogEntriesTabController implements Initializable {
    private Stage currentStage;
    private MainTabPaneController parentTabPaneController;
    @FXML private JFXTreeTableView<LogEntry> logEntriesTreeTableView;
    @FXML private Button generateReportButton;
    @FXML private Button saveButton;
    @FXML private Button undoChangesButton;

    void setCurrentStage(Stage stage) {
        this.currentStage = stage;
    }

    void setParentTabPaneController(MainTabPaneController parentTabPane) {
        this.parentTabPaneController = parentTabPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: Re-comment this
        saveButton.setDisable(true);
        undoChangesButton.setDisable(true);

        // TODO: Re-comment this
        saveButton.setOnMouseClicked(event -> {
            saveButton.setDisable(true);
            undoChangesButton.setDisable(true);
            generateReportButton.setDisable(false);
        });

        // TODO: Re-comment this
        undoChangesButton.setOnMouseClicked(event -> {
            saveButton.setDisable(true);
            undoChangesButton.setDisable(true);
            generateReportButton.setDisable(false);
        });

        generateReportButton.setOnMouseClicked(event -> generateReport());

        // Generate table columns
        JFXTreeTableColumn<LogEntry, String> dateColumn = new JFXTreeTableColumn<>("Date");
        JFXTreeTableColumn<LogEntry, String> hoursColumn = new JFXTreeTableColumn<>("Hours");
        JFXTreeTableColumn<LogEntry, String> commentsColumn = new JFXTreeTableColumn<>("Comments");
        JFXTreeTableColumn<LogEntry, String> locationNameColumn = new JFXTreeTableColumn<>("Location");
        JFXTreeTableColumn<LogEntry, String> locationAddressColumn = new JFXTreeTableColumn<>("Address");
        JFXTreeTableColumn<LogEntry, String> locationCityColumn = new JFXTreeTableColumn<>("City");
        JFXTreeTableColumn<LogEntry, String> locationStateColumn = new JFXTreeTableColumn<>("State");
        JFXTreeTableColumn<LogEntry, String> locationZipCodeColumn = new JFXTreeTableColumn<>("Zip");
        JFXTreeTableColumn<LogEntry, String> companyNameColumn = new JFXTreeTableColumn<>("Company");
        JFXTreeTableColumn<LogEntry, String> supervsiorDisplayNameColumn = new JFXTreeTableColumn<>("Supervisor Display Name");
        JFXTreeTableColumn<LogEntry, String> supervisorFirstNameColumn = new JFXTreeTableColumn<>("Supervisor First Name");
        JFXTreeTableColumn<LogEntry, String> supervisorLastNameColumn = new JFXTreeTableColumn<>("Supervisor Last Name");

        // Set preferred column widths
        int standardWidth = 100;
        dateColumn.setPrefWidth(standardWidth);
        hoursColumn.setPrefWidth(standardWidth);
        commentsColumn.setPrefWidth(standardWidth);
        locationNameColumn.setPrefWidth(standardWidth);
        locationAddressColumn.setPrefWidth(standardWidth);
        locationCityColumn.setPrefWidth(standardWidth);
        locationStateColumn.setPrefWidth(standardWidth);
        locationZipCodeColumn.setPrefWidth(standardWidth);
        companyNameColumn.setPrefWidth(standardWidth);
        supervsiorDisplayNameColumn.setPrefWidth(standardWidth);
        supervisorFirstNameColumn.setPrefWidth(standardWidth);
        supervisorLastNameColumn.setPrefWidth(standardWidth);

        // Set cell value factories
        dateColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().getValue().logEntryDateProperty());
        hoursColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().getValue().logEntryHoursProperty());
        commentsColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().getValue().logEntryCommentsProperty());
        locationNameColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().getValue().logEntryLocationNameProperty());
        locationAddressColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().getValue().logEntryLocationAddressProperty());
        locationCityColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().getValue().logEntryLocationCityProperty());
        locationStateColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().getValue().logEntryLocationStateProperty());
        locationZipCodeColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().getValue().logEntryLocationZipCodeProperty());
        companyNameColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().getValue().logEntryCompanyNameProperty());
        supervsiorDisplayNameColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().getValue().logEntrySupervisorDisplayNameProperty());
        supervisorFirstNameColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().getValue().logEntrySupervisorFirstNameProperty());
        supervisorLastNameColumn.setCellValueFactory(cellDataFeatures -> cellDataFeatures.getValue().getValue().logEntrySupervisorLastNameProperty());

        // Create the observable list, made up of all log entries, for use within the table
        LogEntryMapper logEntryMapper = new LogEntryMapper();
        ObservableList<LogEntry> logEntryObservableList = FXCollections.observableArrayList(logEntryMapper.readAll());

        final TreeItem<LogEntry> root = new RecursiveTreeItem<>(logEntryObservableList, RecursiveTreeObject::getChildren);
        logEntriesTreeTableView.getColumns().setAll(dateColumn, hoursColumn, commentsColumn, locationNameColumn,
                locationAddressColumn, locationCityColumn, locationStateColumn, locationZipCodeColumn, companyNameColumn,
                supervsiorDisplayNameColumn, supervisorFirstNameColumn, supervisorLastNameColumn
        );
        logEntriesTreeTableView.setRoot(root);
        logEntriesTreeTableView.setShowRoot(false);
    }

    /**
     * Generates a PDF report for the
     * user to save wherever they choose
     */
    private void generateReport() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Copy of Report");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.setInitialFileName("Hour Tracker Report.pdf");
        FileChooser.ExtensionFilter pdfExtensionFilter =
                new FileChooser.ExtensionFilter(
                        "PDF - Portable Document Format (.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(pdfExtensionFilter);
        fileChooser.setSelectedExtensionFilter(pdfExtensionFilter);
        File file = fileChooser.showSaveDialog(currentStage);

        if (file != null) {
            try {
                createPDF(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates a PDF of the log file text and saves the
     * new PDF using the {@link File} passed
     *
     * @param file a {@link File}
     * @throws IOException
     */
    private void createPDF(File file) throws IOException {

        BiFunction<PDDocument, PDPage, PDPageContentStream> prepareNewContentStream = (pdDocument, pdPage) -> {
            try {
                PDPageContentStream pdPageContentStream = new PDPageContentStream(pdDocument, pdPage);
                pdPageContentStream.setFont(PDType1Font.HELVETICA, 12);
                pdPageContentStream.setLeading(14.5);
                pdPageContentStream.beginText();
                int bodyX = 57;
                int bodyY = 735;
                pdPageContentStream.newLineAtOffset(bodyX, bodyY);
                return pdPageContentStream;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        };

        // Get logfile text
        BufferedReader bufferedReader = new BufferedReader(new StringReader(LogFileHelper.getLogFileText()));

        // Prepare the PDDocument, PDPage, and PDPageContentStream
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        PDPageContentStream contentStream = prepareNewContentStream.apply(document, page);

        // Start writing to the PDF line by line
        int lineCount = 0;
        // If a line is longer than 40 characters, it is in danger
        // of running off of the edge of the page.
        int maximumLineLength = 40;
        String aLineFromTheLogFile = bufferedReader.readLine();
        if (aLineFromTheLogFile == null) {
            // Create a blank page if the log file is empty
            document.addPage(page);
        }
        while (aLineFromTheLogFile != null) {
            // Use Apache Commons' WordUtils.wrap method to ensure that any line longer than
            // the maximum line length gets wrapped before being written to the current PDF page.
            String wrappedLines = WordUtils.wrap(aLineFromTheLogFile, maximumLineLength, null, true);
            String[] linesToWrite = wrappedLines.split(System.lineSeparator());
            for (String aLine : linesToWrite) {

                /* If 49 lines have been written to the current page,
                 * then the current page is considered to be full.
                 * Close the current stream, add the current page to the
                 * document, create a new page and a new stream,
                 * and reset lineCount
                 */
                if (lineCount == 49) {
                    contentStream.endText();
                    contentStream.close();
                    document.addPage(page);
                    page = new PDPage();
                    contentStream = prepareNewContentStream.apply(document, page);
                    lineCount = 0;
                }

                // Write the line to the PDF
                contentStream.showText(aLine);
                lineCount++;
                contentStream.newLine();
            }

            // Read the next line
            aLineFromTheLogFile = bufferedReader.readLine();

            /* The while loop exits when there's nothing left to read
             * from the log file. Once this loop exits, it's expected
             * that all pages have already been written to the document.
             * Aside from this if statement, the only time I add the
             * current page to the document is when the current page is full.
             * With this if statement, I account for the case where
             * the current page is not full but still has some text,
             * and there's nothing left to read from the log file.
             */
            if ((aLineFromTheLogFile == null) && (lineCount > 0)) {
                document.addPage(page);
            }
        }

        // Ensure contentStream gets closed
        contentStream.endText();
        contentStream.close();

        document.save(file);
        document.close();
    }
}
