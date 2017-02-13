package app.novaci.com.thensingsmysoul;

import java.io.Serializable;

/**
 * Created by Sasha on 10/7/2016.
 * Holds information for each song including
 * Title, text and language
 */

public class SongInfo implements Serializable{

    private int song_UUID;
    private String song_title;
    private String song_text;
    private String language;
    private int number;

    public SongInfo() {

    }

    public SongInfo(int UUID, String title, String text, String language, int number) {
        this.song_UUID = UUID;
        this.song_title = title;
        this.song_text = text;
        this.language = language;
        this.number = number;
    }

    public int getUUID() {
        return this.song_UUID;
    }

    public void setUUID(int UUID) {
        this.song_UUID = UUID;
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

    public int getNumber () {return this.number;}

    public void setNumber(int number) {
        this.number = number;
    }
}
