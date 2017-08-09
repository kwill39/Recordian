package com.kylewill.controller;

import javafx.stage.Stage;

public abstract class DatabaseItemController {
    protected MainViewController mainViewController;
    protected Stage stage;

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
