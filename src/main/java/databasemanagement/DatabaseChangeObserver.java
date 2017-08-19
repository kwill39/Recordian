package databasemanagement;

import model.DatabaseItem;

public interface DatabaseChangeObserver {
    void databaseItemWasCreated(DatabaseItem databaseItem);
    void databaseItemWasUpdated(DatabaseItem databaseItem);
    void databaseItemWasDeleted(DatabaseItem databaseItem);
}