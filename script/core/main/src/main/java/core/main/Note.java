package core.main;

public class Note {
    
    private String title;
    private String text;

    /// Create note with already existing title and text (When loading in already existing notes maybe?)
    public Note(String title, String text) {
        this.title = title;
        this.text = text;
    }

    /// Create empty note
    public Note() {
        
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

}

