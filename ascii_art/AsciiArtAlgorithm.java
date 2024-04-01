package ascii_art;

import image.Image;
import image.ImageBrightnessCalculator;
import image.ImagePadder;
import image.ImageSubDivider;
import image_char_matching.SubImgCharMatcher;

/**
 * This class implements an algorithm to generate ASCII art from an image.
 */
public class AsciiArtAlgorithm {

    private Image image;
    private int resolution;
    private SubImgCharMatcher subImgCharMatcher;
    private double[][] subImagesBrightness;

    /**
     * Constructs an AsciiArtAlgorithm object.
     *
     * @param image             The input image for generating ASCII art.
     * @param resolution        The resolution of the ASCII art (the number of
     *                          characters per row).
     * @param subImgCharMatcher The character matcher based on image brightness.
     */
    public AsciiArtAlgorithm(Image image, int resolution, SubImgCharMatcher subImgCharMatcher) {
        this.image = image;
        this.subImgCharMatcher = subImgCharMatcher;
        this.resolution = resolution;
    }

    /**
     * Generates ASCII art from the input image.
     *
     * @return A 2D char array representing the ASCII art.
     */
    public char[][] run() {
        if (subImagesBrightness == null) {
            image = ImagePadder.padImageToNearestPowerOfTwo(image);
            // we will use Max to not divide by 0 if current image dimensions smaller than target resolution
            int rows = image.getHeight() / Math.max(1,(image.getHeight() / resolution)); 
            int cols = image.getWidth() /  Math.max(1,(image.getWidth() / resolution));
            Image[][] subImages = ImageSubDivider.divideImageToSquares(image,
                    Math.max(1,(image.getWidth() / resolution)));
            subImagesBrightness = new double[rows][cols];
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    subImagesBrightness[row][col] = ImageBrightnessCalculator
                            .calculateImageBrightness(subImages[row][col]);
                }
            }
        }

        int rows = image.getHeight() / Math.max(1,(image.getHeight() / resolution)); 
        int cols = image.getWidth() /  Math.max(1,(image.getWidth() / resolution));
        char[][] asciiData = new char[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                asciiData[row][col] = 
                    subImgCharMatcher.getCharByImageBrightness(subImagesBrightness[row][col]);
            }
        }
        return asciiData;
    }
}
