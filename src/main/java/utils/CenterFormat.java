package utils;

public class CenterFormat {
    public static String center(String text, int width) {
        if (text == null) text = "";
        if (text.length() >= width) return text;

        int padding = width - text.length();
        int left = padding / 2;
        int right = padding - left;

        return " ".repeat(left) + text + " ".repeat(right);
    }
}
