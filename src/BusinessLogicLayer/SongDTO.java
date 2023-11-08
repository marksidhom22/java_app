package BusinessLogicLayer;

/**
 * Song Data Transfer Object (DTO) class.
 * This class is used to transport song data across different layers of the application.
 */
public class SongDTO {

    private int songId;
    private String title;
    private String author;
    private int albumIdentifier; // assuming the album's identifier is used to relate the song to an album

    // Constructors
    public SongDTO() {
    }

    public SongDTO(int songId, String title, String author, int albumIdentifier) {
        this.songId = songId;
        this.title = title;
        this.author = author;
        this.albumIdentifier = albumIdentifier;
    }

    // Getters and Setters
    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAlbumIdentifier() {
        return albumIdentifier;
    }

    public void setAlbumIdentifier(int albumIdentifier) {
        this.albumIdentifier = albumIdentifier;
    }

    // toString method for printing
    @Override
    public String toString() {
        return "SongDTO{" +
               "songId=" + songId +
               ", title='" + title + '\'' +
               ", author='" + author + '\'' +
               ", albumIdentifier=" + albumIdentifier +
               '}';
    }

    // Additional methods like equals and hashCode could be implemented as needed
}
