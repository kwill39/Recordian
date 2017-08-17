package com.kylewill.databasemanagement;

import com.kylewill.databasemanagement.objectrelationalmap.CompanyMapper;
import com.kylewill.model.DatabaseItem;

import java.util.ArrayList;
import java.util.List;

public final class DatabaseChangeObservable {

    private static List<DatabaseChangeObserver> observers = new ArrayList<>();

    public static void registerObserver(DatabaseChangeObserver observer) {
        observers.add(observer);
    }

    public static void removeObserver(DatabaseChangeObserver observer) {
        observers.remove(observer);
    }

    public static void notifyOfCreation(DatabaseItem databaseItem) {
        CompanyMapper companyMapper = new CompanyMapper();
        for (DatabaseChangeObserver observer : observers) {
            observer.databaseItemWasCreated(databaseItem);
        }
    }

    public static void notifyOfUpdate(DatabaseItem databaseItem) {
        for (DatabaseChangeObserver observer : observers) {
            observer.databaseItemWasUpdated(databaseItem);
        }
    }

    public static void notifyOfDelete(DatabaseItem databaseItem) {
        for (DatabaseChangeObserver observer : observers) {
            observer.databaseItemWasDeleted(databaseItem);
        }
    }
}