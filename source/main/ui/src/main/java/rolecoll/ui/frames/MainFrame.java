/*
 * MainFrame.java
 *
 * Created on November 20, 2006, 9:53 PM
 */

package rolecoll.ui.frames;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import rolecoll.beans.*;
import rolecoll.beans.dao.HibernateUtil;
import rolecoll.beans.dao.DocumentBeanFactory;
import rolecoll.beans.dao.NoteBeanFactory;
import rolecoll.ui.*;
import org.flexdock.view.Viewport;
import org.flexdock.view.View;
import org.flexdock.docking.DockingConstants;
import org.flexdock.docking.DockingManager;
import org.flexdock.util.SwingUtility;

/**
 * Created by IntelliJ IDEA.
 * User: Zack
 */
/**
 * This class displays the main UI screen
 */
public class MainFrame extends JFrame implements DockingConstants, MainFrameConstants {
    private JMenuBar menuBar;
    private JTabbedPane tabbedPane;
    private boolean inComponent;
    private boolean isMousePressed = false;
    private HashMap documentsByName;
    private UserBean currentUser;

    /** Creates new form MainFrame */
    public MainFrame() {
        try{
            // set look and feel
            UIManager.setLookAndFeel(GUIBean.getLookAndFeel());
            Dimension frameSize = Toolkit.getDefaultToolkit().getScreenSize();
            // we want the frame to stay the same size and cover the entire screen
            setPreferredSize(frameSize);
            setMinimumSize(frameSize);
            setMaximumSize(frameSize);
            this.setResizable(false);           
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    int answer = JOptionPane.showConfirmDialog(e.getComponent(),
                            "Are you sure you want to quit?",
                            "Exit?",
                            JOptionPane.YES_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if(answer == JOptionPane.YES_OPTION){
                        // shutdown database connection
                        HibernateUtil.shutdown();
                        System.exit(0);
                    }
                }
            });

            initComponents();

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     */
    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Role-Coll");
        try{
            setIconImage(ImageIO.read(MainFrame.class.getClassLoader().getResource("images/RoleColl.jpg")));
        } catch(Exception e){
            e.printStackTrace();
        }

        // top menu
        createMenu();
        setJMenuBar(menuBar);

        getContentPane().setLayout(new BorderLayout());

        Viewport viewport = new Viewport();
        getContentPane().add(viewport, BorderLayout.CENTER);

        // document display
        View musicPage = createMusicPage();
        viewport.dock(musicPage);

        // user tool bar
        View usersView = createUsersTab();
        musicPage.dock(usersView, WEST_REGION);
        DockingManager.setMinimized(usersView, true, viewport, DockingConstants.LEFT);

