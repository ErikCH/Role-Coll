/*
 * NewDocumentFrame.java
 *
 * Created on April 23, 2007, 5:24 PM
 */

package rolecoll.ui.frames;

import rolecoll.beans.*;
import rolecoll.beans.dao.DocumentBeanController;
import rolecoll.ui.FrameException;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.HashMap;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;

import org.jdesktop.layout.LayoutStyle;
import org.jdesktop.layout.GroupLayout;

/**
 *
 * @author  Zack
 */

/**
 * Frame to create documents
 */
public class NewDocumentFrame extends JFrame {
    // Variables declaration
    private JButton addFileButton;
    private JButton cancelButton;
    private JButton createDocumentButton;
    private JList documentList;
    private JScrollPane documentScrollPane;
    private JButton removeFileButton;
    private HashMap fileMap;
    private static File lastFile;

    /** Creates new form */
    public NewDocumentFrame() {
        try{
            fileMap = new HashMap();
            // set look and feel
            UIManager.setLookAndFeel(GUIBean.getLookAndFeel());
            initComponents();
        } catch(Exception e){
            FrameException.showFrameException(e, this);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     */
    private void initComponents() {
        // disable close X
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("New Document");
        try{
            setIconImage(ImageIO.read(MainFrame.class.getClassLoader().getResource("images/RoleColl.jpg")));
        } catch(Exception e){
            e.printStackTrace();
        }
        setResizable(false);

        documentScrollPane = new JScrollPane();
        documentList = new JList();
        addFileButton = new JButton();
        removeFileButton = new JButton();
        createDocumentButton = new JButton();
        cancelButton = new JButton();

        documentList.setFont(new Font("Verdana", 0, 12));
        documentList.setModel(new DefaultListModel());
        documentList.setBackground(Color.WHITE);
        documentScrollPane.setViewportView(documentList);

        addFileButton.setFont(new Font("Verdana", 0, 12));
        addFileButton.setText("<< Add File");
        addFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addFileButtonActionPerformed();
            }
        });

        removeFileButton.setFont(new Font("Verdana", 0, 12));
        removeFileButton.setText(">> Remove Selected");
        removeFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                removeFileButtonActionPerformed();
            }
        });

        createDocumentButton.setFont(new Font("Verdana", 0, 12));
        createDocumentButton.setText("Create Document");
        createDocumentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                createDocumentButtonActionPerformed();
            }
        });

        cancelButton.setFont(new Font("Verdana", 0, 12));
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cancelButtonActionPerformed();
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(layout.createSequentialGroup()
                        .add(documentScrollPane, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(GroupLayout.LEADING, false)
                                .add(createDocumentButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(cancelButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(removeFileButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(addFileButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.LEADING)
                        .add(documentScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(addFileButton)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(removeFileButton)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(createDocumentButton)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(cancelButton))
        );
        pack();
    }

    /**
     * display a dialog box to get the name of the new document ant then save it to the database
     */
    private void createDocumentButtonActionPerformed() {
        StringBuffer sb = new StringBuffer();
        // iterate through map
        Object[] files = fileMap.keySet().toArray();
        for (int i=files.length-1; i>=0; i--) {
            File file =  (File)fileMap.get(files[i]);
            // append file path
            sb.append(file.getAbsolutePath());
            if(i>0){
                // append a ',' after all but last
                sb.append(",");
            }
        }
        // display name dialog box
        String documentName = JOptionPane.showInputDialog("Please Enter Name:", "");
        DocumentBean document = new DocumentBean();
        document.setDocName(documentName);
        document.setImageList(sb.toString().replaceAll("\\\\", "/"));
        // commit to database
        if(DocumentBeanController.addDocument(document)){
            JOptionPane.showMessageDialog(this,
                    "Document successfully added to the database",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(this,
                    "Error while adding document to database",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * dispose on cancel
     */
    private void cancelButtonActionPerformed() {
        this.dispose();
    }

    /**
     * remove selected file from the list of images
     */
    private void removeFileButtonActionPerformed() {
        int selected = documentList.getSelectedIndex();
        String fileName = (String)documentList.getSelectedValue();
        if(fileName != null){
            // remove from map
            fileMap.remove(fileName);
            // remove from list
            DefaultListModel model = (DefaultListModel)documentList.getModel();
            model.remove(selected);
        }
    }

    /**
     * open file chooser for image selection to be added to the image list
     */
    private void addFileButtonActionPerformed() {
        // file dialog
        JFileChooser chooser = new JFileChooser(lastFile);

        // remove "All Files" filter
        FileFilter[] filters = chooser.getChoosableFileFilters();
        chooser.removeChoosableFileFilter(filters[0]);

        // add jpg filter
        FileFilter jpgFilter = new JPGFilter();
        chooser.addChoosableFileFilter(jpgFilter);
        // add gif filter
        FileFilter gifFilter = new GIFFilter();
        chooser.addChoosableFileFilter(gifFilter);
        // add png filter
        FileFilter pngFilter = new PNGFilter();
        chooser.addChoosableFileFilter(pngFilter);
        // add bmp filter
        FileFilter allImagesFilter = new AllImagesFilter();
        chooser.addChoosableFileFilter(allImagesFilter);

        JFrame frame = new JFrame();

        chooser.showOpenDialog(frame);
        // Get the selected file
        File file = chooser.getSelectedFile();
        String fileName = file.getName();

        // memorize last path
        lastFile = file;

        // add file to array
        fileMap.put(fileName, file);
        // append to list
        DefaultListModel model = (DefaultListModel)documentList.getModel();
        int end = documentList.getModel().getSize();
        model.add(end, fileName);
        documentList.repaint();
    }
}
