package databasemanagement;

import model.DatabaseItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Listens to the mapper classes for changes to a
 * {@link DatabaseItem} and notifies its observers
 * whenever a <code>DatabaseItem</code> is modified
 * within the database
 *
 * @author  Kyle Williams
 * @since   Version 3
 */
public final class DatabaseChangeObservable {

    private static List<DatabaseChangeObserver> observers = new ArrayList<>();

    public static void registerObserver(DatabaseChangeObserver observer) {
        observers.add(observer);
    }

    public static void removeObserver(DatabaseChangeObserver observer) {
        observers.remove(observer);
    }

    public static void notifyOfCreation(DatabaseItem databaseItem) {
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

    public static void notifyOfDelete(List<? extends DatabaseItem> databaseItems) {
        for (DatabaseChangeObserver observer : observers) {
            observer.databaseItemsWereDeleted(databaseItems);
        }
    }
}