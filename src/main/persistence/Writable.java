package persistence;

import org.json.JSONObject;

/**
 * Represents an interface for objects that can be written to JSON format.
 * Referenced from the JsonSerialization Demo
 * https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
} 