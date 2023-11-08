package BusinessLogicLayer;

/**
 * Musician Data Transfer Object (DTO) class.
 * This class is used to transport musician data across different layers of the application.
 */
public class MusicianDTO {

    private String ssn;
    private String name;
    private String address;
    private String phoneNumber;

    // Constructors
    public MusicianDTO() {
    }

    public MusicianDTO(String ssn, String name, String address, String phoneNumber) {
        this.ssn = ssn;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // toString method for printing
    @Override
    public String toString() {
        return "MusicianDTO{" +
               "ssn='" + ssn + '\'' +
               ", name='" + name + '\'' +
               ", address='" + address + '\'' +
               ", phoneNumber='" + phoneNumber + '\'' +
               '}';
    }

    // Additional methods like equals and hashCode could be implemented as needed
}
