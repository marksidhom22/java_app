package DataAccessLayer;

import java.io.Serializable;

/**
 * Song DTO class to represent the SONG entity from the database.
 */
public class Song implements Serializable {

    private int songId;
    private String author;
    private String title;
    private int albumIdentifier; // Assuming this is the foreign key to Album

    /**
     * Default constructor for the Song class.
     */
    public Song() {
    }

    /**
     * Parameterized constructor for creating a Song with specific details.
     * 
     * @param songId          Unique identifier for the song.
     * @param author          Author of the song.
     * @param title           Title of the song.
     * @param albumIdentifier Identifier for the album the song appears on.
     */
    public Song(int songId, String author, String title, int albumIdentifier) {
        this.songId = songId;
        this.author = author;
        this.title = title;
        this.albumIdentifier = albumIdentifier;
    }

    // Getters and Setters

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAlbumIdentifier() {
        return albumIdentifier;
    }

    public void setAlbumIdentifier(int albumIdentifier) {
        this.albumIdentifier = albumIdentifier;
    }

    // Overriding toString for easier debugging and logging

    @Override
    public String toString() {
        return "Song{" +
                "songId=" + songId +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", albumIdentifier=" + albumIdentifier +
                '}';
    }

    // Implement equals and hashCode based on 'songId' since it's a unique identifier

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Song song = (Song) o;

        return songId == song.songId;
    }

    @Override
    public int hashCode() {
        return songId;
    }
}
