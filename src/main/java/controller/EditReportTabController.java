package controller;

import databasemanagement.LogFileHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.text.WordUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.PDType3Font;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Function;

public class EditReportTabController implements Initializable {
    private Stage currentStage;
    @FXML private TextArea editLogFileTextArea;
    @FXML private Button generateReportButton;
    @FXML private Button saveButton;
    @FXML private Button undoChangesButton;

    void setCurrentStage(Stage stage) {
        this.currentStage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Keep the save and cancel buttons disabled if the contents of
        // editLogFileTextArea matches the contents of the log file
        // Disable the buttons initially since no changes were made
        saveButton.setDisable(true);
        undoChangesButton.setDisable(true);

        saveButton.setOnMouseClicked(event -> {
            // Overwrites the log file with the contents of editLogFileTextArea
            LogFileHelper.setLogFileText(editLogFileTextArea.getText());

            saveButton.setDisable(true);
            undoChangesButton.setDisable(true);
            generateReportButton.setDisable(false);
        });

        editLogFileTextArea.setText(LogFileHelper.getLogFileText());
        // When changes are made, enable the options to save or
        // cancel those changes and disable the option to generate
        // a report until the changes are saved or canceled.
        editLogFileTextArea.setOnKeyPressed(event -> {
            saveButton.setDisable(false);
            undoChangesButton.setDisable(false);
            generateReportButton.setDisable(true);
        });

        // Onclick: reset editLogFileTextArea to match
        // the contents of the log file, disable the save button
        undoChangesButton.setOnMouseClicked(event -> {
            saveButton.setDisable(true);
            undoChangesButton.setDisable(true);
            editLogFileTextArea.setText(LogFileHelper.getLogFileText());
            generateReportButton.setDisable(false);
        });

        generateReportButton.setOnMouseClicked(event -> generateReport());
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
        fileChooser.setInitialFileName("Hour Tracker Report");
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
