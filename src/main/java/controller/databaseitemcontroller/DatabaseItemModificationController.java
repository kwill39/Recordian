package controller.databaseitemcontroller;

import controller.NewLogEntryTabController;
import javafx.stage.Stage;

/**
 * @author  Kyle Williams
 * @since   Version 2
 */
public abstract class DatabaseItemModificationController {
    /**
     * Used by the controller to close its own stage
     */
    protected Stage stage;
    /**
     * A <code>DatabaseItem</code> modification controller needs to be able
     * to communicate to its <code>NewLogEntryTabController</code> to tell its <code>NewLogEntryTabController</code>
     * that it has updated the database. This is so that the choiceboxes in
     * logEntryTab view can reflect this change in the database.
     */
    protected NewLogEntryTabController newLogEntryTabController;

    /**
     * @param stage the JavaFX <code>stage</code> that contains the
     *              view which this controller interacts with
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * @param newLogEntryTabController the <code>NewLogEntryTabController</code> that
     *                           created the <code>stage</code> of this controller
     */
    public void setNewLogEntryTabController(NewLogEntryTabController newLogEntryTabController) {
        this.newLogEntryTabController = newLogEntryTabController;
        onNewLogEntryTabControllerSet();
    }

    /**
     * Used by the <code>DatabaseItem</code> modification controller to
     * perform certain actions that can only be done once
     * the controller has access to the fields and methods of the
     * <code>NewLogEntryTabController</code> which created its <code>stage</code>.
     */
    protected void onNewLogEntryTabControllerSet() {}
}