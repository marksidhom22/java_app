package BusinessLogicLayer;

/**
 * Instrument Data Transfer Object (DTO) class.
 * This class is used to transport instrument data across different layers of the application.
 */
public class InstrumentDTO {

    private String instrId;
    private String name;
    private String key;

    // Constructors
    public InstrumentDTO() {
    }

    public InstrumentDTO(String instrId, String name, String key) {
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

    // toString method for printing
    @Override
    public String toString() {
        return "InstrumentDTO{" +
               "instrId='" + instrId + '\'' +
               ", name='" + name + '\'' +
               ", key='" + key + '\'' +
               '}';
    }

    // Additional methods like equals and hashCode could be implemented as needed
}
