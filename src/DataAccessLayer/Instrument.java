package DataAccessLayer;

import java.io.Serializable;

/**
 * Instrument DTO class to represent the INSTRUMENT entity from the database.
 */
public class Instrument implements Serializable {

    private String instrId;
    private String name;
    private String key;

    /**
     * Default constructor for the Instrument class.
     */
    public Instrument() {
    }

    /**
     * Parameterized constructor for creating an Instrument with specific details.
     * 
     * @param instrId The unique identifier for the instrument.
     * @param name    The name of the instrument.
     * @param key     The musical key of the instrument.
     */
    public Instrument(String instrId, String name, String key) {
        this.instrId = instrId;
        this.name = name;
        this.key = key;
    }

    // Getters and Setters

    public String getInstrId() {
        return instrId;
    }

    public void setInstrId(String instrId) {
        this.instrId = instrId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    // Overriding toString for easier debugging and logging

    @Override
    public String toString() {
        return "Instrument{" +
                "instrId='" + instrId + '\'' +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                '}';
    }

    // Implement equals and hashCode based on 'instrId' since it's a unique identifier

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Instrument that = (Instrument) o;

        return instrId != null ? instrId.equals(that.instrId) : that.instrId == null;
    }

    @Override
    public int hashCode() {
        return instrId != null ? instrId.hashCode() : 0;
    }
}
