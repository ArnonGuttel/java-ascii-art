package image;

import java.awt.Color;

/**
 * A utility class for calculating the brightness of an image.
 */
public class ImageBrightnessCalculator {
    private static final double MAX_GRAY_VAL = 255.0;
    private static final double RED_FACTOR = 0.2126;
    private static final double GREEN_FACTOR = 0.7152;
    private static final double BLUE_FACTOR = 0.0722;

    /**
     * Calculates the brightness of an image.
     *
     * @param image The image for which brightness is to be calculated.
     * @return The brightness value of the image.
     */
    public static double calculateImageBrightness(Image image) {
        double pictureGraySum = 0;
        for (int row = 0; row < image.getWidth(); row++) {
            for (int col = 0; col < image.getHeight(); col++) {
                Color pixelColor = image.getPixel(row, col);
                pictureGraySum += pixelColor.getRed() * RED_FACTOR +
                        pixelColor.getGreen() * GREEN_FACTOR +
                        pixelColor.getBlue() * BLUE_FACTOR;
            }
        }
        return pictureGraySum / (image.getHeight() * image.getWidth() * MAX_GRAY_VAL);
    }
}
