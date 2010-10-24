/*
 * NewUserFrame.java
 *
 * Created on December 7, 2006, 8:19 PM
 */

package rolecoll.ui.frames;

import javax.imageio.ImageIO;
import javax.swing.*;

import rolecoll.beans.Users;
import rolecoll.beans.GUIBean;
import rolecoll.beans.UserBean;
import rolecoll.beans.Roles;
import rolecoll.beans.dao.UserBeanController;
import rolecoll.beans.dao.UsersBeanFactory;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.*;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Zack
 */
/**
 * Frame for dispaying user information
 */
public class EditUserFrame extends JFrame {
    // Variables declaration
    private boolean isAdmin = false;
    private JButton okButton;
    private JButton cancelButton;
    private JLabel firstNameLabel;
    private JTextField firstNameTextField;
    private JCheckBox isAdminCheckBox;
    private JLabel isAdminLabel;
    private JLabel lastNameLabel;
    private JTextField lastNameTextField;
    private JLabel loginLabel;
    private JPanel loginPanel;
    private JTextField loginTextField;
    private JLabel passwordLabel;
    private JTextField passwordTextField;
    private JPanel personalPanel;
    private JLabel priorityLabel;
    private JTextField priorityTextField;
    private JLabel roleNameLabel;
    private JComboBox roleNameComboBox;
    private JPanel rolePanel;
    private JLabel superiorIDLabel;
    private JComboBox superiorIDComboBox;
    private JTabbedPane tabbedPane;
    private ArrayList failureReasons;

    private AdminFrame parent;
    private UserBean userInfo;

