package rolecoll.ui;

import rolecoll.beans.DocumentBean;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: Zack
 * Date: Apr 24, 2007
 * Time: 4:28:59 PM
 */

/**
 * Extended JTabbedPane to include document information for open documents
 */
public class Document extends JTabbedPane {
    private DocumentBean documentInfo;

    public Document(int tabPlacement){
        super(tabPlacement);
    }

    public DocumentBean getDocumentInfo() {
        return documentInfo;
    }

    public void setDocumentInfo(DocumentBean documentInfo) {
        this.documentInfo = documentInfo;
    }
}
