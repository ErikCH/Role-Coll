/*
 * AdminFrame.java
 *
 * Created on November 26, 2006, 4:49 PM
 */

package rolecoll.ui.frames;

import javax.imageio.ImageIO;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.*;


import rolecoll.beans.*;
import rolecoll.beans.dao.HibernateUtil;
import rolecoll.beans.dao.UserBeanController;
import org.flexdock.util.SwingUtility;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

import java.awt.*;
import java.awt.event.*;

/**
 * Created by IntelliJ IDEA.
 * User: Zack
 */
/**
 * This class displays the admin form
 */
public class AdminFrame extends JFrame {
    // Variables declaration
    private JMenuItem editUserMenuItem;
    private JMenu actionMenu;
    private JLabel firstNameLabel;
    private JTextField firstNameText;
    private JTextField idText;
    private JLabel lastNameLabel;
    private JTextField lastNameText;
    private JLabel loginLabel;
    private JPanel loginPanel;
    private JTextField loginText;
    private JMenuBar menuBar;
    private JMenuItem newUserMenuItem;
    private JLabel passwordLabel;
    private JTextField passwordText;
    private JPanel personalPanel;
    private JMenuItem removeUserMenuItem;
    private JTextField roleName;
    private JLabel roleNameLabel;
    private JPanel rolePanel;
    private JTextField rolePriority;
    private JLabel rolePriorityLabel;
    private JLabel superiorIDLabel;
    private JTextField superiorId;
    private JTabbedPane tabbedPane;
    private JLabel userIDLabel;
    private JTree userTree;
    private JScrollPane userTreeScrollPane;

    private TreePath path = null;
    private static DefaultTreeCellRenderer renderer;

