package com.kylewill.objectrelationalmap;

import java.util.List;

interface ObjectMapper<T> {
    String url = "jdbc:sqlite:Hour_Tracker_Files/HourTracker.db";

    // Create a new record
    void create(T t);
    // Retrieve all records within a table
    List<T> readAll();
    // Update a record - a valid primary key must be available in the object passed
    void update(T t);
    //Delete a record - a valid primary key must be available in the object passed
    void delete(T t);
}