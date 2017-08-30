package controller;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import databasemanagement.LogFileHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Company;
import model.Location;
import model.Supervisor;
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
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.BiFunction;

/**
 * @author  Kyle Williams
 * @since   Version 3
 */
public class EditLogEntriesTabController implements Initializable {
    private Stage currentStage;
    private MainTabPaneController parentTabPaneController;
    @FXML private JFXTreeTableView<?> editLogsTable;
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
        JFXTreeTableColumn<String, String> date = new JFXTreeTableColumn<>("Date");
        JFXTreeTableColumn<String, String> hours = new JFXTreeTableColumn<>("Hours");
        JFXTreeTableColumn<Location, String> locationName = new JFXTreeTableColumn<>("Location");
        JFXTreeTableColumn<Location, String> locationAddress = new JFXTreeTableColumn<>("Address");
        JFXTreeTableColumn<Location, String> locationCity = new JFXTreeTableColumn<>("City");
        JFXTreeTableColumn<Location, String> locationState = new JFXTreeTableColumn<>("State");
        JFXTreeTableColumn<Location, String> locationZipCode = new JFXTreeTableColumn<>("Zip");
        JFXTreeTableColumn<Company, String> companyName = new JFXTreeTableColumn<>("Company");
        JFXTreeTableColumn<Supervisor, String> supervsiorDisplayName = new JFXTreeTableColumn<>("Supervisor Display Name");
        JFXTreeTableColumn<Supervisor, String> supervisorFirstName = new JFXTreeTableColumn<>("Supervisor First Name");
        JFXTreeTableColumn<Supervisor, String> supervisorLastName = new JFXTreeTableColumn<>("Supervisor Last Name");

        // Set preferred column widths
        date.setPrefWidth(150);
        hours.setPrefWidth(150);
        locationName.setPrefWidth(150);
        locationAddress.setPrefWidth(150);
        locationCity.setPrefWidth(150);
        locationState.setPrefWidth(150);
        locationZipCode.setPrefWidth(150);
        companyName.setPrefWidth(150);
        supervsiorDisplayName.setPrefWidth(150);
        supervisorFirstName.setPrefWidth(150);
        supervisorLastName.setPrefWidth(150);
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
