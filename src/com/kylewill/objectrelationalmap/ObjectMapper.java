package com.kylewill.objectrelationalmap;

import com.kylewill.model.DatabaseItem;

import java.util.List;

interface ObjectMapper {
    String url = "jdbc:sqlite:Hour_Tracker_Files/HourTracker.db";

    // Create a new record
    void create(DatabaseItem databaseItem);
    // Retrieve all records within a table
    List<DatabaseItem> readAll();
    // Update a record - a valid primary key must be available in the object passed
    void update(DatabaseItem databaseItem);
    //Delete a record - a valid primary key must be available in the object passed
    void delete(DatabaseItem databaseItem);
}