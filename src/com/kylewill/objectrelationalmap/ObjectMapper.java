package com.kylewill.objectrelationalmap;

import com.kylewill.model.DatabaseItem;

import java.util.List;

/**
 * <code>ObjectMapper</code> is the data access object (DAO) interface
 * that is implemented by all DAO concrete classes in Kyle's Hour Tracker
 *
 * @author  Kyle Williams
 * @since   Version 2
 */
interface ObjectMapper<T> {
    String url = "jdbc:sqlite:Hour_Tracker_Files/HourTracker.db";

    /**
     * Creates a new <code>DatabaseItem</code> record
     *
     * @param t an object corresponding to a record in the database
     */
    void create(T t);

    /**
     * Retrieves the <code>DatabaseItem</code> whose ID matches <code>databaseItemID</code>
     *
     * @param   databaseItemID the ID of the <code>DatabaseItem</code>
     * @return  the <code>DatabaseItem</code> whose ID matches that of <code>databaseItemID</code>
     */
    T read(int databaseItemID);

    /**
     * Retrieves the <code>DatabaseItem</code> whose name matches <code>databaseItemUniqueName</code>
     *
     * @param   databaseItemUniqueName the unique name of the <code>DatabaseItem</code>
     * @return  the <code>DatabaseItem</code> whose unique name matches that of <code>databaseItemUniqueName</code>
     */
    T read(String databaseItemUniqueName);

    /**
     * Retrieves all records which the DAO concrete class interfaces for
     *
     * @return  a <code>List</code> of objects associated with the specific DAO
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