/*
 * LoginFrame.java
 *
 * Created on November 25, 2006, 1:57 PM
 */

package rolecoll.ui.frames;

import javax.imageio.ImageIO;
import javax.swing.*;

import rolecoll.beans.UserBean;
import rolecoll.beans.Users;
import rolecoll.beans.GUIBean;
import rolecoll.beans.dao.HibernateUtil;
import rolecoll.ui.FrameException;
import org.flexdock.util.SwingUtility;

import java.awt.*;
import java.awt.event.*;

/**
 * Created by IntelliJ IDEA.
 * User: Zack
 */
/**
 * This class validates a users credintals and opens the correct frame
 */
public class LoginFrame extends JFrame {
    private JComboBox themeComboBox;
    private JPasswordField mPasswordField;
    private JTextField mLoginField;

    private MainFrame mf;
    private Dimension mfSize;

    /** Creates new form LoginFrame */
    public LoginFrame() {
        try{
            // initilize database connection
            HibernateUtil.getSessionFactory();
            // set look and feel
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            Users.getUsers();
            initComponents();
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    // shutdown database connection
                    HibernateUtil.shutdown();
                    System.exit(0);
                }
            });
        } catch(Exception e){
            // display exceptions
            FrameException.showFrameException(e, this);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     */
    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Role-Coll Login");
        try{
            setIconImage(ImageIO.read(LoginFrame.class.getClassLoader().getResource("images/RoleColl.jpg")));
        } catch(Exception e){
            e.printStackTrace();
        }
        setName("loginFrame");
        setResizable(false);

        JPanel loginPanel = new JPanel();

        JLabel loginLabel = new JLabel();
        loginLabel.setFont(new Font("Verdana", 1, 12));
        loginLabel.setText("Login");

        mLoginField = new JTextField();
        mLoginField.setColumns(20);
        mLoginField.setFont(new Font("Verdana", 0, 12));
        mLoginField.setAutoscrolls(false);
        mLoginField.setSize(67, 14);
        mLoginField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    tryLogin();
                }
            }
        });

        loginLabel.setLabelFor(mLoginField);

        JLabel passwordLabel = new JLabel();
        passwordLabel.setFont(new Font("Verdana", 1, 12));
        passwordLabel.setText("Password");

        mPasswordField = new JPasswordField();
        mPasswordField.setFont(new Font("Verdana", 0, 12));
        mPasswordField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    tryLogin();
                }
            }
        });

        JLabel themeLabel = new JLabel();
        themeLabel.setFont(new java.awt.Font("Verdana", 1, 12));
        themeLabel.setText("Select Theme");

        themeComboBox = new JComboBox();
        themeComboBox.setFont(new Font("Verdana", 0, 12));
        themeComboBox.setModel(new DefaultComboBoxModel(GUIBean.THEME_LIST));
        themeComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                themeComboBoxItemStateChanged();
            }
        });

        JButton okButton = new JButton();
        okButton.setFont(new Font("Verdana", 1, 12));
        okButton.setText("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginActionPerformed();
            }
        });

        JButton oskButton = new JButton();
        oskButton.setFont(new Font("Verdana", 1, 12));
        oskButton.setText("Keyboard");
        oskButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MainFrame.showOSK();
            }
        });

        org.jdesktop.layout.GroupLayout loginPanelLayout = new org.jdesktop.layout.GroupLayout(loginPanel);
        loginPanel.setLayout(loginPanelLayout);
        loginPanelLayout.setHorizontalGroup(
                loginPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, loginPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(loginPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                .add(okButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 67, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, loginPanelLayout.createSequentialGroup()
                                .add(loginPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                        .add(loginLabel)
                                        .add(passwordLabel)
                                        .add(themeLabel)
                                        .add(oskButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .add(22, 22, 22)
                                .add(loginPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(org.jdesktop.layout.GroupLayout.TRAILING, themeComboBox, 0, 124, Short.MAX_VALUE)
                                .add(org.jdesktop.layout.GroupLayout.TRAILING, mPasswordField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                                .add(mLoginField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))))
                        .add(19, 19, 19))
        );
        loginPanelLayout.setVerticalGroup(
                loginPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(loginPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(loginPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(loginLabel)
                                .add(mLoginField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(loginPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(passwordLabel)
                                .add(mPasswordField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(loginPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(themeComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(themeLabel))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(loginPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(oskButton)
                        .add(okButton))
                        .add(29, 29, 29))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(loginPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(loginPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 129, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        pack();
    }

    /**
     * This method sets the skin chosen by the user
     */
    private void themeComboBoxItemStateChanged() {
        GUIBean.setLookAndFeel((String)themeComboBox.getSelectedItem());
        try{
            UIManager.setLookAndFeel(GUIBean.getLookAndFeel());
            this.repaint();
            mf.setSize(mfSize);
            mf.repaint();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method checks the creditals of a user and opens the correct frame
     * for that user
     */
    private void loginActionPerformed() {
        tryLogin();
    }

    /**
     * Display login error to user
     */
    private void showLoginError(){
        JOptionPane.showMessageDialog(this,
                "User name or password is incorrect",
                "Login Error",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * validate user credentials
     */
    private void tryLogin(){
        // get text from fields
        String user = mLoginField.getText();
        String password = String.valueOf(mPasswordField.getPassword());

        // reset field color
        mLoginField.setBackground(Color.WHITE);
        mPasswordField.setBackground(Color.WHITE);

        // highlight empty fields
        if(user.length() <= 0){
            mLoginField.requestFocus();
            mLoginField.setBackground(Color.YELLOW);
        }
        else if(password.length() <= 0){
            mPasswordField.requestFocus();
            mPasswordField.setBackground(Color.YELLOW);
        }
        else{
            // get user bean
            UserBean userBean = (UserBean)Users.userMap.get(user);
            if(userBean != null){
                // check password
                if(userBean.getPassword().equals(password)){
                    // open main frame
                    if(!userBean.isAdmin()){
                        this.dispose();
                        if(mf != null){
                            mf.setUser(userBean);
                            mf.setEnabled(true);
                            mf.requestFocus();
                        }
                    }
                    // open admin frame
                    else{
                        this.dispose();
                        if(mf != null){
                            mf.dispose();
                        }
                        AdminFrame af = new AdminFrame();
                        SwingUtility.centerOnScreen(af);
                        af.setVisible(true);
                    }
                }
                else{
                    clearTextFields();
                    showLoginError();
                }
            }
            else{
                clearTextFields();
                showLoginError();
            }
        }
    }

    /**
     * Clear text fields on bad login
     */
    private void clearTextFields(){
        mLoginField.setText("");
        mPasswordField.setText("");
        mLoginField.grabFocus();
    }

    /**
     * used to pass focus to main frames
     * @param mf - main frame
     */
    public void setMainFrame(MainFrame mf) {
        this.mf = mf;
        this.mfSize = mf.getSize();
        if(mf != null){
            mf.setEnabled(false);
        }
    }

    /**
     * allows main frame access to the login id
     * @return login field
     */
    public JTextField getmLoginField() {
        return mLoginField;
    }

    /**
     * Runs the form
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BackgroundPanel().setVisible(true);
                // create login frame
                LoginFrame lf = new LoginFrame();
                SwingUtility.centerOnScreen(lf);
                lf.setVisible(true);
                // create main frame
                MainFrame mf = new MainFrame();
                SwingUtility.centerOnScreen(mf);
                mf.setVisible(true);
                lf.setMainFrame(mf);
                lf.getmLoginField().requestFocus();
            }
        });
    }
}
