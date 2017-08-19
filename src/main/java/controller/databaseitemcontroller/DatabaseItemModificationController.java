package controller.databaseitemcontroller;

import controller.MainViewController;
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
     * to communicate to its <code>MainViewController</code> to tell its <code>MainViewController</code>
     * that it has updated the database. This is so that the choiceboxes in
     * mainView can reflect this change in the database.
     */
    protected MainViewController mainViewController;

    /**
     * @param stage the JavaFX <code>stage</code> that contains the
     *              view which this controller interacts with
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * @param mainViewController the <code>MainViewController</code> that
     *                           created the <code>stage</code> of this controller
     */
    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
        onMainViewControllerSet();
    }

    /**
     * Used by the <code>DatabaseItem</code> modification controller to
     * perform certain actions that can only be done once
     * the controller has access to the fields and methods of the
     * <code>MainViewController</code> which created its <code>stage</code>.
     */
    protected void onMainViewControllerSet() {}
}