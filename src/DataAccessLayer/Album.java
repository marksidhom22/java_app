package DataAccessLayer;

import java.io.Serializable;
import java.util.Date;





public class Album implements Serializable {

    private int albumIdentifier;
    private String ssn; // Producer's SSN
    private Date copyrightDate;
    private String speed;
    private String title;

    /**
     * Default constructor for the Album class.
     */
    public Album() {
    }

    /**
     * Parameterized constructor for creating an Album with specific details.
     *
     * @param albumIdentifier Unique identifier for the album.
     * @param ssn             SSN of the producer.
     * @param copyrightDate   Copyright date of the album.
     * @param speed           Speed of the album.
     * @param title           Title of the album.
     */
    public Album(int albumIdentifier, String ssn, Date copyrightDate, String speed, String title) {
        this.albumIdentifier = albumIdentifier;
        this.ssn = ssn;
        this.copyrightDate = copyrightDate;
        this.speed = speed;
        this.title = title;
    }

    // Getters and Setters

    public int getAlbumIdentifier() {
        return albumIdentifier;
    }

    public void setAlbumIdentifier(int albumIdentifier) {
        this.albumIdentifier = albumIdentifier;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public Date getCopyrightDate() {
        return copyrightDate;
    }

    public void setCopyrightDate(Date copyrightDate) {
        this.copyrightDate = copyrightDate;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Overriding toString for easier debugging and logging

    @Override
    public String toString() {
        return "Album{" +
                "albumIdentifier=" + albumIdentifier +
                ", ssn='" + ssn + '\'' +
                ", copyrightDate=" + copyrightDate +
                ", speed=" + speed +
                ", title='" + title + '\'' +
                '}';
    }

    // Implement equals and hashCode based on 'albumIdentifier' since it's a unique identifier

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;

        return albumIdentifier == album.albumIdentifier;
    }

    @Override
    public int hashCode() {
        return albumIdentifier;
    }
}