        // tools toolbar
        View toolsView = createToolsTab();
        musicPage.dock(toolsView, EAST_REGION);
        DockingManager.setMinimized(toolsView, true, viewport, DockingConstants.RIGHT);
        pack();
    }

    /**
     * This method creates text note on the currently selected document
     */
    private void createTextNote(String content) {
        createNote("rolecoll.ui.TextNote", null, content);
    }

    /**
     * This method creates scribble note on the currently selected document
     */
    private void createScribbleNote() {
        createNote("rolecoll.ui.Scribble", null, null);
    }

    /**
     * This method creates text note on the currently selected document
     */
    private void createImageNote(String content) {
        createNote("rolecoll.ui.ImageNote", null, content);
    }

    /**
    * This method opens a dialog box for opening documents (tool bar)
    */
    private void openButtonActionPerformed() {
        openAction();
    }

    /**
    * This method opens a dialog box for opening documents (File menu)
    */
    private void openMenuItemActionPerformed() {
        openAction();
    }

    /**
     * creates a open dialog frame
     */
    private void openAction(){
        try{
            documentsByName = new HashMap();
            // retrive all documents from the database
            Collection documents = DocumentBeanFactory.getAllDocuments();
            // create document name list
            ArrayList documentNameList = new ArrayList();
            for (Iterator iterator = documents.iterator(); iterator.hasNext();) {
                DocumentBean documentBean = (DocumentBean) iterator.next();
                documentNameList.add(documentBean.getDocName());
                documentsByName.put(documentBean.getDocName(), documentBean);
            }
            Object[] documentArray = documentNameList.toArray();
            Arrays.sort(documentArray);
            // create open dialog frame
            JFrame of = new OpenFrame(documentArray, this);
            SwingUtility.centerOnScreen(of);
            of.setVisible(true);
        } catch(Exception e){
            // show any exceptions
            FrameException.showFrameException(e, this);
        }
    }

    /**
     * opens selected document and displays it in the main frame
     * @param documentName - name of the selected document
     */
    public void openDocument(String documentName){
        DocumentBean document = (DocumentBean)documentsByName.get(documentName);
        String[] imageList = document.getImageArray();
        // create new tabbed pane
        Document documentPane = new Document(JTabbedPane.TOP);
        documentPane.setDocumentInfo(document);
        // add each page
        for (int i = 0; i < imageList.length; i++) {
            String imageName = imageList[i];
            // Get the selected file
            File file = new File(imageName);
            BufferedImage image = null;
            try {
                image = ImageIO.read(file);
            } catch(IOException e) {
                FrameException.showFrameException(e, this);
                System.out.println("read error: " + e.getMessage());
            }
            ImageIcon icon = new ImageIcon(image);
            JLabel imageLabel = new JLabel(icon);
            JScrollPane imageScroll = new JScrollPane(imageLabel);
            documentPane.add("Page " + (i+1), imageScroll);
        }
        // add page turn listeners
        documentPane.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                JTabbedPane innerTabPane = (JTabbedPane)e.getComponent();
                if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN && innerTabPane != null) {
                    // if not at end
                    if(innerTabPane.getSelectedIndex()+1 < innerTabPane.getTabCount() ){
                        // advance
                        innerTabPane.setSelectedIndex(innerTabPane.getSelectedIndex()+1);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_PAGE_UP && innerTabPane != null) {
                    // if not at begining
                    if(innerTabPane.getSelectedIndex() != 0 ){
                        // go back
                        innerTabPane.setSelectedIndex(innerTabPane.getSelectedIndex()-1);
                    }
                }
            }
        });
        tabbedPane.add(documentName, documentPane);

        // move selected panel to new panel
        if(tabbedPane.getSelectedIndex() > -1 ){
            if(tabbedPane.getTabCount() > 1)
                tabbedPane.setSelectedIndex(tabbedPane.getSelectedIndex()+1);
        }
    }

    /**
     * open a new document dialog frame
     */
    private void newAction(){        
        JFrame ndf = new NewDocumentFrame();
        SwingUtility.centerOnScreen(ndf);
        ndf.setVisible(true);
    }

    /**
     * Create viewport for the documents to display in
     * @return docking view
     */
    private View createMusicPage(){
        String id = "musicPage";
        View musicPage = new View(id, null, null);
        musicPage.setTerritoryBlocked(CENTER_REGION, true);
        musicPage.setTitlebar(null);
        tabbedPane = new JTabbedPane();
        tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        // add page change listeners
        tabbedPane.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                JTabbedPane tabPane = (JTabbedPane)e.getComponent();
                JTabbedPane innerTabPane = (JTabbedPane)tabPane.getSelectedComponent();
                if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN && innerTabPane != null) {
                    // if not at end
                    if(innerTabPane.getSelectedIndex()+1 < innerTabPane.getTabCount() ){
                        // advance
                        innerTabPane.setSelectedIndex(innerTabPane.getSelectedIndex()+1);
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_PAGE_UP && innerTabPane != null) {
                    // if not at begining
                    if(innerTabPane.getSelectedIndex() != 0 ){
                        // go back
                        innerTabPane.setSelectedIndex(innerTabPane.getSelectedIndex()-1);
                    }
                }
            }
        });
        musicPage.setContentPane(tabbedPane);
        return musicPage;
    }

    /**
     * Create users toolbar docked to the left
     * @return docking view
     */
    private View createUsersTab(){
        View usersView = new View("user.list", "Users");

        // create user tree
        JScrollPane jScrollPane1 = new JScrollPane();
        JTree jTree1 = new JTree(Users.userTree);

        jTree1.setFont(new Font("Verdana", 1, 11));
        jScrollPane1.setViewportView(jTree1);

        // Update only one tree instance
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) jTree1.getCellRenderer();

        // Remove the tree icons
        renderer.setLeafIcon(null);
        renderer.setClosedIcon(null);
        renderer.setOpenIcon(null);

        usersView.setContentPane(jScrollPane1);

        return usersView;
    }

    /**
     * Create tools toolbar docked to the right
     * @return docking view
     */
    private View createToolsTab(){
        View toolsView = new View("tools.list", "Tools");

        JToolBar toolBar = new JToolBar(null, JToolBar.HORIZONTAL);
        toolBar.setRollover(true);
        toolBar.setFont(new Font("Verdana", 0, 12));

        // add new doc button
        JButton newDocButton;
        try{
            newDocButton = new JButton(
                    new ImageIcon(
                            ImageIO.read(MainFrame.class.getClassLoader().getResource("images/NewDoc.jpg"))));
        } catch(Exception e){
            newDocButton = new JButton("New Document");
            e.printStackTrace();
        }
        newDocButton.setFont(new Font("Verdana", 1, 12));
        newDocButton.setToolTipText("New Document");
        newDocButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                newAction();
            }
        });
        toolBar.add(newDocButton);

        // add open button
        JButton openButton;
        try{
            openButton = new JButton(
                    new ImageIcon(
                            ImageIO.read(MainFrame.class.getClassLoader().getResource("images/open.jpg"))));
        } catch(Exception e){
            openButton = new JButton("Open");
            e.printStackTrace();
        }
        openButton.setFont(new Font("Verdana", 1, 12));
        openButton.setToolTipText("Open");
        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                openButtonActionPerformed();
            }
        });
        toolBar.add(openButton);

        // text note button
        JButton addTextNote;
        try{
            addTextNote = new JButton(
                    new ImageIcon(
                            ImageIO.read(MainFrame.class.getClassLoader().getResource("images/note.jpg"))));

        } catch(Exception e){
            addTextNote = new JButton("Add Text Note");
            e.printStackTrace();
        }
        addTextNote.setFont(new java.awt.Font("Verdana", 1, 12));
        addTextNote.setToolTipText("Add Text Note");
        addTextNote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                createTextNote(null);
            }
        });
        toolBar.add(addTextNote);

        // scrible note button
        JButton addScribbleNote;
        try{
            addScribbleNote = new JButton(
                    new ImageIcon(
                            ImageIO.read(MainFrame.class.getClassLoader().getResource("images/pencil.jpg"))));

        } catch(Exception e){
            addScribbleNote = new JButton("Add Scribble Note");
            e.printStackTrace();
        }
        addScribbleNote.setFont(new java.awt.Font("Verdana", 1, 12));
        addScribbleNote.setToolTipText("Add Scribble Note");
        addScribbleNote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                createScribbleNote();
            }
        });
        toolBar.add(addScribbleNote);        
        toolsView.add(toolBar);
        // add all other image note buttons
        addMusicNotationButtons(toolsView);
        return toolsView;
    }

    /**
     * Create top menu
     */
    void createMenu(){
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu();
        JMenuItem openMenuItem = new JMenuItem();
        JMenuItem newMenuItem = new JMenuItem();
        JMenu editMenu = new JMenu();
        JMenu helpMenu = new JMenu();
        JMenu skinMenu = new JMenu();

        fileMenu.setText("File");
        openMenuItem.setText("Open");
        openMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                openMenuItemActionPerformed();
            }
        });
        fileMenu.add(openMenuItem);

        newMenuItem.setText("Create New Document");
        newMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                newAction();
            }
        });
        fileMenu.add(newMenuItem);

        menuBar.add(fileMenu);

        JMenu currentDocMenu = new JMenu();
        currentDocMenu.setText("Load Notes for Current Document");
        JMenuItem menuItem = new JMenuItem();
        menuItem.setText("All Notes");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               try{
                    loadNotesAction(NoteBeanFactory.getAllNotesForDocument(
                            getCurrentDocument().getDocumentInfo().getDocID()));
                } catch(Exception e){
                    // log exception
                    e.printStackTrace();
                }
            }
        });
        currentDocMenu.add(menuItem);

        menuItem = new JMenuItem();
        menuItem.setText("All My Notes");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try{
                    loadNotesAction(NoteBeanFactory.getAllNotesForDocument(
                            getCurrentDocument().getDocumentInfo().getDocID(), currentUser.getUserID()));
                } catch(Exception e){
                    // log exception
                    e.printStackTrace();
                }
            }
        });
        currentDocMenu.add(menuItem);

        menuItem = new JMenuItem();
        menuItem.setText("All Notes for My Role");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try{
                    loadNotesAction(NoteBeanFactory.getAllNotesWithPriorityEqual(
                            getCurrentDocument().getDocumentInfo().getDocID(), currentUser.getRolePriority()));
                } catch(Exception e){
                    // log exception
                    e.printStackTrace();
                }
            }
        });
        currentDocMenu.add(menuItem);

        menuItem = new JMenuItem();
        menuItem.setText("All Notes for My Role and Above");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try{
                    loadNotesAction(NoteBeanFactory.getAllNotesWithPriorityGreaterThan(
                            getCurrentDocument().getDocumentInfo().getDocID(), currentUser.getRolePriority()));
                } catch(Exception e){
                    // log exception
                    e.printStackTrace();
                }
            }
        });
        currentDocMenu.add(menuItem);

        menuItem = new JMenuItem();
        menuItem.setText("All Notes for My Role and Below");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try{
                    loadNotesAction(NoteBeanFactory.getAllNotesWithPriorityLessThan(
                            getCurrentDocument().getDocumentInfo().getDocID(), currentUser.getRolePriority()));
                } catch(Exception e){
                    // log exception
                    e.printStackTrace();
                }
            }
        });
        currentDocMenu.add(menuItem);
        JMenu notesMenu = new JMenu();
        notesMenu.setText("Notes");
        notesMenu.add(currentDocMenu);
        
        menuBar.add(notesMenu);

        editMenu.setText("Edit");
        menuBar.add(editMenu);

        helpMenu.setText("Help");
        menuItem = new JMenuItem();
        menuItem.setText("On Screen Keyboard");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                showOSK();
            }
        });
        helpMenu.add(menuItem);
        menuBar.add(helpMenu);

        skinMenu.setText("Skin");
        for(int i = 0; i < GUIBean.THEME_LIST.length; i++){
            JMenuItem tempMenuItem = new JMenuItem();
            tempMenuItem.setText(GUIBean.THEME_LIST[i]);
            tempMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    GUIBean.updateLF(((JMenuItem)evt.getSource()).getText());
                }
            });
            skinMenu.add(tempMenuItem);
        }
        menuBar.add(skinMenu);
    }

    /**
     * Function used to remove the internal frame or boraders from a note
     * @param e - mouse event
     */
    private void removeInternalWindow(MouseEvent e){
        // if the mouse is not in the component and the mouse button is not pressed
        if(!inComponent && !isMousePressed){
            // get the note component from the internal frame tree
            JComponent noteComponent = (JComponent)((JRootPane)((JInternalFrame)e.getComponent()).getComponent(0)).getContentPane();
            // get the current page
            JScrollPane pane = (JScrollPane)((JTabbedPane)tabbedPane.getSelectedComponent()).getSelectedComponent();
            // add the note to the page
            JLabel label = (JLabel)((JViewport)pane.getComponent(0)).getComponent(0);
            // adjust for the border
            Point p = e.getComponent().getLocation();
            noteComponent.setLocation(p.x+xAdjustment, p.y+yAdjustment);
            Dimension d = e.getComponent().getSize();
            noteComponent.setSize(d.width-widthAdjustment, d.height-heightAdjustment);
            label.add(noteComponent);
            // dispose the internal frame
            ((JInternalFrame)e.getComponent()).dispose();
        }
    }

    /**
     * This method uses reflection to create a note of a specific class
     * @param noteClass - string representation of a note class
     * @param noteInfo - stored information about a note
     * @param content - content to add to a note
     */
    private void createNote(String noteClass, NoteBean noteInfo, String content){
        try{
            JComponent noteComponent;

            // create new note
            if(noteInfo == null){
                // use reflection to get the note class
                Class loaderClass = this.getClass().getClassLoader().loadClass(noteClass);
                noteComponent = (JComponent)loaderClass.newInstance();
                // default size
                noteComponent.setSize(100, 100);
                if(noteComponent instanceof TextNote){
                    // set note Info
                    NoteBean newNoteInfo = new NoteBean();
                    newNoteInfo.setDocumentID((getCurrentDocument()).getDocumentInfo().getDocID());
                    newNoteInfo.setPageNumber(((JTabbedPane)tabbedPane.getSelectedComponent()).getSelectedIndex());
                    newNoteInfo.setUserID(currentUser.getUserID());
                    newNoteInfo.setUserPriority(currentUser.getRolePriority());
                    noteComponent.setFont(new Font("Verdana", 1, 12));
                    ((TextNote)noteComponent).setNoteInfo(newNoteInfo);
                }
                else if(noteComponent instanceof Scribble){
                    // set note Info
                    NoteBean newNoteInfo = new NoteBean();
                    newNoteInfo.setDocumentID(getCurrentDocument().getDocumentInfo().getDocID());
                    newNoteInfo.setPageNumber(((JTabbedPane)tabbedPane.getSelectedComponent()).getSelectedIndex());
                    newNoteInfo.setUserID(currentUser.getUserID());
                    newNoteInfo.setUserPriority(currentUser.getRolePriority());
                    ((Scribble)noteComponent).setNoteInfo(newNoteInfo);
                }
                else if(noteComponent instanceof ImageNote){
                    // set note Info
                    noteComponent.setSize(50, 50);
                    NoteBean newNoteInfo = new NoteBean();
                    newNoteInfo.setDocumentID((getCurrentDocument()).getDocumentInfo().getDocID());
                    newNoteInfo.setPageNumber(((JTabbedPane)tabbedPane.getSelectedComponent()).getSelectedIndex());
                    newNoteInfo.setUserID(currentUser.getUserID());
                    newNoteInfo.setUserPriority(currentUser.getRolePriority());
                    newNoteInfo.setNoteContent(content);
                    ((ImageNote)noteComponent).setNoteInfo(newNoteInfo);
                    // add image
                    BufferedImage image = null;
                    try {
                        image = ImageIO.read(MainFrame.class.getClassLoader().getResource(content));
                    } catch(IOException e) {
                        System.out.println("read error: " + e.getMessage());
                    }
                    ImageIcon icon = new ImageIcon(image);
                    ((ImageNote)noteComponent).setIcon(icon);
                }
                // default font color
                noteComponent.setForeground(Color.black);
                // default background
                noteComponent.setBackground(Color.WHITE);
                Dimension d = this.getSize();
                // center top
                noteComponent.setLocation(d.width/2, 0);
                noteComponent.requestFocus();
            }

            // existing note
            else{
                // use reflection to get note class
                Class loaderClass = this.getClass().getClassLoader().loadClass(noteInfo.getNoteClass());
                noteComponent = (JComponent)loaderClass.newInstance();
                // set note Info and load note content
                if(noteComponent instanceof TextNote){
                    ((TextNote)noteComponent).setNoteInfo(noteInfo);
                    ((TextNote)noteComponent).load();
                }
                else if(noteComponent instanceof Scribble){
                    ((Scribble)noteComponent).setNoteInfo(noteInfo);
                    ((Scribble)noteComponent).load();
                }
                else if(noteComponent instanceof ImageNote){
                    ((ImageNote)noteComponent).setNoteInfo(noteInfo);
                    ((ImageNote)noteComponent).load();
                }
                // set transparent
                noteComponent.setOpaque(false);
            }

            // add mouse listener for internal frame or border
            noteComponent.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    // if in note
                    if(e.getComponent().getParent().getParent() instanceof JViewport){
                        inComponent = true;
                        // save note component
                        JComponent tempComponent = (JComponent)e.getComponent();
                        if(tempComponent.isOpaque()){
                            // if not transparent make transparent
                            tempComponent.setOpaque(false);
                        }
                        // create internal frame
                        JInternalFrame note = new JInternalFrame();
                        // remove all butons except close
                        note.setMaximizable(false);
                        note.setIconifiable(false);
                        try{
                            note.setIcon(false);
                        } catch(Exception e1){
                            e1.printStackTrace();
                        }
                        note.setOpaque(false);
                        // if image note prohibit resize
                        if(!(tempComponent instanceof ImageNote)){
                            note.setResizable(true);
                        }
                        note.setClosable(true);
                        // adjust for borders
                        Point p = e.getComponent().getLocation();
                        int x = p.x-xAdjustment;
                        if(x < 0){
                            x = 0;
                        }
                        int y = p.y-yAdjustment;
                        if(y < 0){
                            y = 0;
                        }
                        note.setLocation(x, y);
                        Dimension d = e.getComponent().getSize();
                        note.setSize(d.width+widthAdjustment, d.height+heightAdjustment);
                        // add note component to internal frame
                        note.setContentPane((JComponent)e.getComponent());
                        // add listener for removing internal frame
                        note.addMouseListener(new MouseAdapter() {
                            public void mouseExited(MouseEvent e) {
                                removeInternalWindow(e);
                            }
                        });
                        // listner for moving frame
                        note.addMouseListener(new MouseAdapter() {
                            public void mousePressed(MouseEvent e) {
                                isMousePressed = true;
                            }
                        });
                        // listener to drop note
                        note.addMouseListener(new MouseAdapter() {
                            public void mouseReleased(MouseEvent e) {
                                removeInternalWindow(e);
                                isMousePressed = false;
                            }
                        });
                        note.show();
                        // set note on page
                        JScrollPane pane = (JScrollPane)((JTabbedPane)tabbedPane.getSelectedComponent()).getSelectedComponent();
                        JLabel label = (JLabel)((JViewport)pane.getComponent(0)).getComponent(0);
                        label.add(note);
                        try{
                            note.setSelected(true);
                        } catch(Exception e2){
                            e2.printStackTrace();
                        }
                    }
                    else{
                        e.getComponent().requestFocus();
                    }
                }
            });
            // listener for exiting note
            noteComponent.addMouseListener(new MouseAdapter() {
                public void mouseExited(MouseEvent e) {
                    inComponent = false;
                }
            });

            // set note
            if(noteInfo == null){
                JScrollPane pane = (JScrollPane)((JTabbedPane)tabbedPane.getSelectedComponent()).getSelectedComponent();
                JLabel label = (JLabel)((JViewport)pane.getComponent(0)).getComponent(0);
                label.add(noteComponent);
            }
            else{
                JTabbedPane document = (JTabbedPane)tabbedPane.getSelectedComponent();
                JScrollPane pane = (JScrollPane)document.getComponentAt((int)noteInfo.getPageNumber());
                JLabel label = (JLabel)((JViewport)pane.getComponent(0)).getComponent(0);
                label.add(noteComponent);
                noteComponent.setLocation((int)noteInfo.getNoteLocationX(), (int)noteInfo.getNoteLocationY());
            }
            noteComponent.repaint();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Given a set of notes this method istantiates each note
     * @param notes - collection of notes from the database
     */
    private void loadNotesAction(Collection notes){
        for (Iterator iterator = notes.iterator(); iterator.hasNext();) {
            NoteBean note = (NoteBean) iterator.next();
            // create each note
            createNote(note.getNoteClass(), note, note.getNoteContent());
        }
        JOptionPane.showMessageDialog(this,
                        notes.size() + " note successfully retrived",
                        "Notes Retrived",
                        JOptionPane.INFORMATION_MESSAGE);
    }


    /**
     * set current user
     * @param user - user information
     */
    public void setUser(UserBean user){
        this.currentUser = user;
    }

    /**
     * set current document
     * @return - document information
     */
    private Document getCurrentDocument(){
        return (Document)tabbedPane.getSelectedComponent();
    }

    /**
     * add all of the image note icons to the toolbar
     * @param view - docking toolbar to add to
     */
    private void addMusicNotationButtons(View view){
        JToolBar newToolBar = new JToolBar(null, JToolBar.HORIZONTAL);
        for(int i=0; i<20; i++){
            // get the note icon image and the image placed on the document
            newToolBar.add(getNewButton(musicNotationImages + (i+1) + ".jpg", musicNotationImages + (i+1) + ".gif"));
            if(i==3 || i==6 || i==9 || i==12 || i==17){
                view.add(newToolBar);
                newToolBar = new JToolBar(null, JToolBar.HORIZONTAL);
            }
        }
        view.add(newToolBar);
    }


    private JButton getNewButton(String image, final String noteContent){
        JButton newButton;
        try{
            newButton = new JButton(
                    new ImageIcon(
                            ImageIO.read(MainFrame.class.getClassLoader().getResource(image))));

        } catch(Exception e){
            newButton = new JButton("Add Note");
            e.printStackTrace();
        }
        newButton.setFont(new java.awt.Font("Verdana", 1, 12));
        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                createImageNote(noteContent);
            }
        });
        return newButton;
    }

    public static void showOSK(){
        Runtime run = Runtime.getRuntime();
        try{
            run.exec("osk.exe");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
