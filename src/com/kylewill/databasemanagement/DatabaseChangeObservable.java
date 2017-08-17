package com.kylewill.databasemanagement;

import com.kylewill.model.DatabaseItem;

import java.util.ArrayList;
import java.util.List;

public class DatabaseChangeObservable {
    private static DatabaseChangeObservable observableInstance = new DatabaseChangeObservable();

    public static DatabaseChangeObservable getInstance() {
        return observableInstance;
    }

    private DatabaseChangeObservable() {
    }

    private List<DatabaseChangeObserver> observers = new ArrayList<>();

    public void registerObserver(DatabaseChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(DatabaseChangeObserver observer) {
        observers.remove(observer);
    }

    public void notifyOfCreation(DatabaseItem databaseItem) {
        for (DatabaseChangeObserver observer : observers) {
            observer.databaseItemWasCreated(databaseItem);
        }
    }

    public void notifyOfUpdate(DatabaseItem databaseItem) {
        for (DatabaseChangeObserver observer : observers) {
            observer.databaseItemWasUpdated(databaseItem);
        }
    }

    public void notifyOfDelete(DatabaseItem databaseItem) {
        for (DatabaseChangeObserver observer : observers) {
            observer.databaseItemWasDeleted(databaseItem);
        }
    }
}
