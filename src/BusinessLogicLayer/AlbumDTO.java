package BusinessLogicLayer;

import java.util.Date;

/**
 * Album Data Transfer Object (DTO) class.
 * This class is used to transport album data across different layers of the application.
 */
public class AlbumDTO {

    private int albumIdentifier;
    private String title;
    private Date copyrightDate;
    private int speed;
    private String producerSSN; // assuming the producer's SSN is used to identify them

    // Constructors
    public AlbumDTO() {
    }

    public AlbumDTO(int albumIdentifier, String title, Date copyrightDate, int speed, String producerSSN) {
        this.albumIdentifier = albumIdentifier;
        this.title = title;
        this.copyrightDate = copyrightDate;
        this.speed = speed;
        this.producerSSN = producerSSN;
    }

    // Getters and Setters
    public int getAlbumIdentifier() {
        return albumIdentifier;
    }

    public void setAlbumIdentifier(int albumIdentifier) {
        this.albumIdentifier = albumIdentifier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCopyrightDate() {
        return copyrightDate;
    }

    public void setCopyrightDate(Date copyrightDate) {
        this.copyrightDate = copyrightDate;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getProducerSSN() {
        return producerSSN;
    }

    public void setProducerSSN(String producerSSN) {
        this.producerSSN = producerSSN;
    }

    // toString method for printing
    @Override
    public String toString() {
        return "AlbumDTO{" +
               "albumIdentifier=" + albumIdentifier +
               ", title='" + title + '\'' +
               ", copyrightDate=" + copyrightDate +
               ", speed=" + speed +
               ", producerSSN='" + producerSSN + '\'' +
               '}';
    }

    // Additional methods like equals and hashCode could be implemented as needed
}
