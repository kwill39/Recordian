package databasemanagement;

import model.DatabaseItem;

import java.util.List;

public interface DatabaseChangeObserver {

    /**
     * The method that is called each time a
     * {@link DatabaseItem} is created from the database
     *
     * @param databaseItem the {@link DatabaseItem} that was just created in the database
     */
    void databaseItemWasCreated(DatabaseItem databaseItem);

    /**
     * The method that is called each time a
     * {@link DatabaseItem} is updated from the database
     *
     * @param databaseItem the {@link DatabaseItem} that was just updated in the database
     */
    void databaseItemWasUpdated(DatabaseItem databaseItem);

    /**
     * The method that is called each time a
     * {@link DatabaseItem} is deleted from the database
     *
     * @param databaseItem the {@link DatabaseItem} that was just deleted from the database
     */
    void databaseItemWasDeleted(DatabaseItem databaseItem);

    /**
     * The method that is called each time a collection of
     * {@link DatabaseItem}s are deleted from the database
     *
     * @param databaseItems a collection containing the {@link DatabaseItem}s that were just deleted from the database
     */
    void databaseItemsWereDeleted(List<? extends DatabaseItem> databaseItems);
}