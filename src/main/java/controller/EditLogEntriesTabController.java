package controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import databasemanagement.LogFileHelper;
import databasemanagement.objectrelationalmap.LogEntryMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import model.LogEntry;
import org.apache.commons.text.WordUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.BiFunction;

/**
 * @author  Kyle Williams
 * @since   Version 3
 */
public class EditLogEntriesTabController implements Initializable {
    private Stage currentStage;
    private MainTabPaneController parentTabPaneController;
    @FXML private TableView<LogEntry> logEntriesTableView;
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
        TableColumn<LogEntry, String> dateColumn = new TableColumn<>("Date");
        TableColumn<LogEntry, String> hoursColumn = new TableColumn<>("Hours");
        TableColumn<LogEntry, String> commentsColumn = new TableColumn<>("Comments");
        TableColumn<LogEntry, String> locationNameColumn = new TableColumn<>("Location");
        TableColumn<LogEntry, String> locationAddressColumn = new TableColumn<>("Address");
        TableColumn<LogEntry, String> locationCityColumn = new TableColumn<>("City");
        TableColumn<LogEntry, String> locationStateColumn = new TableColumn<>("State");
        TableColumn<LogEntry, String> locationZipCodeColumn = new TableColumn<>("Zip");
        TableColumn<LogEntry, String> companyNameColumn = new TableColumn<>("Company");
        TableColumn<LogEntry, String> supervisorDisplayNameColumn = new TableColumn<>("Supervisor Display Name");
        TableColumn<LogEntry, String> supervisorFirstNameColumn = new TableColumn<>("Supervisor First Name");
        TableColumn<LogEntry, String> supervisorLastNameColumn = new TableColumn<>("Supervisor Last Name");

        // TODO: if (columnListFile does not exist)

        List<TableColumn<LogEntry, String>> columnList = new ArrayList<>();
        columnList.add(dateColumn);
        columnList.add(hoursColumn);
        columnList.add(commentsColumn);
        columnList.add(locationNameColumn);
        columnList.add(locationAddressColumn);
        columnList.add(locationCityColumn);
        columnList.add(locationStateColumn);
        columnList.add(locationZipCodeColumn);
        columnList.add(companyNameColumn);
        columnList.add(supervisorDisplayNameColumn);
        columnList.add(supervisorFirstNameColumn);
        columnList.add(supervisorLastNameColumn);

        // TODO: write this one to new columnList file

        // TODO: else, load in columnList file list

