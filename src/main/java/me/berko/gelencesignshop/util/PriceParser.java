package me.berko.gelencesignshop.util;

public class PriceParser {

    public static double parsePrice(String input) throws NumberFormatException {
        input = input.trim().toUpperCase(); // handling upper/lower case

        double multiplier = 1.0;

        if (input.endsWith("K")) {
            multiplier = 1_000;
            input = input.substring(0, input.length() - 1);
        } else if (input.endsWith("M")) {
            multiplier = 1_000_000;
            input = input.substring(0, input.length() - 1);
        } else if (input.endsWith("B")) {
            multiplier = 1_000_000_000;
            input = input.substring(0, input.length() - 1);
        }

        double value = Double.parseDouble(input);

        if (multiplier == 1_000_000_000 && value > 999) {
            value = 999;
        }

        return value * multiplier;
    }

    public static String formatPrice(double value) {
        if (value >= 1_000_000_000) return String.format("%.1fB", value / 1_000_000_000);
        if (value >= 1_000_000) return String.format("%.1fM", value / 1_000_000);
        if (value >= 1_000) return String.format("%.1fK", value / 1_000);
        return String.format("%.1f", value);
    }
}
