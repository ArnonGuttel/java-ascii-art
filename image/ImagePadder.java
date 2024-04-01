package image;

import java.awt.Color;

/**
 * A utility class for padding images.
 */
public class ImagePadder {

    /**
     * Pads the given image to the nearest power of two dimensions.
     *
     * @param image The original image to pad.
     * @return A new image padded to the nearest power of two dimensions.
     * @throws IllegalArgumentException if the input image is null.
     */
    public static Image padImageToNearestPowerOfTwo(Image image) {
        if (image == null) {
            throw new IllegalArgumentException("Input image cannot be null");
        }

        int paddedImageHeight = findNearestPowerOfTwo(image.getHeight());
        int paddedImageWidth = findNearestPowerOfTwo(image.getWidth());
        Color[][] paddedImageData = getPaddedImageData(image, paddedImageHeight, paddedImageWidth);
        return new Image(paddedImageData, paddedImageWidth, paddedImageHeight);
    }

    /**
     * Calculates the padded image data with white pixels for padding.
     *
     * @param originalImage     The original image.
     * @param paddedImageHeight The padded image height.
     * @param paddedImageWidth  The padded image width.
     * @return The padded image data.
     */
    private static Color[][] getPaddedImageData(Image orignalImage,
            int paddedImageHeight,
            int paddedImageWidth) {
        Color[][] paddedImageData = new Color[paddedImageHeight][paddedImageWidth];
        for (int row = 0; row < paddedImageHeight; row++) {
            for (int col = 0; col < paddedImageWidth; col++) {
                paddedImageData[row][col] = Color.WHITE;
            }
        }

        int heightDiff = paddedImageHeight - orignalImage.getHeight();
        int widthDiff = paddedImageHeight - orignalImage.getWidth();

        for (int row = 0; row < orignalImage.getHeight(); row++) {
            for (int col = 0; col < orignalImage.getWidth(); col++) {
                paddedImageData[row + (heightDiff / 2)][col + (widthDiff / 2)] = 
                    orignalImage.getPixel(row, col);
            }
        }

        return paddedImageData;
    }

    /**
     * Finds the nearest power of two for a given value.
     */
    private static int findNearestPowerOfTwo(int val) {
        return (int) Math.pow(2, Math.ceil(log2(val)));
    }

    /**
     * Calculates the logarithm base 2 of an integer.
     */
    private static double log2(int n) {
        return Math.log(n) / Math.log(2);
    }

}
