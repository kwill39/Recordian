package controller;

import chart.barchart.CompanyBarChart;
import chart.barchart.LocationBarChart;
import chart.barchart.SupervisorBarChart;
import chart.piechart.CompanyPieChart;
import chart.piechart.LocationPieChart;
import chart.piechart.SupervisorPieChart;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class GraphsTabController implements Initializable {
    private Stage currentStage;
    private MainTabPaneController parentTabPaneController;
    private static final String BAR_CHART = "BAR_CHART";
    private static final String PIE_CHART = "PIE_CHART";
    private List<BarChart<String, Number>> barCharts =
            new ArrayList<>(Arrays.asList(
                    new LocationBarChart().getBarChart(),
                    new CompanyBarChart().getBarChart(),
                    new SupervisorBarChart().getBarChart()
            ));
    private List<PieChart> pieCharts =
            new ArrayList<>(Arrays.asList(
                    new LocationPieChart().getPieChart(),
                    new CompanyPieChart().getPieChart(),
                    new SupervisorPieChart().getPieChart()
            ));
    @FXML private ToggleGroup chartType;
    @FXML private JFXRadioButton barChartButton;
    @FXML private JFXRadioButton pieChartButton;
    @FXML private ScrollPane chartScrollPane;
    @FXML private VBox chartContainer;
    @FXML private JFXButton generateReportButton;

    void setCurrentStage(Stage stage) {
        this.currentStage = stage;
    }

    void setParentTabPaneController(MainTabPaneController parentTabPane) {
        this.parentTabPaneController = parentTabPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateReportButton.setOnMouseClicked(event -> {
            // Find out which charts are currently shown to the user
            // and pass those charts to the generateReport method
            List<Chart> charts = new ArrayList<>();
            switch (chartType.getSelectedToggle().getUserData().toString()) {
                case BAR_CHART:
                    charts.addAll(barCharts);
                    break;
                case PIE_CHART:
                    charts.addAll(pieCharts);
                    break;
            }
            generateReport(charts);
        });

        // Set default chart view
        chartContainer.getChildren().addAll(barCharts);

        chartScrollPane.setFitToWidth(true);

        // Used to find out which radio button was selected
        barChartButton.setUserData(BAR_CHART);
        pieChartButton.setUserData(PIE_CHART);

        // Detect which radio button was selected and
        // add its corresponding charts to the chart container
        chartType.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue.getUserData().toString()) {
                case BAR_CHART:
                    chartContainer.getChildren().clear();
                    chartContainer.getChildren().addAll(barCharts);
                    break;
                case PIE_CHART:
                    chartContainer.getChildren().clear();
                    chartContainer.getChildren().addAll(pieCharts);
                    break;
            }
        });
    }

    /**
     * Generates a PDF report containing images of
     * the charts currently displayed to the user
     *
     * @param charts the charts that are to be exported as images to a PDF
     */
    private void generateReport(List<Chart> charts) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Copy of Graphs");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.setInitialFileName("Log Entry Graphs.pdf");
        FileChooser.ExtensionFilter pdfExtensionFilter =
                new FileChooser.ExtensionFilter(
                        "PDF - Portable Document Format (.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(pdfExtensionFilter);
        fileChooser.setSelectedExtensionFilter(pdfExtensionFilter);
        File file = fileChooser.showSaveDialog(currentStage);

        if (file != null) {
            try {
                exportChartsIntoPDF(file, charts);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates a PDF containing images of charts
     *
     * @param outputFile the file to which the PDF will be written
     * @param charts     the charts that will be included as images in the PDF
     */
    private void exportChartsIntoPDF(File outputFile, List<Chart> charts) {
        List<WritableImage> chartImages = chartsToImages(charts);
        List<Image> iTextImages = new ArrayList<>();

        // Convert the Java images to iText images
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try {
            for (WritableImage chartImage : chartImages) {
                ImageIO.write(SwingFXUtils.fromFXImage(chartImage, null), "png", byteStream);
                iTextImages.add(Image.getInstance(byteStream.toByteArray()));
                byteStream.reset();
            }
        } catch (IOException | BadElementException e) {
            e.printStackTrace();
        }

        // Write the iText images to a pdf and save it to the file chosen by the user
        try {
            Document pdfDocument = new Document(PageSize.LETTER.rotate());
            PdfWriter.getInstance(pdfDocument, new FileOutputStream(outputFile));
            pdfDocument.open();
            for (Image iTextImage : iTextImages) {
                iTextImage.scaleToFit(pdfDocument.getPageSize());
                iTextImage.setAlignment(Image.ALIGN_CENTER);
                pdfDocument.add(iTextImage);
            }
            pdfDocument.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts charts to Java {@link WritableImage}s
     *
     * @param charts the charts to be converted to {@link WritableImage}s
     * @return a {@link List} of {@link WritableImage}s
     */
    private List<WritableImage> chartsToImages(List<Chart> charts) {
        List<WritableImage> chartImages = new ArrayList<>();

        // Scaling the chart image gives it a higher resolution
        // which results in a better image quality when the
        // image is exported to the pdf
        SnapshotParameters snapshotParameters = new SnapshotParameters();
        snapshotParameters.setTransform(new Scale(2, 2));

        for (Chart chart : charts) {
            chartImages.add(chart.snapshot(snapshotParameters, null));
        }

        return  chartImages;
    }
}
