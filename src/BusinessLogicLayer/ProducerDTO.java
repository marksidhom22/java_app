package BusinessLogicLayer;

/**
 * Producer Data Transfer Object (DTO) class.
 * This class is used to transport producer data across different layers of the application.
 */
public class ProducerDTO {

    private String ssn;
    private String name;
    private int totalProducedAlbums; // This is an example of additional data you might want to track.

    // Constructors
    public ProducerDTO() {
    }

    public ProducerDTO(String ssn, String name) {
        this.ssn = ssn;
        this.name = name;
    }

    public ProducerDTO(String ssn, String name, int totalProducedAlbums) {
        this.ssn = ssn;
        this.name = name;
        this.totalProducedAlbums = totalProducedAlbums;
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

    public int getTotalProducedAlbums() {
        return totalProducedAlbums;
    }

    public void setTotalProducedAlbums(int totalProducedAlbums) {
        this.totalProducedAlbums = totalProducedAlbums;
    }

    // toString method for printing
    @Override
    public String toString() {
        return "ProducerDTO{" +
               "ssn='" + ssn + '\'' +
               ", name='" + name + '\'' +
               ", totalProducedAlbums=" + totalProducedAlbums +
               '}';
    }

    // Additional methods like equals and hashCode could be implemented as needed
}
