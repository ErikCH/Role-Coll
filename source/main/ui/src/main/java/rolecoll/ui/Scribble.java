package rolecoll.ui;

import rolecoll.beans.NoteBean;
import rolecoll.beans.dao.NoteBeanController;
import rolecoll.ui.frames.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Vector;
import java.util.Iterator;
import java.io.*;

// This example is from the book _Java in a Nutshell_ by David Flanagan.
// Written by David Flanagan.  Copyright (c) 1996 O'Reilly & Associates.
// You may study, use, modify, and distribute this example for any purpose.

/**
 * This class was written by the author above but vastly edited by Zack.
 */

/**
 * This class is a custom component that supports scribbling.  It also has
 * a popup menu that allows the scribble color to be set and provides access
 * to printing, cut-and-paste, and file loading and saving facilities.
 * Note that it extends Component rather than Canvas, making it "lightweight."
 */
public class Scribble extends JComponent implements ActionListener {
    private NoteBean noteInfo;
    protected short last_x, last_y;                // Coordinates of last click.
    protected Vector lines = new Vector(256,256);  // Store the scribbles.
    protected Color current_color = Color.black;   // Current drawing color.
    protected int current_weight = 1;               // current weight
    protected int width, height;                   // The preferred size.
    protected PopupMenu popup;                     // The popup menu.
    //protected Frame frame;                         // The frame we are within.

    /** This constructor requires a Frame and a desired size */
    public Scribble() {

        // We handle scribbling with low-level events, so we must specify
        // which events we are interested in.
        this.enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        this.enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);

        // Create the popup menu using a loop.  Note the separation of menu
        // "action command" string from menu label.  Good for internationalization.
        String[] labels;
        String[] commands;
        labels = new String[] {"Clear", "Save", "Cut", "Copy", "Paste"};
        commands = new String[] {"clear", "save", "cut", "copy", "paste"};

