import java.awt.Font;
import java.awt.Color;

public class Settings {
    public static final int TOTAL_BUTTONS = 9,
            FRAME_SIZE = 600,
            SLEEP_TIME = 2000,
            TEXT_FIELD_FONT_SIZE = 30,
            BUTTON_FONT_SIZE = 100;
    public static Color panel_background = new Color(50, 50, 50),
            text_field_background_color = new Color(25, 25, 25),
            upper_text_field_background_color = new Color(29, 31, 29),
            button_background_color = new Color(205, 205, 205),
            text_field_foreground_color = new Color(46, 230, 46),
            upper_text_field_foreground_color = new Color(46, 230, 46),
            button_green_color = text_field_foreground_color,
            button_red_color = new Color(255, 71, 71),
            button_blue_color = new Color(71, 145, 255);

    public static String TEXT_FIELD_FONT_NAME = "Ink Free",
            BUTTON_FONT_NAME = "MV Boli";

    public static Font buttonFont = new Font(
            BUTTON_FONT_NAME,
            Font.BOLD,
            BUTTON_FONT_SIZE);
    public static Font textFieldFont = new Font(
            TEXT_FIELD_FONT_NAME,
            Font.BOLD,
            TEXT_FIELD_FONT_SIZE);
    public static Font upperTextFieldFont = new Font(TEXT_FIELD_FONT_NAME, 
            Font.BOLD, 
            TEXT_FIELD_FONT_SIZE);
}
