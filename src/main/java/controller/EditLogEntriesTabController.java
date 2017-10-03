package controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import databasemanagement.objectrelationalmap.LogEntryMapper;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import model.LogEntry;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author  Kyle Williams
 * @since   Version 3
 */
public class EditLogEntriesTabController implements Initializable {
    private Stage currentStage;
    private MainTabPaneController parentTabPaneController;
    @FXML private StackPane tabRootPane;
    @FXML private JFXTreeTableView<LogEntry> logEntriesTableView;
    @FXML private JFXButton generateReportButton;
    @FXML private JFXButton helpButton;

    void setCurrentStage(Stage stage) {
        this.currentStage = stage;
    }

    void setParentTabPaneController(MainTabPaneController parentTabPane) {
        this.parentTabPaneController = parentTabPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        generateReportButton.setOnMouseClicked(event -> generateReport());

        helpButton.setOnMouseClicked(event -> {
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            Text headingText = new Text("Editing & Deleting Logs");
            headingText.setFill(Paint.valueOf("#6c93e4"));
            dialogLayout.setHeading(headingText);
            StringBuilder message = new StringBuilder(System.lineSeparator())
                    .append("To edit a log, click on the cell that you would like to edit.")
                    .append(System.lineSeparator())
                    .append("Once you are finished editing, press the ENTER key to save your changes.")
                    .append(System.lineSeparator())
                    .append("If you click away without pressing the ENTER key, your changes will be lost.")
                    .append(System.lineSeparator())
                    .append(System.lineSeparator())
                    .append("To delete a log, right click on the row containing the log you would like")
                    .append(System.lineSeparator())
                    .append("to delete and choose the Delete option.");
            Text body = new Text(message.toString());
            dialogLayout.setBody(body);

            JFXDialog dialog = new JFXDialog(tabRootPane, dialogLayout, JFXDialog.DialogTransition.CENTER);

            JFXButton okButton = new JFXButton("OKAY");
            okButton.setOnMouseClicked(deleteEvent -> {
                dialog.close();
            });
            okButton.setTextFill(Color.valueOf("#6c93e4"));
            okButton.getStyleClass().add("dialogButton");

            dialogLayout.setActions(okButton);

            dialog.show();
        });

        setUpTableView();
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
        fileChooser.setInitialFileName("Log Entries.pdf");
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

    private void setUpTableView(){
        // Make selecting multiple table rows possible
        logEntriesTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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
        JFXTreeTableColumn<LogEntry, String> supervisorDisplayNameColumn = new JFXTreeTableColumn<>("Supervisor Display Name");
        JFXTreeTableColumn<LogEntry, String> supervisorFirstNameColumn = new JFXTreeTableColumn<>("Supervisor First Name");
        JFXTreeTableColumn<LogEntry, String> supervisorLastNameColumn = new JFXTreeTableColumn<>("Supervisor Last Name");

        List<JFXTreeTableColumn<LogEntry, String>> columnList = new ArrayList<>();
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

        // Set cell value factories
        dateColumn.setCellValueFactory(callback -> callback.getValue().getValue().logEntryDateProperty());
        hoursColumn.setCellValueFactory(callback -> callback.getValue().getValue().logEntryHoursProperty());
        commentsColumn.setCellValueFactory(callback -> callback.getValue().getValue().logEntryCommentsProperty());
        locationNameColumn.setCellValueFactory(callback -> callback.getValue().getValue().logEntryLocationNameProperty());
        locationAddressColumn.setCellValueFactory(callback -> callback.getValue().getValue().logEntryLocationAddressProperty());
        locationCityColumn.setCellValueFactory(callback -> callback.getValue().getValue().logEntryLocationCityProperty());
        locationStateColumn.setCellValueFactory(callback -> callback.getValue().getValue().logEntryLocationStateProperty());
        locationZipCodeColumn.setCellValueFactory(callback -> callback.getValue().getValue().logEntryLocationZipCodeProperty());
        companyNameColumn.setCellValueFactory(callback -> callback.getValue().getValue().logEntryCompanyNameProperty());
        supervisorDisplayNameColumn.setCellValueFactory(callback -> callback.getValue().getValue().logEntrySupervisorDisplayNameProperty());
        supervisorFirstNameColumn.setCellValueFactory(callback -> callback.getValue().getValue().logEntrySupervisorFirstNameProperty());
        supervisorLastNameColumn.setCellValueFactory(callback -> callback.getValue().getValue().logEntrySupervisorLastNameProperty());

        // Get mapper object to be used when fields are edited
        LogEntryMapper logEntryMapper = new LogEntryMapper();

        // Set onEditCommit changes
        /*dateColumn.setOnEditCommit(event -> {
            LogEntry logEntry = event.getRowValue();
            logEntry.setLogEntryDate(event.getNewValue());
            logEntryMapper.update(logEntry);
        });*/
        /*hoursColumn.setOnEditCommit(event -> {
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
        });*/

        // Set column cell factories and width preference
        /*for (TableColumn<LogEntry, String> column : columnList) {
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
        }*/
        for (JFXTreeTableColumn<LogEntry, String> column : columnList) {
            column.setCellFactory(callback -> {
                GenericEditableTreeTableCell cell = new GenericEditableTreeTableCell<>(new TextFieldEditorBuilder());
                cell.setWrapText(true);
                return cell;
            });
            // Default Width
            column.setPrefWidth(140);
        }

        dateColumn.setPrefWidth(100);
        hoursColumn.setPrefWidth(70);
        locationStateColumn.setPrefWidth(70);
        locationZipCodeColumn.setPrefWidth(70);

        logEntriesTableView.setRowFactory(callback -> {
            JFXTreeTableRow<LogEntry> row = new JFXTreeTableRow<>();
            ContextMenu rowContextMenu = new ContextMenu();
            MenuItem deleteEntry = new MenuItem("Delete Row");
            /*deleteEntry.setOnAction(handler -> {
                LogEntryMapper logEntryMapper1 = new LogEntryMapper();
                ObservableList<LogEntry> logEntriesToBeDeleted = logEntriesTableView.getSelectionModel().getSelectedItems();
                Thread deleteLogEntriesThread = new Thread(() -> {
                    logEntryMapper1.delete(logEntriesToBeDeleted);
                });
                deleteLogEntriesThread.run();
                logEntriesTableView.getItems().removeAll(logEntriesToBeDeleted);
                logEntriesTableView.getSelectionModel().clearSelection();
            });*/
            rowContextMenu.getItems().addAll(deleteEntry);

            // Display context menu only for items that are not null
            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                            .then(rowContextMenu)
                            .otherwise((ContextMenu) null));
            return row;
        });

        // Create the observable list, made up of all log entries, for use within the table
        ObservableList<LogEntry> logEntryObservableList = FXCollections.observableArrayList(logEntryMapper.readAll());

        //logEntriesTableView.add(logEntryObservableList);
        TreeItem<LogEntry> root = new RecursiveTreeItem<>(logEntryObservableList, RecursiveTreeObject::getChildren);
        logEntriesTableView.getColumns().setAll(columnList);
        logEntriesTableView.setRoot(root);
        logEntriesTableView.setShowRoot(false);
        logEntriesTableView.setEditable(true);
    }
}