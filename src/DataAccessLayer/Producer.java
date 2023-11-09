package DataAccessLayer;

import java.util.List;

import DataAccessLayer.Album;

/**
 * Producer class that extends the Musician class to represent producers specifically.
 * This class can be tailored based on actual database schema and requirements.
 */
public class Producer extends Musician {
    
    private List<Album> producedAlbums; // List of albums produced by the producer

    /**
     * Default constructor for the Producer class.
     */
    public Producer() {
        super(); // Call the parent (Musician) constructor
    }

    /**
     * Parameterized constructor for creating a Producer with specific details.
     *
     * @param ssn     Social Security Number of the producer, primary key.
     * @param name    Name of the producer.
     * @param address Address of the producer.
     * @param phone   Phone number of the producer.
     */
    public Producer(String ssn, String name, String address, String phone) {
        super(ssn, name, address, phone); // Call the parent (Musician) constructor
    }
    
    public Producer(String ssn, String name) {
        super(ssn, name, null, null); // Call the parent (Musician) constructor
    }

    /**
     * Gets the list of albums produced by this producer.
     *
     * @return A list of Album objects.
     */
    public List<Album> getProducedAlbums() {
        return producedAlbums;
    }

    /**
     * Sets the list of albums produced by this producer.
     *
     * @param producedAlbums A list of Album objects to set.
     */
    public void setProducedAlbums(List<Album> producedAlbums) {
        this.producedAlbums = producedAlbums;
    }

    // You can add more functionality specific to the producer here

    // Overriding toString to include the produced albums
    @Override
    public String toString() {
        return "Producer{" +
                "ssn='" + getSsn() + '\'' +
                ", name='" + getName() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", phone='" + getPhoneNumber() + '\'' +
                ", producedAlbums=" + producedAlbums +
                '}';
    }

    // Note: equals and hashCode methods are inherited from the Musician class
}