    /** Creates new form NewUserFrame */
    public EditUserFrame(AdminFrame parent, UserBean userInfo) {
        try{
            this.parent = parent;
            this.userInfo = userInfo;
            UIManager.setLookAndFeel(GUIBean.getLookAndFeel());
            initComponents();
            firstNameTextField.grabFocus();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     */
    private void initComponents() {
        tabbedPane = new JTabbedPane();
        personalPanel = new JPanel();
        firstNameTextField = new JTextField();
        lastNameTextField = new JTextField();
        firstNameLabel = new JLabel();
        lastNameLabel = new JLabel();
        loginPanel = new JPanel();
        loginTextField = new JTextField();
        passwordTextField = new JTextField();
        loginLabel = new JLabel();
        passwordLabel = new JLabel();
        isAdminLabel = new JLabel();
        isAdminCheckBox = new JCheckBox();
        rolePanel = new JPanel();
        roleNameComboBox = new JComboBox();
        priorityTextField = new JTextField();
        roleNameLabel = new JLabel();
        priorityLabel = new JLabel();
        superiorIDComboBox = new JComboBox();
        superiorIDLabel = new JLabel();
        cancelButton = new JButton();
        okButton = new JButton();

        // disable close X
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Update User Information");
        try{
            setIconImage(ImageIO.read(EditUserFrame.class.getClassLoader().getResource("images/RoleColl.jpg")));
        } catch(Exception e){
            e.printStackTrace();
        }
        setResizable(false);
        tabbedPane.setFont(new java.awt.Font("Verdana", 1, 12));
        firstNameTextField.setColumns(20);
        firstNameTextField.setFont(new java.awt.Font("Verdana", 0, 12));
        firstNameTextField.setText(userInfo.getUserFirstName());

        lastNameTextField.setColumns(20);
        lastNameTextField.setFont(new java.awt.Font("Verdana", 0, 12));
        lastNameTextField.setText(userInfo.getUserLastName());

        firstNameLabel.setFont(new java.awt.Font("Verdana", 1, 12));
        firstNameLabel.setText("First Name");

        lastNameLabel.setFont(new java.awt.Font("Verdana", 1, 12));
        lastNameLabel.setText("Last Name");

        org.jdesktop.layout.GroupLayout personalPanelLayout = new org.jdesktop.layout.GroupLayout(personalPanel);
        personalPanel.setLayout(personalPanelLayout);
        personalPanelLayout.setHorizontalGroup(
                personalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(personalPanelLayout.createSequentialGroup()
                        .add(25, 25, 25)
                        .add(personalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(firstNameLabel)
                                .add(lastNameLabel))
                        .add(35, 35, 35)
                        .add(personalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                .add(firstNameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(lastNameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(54, Short.MAX_VALUE))
        );
        personalPanelLayout.setVerticalGroup(
                personalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(personalPanelLayout.createSequentialGroup()
                        .add(20, 20, 20)
                        .add(personalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(firstNameLabel)
                                .add(firstNameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(personalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(lastNameLabel)
                                .add(lastNameTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(42, Short.MAX_VALUE))
        );
        tabbedPane.addTab("Personal", personalPanel);

        loginTextField.setColumns(20);
        loginTextField.setFont(new java.awt.Font("Verdana", 0, 12));
        loginTextField.setText(userInfo.getUserLogin());

        passwordTextField.setFont(new java.awt.Font("Verdana", 0, 12));
        passwordTextField.setText(userInfo.getPassword());

        loginLabel.setFont(new java.awt.Font("Verdana", 1, 12));
        loginLabel.setText("Login");

        passwordLabel.setFont(new java.awt.Font("Verdana", 1, 12));
        passwordLabel.setText("Password");

        isAdminLabel.setFont(new java.awt.Font("Verdana", 1, 12));
        isAdminLabel.setText("Is Admin?");

        isAdminCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        isAdminCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        isAdminCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt){
                isAdmin = !isAdmin;
                if(isAdmin){
                    rolePanel.disable();
                }
            }
        });

        org.jdesktop.layout.GroupLayout loginPanelLayout = new org.jdesktop.layout.GroupLayout(loginPanel);
        loginPanel.setLayout(loginPanelLayout);
        loginPanelLayout.setHorizontalGroup(
                loginPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(loginPanelLayout.createSequentialGroup()
                        .add(24, 24, 24)
                        .add(loginPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(loginLabel)
                                .add(passwordLabel)
                                .add(isAdminLabel))
                        .add(37, 37, 37)
                        .add(loginPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(isAdminCheckBox)
                                .add(loginPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                .add(passwordTextField)
                                .add(loginTextField)))
                        .add(60, 60, 60))
        );
        loginPanelLayout.setVerticalGroup(
                loginPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(loginPanelLayout.createSequentialGroup()
                        .add(20, 20, 20)
                        .add(loginPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(loginLabel)
                                .add(loginTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(loginPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(passwordLabel)
                                .add(passwordTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(loginPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(isAdminLabel)
                                .add(isAdminCheckBox))
                        .addContainerGap(19, Short.MAX_VALUE))
        );
        tabbedPane.addTab("Login", loginPanel);

        // initilize roles
        Roles.getRoles();
        Object[] roles = Roles.getRolesMap().keySet().toArray();
        Arrays.sort(roles);
        roleNameComboBox.setModel(new DefaultComboBoxModel(roles));
        roleNameComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                roleComboBoxItemStateChanged();
            }
        });
        roleNameComboBox.setFont(new java.awt.Font("Verdana", 0, 12));
        roleNameComboBox.setSelectedItem(userInfo.getRoleName());

        priorityTextField.setColumns(3);
        priorityTextField.setFont(new java.awt.Font("Verdana", 0, 12));
        priorityTextField.setText((String)Roles.getRolesMap().get(roleNameComboBox.getSelectedItem()));
        priorityTextField.disable();

        roleNameLabel.setFont(new java.awt.Font("Verdana", 1, 12));
        roleNameLabel.setText("Role Name");

        priorityLabel.setFont(new java.awt.Font("Verdana", 1, 12));
        priorityLabel.setText("Priority");

        Object[] userNames = Users.userNameMap.keySet().toArray();
        Arrays.sort(userNames);
        superiorIDComboBox.setModel(new DefaultComboBoxModel(userNames));
        superiorIDComboBox.setFont(new java.awt.Font("Verdana", 0, 12));
        superiorIDComboBox.setSelectedItem(Users.userIDMap.get(userInfo.getSuperiorID()));

        superiorIDLabel.setFont(new java.awt.Font("Verdana", 1, 12));
        superiorIDLabel.setText("Superior");

        org.jdesktop.layout.GroupLayout rolePanelLayout = new org.jdesktop.layout.GroupLayout(rolePanel);
        rolePanel.setLayout(rolePanelLayout);
        rolePanelLayout.setHorizontalGroup(
                rolePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(rolePanelLayout.createSequentialGroup()
                        .add(24, 24, 24)
                        .add(rolePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(priorityLabel)
                                .add(roleNameLabel)
                                .add(superiorIDLabel))
                        .add(38, 38, 38)
                        .add(rolePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(priorityTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(roleNameComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(superiorIDComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(47, Short.MAX_VALUE))
        );
        rolePanelLayout.setVerticalGroup(
                rolePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(rolePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(rolePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(roleNameLabel)
                                .add(roleNameComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(rolePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(priorityLabel)
                                .add(priorityTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(rolePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(superiorIDLabel)
                                .add(superiorIDComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(22, Short.MAX_VALUE))
        );
        tabbedPane.addTab("Role", rolePanel);

        cancelButton.setFont(new java.awt.Font("Verdana", 1, 12));
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed();
            }
        });

        okButton.setFont(new java.awt.Font("Verdana", 1, 12));
        okButton.setText("ok");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try{
                    okButtonActionPerformed();
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(268, Short.MAX_VALUE)
                                .add(okButton)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(cancelButton)
                                .add(14, 14, 14))
                        .add(tabbedPane)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(layout.createSequentialGroup()
                        .add(tabbedPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 138, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(okButton)
                                .add(cancelButton))
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }

    /**
     * This method saves the edited user information to the database
     * @throws Exception - database errors
     */
    private void okButtonActionPerformed() throws Exception {
        UserBean user = (UserBean)userInfo.clone();
        user.setAdmin(isAdmin);
        user.setPassword(passwordTextField.getText());
        user.setRoleName((String)roleNameComboBox.getSelectedItem());
        user.setRolePriority(priorityTextField.getText());
        if(superiorIDComboBox.getSelectedItem() != null){
            user.setSuperiorID(
                    Long.toString(((UserBean)Users.userNameMap.get(superiorIDComboBox.getSelectedItem())).getUserID()));
        }
        user.setUserFirstName(firstNameTextField.getText());
        user.setUserLastName(lastNameTextField.getText());
        user.setUserLogin(loginTextField.getText());
        if(validateUser(user)){
            if(UserBeanController.updateUser(user)){
                JOptionPane.showMessageDialog(this,
                        "User information successfully updated",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(this,
                        "Database Error while updating user information",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            Users.getUsers();
            parent. renderTree();
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(this,
                    "Please correct the following information\n" +
                            getFailureReasons(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * validation of user information
     * @param user - user information
     * @return true if user information is valid
     */
    private boolean validateUser(UserBean user){
        failureReasons = new ArrayList();

        // first name entered
        if(user.getUserFirstName() == null || user.getUserFirstName().length() <= 0){
            firstNameLabel.setForeground(Color.RED);
            failureReasons.add("- First Name must be entered\n");
        }

        // last name entered
        if(user.getUserLastName() == null || user.getUserLastName().length() <= 0){
            lastNameLabel.setForeground(Color.RED);
            failureReasons.add("- Last Name must be entered\n");
        }

        // password entered
        if(user.getPassword() == null || user.getPassword().length() <= 0){
            passwordLabel.setForeground(Color.RED);
            failureReasons.add("- Password must be entered\n");
        }

        // user login entered and not used
        if(user.getUserLogin() == null || user.getUserLogin().length() <= 0 ||
                (Users.userMap.containsKey(user.getUserLogin()) && !user.getUserLogin().equals(userInfo.getUserLogin()))){
            loginLabel.setForeground(Color.RED);
            if(Users.userMap.containsKey(user.getUserLogin()) && !user.getUserLogin().equals(userInfo.getUserLogin())){
                failureReasons.add("- Login already exists\n");
            }
            else{
                failureReasons.add("- Login must be entered\n");
            }
        }

        // return false if failure reasons exist
        return failureReasons.size() <= 0;
    }

    /**
     * String of validation failure reasons to display to the user
     * @return reason string
     */
    private String getFailureReasons(){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < failureReasons.size(); i++) {
            sb.append(failureReasons.get(i));
        }
        return sb.toString();
    }

    /**
     * map role names to role priorities String -> int
     */
    private void roleComboBoxItemStateChanged(){
        priorityTextField.setText((String)Roles.getRolesMap().get(roleNameComboBox.getSelectedItem()));
    }

    /**
     * dispose frame on cancelation
     */
    private void cancelButtonActionPerformed() {
        this.dispose();
    }
}
