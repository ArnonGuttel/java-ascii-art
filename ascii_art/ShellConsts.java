package ascii_art;

/**
 * conts related to the shell CLI 
 */
public class ShellConsts {

    /** The regex that will be used for splitting input to commands */
    public final static String WHITESPACE_SPLIT_REGEX = "\\s+";

    /** The cli prefix for user input */
    public final static String INPUT_PREFIX = ">>> ";

    // CLI commands
    /** Command: Add character to ASCII character list */
    public final static String ADD_CHAR_TO_ASCII_MATCHER = "add";
    /** Command: Exit the program */
    public final static String EXIT_PROGRAM = "exit";
    /** Command: Print ASCII characters */
    public final static String PRINT_ASCII_CHARS = "chars";
    /** Command: Remove character from ASCII character list */
    public final static String REMOVE_CHAR_FROM_ASCII_MATCHER = "remove";
    /** Command: Modify resolution */
    public final static String MODIFY_RESOLUTION = "res";
    /** Command: Change image */
    public final static String CHANGE_IMAGE = "image";
    /** Command: Change output method */
    public final static String CHANGE_OUTPUT = "output";
    /** Command: Run ASCII art algorithm */
    public final static String RUN_ASCII_ART_ALGO = "asciiArt";

    // chars command
    /** The format for chars operation prints */
    public final static String ASCII_CHARS_PRINT_FORMAT = "%c ";

    // CLI ASCII matcher modifiers
    /** Modifier: Apply operation to all characters */
    public final static String ASCII_MATCHER_MODIFY_ALL = "all";
    /** Modifier: Apply operation to space character */
    public final static String ASCII_MATCHER_MODIFY_SPACE = "space";
    /** Modifier: Dash character used in modifier ranges */
    public final static char ASCII_MATCHER_DASH = '-';

    // Resolution modification
    /** Resolution modifier: Double the resolution */
    public final static String RESOLUTION_DOUBLE_UP = "up";
    /** Resolution modifier: Halve the resolution */
    public final static String RESOLUTION_DOUBLE_DOWN = "down";
    /** Resolution modifier: the double factor */
    public static final int RESOLUTION_DOUBLE_FACTOR = 2;
    /** Resolution modifier:  the minimum resolution*/
    public static final int MINIMUM_RESOLUTION_THRESHOLD = 1;


    // Output options
    /** Output option: Output as HTML */
    public final static String OUTPUT_HTML = "html";
    /** Output option: Output to console */
    public final static String OUTPUT_CONSOLE = "console";

    // Default values
    /** Default image file path */
    public final static String DEFAULT_IMAGE_PATH = "cat.jpeg";
    /** Default output file name for HTML */
    public final static String DEFAULT_OUTPUT_FILENAME = "out.html";
    /** Default font for HTML output */
    public final static String DEFAULT_OUTPUT_FONT = "Courier New";

    // Indices and lengths
    /** Index of command parameters */
    public final static int COMMAND_PARAMETERS_INDEX = 1;
    /** Index of dash character in modifier ranges */
    public final static int MODIFIER_RANGE_DASH_INDEX = 1;
    /** Length of valid command parameters */
    public final static int VALID_COMMAND_PARAMETERS_LEN = 2;

    // Error messages
    /** Error message: Invalid add command format */
    public final static String INVALID_ADD_PARAMETERS_MSG = "Did not add due to incorrect format.";
    /** Error message: Invalid remove command format */
    public final static String INVALID_REMOVE_PARAMETERS_MSG = "Did not remove due to incorrect format.";
    /** Error message: Invalid command */
    public final static String INVALID_COMMAND_MSG = "Did not execute due to incorrect command.";
    /** Error message: Invalid resolution command format */
    public final static String INVALID_RESOLUTION_PARAMETERS = 
        "Did not change resolution due to incorrect format.";
    /** Error message: Resolution exceeds boundaries */
    public final static String RESOLUTION_EXCEED_BOUNDRAIES_MSG = 
        "Did not change resolution due to exceeding boundaries.";
    /** Error message: Change image fail */
    public final static String CHANGE_IMAGE_FAIL_MSG = "Did not execute due to problem with image file.";
    /** Error message: Change output fail */
    public final static String CHANGE_OUTPUT_FAIL_MSG = 
        "Did not change output method due to incorrect format.";
    /** Error message: Run algorithm fail */
    public final static String RUN_ALGO_FAIL_MSG = "Did not execute. Charset is empty.";
    /** Resolution change message */
    public final static String RESOLUTION_CHANGE_MSG = "Resolution set to %d.";
}
