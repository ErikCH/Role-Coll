package rolecoll.beans;

import javax.swing.*;
import java.util.HashMap;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: znorcross
 * Date: Feb 16, 2007
 * Time: 9:34:47 AM
 */

/**
 * This class stores the look and feel for the application
 */
public class GUIBean {
    private static HashMap lfMap;
    public final static String[] THEME_LIST = new String[] {"Acryl", "Aero", "Aluminium", "Bernstein",
            "Fast", "HiFi", "Luna", "McWin", "Mint", "Noire", "Smart"};

    public static final String PLAF_METAL     = "javax.swing.plaf.metal.MetalLookAndFeel";
    public static final String PLAF_WINDOWS   = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
    public static final String PLAF_MOTIF     = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
    public static final String PLAF_MAC       = "com.sun.java.swing.plaf.mac.MacLookAndFeel";
    public static final String PLAF_OTHER     = "com.jgoodies.looks.plastic.PlasticXPLookAndFeel";

    public static final String PLAF_ACRYL      = "com.jtattoo.plaf.acryl.AcrylLookAndFeel";
    public static final String PLAF_AERO       = "com.jtattoo.plaf.aero.AeroLookAndFeel";
    public static final String PLAF_ALUMINIUM  = "com.jtattoo.plaf.aluminium.AluminiumLookAndFeel";
    public static final String PLAF_BERNSTEIN  = "com.jtattoo.plaf.bernstein.BernsteinLookAndFeel";
    public static final String PLAF_FAST       = "com.jtattoo.plaf.fast.FastLookAndFeel";
    public static final String PLAF_HIFI       = "com.jtattoo.plaf.hifi.HiFiLookAndFeel";
    public static final String PLAF_LUNA       = "com.jtattoo.plaf.luna.LunaLookAndFeel";
    public static final String PLAF_MCWIN      = "com.jtattoo.plaf.mcwin.McWinLookAndFeel";
    public static final String PLAF_MINT       = "com.jtattoo.plaf.mint.MintLookAndFeel";
    public static final String PLAF_NOIRE      = "com.jtattoo.plaf.noire.NoireLookAndFeel";
    public static final String PLAF_SMART      = "com.jtattoo.plaf.smart.SmartLookAndFeel";

    static {
        lfMap = new HashMap();
        lfMap.put("Acryl", PLAF_ACRYL);
        lfMap.put("Aero", PLAF_AERO);
        lfMap.put("Aluminium", PLAF_ALUMINIUM);
        lfMap.put("Bernstein", PLAF_BERNSTEIN);
        lfMap.put("Fast", PLAF_FAST);
        lfMap.put("HiFi", PLAF_HIFI);
        lfMap.put("Luna", PLAF_LUNA);
        lfMap.put("McWin", PLAF_MCWIN);
        lfMap.put("Mint", PLAF_MINT);
        lfMap.put("Noire", PLAF_NOIRE);
        lfMap.put("Smart", PLAF_SMART);
    }

    private static String lookAndFeel = PLAF_ACRYL;
    private String theme = "Default";

    public GUIBean()
    { }

    public static void setLookAndFeel(String aLookAndFeel)
    { lookAndFeel = (String)lfMap.get(aLookAndFeel); }

    public static String getLookAndFeel()
    { return lookAndFeel; }

    public void setTheme(String aTheme)
    { theme = aTheme; }

    public String getTheme()
    { return theme; }

    public boolean isMetalLook()
    { return lookAndFeel.equals(PLAF_METAL); }

    public boolean isWindowsLook()
    { return lookAndFeel.equals(PLAF_WINDOWS); }

    public boolean isMotifLook()
    { return lookAndFeel.equals(PLAF_MOTIF); }

    public boolean isMacLook()
    { return lookAndFeel.equals(PLAF_MAC); }

    public boolean isOtherLook()
    { return lookAndFeel.equals(PLAF_OTHER); }

    public boolean isFastLook()
    { return lookAndFeel.equals(PLAF_FAST); }

    public boolean isSmartLook()
    { return lookAndFeel.equals(PLAF_SMART); }

    public boolean isAcrylLook()
    { return lookAndFeel.equals(PLAF_ACRYL); }

    public boolean isAeroLook()
    { return lookAndFeel.equals(PLAF_AERO); }

    public boolean isBernsteinLook()
    { return lookAndFeel.equals(PLAF_BERNSTEIN); }

    public boolean isAluminiumLook()
    { return lookAndFeel.equals(PLAF_ALUMINIUM); }

    public boolean isMcWinLook()
    { return lookAndFeel.equals(PLAF_MCWIN); }

    public boolean isMintLook()
    { return lookAndFeel.equals(PLAF_MINT); }

    public boolean isHiFiLook()
    { return lookAndFeel.equals(PLAF_HIFI); }

    public boolean isNoireLook()
    { return lookAndFeel.equals(PLAF_NOIRE); }

    public boolean isLunaLook()
    { return lookAndFeel.equals(PLAF_LUNA); }

    public static void updateLF(String lf){
        setLookAndFeel(lf);
        try{
            UIManager.setLookAndFeel(getLookAndFeel());
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void updateLF(String lf, JComponent comp){
        Dimension d = comp.getSize();
        System.out.println(d);
        setLookAndFeel(lf);
        try{
            UIManager.setLookAndFeel(getLookAndFeel());
        } catch(Exception e){
            e.printStackTrace();
        }
        comp.setSize(d);
        System.out.println(comp.getSize());
    }
}
