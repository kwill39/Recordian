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
     * A {@link model.DatabaseItem} modification controller needs to be able
     * to communicate to its {@link NewLogEntryTabController} to tell its <code>NewLogEntryTabController</code>
     * that it has updated the database. This is so that the combo boxes in
     * the newLogEntryTab view can reflect this change in the database.
     */
    protected NewLogEntryTabController newLogEntryTabController;

    /**
     * @param stage the JavaFX {@link Stage} that contains the
     *              view which this controller interacts with
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * @param newLogEntryTabController the {@link NewLogEntryTabController} that
     *                           created the {@link Stage} of this controller
     */
    public void setNewLogEntryTabController(NewLogEntryTabController newLogEntryTabController) {
        this.newLogEntryTabController = newLogEntryTabController;
        onNewLogEntryTabControllerSet();
    }

    /**
     * Used by the {@link model.DatabaseItem} modification controller to
     * perform certain actions that can only be done once
     * the controller has access to the fields and methods of the
     * {@link NewLogEntryTabController} which created its {@link Stage}.
     */
    protected void onNewLogEntryTabControllerSet() {}
}