package nz.archana.loandocs.service;

import nz.archana.loandocs.exception.MissingPlaceholderException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceholderFiller {

    private static final Pattern PLACEHOLDER = Pattern.compile("\\{\\{\\s*(\\w+)\\s*}}");

    private PlaceholderFiller() {
    }

    public static String fill(String template, Map<String, String> values) {
        Matcher matcher = PLACEHOLDER.matcher(template);
        StringBuilder result = new StringBuilder();
        Set<String> missing = new LinkedHashSet<>();

        while (matcher.find()) {
            String key = matcher.group(1);
            String value = values.get(key);
            if (value == null) {
                missing.add(key);
                continue;
            }
            matcher.appendReplacement(result, Matcher.quoteReplacement(value));
        }

        if (!missing.isEmpty()) {
            throw new MissingPlaceholderException(missing);
        }
        matcher.appendTail(result);
        return result.toString();
    }
}