        // Set cell value factories
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("logEntryDate"));
        hoursColumn.setCellValueFactory(new PropertyValueFactory<>("logEntryHours"));
        commentsColumn.setCellValueFactory(new PropertyValueFactory<>("logEntryComments"));
        locationNameColumn.setCellValueFactory(new PropertyValueFactory<>("logEntryLocationName"));
        locationAddressColumn.setCellValueFactory(new PropertyValueFactory<>("logEntryLocationAddress"));
        locationCityColumn.setCellValueFactory(new PropertyValueFactory<>("logEntryLocationCity"));
        locationStateColumn.setCellValueFactory(new PropertyValueFactory<>("logEntryLocationState"));
        locationZipCodeColumn.setCellValueFactory(new PropertyValueFactory<>("logEntryLocationZipCode"));
        companyNameColumn.setCellValueFactory(new PropertyValueFactory<>("logEntryCompanyName"));
        supervisorDisplayNameColumn.setCellValueFactory(new PropertyValueFactory<>("logEntrySupervisorDisplayName"));
        supervisorFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("logEntrySupervisorFirstName"));
        supervisorLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("logEntrySupervisorLastName"));

        // Get mapper object to be used when fields are edited
        LogEntryMapper logEntryMapper = new LogEntryMapper();

        // Set onEditCommit changes
        dateColumn.setOnEditCommit(event -> {
            LogEntry logEntry = event.getRowValue();
            logEntry.setLogEntryDate(event.getNewValue());
            logEntryMapper.update(logEntry);
        });
        hoursColumn.setOnEditCommit(event -> {
            LogEntry logEntry = event.getRowValue();
            logEntry.setLogEntryHours(event.getNewValue());
            logEntryMapper.update(logEntry);
        });
        commentsColumn.setOnEditCommit(event -> {
            LogEntry logEntry = event.getRowValue();
            logEntry.setLogEntryComments(event.getNewValue());
            logEntryMapper.update(logEntry);
        });
        locationNameColumn.setOnEditCommit(event -> {
            LogEntry logEntry = event.getRowValue();
            logEntry.setLogEntryLocationName(event.getNewValue());
            logEntryMapper.update(logEntry);
        });
        locationAddressColumn.setOnEditCommit(event -> {
            LogEntry logEntry = event.getRowValue();
            logEntry.setLogEntryLocationAddress(event.getNewValue());
            logEntryMapper.update(logEntry);
        });
        locationCityColumn.setOnEditCommit(event -> {
            LogEntry logEntry = event.getRowValue();
            logEntry.setLogEntryLocationCity(event.getNewValue());
            logEntryMapper.update(logEntry);
        });
        locationStateColumn.setOnEditCommit(event -> {
            LogEntry logEntry = event.getRowValue();
            logEntry.setLogEntryLocationState(event.getNewValue());
            logEntryMapper.update(logEntry);
        });
        locationZipCodeColumn.setOnEditCommit(event -> {
            LogEntry logEntry = event.getRowValue();
            logEntry.setLogEntryLocationZipCode(event.getNewValue());
            logEntryMapper.update(logEntry);
        });
        supervisorDisplayNameColumn.setOnEditCommit(event -> {
            LogEntry logEntry = event.getRowValue();
            logEntry.setLogEntrySupervisorDisplayName(event.getNewValue());
            logEntryMapper.update(logEntry);
        });
        supervisorFirstNameColumn.setOnEditCommit(event -> {
            LogEntry logEntry = event.getRowValue();
            logEntry.setLogEntrySupervisorFirstName(event.getNewValue());
            logEntryMapper.update(logEntry);
        });
        supervisorLastNameColumn.setOnEditCommit(event -> {
            LogEntry logEntry = event.getRowValue();
            logEntry.setLogEntrySupervisorLastName(event.getNewValue());
            logEntryMapper.update(logEntry);
        });

        // Set column cell factories and width preference
        for (TableColumn<LogEntry, String> column : columnList) {
            column.setCellFactory(callback -> {
                TextFieldTableCell<LogEntry, String> cell = new TextFieldTableCell<LogEntry, String>(new DefaultStringConverter()) {
                    private final Text cellText = createText();

                    @Override
                    public void cancelEdit() {
                        super.cancelEdit();
                        setGraphic(cellText);
                    }

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty() && !isEditing()) {
                            setGraphic(cellText);
                        }
                    }

                    private Text createText() {
                        Text text = new Text();
                        text.wrappingWidthProperty().bind(column.widthProperty());
                        text.textProperty().bind(itemProperty());
                        text.setTextAlignment(TextAlignment.CENTER);
                        return text;
                    }
                };
                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                return cell;
            });
            // Default Width
            column.setPrefWidth(140);
        }

        dateColumn.setPrefWidth(100);
        hoursColumn.setPrefWidth(70);
        locationStateColumn.setPrefWidth(70);
        locationZipCodeColumn.setPrefWidth(70);

        // Create the observable list, made up of all log entries, for use within the table
        ObservableList<LogEntry> logEntryObservableList = FXCollections.observableArrayList(logEntryMapper.readAll());

        logEntriesTableView.setItems(logEntryObservableList);
        logEntriesTableView.getColumns().setAll(columnList);
        logEntriesTableView.setEditable(true);
        dateColumn.setSortType(TableColumn.SortType.DESCENDING);
        logEntriesTableView.getSortOrder().add(dateColumn);
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
                exportTableAsPDF(file);
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

    /**
     * Creates a PDF displaying all log entries in a tabular
     * format and saves the new PDF to the {@link File} passed
     *
     * @param outputFile the {@link File} that is to contain the PDF displaying all log entries
     */
    private void exportTableAsPDF(File outputFile) {
        // PDF document
        Document pdfDocument = new Document(PageSize.LETTER.rotate(), 60, 60, 60, 60);
        try {
            PdfWriter.getInstance(pdfDocument, new FileOutputStream(outputFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        pdfDocument.open();

        // PDF table
        PdfPTable pdfPTable = new PdfPTable(6);
        pdfPTable.setWidthPercentage(100);
        try {
            pdfPTable.setWidths(new float[] {11, 7, 20.5f, 20.5f, 20.5f, 20.5f});
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        // Add column header cells
        Font columnHeaderFont = new Font(Font.FontFamily.HELVETICA, 13, 1);
        PdfPCell dateCell = new PdfPCell(new Phrase("Date", columnHeaderFont));
        PdfPCell hoursCell = new PdfPCell(new Phrase("Hours", columnHeaderFont));
        PdfPCell commentsCell = new PdfPCell(new Phrase("Comments", columnHeaderFont));
        PdfPCell locationCell = new PdfPCell(new Phrase("Location", columnHeaderFont));
        PdfPCell companyNameCell = new PdfPCell(new Phrase("Company", columnHeaderFont));
        PdfPCell supervisorCell = new PdfPCell(new Phrase("Supervisor", columnHeaderFont));

        List<PdfPCell> tableCells = new ArrayList<>();
        tableCells.add(dateCell);
        tableCells.add(hoursCell);
        tableCells.add(commentsCell);
        tableCells.add(locationCell);
        tableCells.add(companyNameCell);
        tableCells.add(supervisorCell);

        for (PdfPCell cell : tableCells) {
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setPaddingBottom(5);
        }

        pdfPTable.addCell(dateCell);
        pdfPTable.addCell(hoursCell);
        pdfPTable.addCell(commentsCell);
        pdfPTable.addCell(locationCell);
        pdfPTable.addCell(companyNameCell);
        pdfPTable.addCell(supervisorCell);

        // Used in populating the table
        LogEntryMapper logEntryMapper = new LogEntryMapper();
        List<LogEntry> logEntries = logEntryMapper.readAll();

        // Sort the log entries by date in descending order
        logEntries.sort((logEntry1, logEntry2) ->
                logEntry1.getLogEntryDate().compareToIgnoreCase(logEntry2.getLogEntryDate()) * (-1));

        // Used in determining the color of a row - either grey or white
        // Starts with row 1 - an odd row
        boolean oddRow = true;
        BaseColor rowColor;
        BaseColor whiteRow = BaseColor.WHITE;
        BaseColor lightGreyRow = new BaseColor(225, 225, 225);

        for (LogEntry logEntry : logEntries) {

            // Ternary operator to check for null values within the log entry
            // If there's a null value for a log entry's field, then the corresponding cell should be empty
            dateCell = (logEntry.getLogEntryDate() == null) ? new PdfPCell() :
                    new PdfPCell(new Phrase(logEntry.getLogEntryDate()));

            hoursCell = (logEntry.getLogEntryHours() == null) ? new PdfPCell() :
                    new PdfPCell(new Phrase(logEntry.getLogEntryHours()));

            commentsCell = (logEntry.getLogEntryComments() == null) ? new PdfPCell() :
                    new PdfPCell(new Phrase(logEntry.getLogEntryComments()));

            // Build the location cell contents
            // Some strings contain formatting characters added on to them such as a space
            String locationName = (logEntry.getLogEntryLocationName() == null) ? "" :
                    logEntry.getLogEntryLocationName();
            String address = (logEntry.getLogEntryLocationAddress() == null) ? "" :
                    System.lineSeparator() + logEntry.getLogEntryLocationAddress();
            String city = (logEntry.getLogEntryLocationCity() == null) ? "" :
                    System.lineSeparator() + logEntry.getLogEntryLocationCity() + " ";
            String state = (logEntry.getLogEntryLocationState() == null) ? "" :
                    logEntry.getLogEntryLocationState() + " ";
            String zipCode = (logEntry.getLogEntryLocationZipCode() == null) ? "" :
                    logEntry.getLogEntryLocationZipCode();
            String locationCellContents = locationName + address + city + state + zipCode;
            locationCell = new PdfPCell(new Phrase(locationCellContents));

            companyNameCell = (logEntry.getLogEntryCompanyName() == null) ? new PdfPCell() :
                    new PdfPCell(new Phrase(logEntry.getLogEntryCompanyName()));

            // Build the supervisor cell contents
            // Some strings contain formatting characters added on to them such as a space
            String supervisorFirstName = (logEntry.getLogEntrySupervisorFirstName() == null) ? "" :
                    logEntry.getLogEntrySupervisorFirstName() + " ";
            String supervisorLastName = (logEntry.getLogEntrySupervisorLastName() == null) ? "" :
                    logEntry.getLogEntrySupervisorLastName() + System.lineSeparator();
            String supervisorDisplayName = (logEntry.getLogEntrySupervisorDisplayName() == null) ? "" :
                    "(" + logEntry.getLogEntrySupervisorDisplayName() + ")";
            String supervisorCellContents = supervisorFirstName + supervisorLastName + supervisorDisplayName;
            supervisorCell = new PdfPCell(new Phrase(supervisorCellContents));

            tableCells.clear();
            tableCells.add(dateCell);
            tableCells.add(hoursCell);
            tableCells.add(commentsCell);
            tableCells.add(locationCell);
            tableCells.add(companyNameCell);
            tableCells.add(supervisorCell);

            rowColor = (oddRow) ? lightGreyRow : whiteRow;
            // Alternate the color for the next row
            oddRow = !oddRow;

            for (PdfPCell cell : tableCells) {
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPaddingBottom(5);
                cell.setBackgroundColor(rowColor);
            }

            pdfPTable.addCell(dateCell);
            pdfPTable.addCell(hoursCell);
            pdfPTable.addCell(commentsCell);
            pdfPTable.addCell(locationCell);
            pdfPTable.addCell(companyNameCell);
            pdfPTable.addCell(supervisorCell);
        }
        try {
            pdfDocument.add(pdfPTable);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        pdfDocument.close();
    }
}