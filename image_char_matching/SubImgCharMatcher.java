package image_char_matching;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * This class provides functionality to match characters to image brightness
 * levels.
 */
public class SubImgCharMatcher {
    private final TreeMap<Character, Double> charsBrightnessMap;
    private final TreeMap<Double, TreeSet<Character>> brightnessMap;
    private final TreeMap<Double, TreeSet<Character>> normalizedBrightnessMap;

    private double minBrightness;
    private double maxBrightness;
    private boolean shouldNormalizeMap;

    /**
     * Constructs a SubImgCharMatcher object with the given character set.
     * 
     * @param charset The character set to be used for matching.
     */
    public SubImgCharMatcher(char[] charset) {
        charsBrightnessMap = new TreeMap<>();
        brightnessMap = new TreeMap<>();
        normalizedBrightnessMap = new TreeMap<>();
        for (char character : charset) {
            double charBrightness = calculateCharBrightness(character);
            charsBrightnessMap.put(character, charBrightness);
            if (brightnessMap.get(charBrightness) == null){
                brightnessMap.put(charBrightness, new TreeSet<>(){});
            }
            brightnessMap.get(charBrightness).add(character);
        }
        shouldNormalizeMap = true;
    }

    /**
     * Gets the character associated with the provided image brightness.
     * 
     * @param brightness The brightness of the image.
     * @return The character matching the provided brightness.
     */
    public char getCharByImageBrightness(double brightness) {
        if (shouldNormalizeMap)
            normalizeBrightnessMap();

        Entry<Double, TreeSet<Character>> floorEntry = normalizedBrightnessMap.floorEntry(brightness);
        Entry<Double, TreeSet<Character>> ceilingEntry = normalizedBrightnessMap.ceilingEntry(brightness);
        return Math.abs(floorEntry.getKey() - brightness) <= Math.abs(ceilingEntry.getKey() - brightness)
                ? floorEntry.getValue().first()
                : ceilingEntry.getValue().first();
    }

    /**
     * Adds a character to the character set.
     * 
     * @param c The character to be added.
     */
    public void addChar(char c) {
        if (charsBrightnessMap.get(c) != null)
            return;
        double charBrightness = calculateCharBrightness(c);
        charsBrightnessMap.put(c, charBrightness);
        if (brightnessMap.get(charBrightness) == null){
            brightnessMap.put(charBrightness, new TreeSet<>(){});
        }
        brightnessMap.get(charBrightness).add(c);
        if (charBrightness < minBrightness || charBrightness > maxBrightness) {
            shouldNormalizeMap = true;
        }
    }

    /**
     * Removes a character from the character set.
     * 
     * @param c The character to be removed.
     */
    public void removeChar(char c) {
        if (charsBrightnessMap.get(c) == null)
            return;
        double charBrightness = charsBrightnessMap.get(c);
        charsBrightnessMap.remove(c);
        brightnessMap.get(charBrightness).remove(c);
        if (brightnessMap.get(charBrightness).isEmpty())
            brightnessMap.remove(charBrightness);
        shouldNormalizeMap = true;
    }

    /**
     * Retuen the charSet that will be used during the AsciiArt algo
     * @return
     */
    public Collection<Character> getCharSet() {
        return charsBrightnessMap.keySet();
    }

    // Private methods

    private void normalizeBrightnessMap() {
        minBrightness = brightnessMap.firstKey();
        maxBrightness = brightnessMap.lastKey();

        normalizedBrightnessMap.clear();
        for (Entry<Double, TreeSet<Character>> entry : brightnessMap.entrySet()) {
            double normalizedVal = (entry.getKey() - minBrightness) / (maxBrightness - minBrightness);
            normalizedBrightnessMap.put(normalizedVal, entry.getValue());
        }

        shouldNormalizeMap = false;
    }

    private double calculateCharBrightness(char character) {
        return calculateBrightnessFromBoolArray(CharConverter.convertToBoolArray(character));
    }

    private double calculateBrightnessFromBoolArray(boolean[][] charBoolArray) {
        double trueCounter = 0;
        int rows = charBoolArray.length;
        int cols = charBoolArray[0].length;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                trueCounter += charBoolArray[row][col] ? 1 : 0;
            }
        }
        return trueCounter / (rows * cols);
    }
}
