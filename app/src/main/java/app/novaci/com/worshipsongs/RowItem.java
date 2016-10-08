package app.novaci.com.worshipsongs;

/**
 * Created by Sasha on 7/26/2016.
 */
public class RowItem {
    String m_Title;
    String m_Description;

    public RowItem(String title, String description) {
        this.m_Title = title;
        this.m_Description = description;
    }

    public String getText() {
        return m_Title;
    }

    public String getDescription(){
        return m_Description;
    }

    public void setTitle(String title) {
        this.m_Title = title;
    }

    public void setDescription(String description) {
        this.m_Description = description;
    }
}
