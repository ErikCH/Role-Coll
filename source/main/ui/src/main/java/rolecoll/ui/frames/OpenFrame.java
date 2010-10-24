/*
 * OpenFrame.java
 *
 * Created on April 23, 2007, 5:30 PM
 */

package rolecoll.ui.frames;

import rolecoll.beans.GUIBean;
import rolecoll.ui.FrameException;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

/**
 *
 * @author  Zack
 */

/**
 * Frame to open a document stored in the database
 */
public class OpenFrame extends JFrame {
    // Variables declaration
    private JButton cancelButton;
    private JComboBox documentListComboBox;
    private JLabel documentListLabel;
    private JButton openButton;
    private Object[] documentList;
    private MainFrame mf;
    
    /** Creates new form OpenFrame */
    public OpenFrame(Object[] documentList, JFrame parent) {
        try{
            // set look and feel
            UIManager.setLookAndFeel(GUIBean.getLookAndFeel());
            this.documentList = documentList;
            this.mf = (MainFrame)parent;
            initComponents();
        } catch(Exception e){
            // display exceptions
            FrameException.showFrameException(e, this);
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     */
    private void initComponents() {
        // disable close X
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Open Document");
        try{
            setIconImage(ImageIO.read(MainFrame.class.getClassLoader().getResource("images/RoleColl.jpg")));
        } catch(Exception e){
            e.printStackTrace();
        }
        setResizable(false);

        documentListLabel = new JLabel();
        documentListComboBox = new JComboBox();
        openButton = new JButton();
        cancelButton = new JButton();

        documentListLabel.setFont(new Font("Verdana", 0, 12));
        documentListLabel.setText("Select Document to Open:");

        documentListComboBox.setFont(new Font("Verdana", 0, 12));
        documentListComboBox.setModel(new DefaultComboBoxModel(documentList));

        openButton.setFont(new Font("Verdana", 0, 12));
        openButton.setText("Open");
        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                openButtonActionPerformed();
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
                .addContainerGap()
                .add(layout.createParallelGroup(GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(documentListLabel)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(documentListComboBox, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
                    .add(GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(cancelButton)
                        .addPreferredGap(LayoutStyle.RELATED)
                        .add(openButton)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(GroupLayout.BASELINE)
                    .add(documentListLabel)
                    .add(documentListComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .add(16, 16, 16)
                .add(layout.createParallelGroup(GroupLayout.BASELINE)
                    .add(openButton)
                    .add(cancelButton))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }

    /**
     * dispose on cancel
     */
    private void cancelButtonActionPerformed() {
        this.dispose();
    }

    /**
     * retrive selected document from the database
     */
    private void openButtonActionPerformed() {
        mf.openDocument((String)documentListComboBox.getSelectedItem());
        this.dispose(); 
    }
}
