package databasemanagement.objectrelationalmap;

import model.DatabaseItem;

import java.util.List;

/**
 * Data access object (DAO) interface
 * that is implemented by all DAO concrete classes in Kyle's Hour Tracker
 *
 * @author  Kyle Williams
 * @since   Version 2
 */
interface DatabaseItemMapper<T> {

    /**
     * Creates a new {@link DatabaseItem} record
     *
     * @param t an object corresponding to a record in the database
     */
    void create(T t);

    /**
     * Retrieves the {@link DatabaseItem} whose ID matches <code>databaseItemID</code>
     *
     * @param   databaseItemID the ID of the {@link DatabaseItem}
     * @return  the {@link DatabaseItem} whose ID matches that of <code>databaseItemID</code>
     */
    T read(int databaseItemID);

    /**
     * Retrieves all records which the DAO concrete class interfaces for
     *
     * @return  a {@link List} of objects associated with the specific DAO
     */
    List<T> readAll();

    /**
     * Updates an object associated with the specific DAO
     *
     * @param t an object corresponding to a record in the database.
     *          A valid primary key must be available in databaseItem
     */
    void update(T t);

    /**
     * Deletes an object associated with the specific DAO
     *
     * @param t an object corresponding to a record in the database.
     *          A valid primary key must be available in databaseItem
     */
    void delete(T t);
}