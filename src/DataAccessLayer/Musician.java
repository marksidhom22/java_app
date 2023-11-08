package DataAccessLayer;

import java.io.Serializable;

/**
 * Represents a Musician entity as a plain old Java object (POJO).
 */
public class Musician implements Serializable {

    private String ssn;
    private String name;
    private String address;
    private String phoneNumber;

    /**
     * Constructs a new Musician with default values.
     */
    public Musician() {
        // Default constructor for initialization
    }

    /**
     * Constructs a new Musician with the specified details.
     *
     * @param ssn          the social security number of the musician
     * @param name         the name of the musician
     * @param address      the address of the musician
     * @param phoneNumber  the phone number of the musician
     */
    public Musician(String ssn, String name, String address, String phoneNumber) {
        this.ssn = ssn;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // Getters and setters for the musician properties

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

    @Override
    public String toString() {
        return "Musician{" +
                "ssn='" + ssn + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    // Implement equals and hashCode based on 'ssn' because it's a unique identifier for Musician

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Musician)) return false;

        Musician musician = (Musician) o;

        return getSsn() != null ? getSsn().equals(musician.getSsn()) : musician.getSsn() == null;
    }

    @Override
    public int hashCode() {
        return getSsn() != null ? getSsn().hashCode() : 0;
    }
}
