package image;

import java.awt.Color;

/**
 * A utility class to divide an image into smaller squares.
 */
public class ImageSubDivider {

    /**
     * Divides the original image into squares of a specified size.
     *
     * @param originalImage The original image to be divided.
     * @param squareSize    The size of each square.
     * @return A 2D array of Image objects representing the divided image squares.
     */
    public static Image[][] divideImageToSquares(Image originalImage, int squareSize) {
        int rows = originalImage.getHeight() / squareSize;
        int cols = originalImage.getWidth() / squareSize;

        Image[][] imageSquares = new Image[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                imageSquares[row][col] = createSubImage(originalImage,
                        squareSize,
                        row * squareSize,
                        col * squareSize);
            }
        }

        return imageSquares;
    }

    /**
     * Creates a sub-image from the original image based on the specified
     * parameters.
     *
     * @param originalImage The original image from which the sub-image is created.
     * @param squareSize    The size of the square sub-image.
     * @param startingRow   The starting row of the sub-image in the original image.
     * @param startingCol   The starting column of the sub-image in the original
     *                      image.
     * @return The sub-image.
     */
    private static Image createSubImage(Image originalImage,
            int squareSize,
            int startingRow,
            int startingCol) {
        Color[][] subImageData = new Color[squareSize][squareSize];
        for (int row = 0; row < squareSize; row++) {
            for (int col = 0; col < squareSize; col++) {
                subImageData[row][col] = originalImage.getPixel(row + startingRow, col + startingCol);
            }
        }

        return new Image(subImageData, squareSize, squareSize);
    }
}
