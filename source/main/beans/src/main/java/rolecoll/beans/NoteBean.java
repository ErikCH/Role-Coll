package rolecoll.beans;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Zack
 * Date: Apr 24, 2007
 * Time: 2:25:04 PM
 */

/**
 * POJO for rows in the Note table
 */
public class NoteBean {
    private long noteID;
    private long documentID;
    private long pageNumber;
    private long noteLocationX;
    private long noteLocationY;
    private long noteHeight;
    private long noteWidth;
    private String noteClass;
    private String noteContent;
    private String noteFont;
    private String noteColor;
    private String userPriority;
    private long userID;

    public long getNoteID() {
        return noteID;
    }

    public void setNoteID(long noteID) {
        this.noteID = noteID;
    }

    public long getDocumentID() {
        return documentID;
    }

    public void setDocumentID(long documentID) {
        this.documentID = documentID;
    }

    public long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getNoteLocationX() {
        return noteLocationX;
    }

    public void setNoteLocationX(long noteLocationX) {
        this.noteLocationX = noteLocationX;
    }

    public long getNoteLocationY() {
        return noteLocationY;
    }

    public void setNoteLocationY(long noteLocationY) {
        this.noteLocationY = noteLocationY;
    }

    public long getNoteHeight() {
        return noteHeight;
    }

    public void setNoteHeight(long noteHeight) {
        this.noteHeight = noteHeight;
    }

    public long getNoteWidth() {
        return noteWidth;
    }

    public void setNoteWidth(long noteWidth) {
        this.noteWidth = noteWidth;
    }

    public String getNoteClass() {
        return noteClass;
    }

    public void setNoteClass(String noteClass) {
        this.noteClass = noteClass;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getUserPriority() {
        return userPriority;
    }

    public void setUserPriority(String userPriority) {
        this.userPriority = userPriority;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getNoteFont() {
        return noteFont;
    }

    public void setNoteFont(String noteFont) {
        this.noteFont = noteFont;
    }

    public String getNoteColor() {
        return noteColor;
    }

    public void setNoteColor(String noteColor) {
        this.noteColor = noteColor;
    }

    public Color getRGBColor(){
        if(noteColor != null){
            String[] array = noteColor.split(",");
            return new Color(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2]));
        }
        return Color.BLACK;
    }
}
