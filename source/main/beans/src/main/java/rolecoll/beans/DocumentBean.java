package rolecoll.beans;

/**
 * Created by IntelliJ IDEA.
 * User: znorcross
 * Date: Apr 22, 2007
 * Time: 11:24:56 PM
 */

/**
 * POJO for rows in the Document table
 */
public class DocumentBean {
    private long docID;
    private String docName;
    private String imageList;

    public long getDocID() {
        return docID;
    }

    public void setDocID(long docID) {
        this.docID = docID;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getImageList() {
        return imageList;
    }

    public void setImageList(String imageList) {
        this.imageList = imageList;
    }

    public String[] getImageArray(){
        return imageList.split(",");
    }
}
