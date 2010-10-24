package rolecoll.ui;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: znorcross
 * Date: Mar 8, 2007
 * Time: 9:30:29 PM
 */

/**
 * Display exception in a dialog box for user to see 
 */
public class FrameException {
    public static void showFrameException(Exception e, JFrame frame){
        JOptionPane.showMessageDialog(frame,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