        popup = new PopupMenu();                   // Create the menu
        for(int i = 0; i < labels.length; i++) {
            MenuItem mi = new MenuItem(labels[i]);   // Create a menu item.
            mi.setActionCommand(commands[i]);        // Set its action command.
            mi.addActionListener(this);              // And its action listener.
            popup.add(mi);                           // Add item to the popup menu.
        }
        Menu colors = new Menu("Color");           // Create a submenu.
        popup.add(colors);                         // And add it to the popup.
        String[] colornames = new String[] { "Black", "Red", "Green", "Blue"};
        for(int i = 0; i < colornames.length; i++) {
            MenuItem mi = new MenuItem(colornames[i]);  // Create the submenu items
            mi.setActionCommand(colornames[i]);         // in the same way.
            mi.addActionListener(this);
            colors.add(mi);
        }
        Menu weight = new Menu("Weight");           // Create a submenu.
        popup.add(weight);                         // And add it to the popup.
        String[] weights = new String[] { "1 px", "2 px", "3 px", "4 px"};
        for(int i = 0; i < weights.length; i++) {
            MenuItem mi = new MenuItem(weights[i]);  // Create the submenu items
            mi.setActionCommand(weights[i]);         // in the same way.
            mi.addActionListener(this);
            weight.add(mi);
        }
        // Finally, register the popup menu with the component it appears over
        this.add(popup);
    }

    /**
     * update popup menu for notes already saved to the database
     */
    private void updatePopupMenu(){
        this.remove(popup);
        String[] labels;
        String[] commands;
        if(noteInfo != null && noteInfo.getNoteID() > 0){
            labels = new String[] {"Clear", "Delete", "Update", "Cut", "Copy", "Paste"};
            commands = new String[] {"clear", "delete", "save", "cut", "copy", "paste"};
        }
        else{
            labels = new String[] {"Clear", "Save", "Cut", "Copy", "Paste"};
            commands = new String[] {"clear", "save", "cut", "copy", "paste"};
        }
        popup = new PopupMenu();                   // Create the menu
        for(int i = 0; i < labels.length; i++) {
            MenuItem mi = new MenuItem(labels[i]);   // Create a menu item.
            mi.setActionCommand(commands[i]);        // Set its action command.
            mi.addActionListener(this);              // And its action listener.
            popup.add(mi);                           // Add item to the popup menu.
        }
        Menu colors = new Menu("Color");           // Create a submenu.
        popup.add(colors);                         // And add it to the popup.
        String[] colornames = new String[] { "Black", "Red", "Green", "Blue"};
        for(int i = 0; i < colornames.length; i++) {
            MenuItem mi = new MenuItem(colornames[i]);  // Create the submenu items
            mi.setActionCommand(colornames[i]);         // in the same way.
            mi.addActionListener(this);
            colors.add(mi);
        }
        Menu weight = new Menu("Weight");           // Create a submenu.
        popup.add(weight);                         // And add it to the popup.
        String[] weights = new String[] { "1 px", "2 px", "3 px", "4 px"};
        for(int i = 0; i < weights.length; i++) {
            MenuItem mi = new MenuItem(weights[i]);  // Create the submenu items
            mi.setActionCommand(weights[i]);         // in the same way.
            mi.addActionListener(this);
            weight.add(mi);
        }
        // Finally, register the popup menu with the component it appears over
        this.add(popup);
    }

    /** Specifies big the component would like to be.  It always returns the
     *  preferred size passed to the Scribble() constructor */
    public Dimension getPreferredSize() { return new Dimension(width, height); }

    /** This is the ActionListener method invoked by the popup menu items */
    public void actionPerformed(ActionEvent event) {
        // Get the "action command" of the event, and dispatch based on that.
        // This method calls a lot of the interesting methods in this class.
        String command = event.getActionCommand();
        if (command.equals("clear")) clear();
        else if (command.equals("save")) save();
        else if (command.equals("delete")) delete();
        else if (command.equals("cut")) cut();
        else if (command.equals("copy")) copy();
        else if (command.equals("paste")) paste();
        else if (command.equals("Black")) current_color = Color.black;
        else if (command.equals("Red")) current_color = Color.red;
        else if (command.equals("Green")) current_color = Color.green;
        else if (command.equals("Blue")) current_color = Color.blue;
        else if (command.equals("1 px")) current_weight = 1;
        else if (command.equals("2 px")) current_weight = 2;
        else if (command.equals("3 px")) current_weight = 3;
        else if (command.equals("4 px")) current_weight = 4;
    }

    /** Draw all the saved lines of the scribble, in the appropriate colors */
    public void paint(Graphics g) {
        for(int i = 0; i < lines.size(); i++) {
            Line l = (Line)lines.elementAt(i);
            g.setColor(l.color);
            ((Graphics2D)g).setStroke(new BasicStroke(l.width));
            g.drawLine(l.x1, l.y1, l.x2, l.y2);
        }
    }

    /**
     * This is the low-level event-handling method called on mouse events
     * that do not involve mouse motion.  Note the use of isPopupTrigger()
     * to check for the platform-dependent popup menu posting event, and of
     * the show() method to make the popup visible.  If the menu is not posted,
     * then this method saves the coordinates of a mouse click or invokes
     * the superclass method.
     */
    public void processMouseEvent(MouseEvent e) {
        if (e.isPopupTrigger())                               // If popup trigger,
            popup.show(this, e.getX(), e.getY());               // pop up the menu.
        else if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            last_x = (short)e.getX(); last_y = (short)e.getY(); // Save position.
        }
        else super.processMouseEvent(e);  // Pass other event types on.
    }

    /**
     * This method is called for mouse motion events.  It adds a line to the
     * scribble, on screen, and in the saved representation
     */
    public void processMouseMotionEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_DRAGGED) {
            Graphics g = getGraphics();                     // Object to draw with.
            g.setColor(current_color);                      // Set the current color.
            ((Graphics2D)g).setStroke(new BasicStroke(current_weight));  // set stroke width
            g.drawLine(last_x, last_y, e.getX(), e.getY()); // Draw this line
            lines.addElement(new Line(last_x, last_y,       // and save it, too.
                    (short) e.getX(), (short)e.getY(),
                    current_color, current_weight));
            last_x = (short) e.getX();  // Remember current mouse coordinates.
            last_y = (short) e.getY();
        }
        else super.processMouseMotionEvent(e);  // Important!
    }

    /** Clear the scribble.  Invoked by popup menu */
    void clear() {
        lines.removeAllElements();   // Throw out the saved scribble
        repaint();                   // and redraw everything.
    }

    /**
     * The DataFlavor used for our particular type of cut-and-paste data.
     * This one will transfer data in the form of a serialized Vector object.
     * Note that in Java 1.1.1, this works intra-application, but not between
     * applications.  Java 1.1.1 inter-application data transfer is limited to
     * the pre-defined string and text data flavors.
     */
    public static final DataFlavor dataFlavor =
            new DataFlavor(Vector.class, "ScribbleVectorOfLines");

    /**
     * Copy the current scribble and store it in a SimpleSelection object
     * (defined below).  Then put that object on the clipboard for pasting.
     */
    public void copy() {
        // Get system clipboard
        Clipboard c = this.getToolkit().getSystemClipboard();
        // Copy and save the scribble in a Transferable object
        SimpleSelection s = new SimpleSelection(lines.clone(), dataFlavor);
        // Put that object on the clipboard
        c.setContents(s, s);
    }

    /** Cut is just like a copy, except we erase the scribble afterwards */
    public void cut() { copy(); clear();  }

    /**
     * Ask for the Transferable contents of the system clipboard, then ask that
     * object for the scribble data it represents.  If either step fails, beep!
     */
    public void paste() {
        Clipboard c = this.getToolkit().getSystemClipboard();  // Get clipboard.
        Transferable t = c.getContents(this);                  // Get its contents.
        if (t == null) {              // If there is nothing to paste, beep.
            this.getToolkit().beep();
            return;
        }
        try {
            // Ask for clipboard contents to be converted to our data flavor.
            // This will throw an exception if our flavor is not supported.
            Vector newlines = (Vector) t.getTransferData(dataFlavor);
            // Add all those pasted lines to our scribble.
            for(int i = 0; i < newlines.size(); i++)
                lines.addElement(newlines.elementAt(i));
            // And redraw the whole thing
            repaint();
        }
        catch (UnsupportedFlavorException e) {
            this.getToolkit().beep();   // If clipboard has some other type of data
        }
        catch (Exception e) {
            this.getToolkit().beep();   // Or if anything else goes wrong...
        }
    }

    /**
     * This nested class implements the Transferable and ClipboardOwner
     * interfaces used in data transfer.  It is a simple class that remembers a
     * selected object and makes it available in only one specified flavor.
     */
    static class SimpleSelection implements Transferable, ClipboardOwner {
        protected Object selection;    // The data to be transferred.
        protected DataFlavor flavor;   // The one data flavor supported.
        public SimpleSelection(Object selection, DataFlavor flavor) {
            this.selection = selection;  // Specify data.
            this.flavor = flavor;        // Specify flavor.
        }

        /** Return the list of supported flavors.  Just one in this case */
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[] { flavor };
        }
        /** Check whether we support a specified flavor */
        public boolean isDataFlavorSupported(DataFlavor f) {
            return f.equals(flavor);
        }
        /** If the flavor is right, transfer the data (i.e. return it) */
        public Object getTransferData(DataFlavor f)
                throws UnsupportedFlavorException {
            if (f.equals(flavor)) return selection;
            else throw new UnsupportedFlavorException(f);
        }

        /** This is the ClipboardOwner method.  Called when the data is no
         *  longer on the clipboard.  In this case, we don't need to do much. */
        public void lostOwnership(Clipboard c, Transferable t) {
            selection = null;
        }
    }

    /**
     * save note to the database
     */
    public void save() {
        // get internal frame
        JComponent internalFrame = (JInternalFrame)this.getParent().getParent().getParent();
        Point p = internalFrame.getLocation();
        Dimension d = internalFrame.getSize();
        noteInfo.setNoteClass(this.getClass().getName());
        noteInfo.setNoteLocationX(p.x + MainFrame.xAdjustment);
        noteInfo.setNoteLocationY(p.y + MainFrame.yAdjustment);
        noteInfo.setNoteHeight(d.height - MainFrame.heightAdjustment);
        noteInfo.setNoteWidth(d.width - MainFrame.widthAdjustment);
        try{
            StringBuffer sb = new StringBuffer();
            for (Iterator iterator = lines.iterator(); iterator.hasNext();) {
                Line line = (Line) iterator.next();
                sb.append(line.toString());
                // all but last
                if(iterator.hasNext()){
                    sb.append(";");
                }
            }
            noteInfo.setNoteContent(sb.toString());      // Write the entire Vector of scribbles
        } catch (Exception e){
            e.printStackTrace();
        }
        // commit to the database
        if(noteInfo.getNoteID() == 0){
            if(NoteBeanController.addNote(noteInfo)){
                JOptionPane.showMessageDialog(this,
                        "Note successfully saved to the database",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(this,
                        "Error saving note to the database",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        else{
            if(NoteBeanController.updateNote(noteInfo)){
                JOptionPane.showMessageDialog(this,
                        "Note successfully updated to the database",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(this,
                        "Error updating note to the database",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        // update note
        updatePopupMenu();
    }

    /**
     * load note from the database
     */
    public void load() {
        updatePopupMenu();
        this.setLocation((int)noteInfo.getNoteLocationX(), (int)noteInfo.getNoteLocationY());
        this.setSize((int)noteInfo.getNoteWidth(), (int)noteInfo.getNoteHeight());
        try{
            String[] linesArray = noteInfo.getNoteContent().split(";");
            Vector newlines = new Vector();
            for (int i = 0; i < linesArray.length; i++) {
                String line = linesArray[i];
                newlines.add(new Line(line));
            }
            lines = newlines;              // Set the Vector of lines.
            repaint();                     // And redisplay the scribble.
        }
        // Print out exceptions.  We should really display them in a dialog...
        catch (Exception e) { System.out.println(e); }
    }

    /** A class to store the coordinates and color of one scribbled line.
     *  The complete scribble is stored as a Vector of these objects */
    static class Line implements Serializable {
        public short x1, y1, x2, y2;
        public Color color;
        public int width;
        public Line(short x1, short y1, short x2, short y2, Color c, int w) {
            this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2; this.color = c; this.width = w;
        }
        public Line(String line){
            String[] params = line.split(",");
            if(params.length == 8){
                this.x1 = Short.parseShort(params[0]);
                this.y1 = Short.parseShort(params[1]);
                this.x2 = Short.parseShort(params[2]);
                this.y2 = Short.parseShort(params[3]);
                this.color = new Color(Integer.parseInt(params[4]),
                        Integer.parseInt(params[5]), Integer.parseInt(params[6]));
                this.width = Integer.parseInt(params[7]);
            }
        }
        public String toString(){
            return x1 + "," + y1 + "," + x2 + "," + y2 + "," +
                    color.getRed() + "," + color.getGreen() + "," + color.getBlue() + "," + width;
        }
    }

    /**
     * delete note from the database
     */
    private void delete(){
        if(NoteBeanController.removeNote(noteInfo)){
            ((JInternalFrame)this.getParent().getParent().getParent()).dispose();
            JOptionPane.showMessageDialog(this,
                    "Note successfully deleted from the database",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(this,
                    "Error deleting note from the database",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
   
    public NoteBean getNoteInfo() {
        return noteInfo;
    }

    public void setNoteInfo(NoteBean noteInfo) {
        this.noteInfo = noteInfo;
    }
}