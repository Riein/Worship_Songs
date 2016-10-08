package app.novaci.com.worshipsongs;

/**
 * Created by Sasha on 10/7/2016.
 * Holds information for each song including
 * Title, text and language
 */

public class SongInfo {

    private String song_title;
    private String song_text;
    private String language;

    public SongInfo(String title, String text, String language) {
        this.song_title = title;
        this.song_text = text;
        this.language = language;
    }

    public String getTitle() {
        return this.song_title;
    }

    public String getText() {
        return this.song_text;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setTitle(String title) {
        this.song_title = title;
    }

    public void setText(String text) {
        this.song_text = text;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
