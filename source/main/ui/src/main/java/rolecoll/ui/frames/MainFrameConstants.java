package rolecoll.ui.frames;

/**
 * Created by IntelliJ IDEA.
 * User: Zack
 * Date: Apr 25, 2007
 * Time: 10:01:38 PM
 */

/**
 * Constants used in the main frame class
 */
public interface MainFrameConstants {
    // boarder adjustments
    public static final int yAdjustment = 26;
    public static final int xAdjustment = 5;
    public static final int heightAdjustment = 31;
    public static final int widthAdjustment = 10;

    // music fonts
    public static final String TOCCATA = "TOCCATA";
    public static final String MAESTRO = "MAESTRO";

    public static final String musicNotationImages = "images/MusicNote";
    public static final String[] musicNotationFonts = new String[]{
            MAESTRO, MAESTRO, MAESTRO, MAESTRO,
            TOCCATA, TOCCATA, TOCCATA,
            MAESTRO, MAESTRO, MAESTRO,
            MAESTRO, MAESTRO, MAESTRO,
            MAESTRO, MAESTRO, MAESTRO, TOCCATA, TOCCATA,
            TOCCATA, TOCCATA
    };

    public static final String[] musicNotationContent = new String[]{
            "p", "P", "f", "F",
            "c", "d", "e",
            "Z", "S", ">",
            "b", "n", "#",
            "U", "%", "\"", "f", "j",
            "U", "X"
    };
}
