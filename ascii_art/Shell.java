package ascii_art;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;
import image_char_matching.SubImgCharMatcher;

/**
* This class represents a shell for interacting with an ASCII art generation program.
* It allows users to modify parameters, input images, and output formats, 
* and run the ASCII art generation algorithm.
*/
public class Shell {
    private SubImgCharMatcher subImgCharMatcher;
    private int resolution;
    private Image image;
    private AsciiOutput asciiOutput;
    private AsciiArtAlgorithm asciiArtAlgo;
    private Image prevAlgoRunImage;
    private int prevAlgoRunResolution;

    /** Constructor initializes default values */
    public Shell() {
        subImgCharMatcher = new SubImgCharMatcher(AsciiConsts.DEFAULT_CHAR_SET);
        resolution = AsciiConsts.DEFAULT_RESOLUTION;
        changeImage(ShellConsts.DEFAULT_IMAGE_PATH);
        asciiOutput = new ConsoleAsciiOutput();
    }

    /** main class function - will run the cli until user leave the program */
    public void run() {
        while (true) {
            System.out.print(ShellConsts.INPUT_PREFIX);

            String input = KeyboardInput.readLine();
            List<String> commands = parseInputToCommands(input);
            String operation = extractOperationFromCommand(commands);

            try {
                switch (operation) {
                    case ShellConsts.EXIT_PROGRAM:
                        return;
                    case ShellConsts.PRINT_ASCII_CHARS:
                        printAsciiCharSet();
                        break;
                    case ShellConsts.ADD_CHAR_TO_ASCII_MATCHER:
                    case ShellConsts.REMOVE_CHAR_FROM_ASCII_MATCHER:
                        modifyAsciiMatcher(operation, extractParametersFromCommand(commands));
                        break;
                    case ShellConsts.MODIFY_RESOLUTION:
                        modifyResolution(extractParametersFromCommand(commands));
                        break;
                    case ShellConsts.CHANGE_IMAGE:
                        changeImage(extractParametersFromCommand(commands));
                        break;
                    case ShellConsts.CHANGE_OUTPUT:
                        changeOutput(extractParametersFromCommand(commands));
                        break;
                    case ShellConsts.RUN_ASCII_ART_ALGO:
                        runAsciiArtAlgo();
                        break;
                    default:
                        System.out.println(ShellConsts.INVALID_COMMAND_MSG);
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // Method to execute the ASCII art generation algorithm
    private void runAsciiArtAlgo() throws IllegalArgumentException {
        if (subImgCharMatcher.getCharSet().isEmpty()) {
            throw new IllegalArgumentException(ShellConsts.RUN_ALGO_FAIL_MSG);
        }
        if (image != prevAlgoRunImage || resolution != prevAlgoRunResolution)
            asciiArtAlgo = new AsciiArtAlgorithm(image, resolution, subImgCharMatcher);
        asciiOutput.out(asciiArtAlgo.run());
        prevAlgoRunImage = image;
        prevAlgoRunResolution = resolution;
    }

    // Method to change the output format
    private void changeOutput(String parameters) throws IllegalArgumentException {
        switch (parameters) {
            case ShellConsts.OUTPUT_HTML:
                asciiOutput = new HtmlAsciiOutput(ShellConsts.DEFAULT_OUTPUT_FILENAME,
                                                  ShellConsts.DEFAULT_OUTPUT_FONT);
                break;
            case ShellConsts.OUTPUT_CONSOLE:
                asciiOutput = new ConsoleAsciiOutput();
                break;
            default:
                throw new IllegalArgumentException(ShellConsts.CHANGE_OUTPUT_FAIL_MSG);
        }
    }

    // Method to change the input image
    private void changeImage(String imagePath) {
        try {
            image = new Image(imagePath);
        } catch (IOException e) {
            System.out.println(ShellConsts.CHANGE_IMAGE_FAIL_MSG);
        }
    }

    // Method to modify the resolution
    private void modifyResolution(String parameters) throws IllegalArgumentException {
        switch (parameters) {
            case ShellConsts.RESOLUTION_DOUBLE_UP:
                if (resolution * ShellConsts.RESOLUTION_DOUBLE_FACTOR > image.getWidth()) {
                    System.out.println(ShellConsts.RESOLUTION_EXCEED_BOUNDRAIES_MSG);
                    return;
                }
                resolution *= ShellConsts.RESOLUTION_DOUBLE_FACTOR;
                System.out.println(String.format(ShellConsts.RESOLUTION_CHANGE_MSG, resolution));
                break;
            case ShellConsts.RESOLUTION_DOUBLE_DOWN:
                if (resolution / ShellConsts.RESOLUTION_DOUBLE_FACTOR < Math.max(
                        ShellConsts.MINIMUM_RESOLUTION_THRESHOLD,
                        image.getWidth() / image.getHeight())) {
                    System.out.println(ShellConsts.RESOLUTION_EXCEED_BOUNDRAIES_MSG);
                    return;
                }
                resolution /= ShellConsts.RESOLUTION_DOUBLE_FACTOR;
                System.out.println(String.format(ShellConsts.RESOLUTION_CHANGE_MSG, resolution));
                break;
            default:
                throw new IllegalArgumentException(ShellConsts.INVALID_RESOLUTION_PARAMETERS);
        }
    }

    // Method to add or remove characters from the ASCII matcher
    private void modifyAsciiMatcher(String operation, String parameters) {
        int startInd = 0, endInd = -1;

        if (parameters == "") {
            handleInvalidModifier(operation);
            return;
        }

        if (parameters.equals(ShellConsts.ASCII_MATCHER_MODIFY_ALL)) {
            startInd = AsciiConsts.ASCII_ART_FIRST_VAL;
            endInd = AsciiConsts.ASCII_ART_LAST_VAL;
        } else if (parameters.equals(ShellConsts.ASCII_MATCHER_MODIFY_SPACE)) {
            modifyAsciiMatcherCharacter(operation, AsciiConsts.SPACE_CHAR);
        } else if (parameters.length() == 1) /* single char condition */ {
            startInd = (char) parameters.charAt(0);
            endInd = startInd;
        } else if (parameters.charAt(ShellConsts.MODIFIER_RANGE_DASH_INDEX) == 
                ShellConsts.ASCII_MATCHER_DASH) {
            startInd = parameters.charAt(0);
            endInd = parameters.charAt(2);
            if (startInd > endInd) {
                int temp = startInd;
                startInd = endInd;
                endInd = temp;
            }
        } else {
            handleInvalidModifier(operation);
            return;
        }

        while (startInd <= endInd) {
            modifyAsciiMatcherCharacter(operation, (char) startInd++);
        }
    }

    // Method to add or remove a specific character from the ASCII matcher
    private void modifyAsciiMatcherCharacter(String operation, char character) {
        if (operation.equals(ShellConsts.ADD_CHAR_TO_ASCII_MATCHER)) {
            subImgCharMatcher.addChar(character);
        } else if (operation.equals(ShellConsts.REMOVE_CHAR_FROM_ASCII_MATCHER)) {
            subImgCharMatcher.removeChar(character);
        }
    }

    // Method to handle invalid modifier operations
    private void handleInvalidModifier(String operation) throws IllegalArgumentException {
        if (operation.equals(ShellConsts.ADD_CHAR_TO_ASCII_MATCHER))
            throw new IllegalArgumentException(ShellConsts.INVALID_ADD_PARAMETERS_MSG);
        else if (operation.equals(ShellConsts.REMOVE_CHAR_FROM_ASCII_MATCHER))
            throw new IllegalArgumentException(ShellConsts.INVALID_REMOVE_PARAMETERS_MSG);
    }

    // Method to print the current ASCII character set
    private void printAsciiCharSet() {
        for (Character c : subImgCharMatcher.getCharSet()) {
            System.out.printf(ShellConsts.ASCII_CHARS_PRINT_FORMAT, c);
        }

        System.out.println();
    }

    // Method to parse user input into commands
    private List<String> parseInputToCommands(String input) {
        return Arrays.asList(input.split(ShellConsts.WHITESPACE_SPLIT_REGEX));
    }

    // Method to extract the operation from a command
    private String extractOperationFromCommand(List<String> commands) {
        return commands.get(0);
    }

    // Method to extract parameters from a command
    private String extractParametersFromCommand(List<String> commands) {
        if (commands.size() != ShellConsts.VALID_COMMAND_PARAMETERS_LEN)
            return "";

        return commands.get(ShellConsts.COMMAND_PARAMETERS_INDEX);
    }

    /**
     * main progrmam function - will init the CLI
     * @param args
     */
    public static void main(String[] args) {
        new Shell().run();
    }
}