    /** Creates new frame */
    public AdminFrame() {
        try{
            // init skin
            UIManager.setLookAndFeel(GUIBean.getLookAndFeel());
            initComponents();
            // Update only one tree instance
            renderer = (DefaultTreeCellRenderer)userTree.getCellRenderer();

            // Remove the icons
            renderer.setLeafIcon(null);
            renderer.setClosedIcon(null);
            renderer.setOpenIcon(null);

            renderTree();
            personalPanel.grabFocus();

            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    int answer = JOptionPane.showConfirmDialog(e.getComponent(),
                            "Are you sure you want to quit?",
                            "Exit?",
                            JOptionPane.YES_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if(answer == JOptionPane.YES_OPTION){
                        // shutdown hibernate
                        HibernateUtil.shutdown();
                        System.exit(0);
                    }
                }
            });
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.     
     */
    private void initComponents() {
        userTreeScrollPane = new JScrollPane();
        userTree = new JTree(Users.userTree);
        tabbedPane = new JTabbedPane();
        personalPanel = new JPanel();
        firstNameText = new JTextField();
        lastNameText = new JTextField();
        firstNameLabel = new JLabel();
        lastNameLabel = new JLabel();
        loginPanel = new JPanel();
        loginLabel = new JLabel();
        passwordLabel = new JLabel();
        loginText = new JTextField();
        passwordText = new JTextField();
        userIDLabel = new JLabel();
        idText = new JTextField();
        rolePanel = new JPanel();
        roleName = new JTextField();
        roleNameLabel = new JLabel();
        rolePriorityLabel = new JLabel();
        rolePriority = new JTextField();
        superiorIDLabel = new JLabel();
        superiorId = new JTextField();
        menuBar = new JMenuBar();
        actionMenu = new JMenu();
        newUserMenuItem = new JMenuItem();
        editUserMenuItem = new JMenuItem();
        removeUserMenuItem = new JMenuItem();

        setTitle("Role-Coll");
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        try{
            setIconImage(ImageIO.read(MainFrame.class.getClassLoader().getResource("images/RoleColl.jpg")));
        } catch(Exception e){
            e.printStackTrace();
        }
        setResizable(false);
        userTree.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                userTreeFocusGained();
            }
        });

        userTreeScrollPane.setViewportView(userTree);

        tabbedPane.setFont(new Font("Verdana", 1, 12));
        personalPanel.setFont(new Font("Verdana", 1, 12));
        firstNameText.setFont(new Font("Verdana", 0, 12));

        lastNameText.setFont(new Font("Verdana", 0, 12));

        firstNameLabel.setFont(new Font("Verdana", 1, 12));
        firstNameLabel.setText("First Name");

        lastNameLabel.setFont(new Font("Verdana", 1, 12));
        lastNameLabel.setText("Last Name");

        GroupLayout personalPanelLayout = new GroupLayout(personalPanel);
        personalPanel.setLayout(personalPanelLayout);
        personalPanelLayout.setHorizontalGroup(
            personalPanelLayout.createParallelGroup(GroupLayout.LEADING)
            .add(personalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(personalPanelLayout.createParallelGroup(GroupLayout.LEADING)
                    .add(firstNameLabel)
                    .add(lastNameLabel))
                .add(12, 12, 12)
                .add(personalPanelLayout.createParallelGroup(GroupLayout.TRAILING)
                    .add(lastNameText, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                    .add(firstNameText, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE))
                .addContainerGap())
        );
        personalPanelLayout.setVerticalGroup(
            personalPanelLayout.createParallelGroup(GroupLayout.LEADING)
            .add(personalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(personalPanelLayout.createParallelGroup(GroupLayout.BASELINE)
                    .add(firstNameLabel)
                    .add(firstNameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .add(12, 12, 12)
                .add(personalPanelLayout.createParallelGroup(GroupLayout.BASELINE)
                    .add(lastNameLabel)
                    .add(lastNameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(198, Short.MAX_VALUE))
        );
        tabbedPane.addTab("Personal", personalPanel);

        loginPanel.setFont(new Font("Verdana", 1, 12));
        loginLabel.setFont(new Font("Verdana", 1, 12));
        loginLabel.setText("Login");

        passwordLabel.setFont(new Font("Verdana", 1, 12));
        passwordLabel.setText("Password");

        loginText.setFont(new Font("Verdana", 0, 12));

        passwordText.setFont(new Font("Verdana", 0, 12));

        userIDLabel.setFont(new Font("Verdana", 1, 12));
        userIDLabel.setText("User ID");

        idText.setEditable(false);
        idText.setFont(new Font("Verdana", 0, 12));

        GroupLayout loginPanelLayout = new GroupLayout(loginPanel);
        loginPanel.setLayout(loginPanelLayout);
        loginPanelLayout.setHorizontalGroup(
            loginPanelLayout.createParallelGroup(GroupLayout.LEADING)
            .add(loginPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(loginPanelLayout.createParallelGroup(GroupLayout.LEADING)
                    .add(passwordLabel)
                    .add(loginLabel)
                    .add(userIDLabel))
                .add(24, 24, 24)
                .add(loginPanelLayout.createParallelGroup(GroupLayout.LEADING)
                    .add(loginText, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .add(passwordText, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .add(idText, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        loginPanelLayout.setVerticalGroup(
            loginPanelLayout.createParallelGroup(GroupLayout.LEADING)
            .add(loginPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(loginPanelLayout.createParallelGroup(GroupLayout.BASELINE)
                    .add(loginText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .add(loginLabel))
                .add(9, 9, 9)
                .add(loginPanelLayout.createParallelGroup(GroupLayout.BASELINE)
                    .add(passwordText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .add(passwordLabel))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(loginPanelLayout.createParallelGroup(GroupLayout.BASELINE)
                    .add(userIDLabel)
                    .add(idText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(173, Short.MAX_VALUE))
        );
        tabbedPane.addTab("Login", loginPanel);

        roleName.setFont(new Font("Verdana", 0, 12));

        roleNameLabel.setFont(new Font("Verdana", 1, 12));
        roleNameLabel.setText("Role Name");

        rolePriorityLabel.setFont(new Font("Verdana", 1, 12));
        rolePriorityLabel.setText("Role Priority");

        rolePriority.setFont(new Font("Verdana", 0, 12));

        superiorIDLabel.setFont(new Font("Verdana", 1, 12));
        superiorIDLabel.setText("Superior ID");

        superiorId.setFont(new Font("Verdana", 0, 12));

        GroupLayout rolePanelLayout = new GroupLayout(rolePanel);
        rolePanel.setLayout(rolePanelLayout);
        rolePanelLayout.setHorizontalGroup(
            rolePanelLayout.createParallelGroup(GroupLayout.LEADING)
            .add(rolePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(rolePanelLayout.createParallelGroup(GroupLayout.LEADING)
                    .add(roleNameLabel)
                    .add(rolePriorityLabel)
                    .add(superiorIDLabel))
                .add(17, 17, 17)
                .add(rolePanelLayout.createParallelGroup(GroupLayout.LEADING)
                    .add(roleName, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                    .add(rolePanelLayout.createParallelGroup(GroupLayout.TRAILING, false)
                        .add(GroupLayout.LEADING, rolePriority)
                        .add(GroupLayout.LEADING, superiorId, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)))
                .addContainerGap())
        );
        rolePanelLayout.setVerticalGroup(
            rolePanelLayout.createParallelGroup(GroupLayout.LEADING)
            .add(rolePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(rolePanelLayout.createParallelGroup(GroupLayout.BASELINE)
                    .add(roleNameLabel)
                    .add(roleName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(rolePanelLayout.createParallelGroup(GroupLayout.BASELINE)
                    .add(rolePriorityLabel)
                    .add(rolePriority, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.RELATED)
                .add(rolePanelLayout.createParallelGroup(GroupLayout.BASELINE)
                    .add(superiorIDLabel)
                    .add(superiorId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(175, Short.MAX_VALUE))
        );
        tabbedPane.addTab("Role", rolePanel);

        actionMenu.setText("Actions");
        newUserMenuItem.setText("Add New User");
        newUserMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                newUserMenuItemActionPerformed();
            }
        });

        actionMenu.add(newUserMenuItem);

        editUserMenuItem.setText("Edit Selected User");
        editUserMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                editUserMenuItemActionPerformed();
            }
        });

        actionMenu.add(editUserMenuItem);

        removeUserMenuItem.setText("Delete Selected User");
        removeUserMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                removeUserMenuItemActionPerformed(evt);
            }
        });

        actionMenu.add(removeUserMenuItem);

        menuBar.add(actionMenu);

        setJMenuBar(menuBar);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(userTreeScrollPane, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.RELATED)
                .add(tabbedPane, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.LEADING)
            .add(userTreeScrollPane, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
            .add(GroupLayout.TRAILING, tabbedPane, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );
        pack();
    }

    /**
     * Delete the currently selected user
     */
    private void removeUserMenuItemActionPerformed(ActionEvent evt) {
        path = userTree.getSelectionPath();
        boolean isSuccess;
        if(path != null){
            isSuccess = true;
            UserTreeNode userNode = (UserTreeNode)path.getLastPathComponent();
            UserBean userInfo = userNode.getUserInfo();
            int childrenCount = userNode.getChildCount();
            // set all children superior to this nodes superior
            for(int i=0; i<childrenCount; i++){
                if(isSuccess){
                    UserTreeNode childNode = (UserTreeNode)userNode.getChildAt(i);
                    UserBean child = childNode.getUserInfo();
                    child.setSuperiorID( userInfo.getSuperiorID());
                    isSuccess = UserBeanController.updateUser(child);
                }
            }
            if(isSuccess){
                // remove user
                isSuccess = UserBeanController.removeUser(userInfo);
            }
        }
        else{
            isSuccess = false;
        }

        // display message
        if(isSuccess){
            JOptionPane.showMessageDialog(this,
                    "User successfully removed from the database",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(this,
                    "Error removing the user from the database",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        this.renderTree();
    }

    /**
     * Open a frame to edit the currently selected users information
     */
    private void editUserMenuItemActionPerformed() {
        new BackgroundPanel().setVisible(true);
        path = userTree.getSelectionPath();
        if(path != null){
            UserTreeNode userNode = (UserTreeNode)path.getLastPathComponent();
            UserBean userInfo = userNode.getUserInfo();
            JFrame ef = new EditUserFrame(this, userInfo);
            SwingUtility.centerOnScreen(ef);
            ef.setVisible(true);
        }
    }

    /**
     * Open a new user dialog
     */
    private void newUserMenuItemActionPerformed() {
        new BackgroundPanel().setVisible(true);
        JFrame nf = new NewUserFrame(this);
        SwingUtility.centerOnScreen(nf);
        nf.setVisible(true);
    }

    /**
     * Populate the user information on the admin frame
     */
    private void userTreeFocusGained() {
        path = userTree.getSelectionPath();
        if(path != null){
            UserTreeNode userNode = (UserTreeNode)path.getLastPathComponent();
            UserBean userInfo = userNode.getUserInfo();
            firstNameText.setText(userInfo.getUserFirstName());
            lastNameText.setText(userInfo.getUserLastName());
            loginText.setText(userInfo.getUserLogin());
            passwordText.setText(userInfo.getPassword());
            idText.setText(Long.toString(userInfo.getUserID()));
            roleName.setText(userInfo.getRoleName());
            rolePriority.setText(userInfo.getRolePriority());
            if(userInfo.getSuperiorID() != null){
                superiorId.setText(((UserBean)Users.userIDMap.get(userInfo.getSuperiorID())).getWholeName());
            }
            else{
                superiorId.setText("");
            }
            loginText.updateUI();
            personalPanel.grabFocus();
        }

    }

    /**
     * Update the user tree when a user is added, deleted, or updated
     */
    public void renderTree(){
        // update the tree
        Users.getUsers();
        userTree.setCellRenderer(renderer);
        userTree.setModel(new DefaultTreeModel(Users.userTree, false));
    }
}
