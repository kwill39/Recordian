import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.cells.editors.base.EditorNodeBuilder;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class TextAreaEditorBuilder implements EditorNodeBuilder<String> {

    private JFXTextArea textArea;

    @Override
    public void startEdit() {
        Platform.runLater(() -> {
            textArea.selectAll();
        });
    }

    @Override
    public void cancelEdit() {

    }

    @Override
    public void updateItem(String s, boolean isEmpty) {

        Platform.runLater(() -> {
            textArea.selectAll();
            textArea.requestFocus();
        });
    }

    @Override
    public Region createNode(
            String value,
            DoubleBinding minWidthBinding,
            EventHandler<KeyEvent> keyEventsHandler,
            ChangeListener<Boolean> focusChangeListener) {

        StackPane pane = new StackPane();
        pane.setStyle("-fx-padding:-10 0 -10 0");
        textArea = new JFXTextArea(value);
        textArea.setPrefRowCount(4);
        textArea.setWrapText(true);
        textArea.minWidthProperty().bind(minWidthBinding.subtract(10));
        textArea.setOnKeyPressed(keyEventsHandler);
        textArea.focusedProperty().addListener(focusChangeListener);
        pane.getChildren().add(textArea);

        return ControlUtil.styleNodeWithPadding(pane);
    }

    @Override
    public void setValue(String s) {
        textArea.setText(s);
    }

    @Override
    public String getValue() {
        return textArea.getText();
    }

    @Override
    public void validateValue() throws Exception {

    }
}
